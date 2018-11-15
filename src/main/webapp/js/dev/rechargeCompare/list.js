/**
 * Created by Chosen on 2016/9/18.
 * 角色分页查询
 */

var rechargeCompareSearchApp = rechargeCompareSearchApp || {};
rechargeCompareSearchApp.model = rechargeCompareSearchApp.model || {};
rechargeCompareSearchApp.view = rechargeCompareSearchApp.view || {};
rechargeCompareSearchApp.instance = rechargeCompareSearchApp.instance || {};


$(function(){
    //分页模型
    rechargeCompareSearchApp.model.pageMode = Backbone.Model.extend({
        url : window.ctx + "/api/recharge/rechargeCompare/query",
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
    rechargeCompareSearchApp.view.executeView = Backbone.View.extend({
        el : "#formDiv",

        events : {

            "click #searchBtn"  : "search",   //查询
            "click #exportBtn" : "export"  //导出
        },

        initialize : function() {
            $(":text:eq(0)").focus();
            this.setQueryParam();
        },

        search : function (){
            var tradeTime = $("[name=tradeTime]").val();
            if(tradeTime.length==0){
                layer.msg("请输入交易日期！");
                return;
            }
            var pageSize = $("#psSelect").val();
            this.setQueryParam(1, pageSize);
        },

        export: function () {
            var tradeTime = $("[name=tradeTime]").val();
            if(tradeTime.length==0){
                layer.msg("请输入交易日期！");
                return;
            }

            location.href =  window.ctx + "/rechargeCompare/exportRechargeCompareRpt?" + $("#formDiv form").serialize();
        },

        setQueryParam : function(pageNo, pageSize) {
            $("[name=pageNo]").val(pageNo ? pageNo : 1);
            $("[name=pageSize]").val(pageSize ? pageSize : 10);
            //增加进度条
            layer.load(0, {shade: [0.7, '#fff']});
            this.execute();
        },

        execute : function() {
            // $(".widget-list").find("tbody").empty();
            if(rechargeCompareSearchApp.instance.pageView) {
                rechargeCompareSearchApp.instance.pageView.initialize();
            } else {
                rechargeCompareSearchApp.instance.pageView = new rechargeCompareSearchApp.view.PageView();
            }
        }
    });

    //分页视图
    rechargeCompareSearchApp.view.PageView = Backbone.View.extend({
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

        templateSum : _.template($("#sumTmp").html()),

        model : new rechargeCompareSearchApp.model.pageMode(),

        initialize : function () {
            var context = this;
            this.model.fetch({
                data : $("form").serializeArray(),
                success : function() {
                    context.render();
                }
            })
        },

        render : function() {
            $(".widget-list").find("tbody").empty();
            layer.closeAll();
            var context = this;
            var results = this.model.get("result");
            $("#ulTotle").empty();
            if(_.isEmpty(results)) {
                $(context.el).find("tbody").append("<tr class='text-danger'><td colspan='12' align='center'>无数据存在！</td></tr>")
            } else {
                var no = this.model.get("first") - 1;
                _.each(results, function(value, index) {
                    no++;
                    value.no = no;
                    var trView = new rechargeCompareSearchApp.view.trView({data : value});
                    $(this.el).find("tbody").append(trView.render().el);
                }, this);

                //显示统计结果
                var first = results[0].rechargeTotleInfo;
                $("#ulTotle").append(this.templateSum({entity : first}));
                $("#ulTotle").show();
            }
            //分页进度条
            $(this.el).find(".widget-bottom").html(context.template(this.model.toJSON()));
            $(this.el).find(".widget-bottom #psSelect").val(this.model.get("pageSize"));
        },

        //改变页码
        changePageSize : function(e) {
            var pageSize = $(e.currentTarget).val();
            rechargeCompareSearchApp.instance.execute.setQueryParam(1, pageSize);
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
                rechargeCompareSearchApp.instance.execute.setQueryParam(pageNo, pageSize);
            }
        },

        //第一页
        goFirst : function(){
            var pageSize = $("#psSelect").val();
            rechargeCompareSearchApp.instance.execute.setQueryParam(1, pageSize);
        },

        //上一页
        goPre : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            rechargeCompareSearchApp.instance.execute.setQueryParam(pageNo - 1, pageSize);
        },

        //下一页
        goNext : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            rechargeCompareSearchApp.instance.execute.setQueryParam(pageNo + 1, pageSize);
        },

        //尾页
        goLast : function() {
            var lastPage = this.model.get("totalPages");
            var pageSize = $("#psSelect").val();
            rechargeCompareSearchApp.instance.execute.setQueryParam(lastPage, pageSize);
        }
    });

    //每行数据展示
    rechargeCompareSearchApp.view.trView = Backbone.View.extend({
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

    rechargeCompareSearchApp.instance.execute = new rechargeCompareSearchApp.view.executeView();
});