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
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">
    <script type="text/javascript" src="${ctx}/js/dev/rbac/user/list.js"></script>
</head>
<body>
<div>
    <ul class="breadcrumb">
        <li class="icon"></li>
        <li>您所在的位置：</li>
        <li>系统管理</li>
        <li>用户管理</li>
    </ul>
    <div class="widget widget-table">
        <div class="widget-content" id="formDiv">
            <form>
                <input type="hidden" name="pageNo"/>
                <input type="hidden" name="pageSize"/>
                <table class="pn-ftable" border="0" cellpadding="10">
                    <tbody>
                        <tr>
                            <th>用户名：</th>
                            <td class="pn-fcontent"><input name="loginName" maxlength="20" type="text" class="form-control"/></td>
                            <th>姓名：</th>
                            <td class="pn-fcontent"><input name="userName" maxlength="20" type="text" class="form-control"/></td>
                        </tr>
                        <tr>
                            <th>状态:</th>
                            <td class="pn-fcontent">
                                <select name="status" class="form-control">
                                    <option value="">全部</option>
                                    <option value="1">锁定</option>
                                    <option value="0">未锁定</option>
                                </select>
                            </td>
                            <td colspan="2"/>
                        </tr>
                    </tbody>
                </table>
                <div class="widget-bottom">
                    <a class="btn btn-s-md btn-primary pull-right" id="searchBtn"
                       href="javascript:;">搜索</a>
                    <a class="btn btn-s-md pull-right" id="addBtn" href="javascript:;">添加</a>
                </div>
            </form>
        </div>
    </div>
    <div class="separator line"></div>
    <div class="widget widget-table">
        <div class="widget-header">
            <i class="icon-th-list"></i>
            <h5>用户列表</h5>
        </div>
        <div class="widget-content widget-list">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>用户名</th>
                    <th>姓名</th>
                    <th>状态</th>
                    <th>更新时间</th>
                    <th>最后登录时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <div class="widget-bottom">
            </div>
        </div>
    </div>
</div>
</body>
</html>

<!-- 每行模版 -->
<script type="text/template" id="trTmp">
    <td><@=no@></td>
    <td><@=loginName@></td>
    <td><@=userName@></td>
    <td><@=(status==1) ? '锁定' : '未锁定' @></td>
    <td><@=moment(updateTime).format('YYYY-MM-DD HH:mm:ss')@></td>
    <td>
        <@ if(lastLoginTime) {
        @>
            <@=moment(lastLoginTime).format('YYYY-MM-DD HH:mm:ss')@>
        <@
            }
        @>
    </td>
    <td>
        <a class="btn btn-info edit" href="javascript:;"><span class="glyphicon glyphicon-pencil"></span> 编辑</a>
        <a class="btn btn-warning del" href="javascript:;"><span class="glyphicon glyphicon-trash"></span> 删除</a>
    </td>
</script>


<!-- 分页模版 -->
<%@ include file="/common/page.jsp" %>