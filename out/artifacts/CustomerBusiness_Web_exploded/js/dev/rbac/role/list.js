/**
 * Created by hegc on 2016/4/3.
 * 角色分页查询
 */

var roleSearchApp = roleSearchApp || {};
roleSearchApp.model = roleSearchApp.model || {};
roleSearchApp.view = roleSearchApp.view || {};
roleSearchApp.instance = roleSearchApp.instance || {};


$(function(){
    //分页模型
    roleSearchApp.model.pageMode = Backbone.Model.extend({
        url : window.ctx + "/api/v1/role/query",
        defaults : {
            totalCount : 0,
            totalPages : 0,
            pageNo : 1,
            pageSize : 10,
            result : [],
            hasNext : false,
            hasPre : false,
            prePage : 0,
            nextPage : 0
        }
    });

    //入口视图
    roleSearchApp.view.executeView = Backbone.View.extend({
        el : "#formDiv",

        events : {

            "click #searchBtn"  : "search",   //搜索
            "click #addBtn" : "add"  //新增用户
        },

        initialize : function() {
            $(":text:eq(0)").focus();
            this.setQueryParam();
        },

        search : function (){
            var pageSize = $("#psSelect").val();
            this.setQueryParam(1, pageSize);
        },

        add : function () {
            location.href = window.ctx + "/role/add";
        },

        setQueryParam : function(pageNo, pageSize) {
            $("[name=pageNo]").val(pageNo ? pageNo : 1);
            $("[name=pageSize]").val(pageSize ? pageSize : 10);

            layer.load(0, {shade: [0.7, '#fff']});
            this.execute();
        },

        execute : function() {
            if(roleSearchApp.instance.pageView) {
                roleSearchApp.instance.pageView.initialize();
            } else {
                roleSearchApp.instance.pageView = new roleSearchApp.view.PageView();
            }
        }
    });

    //分页视图
    roleSearchApp.view.PageView = Backbone.View.extend({
        el : ".widget-list",

        events : {
          "change #psSelect" : "changePageSize",
          "click #jumpBtn" : "jumpPage",
          "click .firstPage"  : "goFirst",
          "click .prePage" : "goPre",
          "click .nextPage" : "goNext",
          "click .lastPage" : "goLast"
        },

        template : _.template($("#pageTmp").html()),

        model : new roleSearchApp.model.pageMode(),

        initialize : function () {
            var context = this;
            this.model.fetch({
                data : $("form").serializeArray(),
                success : function() {
                    context.render();
                },
                error : function() {
                    layer.alert('服务端异常！', {icon : 7});
                }
            })
        },

        render : function() {
            layer.closeAll();
            $(".widget-list").find("tbody").empty();
            var context = this;
            var results = this.model.get("result");
            if(_.isEmpty(results)) {
                $(context.el).find("tbody").append("<tr class='text-danger'><td colspan='5' align='center'>无数据存在！</td></tr>")
            } else {
                var no = this.model.get("first") - 1;
                _.each(results, function(value, index) {
                    no++;
                    value.no = no;
                    var trView = new roleSearchApp.view.trView({data : value});
                    $(this.el).find("tbody").append(trView.render().el);
                }, this);
            }
            //分页进度条
            $(this.el).find(".widget-bottom").html(context.template(this.model.toJSON()));
            $(this.el).find(".widget-bottom #psSelect").val(this.model.get("pageSize"));
        },

        //改变页码
        changePageSize : function(e) {
            var pageSize = $(e.currentTarget).val();
            roleSearchApp.instance.execute.setQueryParam(1, pageSize);
        },


        //页码跳转
        jumpPage : function() {
            var pageNo = $.trim($("#currentPage").val());
            var pageSize = $("#psSelect").val();
            if(pageNo) {
                try {
                    if(pageNo == parseInt(pageNo)) {
                        var totalPage = this.model.get("totalPages");
                        if(pageNo < 1) {
                            $("#currentPage").val(1);
                        } else if (pageNo > totalPage) {
                            $("#currentPage").val(1);
                        }
                    }
                } catch (e) {
                    //当转换失败时
                    $("#currentPage").val(1);
                }
                pageNo = $("#currentPage").val();
                roleSearchApp.instance.execute.setQueryParam(pageNo, pageSize);
            }
        },

        //第一页
        goFirst : function(){
            var pageSize = $("#psSelect").val();
            userSearchApp.instance.execute.setQueryParam(1, pageSize);
        },

        //上一页
        goPre : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            userSearchApp.instance.execute.setQueryParam(pageNo - 1, pageSize);
        },

        //下一页
        goNext : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            userSearchApp.instance.execute.setQueryParam(pageNo + 1, pageSize);
        },

        //尾页
        goLast : function() {
            var lastPage = this.model.get("totalPages");
            var pageSize = $("#psSelect").val();
            userSearchApp.instance.execute.setQueryParam(lastPage, pageSize);
        }
    });

    //每行数据展示
    roleSearchApp.view.trView = Backbone.View.extend({
        tagName : "tr",

        template : _.template($("#trTmp").html()),

        events : {
          "click .edit" : "edit",   //编辑
          "click .del" : "del"  //删除
        },


        initialize : function(option) {
            this.data = option.data;
        },

        render : function() {
            $(this.el).html(this.template(this.data));
            return this;
        },

        edit : function() {
            var id = this.data.id;
            location.href = window.ctx + '/role/edit/' + id;
        },

        del : function() {
            var context = this;
            /*$.dialog.confirm("确定删除角色" + context.data.name + "？", function(){
                $.get(window.ctx + "/api/v1/role/delete/" + context.data.id, function(result){
                   if(result) {
                       hintDialog("删除角色成功！");
                       roleSearchApp.instance.execute.setQueryParam(1);
                   }
                });
            }, function() {});*/
            layer.confirm("确定删除角色：" + context.data.name + "？",
                {icon: 7},
                function(){
                    $.get(window.ctx + "/api/v1/role/delete/" + context.data.id, function(result){
                        if(result) {
                            layer.msg("删除角色成功！", {time : 1000}, function(){
                                roleSearchApp.instance.execute.setQueryParam(1);
                            });
                        } else {
                            layer.msg("删除角色失败！", {time : 1000});
                        }
                    });
                },
                function(index){
                    layer.close(index);
                });
        }
    });

    roleSearchApp.instance.execute = new roleSearchApp.view.executeView();
});