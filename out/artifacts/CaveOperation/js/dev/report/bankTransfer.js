/**
 * Created by hegc on 2017/8/2.
 * 银行圈存信息报表
 */
var bankTransferApp = bankTransferApp || {};
bankTransferApp.model = bankTransferApp.model || {};
bankTransferApp.view = bankTransferApp.view || {};
bankTransferApp.instance = bankTransferApp.instance || {};

$(function () {

    var start = {
        elem: '#month',
        format: 'YYYY-MM',
        min:"1900-01",
        max: laydate.now(), //最大日期
        isclear: false, //是否显示清空
        istoday: false
    };
    laydate(start);

    //模型
    bankTransferApp.model.infoModel = Backbone.Model.extend({
        url : window.ctx + "/api/v1/bankTransfer/query",
        defaults: {
            time : "",
            results : []
        }
    });


    //入口视图
    bankTransferApp.view.executeView = Backbone.View.extend({
        el: "#formDiv",

        events: {
            "click #searchBtn": "search",      //查询
            "click #exportBtn": "export"        //导出
        },

        model : new bankTransferApp.model.infoModel(),

        initialize : function() {
            var context = this;
            layer.load(0, {shade: [0.7, '#fff']});
            this.model.fetch({
               data : $("form").serializeArray(),
               success : function() {
                   layer.closeAll();
                   context.render();
               },
               error : function() {
                   layer.alert('服务端异常!', {icon: 7});
               }
            });
        },

        render : function() {
            var data = this.model.toJSON();
            //渲染时间
            $(".dateScope").text(data.time);
            $(".widget-list").find("tbody").empty();
            var results = data.results;
            if(_.isEmpty(results)) {
                $(".widget-list").find("tbody").append("<tr class='text-danger'><td colspan='15' align='center'>无数据存在！</td></tr>")
            } else {
                _.each(results, function(result, index) {
                    var trView = new bankTransferApp.view.trView({data : result});
                    $(".widget-list").find("tbody").append(trView.render().el);
                }, this);
            }
        },

        //查询
        search : function() {
            if(this.valid()) {
                bankTransferApp.instance.execute.initialize();
            }
        },

        //导出
        export : function() {
            if(this.valid()) {
                location.href = window.ctx + "/bankTransfer/export?month=" + $("#month").val();
            }
        },

        //校验输入框是否为空
        valid : function() {
            if($("#month").val()) {
                return true;
            }
            layer.alert('请选择统计月份!', {icon: 7});
            return false;
        }
    });

    //每行数据展示
    bankTransferApp.view.trView = Backbone.View.extend({
        tagName: "tr",

        template: _.template($("#trTmp").html()),

        initialize : function(option) {
            this.data = option.data;
        },


        render: function () {
            $(this.el).html(this.template(this.data));
            return this;
        }
    });

    bankTransferApp.instance.execute = new bankTransferApp.view.executeView();
});

/**
 * 格式化金额
 * @param s
 * @param n 保留2位
 * @returns {string}
 */
function fmoney(s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}