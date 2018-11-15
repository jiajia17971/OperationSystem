/**
 * Created by zwb on 2016/9/14.
 * 账户月报表查询
 */

var monthlySearchApp = monthlySearchApp || {};
monthlySearchApp.model = monthlySearchApp.model || {};
monthlySearchApp.view = monthlySearchApp.view || {};
monthlySearchApp.instance = monthlySearchApp.instance || {};


$(function(){
    //分页模型
    monthlySearchApp.model.pageMode = Backbone.Model.extend({
        url : window.ctx + "/api/v1/rpt/queryMonthly",
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
    monthlySearchApp.view.executeView = Backbone.View.extend({
        el : "#formDiv",

        events : {
            "click #searchBtn"  : "search",      //查询
            "click #cleanBtn" : "clean",          //清空
            "click #exportBtn" : "export"          //导出
        },

        initialize : function() {
            $(":text:eq(0)").focus();
            this.setQueryParam();
        },

        search : function (){
            var statisDate = $("#statisDate").val();
            $(".dateScope").html(statisDate);

            var pageSize = $("#psSelect").val();
            this.setQueryParam(1, pageSize);
        },
        clean : function () {
            $("#statisDate").val("");
            $(":text:eq(0)").focus();
        },
        export: function () {
            var statisDate = $("#statisDate").val();
            if (statisDate == "") {
                layer.alert('请选择导出的月份', {icon: 7});
                return;
            }
            location.href =  window.ctx + "/report/exportMonthlySummaryRpt?" + $("#formDiv form").serialize();
        },
        setQueryParam : function(pageNo, pageSize) {
            $("[name=pageNo]").val(pageNo ? pageNo : 1);
            $("[name=pageSize]").val(pageSize ? pageSize : 10);
            //增加进度条
            layer.load(0, {shade: [0.7, '#fff']});
            this.execute();
        },

        execute : function() {
            if(monthlySearchApp.instance.pageView) {
                monthlySearchApp.instance.pageView.initialize();
            } else {
                monthlySearchApp.instance.pageView = new monthlySearchApp.view.PageView();
            }
        }
    });

    //分页视图
    monthlySearchApp.view.PageView = Backbone.View.extend({
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

        model : new monthlySearchApp.model.pageMode(),

        initialize : function () {
            var context = this;
            this.model.fetch({
                data : $("form").serializeArray(),
                success : function() {
                    context.render();
                },
                error : function(model, response, options) {
                    layer.alert('服务端异常！', {icon: 7});
                }
            })
        },

        render : function() {
            $(".widget-list").find("tbody").empty();
            layer.closeAll();
            var context = this;
            var results = this.model.get("result");
            if(_.isEmpty(results)) {
                $(context.el).find("tbody").append("<tr class='text-danger'><td colspan='24' align='center'>无数据存在！</td></tr>")
            } else {
                var no = this.model.get("first") - 1;
                _.each(results, function(value, index) {
                    no++;
                    value.no = no;
                    var trView = new monthlySearchApp.view.trView({data : value});
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
            monthlySearchApp.instance.execute.setQueryParam(1, pageSize);
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
                monthlySearchApp.instance.execute.setQueryParam(pageNo, pageSize);
            }
        },

        //第一页
        goFirst : function(){
            var pageSize = $("#psSelect").val();
            monthlySearchApp.instance.execute.setQueryParam(1, pageSize);
        },

        //上一页
        goPre : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            monthlySearchApp.instance.execute.setQueryParam(pageNo - 1, pageSize);
        },

        //下一页
        goNext : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            monthlySearchApp.instance.execute.setQueryParam(pageNo + 1, pageSize);
        },

        //尾页
        goLast : function() {
            var lastPage = this.model.get("totalPages");
            var pageSize = $("#psSelect").val();
            monthlySearchApp.instance.execute.setQueryParam(lastPage, pageSize);
        }
    });

    //每行数据展示
    monthlySearchApp.view.trView = Backbone.View.extend({
        tagName : "tr",

        template : _.template($("#trTmp").html()),

        initialize : function(option) {
            this.data = option.data;
        },

        render : function() {
            $(this.el).html(this.template(this.data));
            return this;
        }
    });

    monthlySearchApp.instance.execute = new monthlySearchApp.view.executeView();
});