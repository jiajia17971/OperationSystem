<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>

<script type="text/javascript" language="javascript">
    $(function () {
        //父菜单节点监听
        $('body').on("click", "#nav a:not('.sub-menu a')", function () {
            var subMenu = $(this).siblings('ul');
            var li = $(this).parents('li');
            var chevron = $(this).find(".arrow");
            var subMenus = $('#nav li ul.sub-menu');
            if (li.hasClass('open')) {
                subMenus.slideUp();
                li.removeClass('open').removeClass('active');
                chevron.attr("class", "arrow icon-angle-left");
            } else {
                $(".arrow").attr("class", "arrow icon-angle-left");
                subMenus.slideUp();
                subMenu.slideDown();
                $('#nav li.open').removeClass('open');
                $("#nav li.active").removeClass('active');
                li.addClass('active').addClass('open');
                chevron.attr("class", "arrow icon-angle-down");
            }
        });

        //菜单超链接的事件监听
        $('body').on("click", "#nav .sub-menu a", function () {
            $("#nav .sub-menu a.active").removeClass("active");
            $(this).addClass("active");
        });

        var width = $(window).width() - $("#page-sidebar").width() - 30;
        $("#c_right").css("width", width + "px");

        $("#rightFrame").load(function () {
            try {
                var height = $(this).contents().find("body").height();
                if (!height) height = 540;
                $(this).height(height + 30);
            } catch (e) {

            }
        });

        //页面加载完成后的初始化
        function init(){
            if($(".sub-menu a").size() > 0) {
                var firstLink = $(".sub-menu a").eq(0);
                var href = firstLink.attr("href");
                firstLink.parents(".sub-menu").prev().trigger("click");
                firstLink.trigger("click");
                $("#rightFrame").attr("src", href);
            }
        }
        init();
    });

</script>
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="test">
            <c:forEach var="module" items="${modules}">
            <li class="layui-nav-item layui-nav-itemed">

                <a class="" href="javascript:;">${module.name}</a>
                <c:if test="${not empty module.childModules}">
                <dl class="layui-nav-child">
                    <c:forEach items="${module.childModules}" var="child">
                        <dd><a href="#" target="right_frame" href="${ctx}${child.url}">${child.name},${ctx}${child.url}</a></dd>
                    </c:forEach>
                    <%--<dd><a href="#" onclick="reloadPages1()">新建开库单</a></dd>
                    <dd><a href="javascript:;">开库单管理</a></dd>
                    <dd><a href="javascript:;">跨行办理30天</a></dd>--%>
                    <!--<dd><a href="">超链接</a></dd>-->
                </dl>
                </c:if>
            </li>
            </c:forEach>
            <%--<li class="layui-nav-item"><a href="">统计分析</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">日志管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">操作日志管理</a></dd>

                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">系统管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:; target='right_frame'">用户管理</a></dd>
                    <dd><a href="javascript:;">角色管理</a></dd>
                    <dd><a href="">系统模块管理</a></dd>
                </dl>
            </li>--%>
            <!--<li class="layui-nav-item"><a href="">云市场</a></li>
            <li class="layui-nav-item"><a href="">发布商品</a></li>-->
        </ul>
    </div>
</div>