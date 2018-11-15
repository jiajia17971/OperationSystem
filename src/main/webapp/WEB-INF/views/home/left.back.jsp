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
<div id="side-nav">
    <ul id="nav">
        <c:forEach var="module" items="${modules}">
            <li>
                <a href="javascript:;">
                    <i class="${module.imageName}"></i> ${module.name}<i class="arrow icon-angle-left"></i>
                </a>
                <c:if test="${not empty module.childModules}">
                    <ul class="sub-menu">
                        <c:forEach items="${module.childModules}" var="child">
                            <li><a target="rightFrame" href="${ctx}${child.url}">${child.name}</a></li>
                        </c:forEach>
                    </ul>
                </c:if>
            </li>
        </c:forEach>
    </ul>
</div>