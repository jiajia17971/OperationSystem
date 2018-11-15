/**
 * Created by hegc on 2016/4/3.
 * 角色编辑js
 */
var roleEditApp = roleEditApp || {};
roleEditApp.model = roleEditApp.model || {};
roleEditApp.view = roleEditApp.view || {};

$(function() {

    // model
    roleEditApp.model.roleModel = Backbone.Model.extend({
       url : window.ctx + "/api/v1/role/get",
       defaults : {
           role : {
               id : '',
               name : '',
               status : 0,
               remark : ''
           },
           tree : []
       }
    });

    //view
    roleEditApp.view.executeView = Backbone.View.extend({
       el : "#formDiv",

       model : new roleEditApp.model.roleModel(),

       template : _.template($("#roleTmp").html()),

       events : {
           "click #saveBtn" : "save",
           "click #backBtn" : "back"
       },

       initialize : function() {
           var context = this;
           this.model.fetch({
              data : {
                  id : $("form").find("[name=id]").val()
              },
              success : function(){
                  context.render()
              },
              error : function() {
                  //hintDialog("获取角色数据失败！");
                  layer.alert("获取角色数据失败！", {icon : 7});
              }
           });
       },

       render : function() {
           $(this.el).find("form").append(this.template(this.model.toJSON()));
           $(":text:eq(0)").focus();

           this.renderTree();
       },


        //选人树
       renderTree : function() {
           var context = this;
           var setting = {
               data : {
                   simpleData : {
                       idKey : "id",
                       pIdKey : "parentId",
                       rootPid : null,
                       enable : true
                   }
               },
               check : {
                   enable : true
               }
           };
           $.fn.zTree.init($(this.el).find("#treeUl"), setting, context.model.get('tree'));
           //定义tree的全局变量
           var zTree = $.fn.zTree.getZTreeObj("treeUl");
           //展开全部节点
           zTree.expandAll(true);
           var modules = context.model.get("role").modules;
           if(!_.isEmpty(modules)) {
               _.each(modules, function(module, index){
                   var moduleId = module.id;
                   var nodes = zTree.getNodesByParam("id", moduleId, null);
                   _.each(nodes, function(node, i){
                        zTree.checkNode(node, true, false);
                   });

               });
           }
       },

        //获取选中的树数据
       getTreeSelectData : function() {
            var zTree = $.fn.zTree.getZTreeObj("treeUl");
            var modulesIds = [];
            var selectNodes = zTree.getCheckedNodes(true);
           _.each(selectNodes, function(node, index){
              var obj = {};
               obj.id = node.id;
               modulesIds.push(obj);
           });
           return modulesIds;
       },

        //保存
       save : function() {
           var context = this;
           var validate = $(context.el).find("form").validate({
               debug : false,
               rules : {
                   "name" : {
                       required : true,
                       rangelength : [3, 20],
                       remote : {
                           url : window.ctx + "/api/v1/role/checkRoleName",
                           type : "post",
                           dataType : "json",
                           data : {
                               id : function() {
                                   return $("[name=id]").val();
                               }
                           }
                       }
                   }
               },

               messages : {
                   name : {
                       remote : '此角色名已存在。'
                   }
               },

               submitHandler : function() {
                   var param = {id : $("[name=id]").val()};
                   param.name = $.trim($("[name=name]").val());
                   param.remark = $.trim($("[name=remark]").val());
                   param.status = $("[name=status]:checked").val();

                   param.modules = context.getTreeSelectData();

                   $.ajax({
                       type : "post",
                       url : window.ctx + "/api/v1/role/save",
                       dataType : "json",
                       contentType : "application/json",
                       data : JSON.stringify(param),
                       success:function(data){
                           if(data) {
                               //$.dialog.alert("保存角色数据成功！", function(){
                               //    location.href = window.ctx + "/role/list";
                               //});
                               layer.alert("保存角色数据成功！",{icon: 1}, function(){
                                   location.href = window.ctx + "/role/list";
                               });
                           } else {
                               //hintDialog("保存角色数据失败！");
                               layer.msg("保存角色数据失败！");
                           }
                       },
                       error : function(data) {
                           //hintDialog("保存角色数据失败！");
                           layer.msg("保存角色数据失败！");
                       }
                   });
                   return;
               }
           });
           $(context.el).find("form").submit();
       },

       back : function() {
           location.href = window.ctx + "/role/list";
       }
    });

    new roleEditApp.view.executeView();
});
