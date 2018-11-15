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
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/zTree/zTreeStyle/zTreeStyle.css">

    <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.common_rules.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/validate/messages_cn.js"></script>

    <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.excheck-3.5.min.js"></script>

    <script type="text/javascript" src="${ctx}/js/dev/rbac/role/edit.js"></script>
</head>
<body style="min-height: 650px; overflow-y: auto;">
<div>
  <ul class="breadcrumb">
    <li class="icon"></li>
    <li>您所在的位置：</li>
    <li>系统管理</li>
    <li>角色管理</li>
    <li>角色${empty id ? "新增" : "修改"}</li>
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

<!-- 角色模版 -->
<script type="text/template" id="roleTmp">
  <div class="widget-content">
      <table class="pn-ftable table-bordered" border="0" cellpadding="10">
          <tbody>
            <tr>
              <th><span class="point">*</span>角色名：</th>
              <td class="pn-fcontent form-inline">
                <input type="text" name="name" class="form-control" value="<@=role.name@>" onblur="this.value=$.trim(this.value);" style="width:25%"/>
              </td>
            </tr>
            <tr>
              <th><span class="point">*</span>是否默认角色：</th>
              <td class="pn-fcontent form-inline">
                <label class="radio-inline">
                  <input type="radio" name="status" value="1" <@=(role.status == 1) ? "checked" : ""@>/>是
                </label>
                <label class="radio-inline">
                  <input type="radio" name="status" value="0" <@=(role.status == 0) ? "checked" : ""@> <@=(_.isNull(role.status)) ? "checked" : ""@>/>否
                </label>
              </td>
            </tr>
            <tr>
                <th>备注：</th>
                <td class="pn-fcontent form-inline">
                    <textarea rows="3" cols="20" name="remark" maxlength="60" class="form-control">
                        <@=role.remark@>
                    </textarea>
                </td>
            </tr>
            <tr>
              <th>功能模块：</th>
              <td class="pn-fcontent form-inline">
                  <ul class="ztree" id="treeUl">
                  </ul>
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
