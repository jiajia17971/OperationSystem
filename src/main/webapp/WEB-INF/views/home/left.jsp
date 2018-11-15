<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>


<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree"  lay-filter="test">

            <c:forEach var="module" items="${modules}" varStatus="status">
                <c:if test='${status.count == 1}'>
                    <li class="layui-nav-item layui-nav-itemed">
                            <%--<i class="layui-icon layui-icon-form" style="font-size: 30px; color: #1E9FFF;"></i>--%>
                        <a class="" href="javascript:;">${module.name}</a>
                        <c:if test="${not empty module.childModules}">


                            <dl class="layui-nav-child">
                                <c:forEach items="${module.childModules}" var="child" varStatus="ch">
                                    <dd class="<c:if test='${ch.count == 2}'>layui-this</c:if>"><a  target="right_frame" href="${ctx}${child.url}">${child.name}</a></dd>
                                </c:forEach>

                            </dl>



                        </c:if>
                    </li>
                </c:if>
                <c:if test='${status.count != 1}'>
                    <li class="layui-nav-item">
                            <%--<i class="layui-icon layui-icon-form" style="font-size: 30px; color: #1E9FFF;"></i>--%>
                        <a class="" href="javascript:;">${module.name}</a>
                        <c:if test="${not empty module.childModules}">


                            <dl class="layui-nav-child">
                                <c:forEach items="${module.childModules}" var="child" varStatus="ch">
                                    <dd class=""><a  target="right_frame" href="${ctx}${child.url}">${child.name}</a></dd>
                                </c:forEach>

                            </dl>
                        </c:if>
                    </li>
                </c:if>
            </c:forEach>

        </ul>
    </div>
</div>