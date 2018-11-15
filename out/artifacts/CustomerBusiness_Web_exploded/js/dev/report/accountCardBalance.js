/**
 * Created by hegc on 2017/3/9.
 * 账余卡余统计报表
 */
var balanceApp = balanceApp || {};
balanceApp.model = balanceApp.model || {};
balanceApp.collection = balanceApp.collection || {};
balanceApp.view = balanceApp.view || {};
balanceApp.instance = balanceApp.instance || {};

$(function () {
    //分页模型
    balanceApp.model.infoModel = Backbone.Model.extend({
        defaults: {
            no : 0,
            orgCode : "",
            orgName : "",
            totalBalance : 0,
            accountBalance : 0,
            cardBalance : 0,
            lastTotalBalance : 0,
            lastAccountBalance : 0,
            lastCardBalance : 0
        }
    });

    balanceApp.collection.infoCollection = Backbone.Collection.extend({
       url : window.ctx + "/api/v1/accountCardBalance/info",
       model : balanceApp.model.infoModel
    });

    //入口视图
    balanceApp.view.executeView = Backbone.View.extend({
        el: "#formDiv",

        events: {
            "click #searchBtn": "search"      //查询
        },

        collection : new balanceApp.collection.infoCollection(),

        initialize : function() {
            var context = this;
            layer.load(0, {shade: [0.7, '#fff']});
            this.collection.fetch({
               success : function() {
                   context.render();
               },
               error : function() {
                   layer.alert('服务端异常!', {icon: 7});
               }
            });
        },

        render : function() {
            $(".widget-list").find("tbody").empty();
            var models = this.collection.models;
            layer.closeAll();
            if(_.isEmpty(models)) {
                $(".widget-list").find("tbody").append("<tr class='text-danger'><td colspan='8' align='center'>无数据存在！</td></tr>")
            } else {
                var no = 0;
                _.each(models, function(model, index) {
                    no++;
                    model.set("no", no);
                    var trView = new balanceApp.view.trView({model : model});
                    $(".widget-list").find("tbody").append(trView.render().el);
                }, this);
            }
        },

        //重新刷新页面
        search : function() {
            location.href = window.ctx + "/accountCardBalance/info";
        }
    });

    //每行数据展示
    balanceApp.view.trView = Backbone.View.extend({
        tagName: "tr",

        template: _.template($("#trTmp").html()),

        render: function () {
            $(this.el).html(this.template(this.model.toJSON()));
            return this;
        }
    });

    balanceApp.instance.execute = new balanceApp.view.executeView();
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