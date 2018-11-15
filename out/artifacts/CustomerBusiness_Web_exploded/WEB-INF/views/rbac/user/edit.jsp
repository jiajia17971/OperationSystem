<%--
  Created by IntelliJ IDEA.
  User: hegc
  Date: 2016/4/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/validate/validate.css">
    <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.common_rules.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/validate/messages_cn.js"></script>

    <script type="text/javascript" src="${ctx}/js/dev/rbac/user/edit.js"></script>
</head>
<body  style="min-height: 650px; overflow-y: auto;">
<div>
  <ul class="breadcrumb">
    <li class="icon"></li>
    <li>您所在的位置：</li>
    <li>系统管理</li>
    <li>用户管理</li>
    <li>用户${empty id ? "新增" : "修改"}</li>
  </ul>
  <div class="widget widget-table">
    <div class="widget-content" id="formDiv">
        <form>
          <input type="hidden" name="id" value="${id}"/>

        </form>
    </div>
  </div>
</div>
</body>
</html>

<!-- 用户模版 -->
<script type="text/template" id="userTmp">
  <div class="widget-content">
      <table class="pn-ftable table-bordered" border="0" cellpadding="10">
          <tbody>
            <@ if(user.id)
              {
            @>
                <tr>
                  <th>用户名：</th>
                  <td class="pn-fcontent form-inline"><@=user.loginName@></td>
                </tr>
            <@
              } else {
            @>
                <tr>
                  <th><span class="point">*</span>用户名：</th>
                  <td class="pn-fcontent form-inline">
                    <input type="text" name="loginName" class="form-control" value="<@=user.loginName@>" onblur="this.value=$.trim(this.value);" style="width:25%"/>
                  </td>
                </tr>
            <@
              }
            @>
            <tr>
              <th><span class="point">*</span>姓名：</th>
              <td class="pn-fcontent form-inline" width="30%">
                <input type="text" name="userName" class="form-control" value="<@=user.userName@>" maxlength="20" style="width:25%"/>
              </td>
            </tr>
            <tr>
              <th><span class="point">*</span>密码：</th>
              <td class="pn-fcontent form-inline">
                <input type="password" id="password" name="password" class="form-control" maxlength="20" style="width:25%"/>
            </tr>
            <tr>
              <th><span class="point">*</span>重复密码：</th>
              <td class="pn-fcontent form-inline">
                <input type="password" name="repassword" class="form-control" maxlength="20" style="width:25%"/>
              </td>
            </tr>
            <tr>
              <th><span class="point">*</span>状态：</th>
              <td class="pn-fcontent form-inline">
                <label class="radio-inline">
                  <input type="radio" name="status" value="0" <@=(user.status == 0) ? "checked" : ""@> <@=(_.isNull(user.status)) ? "checked" : ""@>/>未锁定
                </label>
                <label class="radio-inline">
                  <input type="radio" name="status" value="1" <@=(user.status == 1) ? "checked" : ""@>/>锁定
                </label>
              </td>
            </tr>
            <tr>
              <th>角色：</th>
              <td class="pn-fcontent form-inline">
                <@
                  if(!_.isEmpty(roles)) {
                    _.each(roles, function(role, i){
                @>
                        <input type="checkbox" name="roleId" value="<@=role.id@>" <@=role.check ? "checked" : ""@>>
                        <@
                            if(role.status == 1) {
                        @>
                            <font color="red"><@=role.name@></font>
                        <@
                            } else {
                        @>
                                <@=role.name@>
                        <@
                            }
                        @>
                    &nbsp;&nbsp;&nbsp;
                    <@=(i != 0 && i % 5 == 0) ? "<br/>" : ""@>
                <@
                    }, this);
                  }
                @>
              </td>
            </tr>
            <tr>
              <td class="pn-fcontent text-center" colspan="2">
                <button type="button" class="btn btn-info" id="saveBtn">保存</button>
                <button type="button" class="btn btn-default" id="backBtn">返回</button>
              </td>
            </tr>
          </tbody>
      </table>
  </div>
</script>
