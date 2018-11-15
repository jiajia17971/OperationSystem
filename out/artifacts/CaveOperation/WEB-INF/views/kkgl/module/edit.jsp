<%--
  Created by IntelliJ IDEA.
  User: WQP
  Date: 2018/8/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
  模块编辑类
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/common/meta.jsp"%>
  <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/validate/validate.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/zTree/zTreeStyle/zTreeStyle.css">
  <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">

  <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.min.js"></script>
  <script type="text/javascript" src="${ctx}/js/lib/validate/jquery.validate.common_rules.js"></script>
  <script type="text/javascript" src="${ctx}/js/lib/validate/messages_cn.js"></script>

  <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.core-3.5.min.js"></script>
  <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.excheck-3.5.min.js"></script>
  <%--<script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script><script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>--%>

  <script type="text/javascript" src="${ctx}/js/dev/rbac/module/edit.js"></script>
  <style type="text/css">
        .dropdown-menu ul {
            width: 350px;
            height: 200px;
            padding: 3px;
            overflow-x: auto;
            overflow-y: auto;
        }

        .dropdown-menu ul li {
            padding: 3px;
            float: left;
            width: 20px;
            text-align: center;
            line-height: 20px;
        }

        .dropdown-menu ul li:hover {
            cursor: pointer;
            background: #f0f0f0;
        }
        .col-xs-4 {
        +width: 27%; /* ie7 */
            _width: 27%; /* ie6 */
        }
      td{
          border:none;
          height: 40px;
          padding-left: 10px;
      }
        .breadcrumb{
            border-radius: 3px 3px 0px 0px;
            height: 30px;
            line-height: 30px;
            padding: 0px 8px;
            margin: 0px;
            font-size: 9pt;
            margin-bottom: 10px;
            border: 1px solid #CACACA;
            background-color: #f2f2f2;
            box-shadow: 0px 1px 0px #FFF inset;
        }
        .breadcrumb li{
            display: inline-block;
            float: left;
        }
        li:before{
            content: ">";
        }
  </style>

</head>
<body  style="min-height: 650px; overflow-y: auto;">
  <div>
    <ul class="breadcrumb">
      <li class="icon"></li>
      <li>您所在的位置：</li>
      <li>系统管理</li>
      <li>模块管理</li>
    </ul>

    <div class="widget widget-table">
      <div class="widget-content" id="formDiv">
        <div class="row">
          <div class="col-xs-4 form-inline" style="padding-top: 5px">
            <button id="addTopBtn" type="button" class="layui-btn layui-btn-danger">添加顶层模块</button>
            <button id="addChildBtn" type="button" class="layui-btn">添加子层模块</button>
            <ul class="ztree" id="treeUl">
            </ul>
          </div>
          <div class="col-xs-8" id="contentDiv">

          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>

<!-- 模块模版 -->
<script type="text/template" id="moduleTmp">
  <form>
    <table class="pn-ftable table-bordered" border="0" cellpadding="10">
      <tbody>
        <tr>
          <th>当前操作：</th>
          <td class="pn-fcontent" style="color : blue">
              <@
                  if(scene == 1) {
              @>
                  添加顶级模块
              <@
                  } else if(scene == 2) {
              @>
                  添加子模块
              <@
                  } else if(scene == 3) {
              @>
                  修改顶级模块
              <@
                  } else if(scene == 4) {
              @>
                  修改子模块
              <@
                  }
              @>
          </td>
        </tr>
        <tr>
          <th>
              <@=(scene == 2) ? '<span style="color: red">*</span>' : ''@>
              父目录：
          </th>
          <td class="pn-fcontent">
            <@
                if(scene == 1 || scene == 3 || scene == 4) {
            @>
              <input type="hidden" name="parentId" value="<@=entity.parentId@>"/>
              <@=parentName@>
            <@
                } else {
            @>
                <select name="pId" class="form-control" >
                  <option value="">请选择</option>
            <@
                    _.each(options, function(option, index){

            @>
                      <option value="<@=option.id@>"><@=option.name@></option>
            <@
                    });
                 }
            @>
              </select>

          </td>
        </tr>
        <tr>
          <th><span style="color: red">*</span>模块名称：</th>
          <td class="pn-fcontent">
              <input type="text" class="form-control" name="name" value="<@=entity.name@>" onblur="this.value=$.trim(this.value)"
                     onafterpaste="this.value=$.trim(this.value)" />
          </td>
        </tr>
        <@
            if(scene == 2 || scene == 4) {
        @>
            <tr>
              <th><span style="color: red">*</span>模块地址：</th>
              <td class="pn-fcontent">
                <input type="text" class="form-control" name="url" value="<@=entity.url@>" onblur="this.value=$.trim(this.value)"
                       onafterpaste="this.value=$.trim(this.value)" />
              </td>
            </tr>
        <@
            }
        @>
        <tr>
          <th><span style="color: red">*</span>排序：</th>
          <td class="pn-fcontent">
            <input type="text" class="form-control" name="sortNo" maxlength="4"
                   value="<@=entity.sortNo@>"
                   onblur="this.value=this.value.replace(/\D/g,'')"
                   onafterpaste="this.value=this.value.replace(/\D/g,'')" /></td>
        </tr>
        <@
            if(scene == 1 || scene == 3) {
        @>
            <tr>
                <th>图标：</th>
                <td class="pn-fcontent">
                    <div class="btn-group dropup">
                        <a class="btn dropdown-toggle" data-toggle="dropdown"> <span>&nbsp;<i
                                class="<@=entity.imageName@>"></i>&nbsp;
										</span>
                        </a>
                        <div class="dropdown-menu">
                            <ul><@=html@></ul>
                        </div>
                    </div>
                    <input type="hidden" name="imageName" value="<@=entity.imageName@>" />
                </td>
            </tr>
        <@
            }
        @>
        <tr>
          <th><span style="color: red">*</span>是否启用：</th>
          <td class="pn-fcontent">
              <label class="form-inline">
                <input
                  type="radio" name="status" value="1" <@=(entity.status == 1) ? "checked" :""@> />是
              </label>
              <label class="form-inline">
                <input type="radio" name="status" value="0" <@=(entity.status == 0) ? "checked" :""@>/>否
              </label>
          </td>

        </tr>
        <tr>
          <th>备注：</th>
          <td class="pn-fcontent">
            <input type="text" class="form-control"
                   name="remark" value="<@=entity.remark@>" size="50"  maxlength="50" />
          </td>
        </tr>
      </tbody>
    </table>
    <div class="widget-bottom">
      <input type="hidden" name="id" value="<@=entity.id@>"/>
      <@
        if(scene == 1 || scene == 2) {
      @>
        <a class="layui-btn pull-right" id="addBtn">添加</a>
      <@
        } else {
      @>
        <a class="layui-btn layui-btn-danger pull-right" id="deleteBtn">删除</a>
        <a class="layui-btn pull-right" style="margin-right: 5px" id="updateBtn">修改</a>
      <@
        }
      @>
    </div>
  </form>
</script>

<!-- 选择框模版 -->
<script type="text/template" id="optionTmp">
  <option value="<@=id@>" selected><@=name@></option>
</script>
