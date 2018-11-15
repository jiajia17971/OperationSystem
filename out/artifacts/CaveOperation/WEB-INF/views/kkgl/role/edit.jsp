<%--
  Created by IntelliJ IDEA.
  User: hegc
  Date: 2016/4/2
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@ include file="/common/meta.jsp"%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/validate/validate.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/js/lib/zTree/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/index/style.css" />
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script><script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/backbone-1.0.0.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/zTree/jquery.ztree.excheck-3.5.min.js"></script>
<style type="text/css">
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
          <input type="hidden" name="id" value="${id}" id="roleid"/>


          <div class="layui-form-item " style="width: 100%;">

              <label class="layui-form-label" style="width: 96px;">角色名&nbsp;<font color="red">*</font></label>
              <div class="layui-input-block" style="width: 50%">
                  <input type="text" name="name" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input" value="${rolemap.role.name}">
              </div>
          </div>
          <div class="layui-form-item" style="width: 100%;">
              <label class="layui-form-label" style="width: 96px;">默认角色&nbsp;<font color="red">*</font></label>
              <div class="layui-form-item" style="width: 50%">
                  <label class="layui-form-label"></label>
                  <div class="layui-input-block">
                      <input type="radio" name="status" value="1" title="是" <c:if test="${rolemap.role.status==1}">checked</c:if>  >
                      <input type="radio" name="status" value="0" title="否" <c:if test="${rolemap.role.status==0}">checked</c:if>>
                  </div>
              </div>
          </div>
          <div class="layui-form-item" style="width: 100%;">
              <label class="layui-form-label" style="width: 96px;">备注</label>
              <div class="layui-input-block" style="width: 50%">
                  <textarea placeholder="请输入角色相关备注" name="remark" lay-verify="area" class="layui-textarea" <c:if test="${'view' eq type}">disabled</c:if>>${voucher.theme}</textarea>
              </div>
          </div>

          <div class="layui-form-item" style="width: 100%;">
              <label class="layui-form-label" style="width: 96px;">功能模块</label>
              <div class="layui-input-block" style="border: 1px solid #EEEEEE">
                  <ul class="ztree" id="treeUl">
                  </ul>
              </div>
          </div>



      </div>

          <div class="layui-form-item layui-layout-admin">
              <div class="layui-input-block">
                  <div class="layui-footer" style="left: 0;">
                      <button class="layui-btn" lay-submit="" lay-filter="btn_submit" id="btn_submit" style="margin-left: 40%">提交</button>
                      <button type="reset" id="btn_back"  class="layui-btn layui-btn-primary">返回</button>
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
              form.on('submit(btn_submit)', function(data) {
                  var param = {id:data.field.id};
                  param["name"] = data.field.name;
                  param["status"] = data.field.status;
                  param["remark"] = data.field.remark;
                  param["status"]  = data.field.status;
                  param["modules"] = getTreeSelectData();

                  $.ajax({
                      url:window.ctx + "/api/v1/role/save",
                      data:JSON.stringify(param),
                      type:"post",
                      contentType : "application/json",
                      success:function(result){

                          if(result){
                              alert("数据保存成功！");
                              window.location.href = "${ctx}/role/list"
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
              window.location.href = "${ctx}/role/list";
          })

          var setting = {
              data : {
                  simpleData : {
                      idKey : "id",
                      pIdKey : "parentId",
                      rootPid : null,
                      enable : true
                  }
              },
              check : {
                  enable : true
              },
              callback : {
                  click: zTreeOnClick      //回调函数，协议重写这个回调函数
              }
          };

          //回调函数：zTreeOnClick，当用户点击树形结构的节点时触发

          function zTreeOnClick(event, treeId, treeNode) {
              //treeNode为用户点击的节点
              // alert(treeNode.tId ", " treeNode.name);
              window.location.href="new.jsp";
          }
          var zTree;
          var treeNodes;
          var modules;
          $(function(){
              $.ajax({
                  async : false,
                  cache:false,
                  data:{id:$("#roleid").val()},
                  type: 'GET',
                  dataType : "json",
                  url: "${ctx}/api/v1/role/get",//请求的action路径
                  error: function () {//请求失败处理函数
                      layer.alert('请求失败');
                  },
                  success:function(data){ //请求成功后处理函数。
                      treeNodes = data.tree; //把后台封装好的简单Json格式赋给treeNodes
                      modules = data.role.modules;
                  }
              });
              // zTree = $.fn.zTree("#treeUl").zTree(setting, treeNodes);
              zTree = $.fn.zTree.init($("#treeUl"), setting, treeNodes)
              zTree.expandAll(true);

              if(!_.isEmpty(modules)) {
                  _.each(modules, function(module, index){
                      var moduleId = module.id;
                      var nodes = zTree.getNodesByParam("id", moduleId, null);
                      _.each(nodes, function(node, i){
                          zTree.checkNode(node, true, false);
                      });

                  });
              }
          });

          function getTreeSelectData() {
              var zTree = $.fn.zTree.getZTreeObj("treeUl");
              var modulesIds = [];
              var selectNodes = zTree.getCheckedNodes(true);
              _.each(selectNodes, function(node, index){
                  var obj = {};
                  obj.id = node.id;
                  modulesIds.push(obj);
              });
              return modulesIds;
          }
      </script>
  </div>
</div>
</body>
</html>
