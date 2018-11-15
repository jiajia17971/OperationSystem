<%--
  Created by IntelliJ IDEA.
  User: hegc
  Date: 2016/4/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/meta.jsp" %>
    <script type="text/javascript">
        $(window).resize(function () {
            var width = $(this).width() - 200;
            if (width < 768) width = 768;
            $(".page-content").css("width", (width - 10) + "px");
        });

        function setIFrameHeight() {
            var mainHeight = $("#rightFrame").contents().find("body").prop('scrollHeight');
            if (mainHeight < 580) {
                mainHeight = 580;
            }
            $("#rightFrame").height(mainHeight);
        }

        //点击效果
        function splitter(img) {
            var side = "right";
            if ($(".sidebar").css("left") == "0px") {
                $(".sidebar").animate({left: "-201px"}, 500);
                $(".page-content").animate({left: "0px", width: "96%"}, 500);
            }
            else {
                $(".sidebar").animate({left: "0px"}, 500);
                $(".page-content").animate({left: "200px", width: "85%"}, 500);
                side = "left";
            }
            img.attr("src", "${ctx}/images/mini-" + side + ".gif");
        }
    </script>
    <script type="text/javascript" src="${ctx}/js/lib/toTop.js"></script>
</head>
<body class="menu-left full-container">
<div class="navbar navbar-static-top" role="navigation">
    <div class="container">
        <%@include file="/WEB-INF/views/home/left.jsp" %>
    </div>
</div>
<div class="container relative main">
    <div class="sidebar">
        <img src="${ctx}/images/mini-left.gif" class="splitter" onclick="splitter($(this))"
             style="position:absolute; right:-11px; top:287px; cursor:pointer;"/>
        <%@include file="/WEB-INF/views/home/left.jsp" %>
    </div>
    <div class="page-content">
        <div class="content container" style="padding:5px;">
            <div class="row">
                <div class="col-lg-12">
                    <iframe id="rightFrame" name="rightFrame" onLoad="setIFrameHeight()" src="" width="100%"
                            height="100%"
                            frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto"/>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
