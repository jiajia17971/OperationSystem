/**
 * Created by hegc on 2016/4/3.
 * 用户编辑js
 */
var userEditApp = userEditApp || {};
userEditApp.model = userEditApp.model || {};
userEditApp.view = userEditApp.view || {};

$(function() {

    // model
    userEditApp.model.userModel = Backbone.Model.extend({
       url : window.ctx + "/api/v1/user/get",
       defaults : {
           user : {
               id : '',
               loginName : '',
               userName : '',
               status : 0
           },
           roles : {
               id : '',
               name : '',
               check : false
           }
       }
    });

    //view
    userEditApp.view.executeView = Backbone.View.extend({
       el : "#formDiv",

       model : new userEditApp.model.userModel(),

       template : _.template($("#userTmp").html()),

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
                  //hintDialog("获取用户数据失败！");
                  layer.alert("获取用户数据失败！", {icon : 7});
              }
           });
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
                   "loginName" : {
                       required : true,
                       rangelength : [3, 20],
                       alnum : true,
                       remote : {
                           url : window.ctx + "/api/v1/user/checkLoginName",
                           type : "post",
                           dataType : "json",
                           data : {
                               id : function() {
                                   return $("[name=id]").val();
                               }
                           }
                       }
                   },
                   "userName" : {
                       required : true
                   },
                   "password" : {
                       required : true,
                       rangelength : [3, 20]
                   },
                   "repassword" : {
                       required : true,
                       equalTo : "#password"
                   }
               },

               messages : {
                   loginName : {
                       remote : '此用户名已存在。'
                   }
               },

               submitHandler : function() {
                   var param = {id : $("[name=id]").val()};
                   $(context.el).find("form").find(":text").not(":password").each(function(){
                       param[$(this).attr("name")] = $.trim($(this).val());
                   });
                   param["password"] = $("[name=password]").val();
                   param["status"] = $("[name=status]:checked").val();
                   var roleIds = [];
                   $(context.el).find("form").find("[name=roleId]:checked").each(function(){
                      roleIds.push({"id" : $(this).val()});
                   });
                   param["roles"] = roleIds;

                   $.ajax({
                       type : "post",
                       url : window.ctx + "/api/v1/user/save",
                       dataType : "json",
                       contentType : "application/json",
                       data : JSON.stringify(param),
                       success:function(data){
                           if(data) {
                              /* $.dialog.alert("保存用户数据成功！",  function(){
                                   location.href = window.ctx + "/user/list"
                               });*/
                               layer.alert("保存用户数据成功！",{icon: 1}, function(){
                                   location.href = window.ctx + "/user/list";
                               });
                           } else {
                               //hintDialog("保存用户数据失败！");
                               layer.msg("保存用户数据失败！")
                           }
                       },
                       error : function(data) {
                           //hintDialog("保存用户数据失败！");
                           layer.msg("保存用户数据失败！")
                       }
                   });
                   return;
               }
           });
           $(context.el).find("form").submit();
       },

       back : function() {
           location.href = window.ctx + "/user/list";
       }
    });

    new userEditApp.view.executeView();
});
