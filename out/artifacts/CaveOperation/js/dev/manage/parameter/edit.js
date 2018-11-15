/**
 * Created by hegc on 2016/9/21.
 * 参数编辑js
 */
var paramEditApp = paramEditApp || {};
paramEditApp.model = paramEditApp.model || {};
paramEditApp.view = paramEditApp.view || {};

$(function() {

    // model
    paramEditApp.model.paramModel = Backbone.Model.extend({
       url : window.ctx + "/api/v1/parameter/get",
       defaults : {
           id : '',
           code : '',
           name : '',
           value : '',
           status : 1,
           remark : ''
       }
    });

    //view
    paramEditApp.view.executeView = Backbone.View.extend({
       el : "#formDiv",

       model : new paramEditApp.model.paramModel(),

       template : _.template($("#paramTmp").html()),

       events : {
           "click #saveBtn" : "save",   //保存
           "click #backBtn" : "back"    //返回
       },

       initialize : function() {
           var context = this;
           var id = $("form").find("[name=id]").val();
           if(id) {
               this.model.fetch({
                   data: {
                       id: id
                   },
                   success: function () {
                       context.render();
                   },
                   error: function () {
                       layer.alert("获取参数数据失败！", {icon: 7});
                   }
               });
           } else {
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
                   "name" : {
                       required : true,
                       remote : {
                           url : window.ctx + "/api/v1/parameter/checkName",
                           type : "post",
                           dataType : "json",
                           data : {
                               id : function() {
                                   return $("[name=id]").val();
                               }
                           }
                       }
                   },
                   "value" : {
                       required : true
                   },
                   "status" : {
                       required : true
                   }
               },

               messages : {
                   name : {
                       remote : '此参数名已存在。'
                   }
               },

               submitHandler : function() {
                   $("#saveBtn").attr("disabled", true);
                   var param = {id : $("[name=id]").val()};
                   $(context.el).find("form").find(":text,textarea").each(function(){
                       param[$(this).attr("name")] = $.trim($(this).val());
                   });
                   param["status"] = $("[name=status]:checked").val();
                   $.ajax({
                       type : "post",
                       url : window.ctx + "/api/v1/parameter/save",
                       dataType : "json",
                       contentType : "application/json",
                       data : JSON.stringify(param),
                       success:function(data){
                           $("#saveBtn").attr("disabled", false);
                           if(data) {
                               var firstTr = $("form #paramNameTr");
                               firstTr.prev().remove();
                               $("form [name=id]").val(data.id);
                               var codeTr =  _.template($("#codeTmp").html())({code : data.code});
                               firstTr.before(codeTr);
                               layer.alert("保存参数数据成功！",{icon: 1}, function(){
                                   location.href = window.ctx + "/parameter/list";
                               });
                           } else {
                               layer.msg("保存参数数据失败！")
                           }
                       },
                       error : function(data) {
                           $("#saveBtn").attr("disabled", false);
                           layer.msg("保存参数数据失败！")
                       }
                   });
                   return;
               }
           });
           $(context.el).find("form").submit();
       },

       back : function() {
           location.href = window.ctx + "/parameter/list";
       }
    });

    new paramEditApp.view.executeView();
});
