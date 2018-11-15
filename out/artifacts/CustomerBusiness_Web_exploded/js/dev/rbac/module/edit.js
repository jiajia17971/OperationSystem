/**
 * Created by hegc on 2016/4/7.
 * 模块修改js
 */
var moduleEditApp = moduleEditApp || {};
moduleEditApp.model = moduleEditApp.model || {};
moduleEditApp.view = moduleEditApp.view || {};
moduleEditApp.instance = moduleEditApp.instance || {};
$(function(){

    /**
     * 模型
     * @type {void|*}
     */
    moduleEditApp.model.module = Backbone.Model.extend({
       defaults :{
           id : '',
           name : '',
           url : '',
           imageName : '',
           parentId : '',
           status : 1,
           sortNo : 1,
           levels : 1
       },

       setUrl : function(url) {
           this.url =  url;
       }
    });

    /**
     * 主试图
     * @type {void|*}
     */
    moduleEditApp.view.executeView = Backbone.View.extend({
        el : '#formDiv',

        events : {
            "click #addTopBtn,#addChildBtn" : "add" //添加父子模块按钮
        },

        initialize : function(){
            moduleEditApp.instance.topModules = [];
            //全局下拉框变量
            var context = this;
            $.get(window.ctx + "/api/v1/module/getAllTree", function(data){
                _.each(data, function(d, index){
                    if(!d.parentId) {
                        var obj = {};
                        obj.id = d.id;
                        obj.name = d.name;
                        moduleEditApp.instance.topModules.push(obj);
                    }
                });
                context.treeData = data;
                context.render();
            }, "json");
        },

        //添加模块
        add : function(e) {
            if(moduleEditApp.instance.treeInstance) {
                moduleEditApp.instance.treeInstance.cancelSelectedNode();
            }
            var param = {id : ''};
            if($(e.currentTarget).is("#addTopBtn")) {
                //场景1: 添加顶层模块
                param.scene = 1;
                param.parentName = "根节点";
            } else if($(e.currentTarget).is("#addChildBtn")) {
                //场景1: 添加子模块
                param.scene = 2;
                param.parentName = '';
            }
            if(moduleEditApp.instance.tableInstance) {
                moduleEditApp.instance.tableInstance.initialize(param);
            } else {
                moduleEditApp.instance.tableInstance = new moduleEditApp.view.tableView(param);
            }
        },

        //加载树插件
        render : function() {
            var context = this;
            var setting = {
                data: {
                    simpleData: {
                        idKey: "id",
                        pIdKey: "parentId",
                        rootPid: null,
                        enable: true
                    }
                },
                callback: {
                    onClick: context.treeClick
                }
            };
            $.fn.zTree.init($(this.el).find("#treeUl"), setting, context.treeData);
            //定义tree的全局变量
            moduleEditApp.instance.treeInstance = $.fn.zTree.getZTreeObj("treeUl");

            $(this.el).find("#addTopBtn").trigger("click");
        },

        /**
         * 树点击事件
         * @param event
         * @param treeId
         * @param treeNode
         */
        treeClick : function(event, treeId, treeNode) {
            var treeId = treeNode.id;
            var parentNode = treeNode.getParentNode();
            if(!parentNode) {
                //场景3: 顶层模块修改
                moduleEditApp.instance.tableInstance.initialize({'id' : treeId, 'parentName' : '根节点', scene : 3});
            } else {
                //场景4: 子层模块修改
                moduleEditApp.instance.tableInstance.initialize({'id' : treeId, 'parentName' : parentNode.name, scene : 4});
            }

        }
    });

    /**
     * 右侧表格试图
     * @type {void|*}
     */
    moduleEditApp.view.tableView = Backbone.View.extend({
        el : '#contentDiv',

        template : _.template($("#moduleTmp").html()),

        events : {
            "click #addBtn,#updateBtn" : "save",  //保存
            "click #deleteBtn" : "delete",  //删除
            "click .dropdown-menu li" : "selectIcon"   //选择图标
        },

        initialize : function(option) {
            //这里要放在里面来
            this.model = new moduleEditApp.model.module();
            var context = this;
            this.scene = option.scene;
            this.parentName =  option.parentName;
            if(!option.id) {
                this.render();
            } else {
                var url = window.ctx + "/api/v1/module/get/" + option.id;
                this.model.setUrl(url);
                this.model.fetch({
                   success : function(){
                       context.render();
                   },
                   error : function() {
                       //hintDialog("获取模块数据错误");
                       layer.alert("获取模块数据错误！", {icon : 7});
                   }
                });
            }
        },

        /**
         * 渲染右侧表格
         */
        render : function() {
            var context = this;
            $(this.el).empty();

            var data = {};
            data.entity = context.model.toJSON();
            data.parentName = context.parentName;
            data.scene = context.scene;
            data.options = moduleEditApp.instance.topModules;

            if(context.scene == 1 || context.scene == 3) {
                var iconArray = "glass.music.search.envelope-alt.heart.star.star-empty.user.film.th-large.th.th-list.ok.remove.zoom-in.zoom-out.off.signal.cog.trash.home.file-alt.time.road.download-alt.download.upload.inbox.play-circle.repeat.refresh.list-alt.lock.flag.headphones.volume-off.volume-down.volume-up.qrcode.barcode.tag.tags.book.bookmark.print.camera.font.bold.italic.text-height.text-width.align-left.align-center.align-right.align-justify.list.indent-left.indent-right.facetime-video.picture.pencil.map-marker.adjust.tint.edit.share.check.move.step-backward.fast-backward.backward.play.pause.stop.forward.fast-forward.step-forward.eject.chevron-left.chevron-right.plus-sign.minus-sign.remove-sign.ok-sign.question-sign.info-sign.screenshot.remove-circle.ok-circle.ban-circle.arrow-left.arrow-right.arrow-up.arrow-down.share-alt.resize-full.resize-small.plus.minus.asterisk.exclamation-sign.gift.leaf.fire.eye-open.eye-close.warning-sign.plane.calendar.random.comment.magnet.chevron-up.chevron-down.retweet.shopping-cart.folder-close.folder-open.resize-vertical.resize-horizontal.bar-chart.twitter-sign.facebook-sign.camera-retro.key.cogs.comments.thumbs-up-alt.thumbs-down-alt.star-half.heart-empty.signout.linkedin-sign.pushpin.external-link.signin.trophy.github-sign.upload-alt.lemon.phone.check-empty.bookmark-empty.phone-sign.twitter.facebook.github.unlock.credit-card.rss.hdd.bullhorn.bell.certificate.hand-right.hand-left.hand-up.hand-down.circle-arrow-left.circle-arrow-right.circle-arrow-up.circle-arrow-down.globe.wrench.tasks.filter.briefcase.fullscreen.group.link.cloud.beaker.cut.copy.paper-clip.save.sign-blank.reorder.list-ul.list-ol.strikethrough.underline.table.magic.truck.pinterest.pinterest-sign.google-plus-sign.google-plus.money.caret-down.caret-up.caret-left.caret-right.columns.sort.sort-down.sort-up.envelope.linkedin.undo.legal.dashboard.comment-alt.comments-alt.bolt.sitemap.umbrella.paste.lightbulb.exchange.cloud-download.cloud-upload.user-md.stethoscope.suitcase.bell-alt.coffee.food.file-text-alt.building.hospital.ambulance.medkit.fighter-jet.beer.h-sign.plus-sign-alt.double-angle-left.double-angle-right.double-angle-up.double-angle-down.angle-left.angle-right.angle-up.angle-down.desktop.laptop.tablet.mobile-phone.circle-blank.quote-left.quote-right.spinner.circle.reply.github-alt.folder-close-alt.folder-open-alt.expand-alt.collapse-alt.smile.frown.meh.gamepad.keyboard.flag-alt.flag-checkered.terminal.code.reply-all.mail-reply-all.star-half-empty.location-arrow.crop.code-fork.unlink.question.info.exclamation.superscript.subscript.eraser.puzzle-piece.microphone.microphone-off.shield.calendar-empty.fire-extinguisher.rocket.maxcdn.chevron-sign-left.chevron-sign-right.chevron-sign-up.chevron-sign-down.html5.css3.anchor.unlock-alt.bullseye.ellipsis-horizontal.ellipsis-vertical.rss-sign.play-sign.ticket.minus-sign-alt.check-minus.level-up.level-down.check-sign.edit-sign.external-link-sign.share-sign.compass.collapse.collapse-top.expand.eur.gbp.usd.inr.jpy.cny.krw.btc.file.file-text.sort-by-alphabet.sort-by-alphabet-alt.sort-by-attributes.sort-by-attributes-alt.sort-by-order.sort-by-order-alt.thumbs-up.thumbs-down.youtube-sign.youtube.xing.xing-sign.youtube-play.dropbox.stackexchange.instagram.flickr.adn.bitbucket.bitbucket-sign.tumblr.tumblr-sign.long-arrow-down.long-arrow-up.long-arrow-left.long-arrow-right.apple.windows.android.linux.dribbble.skype.foursquare.trello.female.male.gittip.sun.moon.archive.bug.vk.weibo.renren"
                    .split(".");
                var html = "";
                for(var i = 0; i < iconArray.length; i++) {
                    html += "<li><i class='icon-" + iconArray[i]  + "'></i></li>";
                }
                data.html = html;
            }
            $(this.el).html(this.template(data));
            $(this.el).find(":text").eq(0).focus();
        },

        /**
         * 新增或者修改
         */
        save : function(e) {
            var context = this;
            var validate = $(context.el).find("form").validate({
                debug: false,
                rules: {
                    name : {
                        required : true,
                        remote : {
                            url : window.ctx + "/api/v1/module/checkName",
                            type : "get",
                            dataType : "json",
                            data : {
                                "id" : function(){
                                    //返回true表示没重复
                                    return $(context.el).find('[name="id"]').val();
                                }
                            }
                        }
                    },

                    pId : {
                        required : true
                    },

                    sortNo : {
                        required : true,
                        number : true
                    },

                    url : {
                        required : true
                    },

                    imageName : {
                        required :true
                    },

                    status : {
                        required : true
                    }
                },

                messages : {
                    name : {
                        remote : '此模块名称已存在。'
                    }
                },

                submitHandler : function() {
                    var params = $("form").serializeArray();
                    if($(context.el).find("[name=parentId]").size() == 0) {
                        var pIdObj = {name : 'parentId', value : $("[name=pId]").val()};
                        params.push(pIdObj);
                    }
                    $.post(window.ctx + "/api/v1/module/save", params, function(data){
                        if(data) {
                            /*$.dialog.alert("保存成功！", function(){
                                location.href = window.ctx + "/module/edit";
                            });*/
                            layer.alert("保存模块成功！",{icon: 1}, function(){
                                location.href = window.ctx + "/module/edit";
                            });
                        } else {
                            //hintDialog("保存失败！");
                            layer.msg("保存模块失败！");
                        }
                    });
                    return;
                }
            });

            $(this.el).find("form").submit();
        },

        /**
         * 删除
         */
        delete : function() {
            var id = $(this.el).find("[name=id]").val();
            var nodes = moduleEditApp.instance.treeInstance.getNodesByParam("parentId", id, null);
            if(_.isEmpty(nodes)) {
                $.get(window.ctx + "/api/v1/module/delete/" + id, function(result){
                    if(result) {
                       /* $.dialog.alert("删除模块成功！", function(){
                            location.href = window.ctx + "/module/edit";
                        });*/
                        layer.alert("删除模块成功！",{icon: 1}, function(){
                            location.href = window.ctx + "/module/edit";
                        });
                    }
                });
            } else {
                //hintDialog("此模块下存在子模块，不能删除");
                layer.msg("此模块下存在子模块，不能删除！");
            }
        },

        /**
         * 选择图标
         * @param e
         */
        selectIcon : function(e){
            var n = $(e.currentTarget).find("i").attr("class");
            $(this.el).find(".btn.dropdown-toggle i").attr("class", n);
            $(this.el).find("[name='imageName']").val(n);
        }
    });

    new moduleEditApp.view.executeView();
    
});