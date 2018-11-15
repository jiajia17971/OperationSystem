/**
 * Created by hegc on 2016/9/21.
 * 参数编辑js
 */
var operateEditApp = operateEditApp || {};
operateEditApp.model = operateEditApp.model || {};
operateEditApp.view = operateEditApp.view || {};

$(function() {

    // model
    operateEditApp.model.operateModel = Backbone.Model.extend({
       url : window.ctx + "/api/v1/operate/get",
       defaults : {
           id : '',
           code : '',
           title : '',
           cardNo : '',
           system : 1,
           scene : 1,
           fee : 0,
           remark : ''
       }
    });

    //view
    operateEditApp.view.executeView = Backbone.View.extend({
       el : "#formDiv",

       model : new operateEditApp.model.operateModel(),

       events : {
           "click #saveBtn" : "save",   //保存
           "click #backBtn" : "back"    //返回
       },

       initialize : function() {
           var context = this;
           var id = $("form").find("[name=id]").val();
           if(id) {
               //如果存在id, 则是查看
               this.template = _.template($("#viewTmp").html());
               this.model.fetch({
                   data: {
                       id: id
                   },
                   success: function () {
                       context.render();
                   },
                   error: function () {
                       layer.alert("获取开库单数据失败！", {icon: 7});
                   }
               });
           } else {
               this.template = _.template($("#saveTmp").html());
               context.render();
           }
       },

       render : function() {
           $(this.el).find("form").append(this.template(this.model.toJSON()));
           $(":text:eq(0)").focus();
       },

       save : function() {
           var context = this;
           var validate = $(context.el).find("form").validate({
               debug : false,
               rules : {
                   "system" : {
                       required : true
                   },
                   "scene" : {
                       required : true
                   },
                   "code" : {
                       required : true,
                       remote : {
                           url : window.ctx + "/api/v1/operate/checkCode",
                           type : "post",
                           dataType : "json"
                       }
                   },
                   "title" : {
                       required : true
                   },
                   "cardNo" : {
                       required : true
                   },
                   "fee" : {
                       required : true,
                       digits : true
                   },
                   "remark" : {
                       required : true
                   },
                   "file" : {
                       required : true,
                       image : true
                   }
               },

               messages : {
                   code : {
                       remote : '此开库单编号已存在。'
                   }
               },

               submitHandler : function() {
                   $("#saveBtn").attr("disabled", true);
                   var param = {};
                   $(context.el).find("form").find(":text,textarea,select").each(function(){
                       param[$(this).attr("name")] = $.trim($(this).val());
                   });
                   $.ajaxFileUpload({
                       type : "post",
                       fileElementId : "file",
                       url : window.ctx + "/api/v1/operate/save",
                       secureuri: false,
                       data : param,
                       dataType : "text",
                       success:function(data){
                           $("#saveBtn").attr("disabled", false);
                           if(data.indexOf("true") != -1) {
                               layer.alert("保存开库单数据成功！",{icon: 1}, function(){
                                   location.href = window.ctx + "/operate/list";
                               });
                           } else {
                               layer.msg("保存开库单数据失败！")
                           }
                       },
                       error : function(data) {
                           $("#saveBtn").attr("disabled", false);
                           layer.msg("保存开库单数据失败！")
                       }
                   });
                   return;
               }
           });
           $(context.el).find("form").submit();
       },

       back : function() {
           location.href = window.ctx + "/operate/list";
       }
    });

    new operateEditApp.view.executeView();
});
