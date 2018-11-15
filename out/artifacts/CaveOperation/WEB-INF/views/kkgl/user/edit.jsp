<%--
<%--
  Created by IntelliJ IDEA.
  User: WQP
  Date: 2018/8/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">

    <link rel="stylesheet" type="text/css" href="${ctx}/css/index/style.css" />
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>

    <script type="text/javascript" src="${ctx}/js/lib/backbone-1.0.0.min.js" charset="utf-8"></script>
<style>
    .form-style{
        height: 525px;
    }
</style>
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
  <div class="widget widget-table" style="background: #f9f9f9">
      <div class="layui-card-header" style="border-bottom: none">设置我的资料</div>
      <hr>

      <div class="widget-content" id="formDiv">


          <form layui-form layui-form>


          </form>
      </div>
      <form class="layui-form layui-form form-style" action=""  id="param_form">
      <div style="width: 60%;margin-left: 20px">
          <input type="hidden" name="id" value="${usermap.user.id}" id="userid"/>
          <div class="layui-form-item " >

              <label class="layui-form-label">登录名</label>
              <div class="layui-input-block" style="width: 25%">
                  <input type="text" name="loginName" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input" value="${usermap.user.loginName}" <c:if test="${'edit' eq type}">disabled</c:if> >
              </div>
          </div>

          <div class="layui-form-item ">

              <label class="layui-form-label">用户名</label>
              <div class="layui-input-block" style="width: 25%">
                  <input type="text" name="userName" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input" value="${usermap.user.userName}">
              </div>
          </div>
          <div class="layui-form-item" >
              <label class="layui-form-label">密码</label>
              <div class="layui-input-block" style="width: 25%">
                  <input type="password" name="password" lay-verify="title" autocomplete="off"  class="layui-input" id="password">
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">重复密码</label>
              <div class="layui-input-block" style="width: 25%">
                  <input type="password"  lay-verify="title" autocomplete="off"  class="layui-input" id="repassword">
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">状态</label>
              <div class="layui-input-block">
                  <c:set var="contains" value="no" />
                  <c:forEach items="${sessionScope.operator.roles}" var="role">
                      <c:if test="${'admin' eq role.id }">
                          <c:set var="contains" value="yes" />
                      </c:if>
                  </c:forEach>
                  <c:choose>
                      <c:when test="${contains=='no' }">
                              <input type="radio" name="status" value="1" disabled title="锁定" <c:if test="${'1' eq usermap.user.status}">checked</c:if> >
                              <input type="radio" name="status" value="0" disabled  title="未锁定" <c:if test="${'0' eq usermap.user.status}">checked</c:if> >
                      </c:when>
                      <c:otherwise>
                          <input type="radio" name="status" value="1"   title="锁定" <c:if test="${'1' eq usermap.user.status}">checked</c:if> >
                          <input type="radio" name="status" value="0"   title="未锁定" <c:if test="${'0' eq usermap.user.status}">checked</c:if> >
                      </c:otherwise>
                  </c:choose>

              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label">角色</label>
              <div class="layui-input-block " >
                  <c:set var="contains" value="no" />



                  <c:forEach items="${sessionScope.operator.roles}" var="role">
                      <c:if test="${'admin' eq role.id }">
                          <c:set var="contains" value="yes" />
                      </c:if>
                  </c:forEach>
                  <c:choose>
                      <c:when test="${contains=='no' }">
                          <c:forEach items="${usermap.roles}" var="role">

                              <c:if test="${true == role.check}">
                                  <input type="checkbox" name="roleId" onclick="return false;" disabled value="${role.id}"    title="${role.name}" <c:if test="${true == role.check}">checked</c:if>>
                              </c:if>

                          </c:forEach>
                      </c:when>
                      <c:otherwise>
                          <c:forEach items="${usermap.roles}" var="role">
                                  <input type="checkbox" name="roleId" onclick="return false;"  value="${role.id}"    title="${role.name}" <c:if test="${true == role.check}">checked</c:if>>
                          </c:forEach>
                      </c:otherwise>
                  </c:choose>


              </div>
          </div>

      </div>

          <div class="layui-form-item layui-layout-admin">
              <div class="layui-input-block">
                  <div class="layui-footer" style="left: 0;">
                  <button class="layui-btn" lay-submit="" lay-filter="demo1" style="position: relative;left: 35%;width: 100px;">提交</button>
                  <button type="reset" class="layui-btn layui-btn-primary" style="position: relative;left: 45%;width: 100px;" id="btn_back">返回</button>
              </div>
          </div>
      </div>
      </form>

      <script>


          layui.use(['form', 'layedit', 'laydate'], function() {
              var form = layui.form,
                  layer = layui.layer,
                  layedit = layui.layedit,
                  laydate = layui.laydate;

              //日期
              laydate.render({
                  elem: '#date'
              });
              laydate.render({
                  elem: '#date1'
              });

              //创建一个编辑器
              var editIndex = layedit.build('LAY_demo_editor');

              //自定义验证规则
              form.verify({
                  title: function(value) {
                      if(value.length < 3) {
                          return '标题至少得3个字符啊';
                      }
                      if($("#repassword").val()!=$("#password").val()){
                          return '两次密码输入不一致';
                      }
                  },
                  pass: [/(.+){3,12}$/, '密码必须3到12位'],
                  content: function(value) {
                      layedit.sync(editIndex);
                  },
                  pass: [/(.+){3,12}$/, '密码必须3到12位'],
                  content: function(value) {
                      layedit.sync(editIndex);
                  }

              });



              //监听提交
              form.on('submit(demo1)', function(data) {
                  
                  var param = {id:data.field.id};
                  param["password"] = data.field.password;
                  param["loginName"] = data.field.loginName;
                  param["userName"] = data.field.userName;
                  param["status"]  = data.field.status;
                  var roleIds = [];
                   $("#param_form").find("[name=roleId]:checked").each(function(){
                      roleIds.push({"id" : $(this).val()});
                  });
                  param["roles"] = roleIds;
                  $.ajax({
                      url:window.ctx + "/api/v1/user/save",
                      data:JSON.stringify(param),
                      type:"post",
                      contentType : "application/json",
                      success:function(result){

                          if(result){
                              alert("数据保存成功！");
                              window.location.href = "${ctx}/user/list"
                          }else{
                              layer.alert("数据保存失败！");
                              return ;
                          }

                      },
                      error:function(){
                          layer.alert("服务端错误！");
                          return;
                      }
                  })

                  return false;
              });



          });
          $("#btn_back").click(function () {
              window.location.href = "${ctx}/voucher/list"
          })
      </script>
  </div>
</div>
</body>
</html>
