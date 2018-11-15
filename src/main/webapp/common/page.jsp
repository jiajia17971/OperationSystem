<%--
  Created by IntelliJ IDEA.
  User: hegc
  Date: 2016/9/21
  Time: 15:12
  To change this template use File | Settings | File Templates.
  分页模版
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="pageTmp">
  <div class="paginations pull-right">
    <ul class="pagination pagination-sm pull-left">
      <li><a href="javascript:void(0);" <@=!hasPre ? "disabled class='btn disabled'" : "class='firstPage'"@>>首页</a> </li>
      <li><a href="javascript:void(0);" <@=!hasPre ? "disabled class='btn disabled'" : "class='prePage'"@>>上一页</a> </li>
      <li><a href="javascript:void(0);" <@=!hasNext ? "disabled class='btn disabled'" : "class='nextPage'"@>>下一页</a> </li>
      <li><a href="javascript:void(0);" <@=!hasNext ? "disabled class='btn disabled'" : "class='lastPage'"@>>末页</a> </li>
    </ul>
    <div class="toPage pull-left">
      &nbsp;&nbsp;第&nbsp;<@=pageNo@>&nbsp;页&nbsp;(共&nbsp;<@=totalCount@>&nbsp;条，&nbsp;<@=totalPages@>&nbsp;页)&nbsp;&nbsp;
      ，显示<select id="psSelect" style="width:60px; line-height:24px;">
      <option value="10">10</option>
      <option value="20">20</option>
      <option value="50">50</option>
      <option value="100">100</option>
    </select> 项，跳转到第
      <input type="text" id="currentPage" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" size="2" value="<@=pageNo@>"  onfocus="this.select();"/>
      页&nbsp;&nbsp;
      <a class="btn btn-default btn-sm" id="jumpBtn" >跳转</a>&nbsp;&nbsp;&nbsp;
    </div>
  </div>
</script>
