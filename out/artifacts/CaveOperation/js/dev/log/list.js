/**
 * Created by hegc on 2016/9/27.
 * 业务日志查询
 */

var logSearchApp = logSearchApp || {};
logSearchApp.model = logSearchApp.model || {};
logSearchApp.view = logSearchApp.view || {};
logSearchApp.instance = logSearchApp.instance || {};


$(function(){
    //分页模型
    logSearchApp.model.pageMode = Backbone.Model.extend({
        url : window.ctx + "/api/v1/log/query",
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
    logSearchApp.view.executeView = Backbone.View.extend({
        el : "#formDiv",

        events : {

            "click #searchBtn"  : "search",   //搜索
            "click #clearBtn" : "clear"  //清空
        },

        initialize : function() {
            $(":text:eq(0)").focus();
            this.setQueryParam();
        },

        search : function (){
            var pageSize = $("#psSelect").val();
            this.setQueryParam(1, pageSize);
        },

        clear : function () {
            $("form").find(":text, select").val('');
            start.max = laydate.now();
            end.min = start.min;
        },

        setQueryParam : function(pageNo, pageSize) {

            var startTime = $("form").find("#startTime").val();
            var endTime = $("form").find("#endTime").val();

            if(!startTime || !endTime) {
                layer.msg("开始日期或结束日期不能为空！", {time : 1000});
                return;
            }

            $("[name=pageNo]").val(pageNo ? pageNo : 1);
            $("[name=pageSize]").val(pageSize ? pageSize : 10);
            layer.load(0, {shade: [0.7, '#fff']});

            this.execute();
        },

        execute : function() {
            if(logSearchApp.instance.pageView) {
                logSearchApp.instance.pageView.initialize();
            } else {
                logSearchApp.instance.pageView = new logSearchApp.view.PageView();
            }
        }
    });

    //分页视图
    logSearchApp.view.PageView = Backbone.View.extend({
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

        model : new logSearchApp.model.pageMode(),

        initialize : function () {
            var context = this;
            this.model.fetch({
                data : $("form").serializeArray(),
                success : function() {
                    context.render();
                },
                error : function(model, response, options) {
                    layer.closeAll();
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
                $(context.el).find("tbody").append("<tr class='text-danger'><td colspan='10' align='center'>无数据存在！</td></tr>")
            } else {
                var dict = {"0" : "总账", "1" : "子账", "2" : "卡账"};
                var no = this.model.get("first") - 1;
                _.each(results, function(value, index) {
                    no++;
                    value.no = no;
                    value.accName = dict[value.accType];
                    var trView = new logSearchApp.view.trView({data : value});
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
            logSearchApp.instance.execute.setQueryParam(1, pageSize);
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
                logSearchApp.instance.execute.setQueryParam(pageNo, pageSize);
            }
        },

        //第一页
        goFirst : function(){
            var pageSize = $("#psSelect").val();
            logSearchApp.instance.execute.setQueryParam(1, pageSize);
        },

        //上一页
        goPre : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            logSearchApp.instance.execute.setQueryParam(pageNo - 1, pageSize);
        },

        //下一页
        goNext : function() {
            var pageNo = this.model.get("pageNo");
            var pageSize = $("#psSelect").val();
            logSearchApp.instance.execute.setQueryParam(pageNo + 1, pageSize);
        },

        //尾页
        goLast : function() {
            var lastPage = this.model.get("totalPages");
            var pageSize = $("#psSelect").val();
            logSearchApp.instance.execute.setQueryParam(lastPage, pageSize);
        }
    });

    //每行数据展示
    logSearchApp.view.trView = Backbone.View.extend({
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

    logSearchApp.instance.execute = new logSearchApp.view.executeView();


    //初始化日期输入框
    var start = {
        elem : '#startTime',
        format : 'YYYY-MM-DD',
        min : "1900-01-01",
        max : laydate.now(), //最大日期
        isclear : false, //是否显示清空
        istoday : false,
        choose : function(datas){
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };

    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD',
        min: start.min,
        max: laydate.now(),
        isclear: false, //是否显示清空
        istoday: false,
        choose: function(datas){
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
    laydate(start);
    laydate(end);
});