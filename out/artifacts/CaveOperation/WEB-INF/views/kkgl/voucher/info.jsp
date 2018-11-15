<%--
  Created by IntelliJ IDEA.
  User: wqp
  Date: 2018-7-29
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglibs.jsp" %>
<%--<%@ include file="/common/meta.jsp" %>--%>
<!DOCTYPE html>
<html>
<head>
  <%--  <%@ include file="/common/meta.jsp" %>--%>
    <link rel="stylesheet" href="${ctx}/js/lib/layui/css/layui.css">

    <%--<link type="text/css" rel="stylesheet" href="${ctx}/css/bootstrap.min.css" charset="utf-8"/>--%>
    <script type="text/javascript" src="${ctx}/js/dev/kkgl/voucher/info.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/laydate/laydate.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/layui/layui.js"></script>
    <script type="text/javascript" src="${ctx}/js/lib/jquery-1.11.1.min.js"></script>




    <style>
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
<body>
    <ul class="breadcrumb">
        <li class="icon"></li>
        <li>您所在的位置：</li>
        <li>开库管理</li>
        <li>开库单信息</li>
    </ul>
<div class="layui-fluid" >
    <div class="layui-card" style="background: #f9f9f9">
        <div class="layui-card-header" style="border-bottom:none">开库单信息</div>
        <hr>
        <input type="hidden" value="${type}" id="pageType">
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action=""  id="info_form" >
                <div class="layui-form-item ">
                    <label class="layui-form-label">开库单编号&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 80%">
                        <input type="text" name="voucherid" readonly lay-verify="title" autocomplete="off" placeholder="请输入开库单编号"  class="layui-input" style="width: 40%;float: left" <c:if test="${'view' eq type}">disabled</c:if> <c:if test="${'add'== type}">value="${voucherid}"</c:if><c:if test="${'edit'== type||'view'== type}">value="${voucher.voucherid}"</c:if> >
                        <%--<input type="hidden" id="${voucher.kid}">--%>
                        <input type="hidden" name="id" value="${voucher.id}" id="kid">
                        <input type="hidden" name="status" value="${voucher.status}" >
                        <div class="layui-form-mid layui-word-aux" style="float: left;margin-left: 20px">请输入20位开库单编号</div>
                    </div>
                </div>

                <div class="layui-form-item" style="">
                    <label class="layui-form-label">开库类型&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block" style="width: 32%;display: inline-block;float: left;margin-left: 0px">
                        <select name="type" lay-filter="" id="type" lay-verify="type" value="${voucher.type}" <c:if test="${'view' eq type}">disabled</c:if> >
                            <option value="" selected=""></option>
                            <option value="0" <c:if test="${'0' eq voucher.type}">selected</c:if> >车道流水丢失处理</option>
                            <option value="1" <c:if test="${'1' eq voucher.type}">selected</c:if>>车道重复交易</option>
                            <option value="2" <c:if test="${'2' eq voucher.type}">selected</c:if>>银行充值长款</option>
                            <option value="3" <c:if test="${'3' eq voucher.type}">selected</c:if>>记账文件重发</option>
                            <option value="5" <c:if test="${'5' eq voucher.type}">selected</c:if>>收费异常</option>
                            <option value="4" <c:if test="${'4' eq voucher.type}">selected</c:if>>其他</option>

                        </select>
                    </div>
                    <div style="display: inline-block;margin-left: 20px">
                        <img  src="${ctx}/images/info-blue.png" onmouseover="opennote()" onmouseleave="removenote()" id="infoBtn" style="width: 24px;height: 24px;position: relative;top: 5px;"/>
                    </div>
                </div>
                <div class="layui-form-item" style="width: 40%;">
                    <label class="layui-form-label">提出单位&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block" style="width: 80%;">
                        <select name="orgnization" lay-filter=""  lay-verify="required" value="${voucher.orgnization}" <c:if test="${'view' eq type}">disabled</c:if>>
                            <option value="" selected="">全部</option>

                            <option value="0" <c:if test="${'0' eq voucher.orgnization}">selected</c:if>>联网中心</option>
                            <option value="1" <c:if test="${'1' eq voucher.orgnization}">selected</c:if>>工商银行</option>
                            <option value="2" <c:if test="${'2' eq voucher.orgnization}">selected</c:if>>农业银行</option>
                            <option value="3" <c:if test="${'3' eq voucher.orgnization}">selected</c:if>>中国银行</option>
                            <option value="4" <c:if test="${'4' eq voucher.orgnization}">selected</c:if>>建设银行</option>
                            <option value="5" <c:if test="${'5' eq voucher.orgnization}">selected</c:if>>农商</option>
                            <option value="6" <c:if test="${'6' eq voucher.orgnization}">selected</c:if>>邮储</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item" style="width: 40%;">
                    <label class="layui-form-label">提出人&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block" style="width: 80%;">
                        <c:if test="${'add' eq type}">
                            <input type="text"  lay-verify="person" autocomplete="off" value="${sessionScope.operator.userName}"  class="layui-input" disabled>
                            <input type="hidden" name="owner"  value="${sessionScope.operator.id}"  >
                        </c:if>
                        <c:if test="${'view' eq type || 'edit' eq type }">
                            <input type="text"  lay-verify="person" autocomplete="off" value="${user.user.userName}"  class="layui-input" disabled>
                            <input type="hidden" name="owner"  value="${voucher.owner}"  >
                        </c:if>

                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">提出时间&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 32%">
                        <input type="text" name="creTime" id="date" lay-verify="date" placeholder="" autocomplete="off" class="layui-input"  value="${voucher.creTime}" <c:if test="${'view' eq type}">disabled</c:if>>
                    </div>
                    <div class="layui-form-mid layui-word-aux" style="float: left;margin-left: 20px">开库单提出时间</div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">截止时间&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-inline" style="width: 32%">
                        <input type="text" name="endTime" id="dateline" lay-verify="date" placeholder="" autocomplete="off" class="layui-input" value="${voucher.endTime}" <c:if test="${'view' eq type}">disabled</c:if>>
                    </div>
                    <div class="layui-form-mid layui-word-aux" style="float: left;margin-left: 20px">开库单办理截止时间</div>
                </div>

                <div class="layui-form-item layui-form-text" style="width: 40%;">
                    <label class="layui-form-label">申请主题&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block" style="width: 80%;">

                        <input placeholder="请输入开库单主题内容" name="theme" lay-verify="area" class="layui-input" <c:if test="${'view' eq type}">readonly</c:if> value="${voucher.theme}"</input>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">申请描述&nbsp;<font color="red">*</font></label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入开库单内容描述" name="description" lay-verify="area" class="layui-textarea" <c:if test="${'view' eq type}">disabled</c:if>>${voucher.description}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">附件</label>
                    <%--<div class="layui-input-block">
                        <button type="button" class="layui-btn" id="btn_upload">
                            <i class="layui-icon">&#xe67c;</i>上传图片
                        </button>
                        <div class="layui-form-mid layui-word-aux" style="    float: none;margin-left: 20px;display: inline-block;position: relative;top: 9px;"><sub>*支持上传格式为JPG</sub></div>
                    </div>--%>
                    <div class="layui-upload">
                        <c:if test="${'view'!= type}">
                            <button type="button" class="layui-btn layui-btn-normal" id="testList" <c:if test="${'view' eq type}">disabled</c:if>>文档拍照</button>
                            <button type="button" class="layui-btn " id="testListAction">开始上传
                                <i id="loading_i" class="layui-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop" style="font-size: 20px; color: #1E9FFF;display:none"></i>
                            </button>

                        </c:if>
                        <div class="layui-upload-list" style="padding-left: 110px;">
                            <table class="layui-table">
                                <thead>
                                <tr><th>文件名</th>
                                    <th>大小</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>

                                </thead>
                                <tbody id="demoList">

                                </tbody>
                            </table>
                        </div>

                    </div>
                    <input name="attachment" id="filename" type="hidden" >
                </div>
             <%--   <div>
                    <object id="Capture" style ="width: 92%;height: 550px;border: 5px gray solid;float:right" align="middle" classid="clsid:9A73DB73-2CA3-478D-9A3F-7E9D6A8D327C" codebase="./lib/CaptureVideo.cab">
                        <!-- 如果要指定cab包和/或版本号参照以下示例 -->
                        <!-- object id="Capture" style ="width: 99%;height: 550px;border: 5 gray solid;" align="middle" classid="clsid:9A73DB73-2CA3-478D-9A3F-7E9D6A8D327C" codebase="./lib/CaptureVideo.cab#version=1,7,8,0" -->
                    </object>
                </div>--%>
                <%--<div class="layui-form-item layui-form-text">
                    <div class="layui-input-block" style="width: 90.5%;height: 100px;border: 1px solid;border-color: #e6e6e6;">

                    </div>
                </div>--%>


                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <button class="layui-btn" lay-submit="" lay-filter="btn_submit"  id="btn_confirm" style="margin-left: 40%;display: none;"  >确定</button>
                            <button class="layui-btn" lay-submit="" lay-filter="btn_submit" id="btn_submit" style="margin-left: 40%">下一步</button>
                            <a  id="btn_export" <c:if test="${'add' eq type}">style="display: none" </c:if> class="layui-btn layui-btn-primary">导出开库单</a>
                            <a  id="btn_receipt" <c:if test="${'2' ne voucher.status}">style="display: none" </c:if> class="layui-btn">打印回执</a>
                            <button type="reset" id="btn_back"  class="layui-btn layui-btn-primary">返回</button>

                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    var tip_index=0
    var files = [];
    var submitURL = "";
    var uploadAttactment = 0;
    function opennote() {
        tip_index= layer.tips('车道流水丢失处理:连续通行记录出现断层</br>车道重复交易：一次通行产生两次通行流水</br>银行充值长款：银行充值文件有该笔充值记录，但充值平台无此记录。</br>银行记账划账文件处理：当银行需要重复消费流水时，进行重发消费流水</br>跨行30天办理处理：解除跨行30天办理的限制', '#infoBtn', {
            tips: [3, '#3595CC'],
            area: ['500px', 'auto']

        });

    }

    function removenote() {
        layer.close(tip_index)

    }

    $("#btn_back").click(function(){
        window.location.href = "${ctx}/voucher/list"
    })

    $("#btn_export").click(function(){
        /*$.ajax({
            type: 'GET',
            url: "${ctx}/api/v1/voucher/printvoucher/"+$("#kid").val(),
            success:function (res) {
            },
            dataType: 'json'
        });*/
        window.location.href = "${ctx}/voucher/printvoucher/"+$("#kid").val();

    })
    $("#btn_receipt").click(function(){

        window.location.href = "${ctx}/voucher/printreceipt/"+$("#kid").val();

    })
    $("body").on("click", ".btn_view", previewImg);

    $("#testList").click(function(){

        layer.open({
            type: 2,
            shade: 0,
            maxmin:true,
            offset: 'auto',
            area: [920 + 'px',550+'px'],
            shadeClose:true,
            scrollbar: false,
            title: "图片预览", //不显示标题
            content: "${ctx}/capture/CaptureVideo.html", //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {

                //取消时间

            }
        });
        //1打开设备
        //2拍摄照片
        //3获得图片文件渲染列表
        //关闭设备
    })

    function callback(data){
        

        fileList(data)
    }

    function searchFileInfo(index){

        var path = "E:\\DocImage";
        
        var   fso   =   new   ActiveXObject( "Scripting.FileSystemObject");
        var   files   =   new   Array();
         return fso.GetFile(path+"/"+index+".jpg").size
    }


    function previewImg(e) {

      var  name =  $(e.currentTarget).attr("name");
      var  url =  $(e.currentTarget).attr("id");
        var imgHtml = '';
        if(name==null&&name==undefined){
            src = url;
            imgHtml = "<div id='div' onmousewheel='return bbimg(this)'><img id='img' onclick='rolateLeft(this)' src='${ctx}" + src + "' width='100%' height='90%'/></div>";
        }else{
            src = name + '.jpg';
            imgHtml = "<img id='img' src='" + src + "' width='100%' height='95%'/><input type='button' class = 'layui-btn'>";
        }



        //弹出层
        layer.open({
            type: 1,
            shade: 0,
            offset: 'auto',
            /*area: [600 + 'px',600+'px'],*/
            shadeClose:true,
            scrollbar: true,
            area:['50%','100%'],
            title: "图片预览", //不显示标题
            content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
            cancel: function () {
                //layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', { time: 5000, icon: 6 });
            }
        });
    }

    $("body").on("click", ".btn_download", downFile);
    $("body").on("click", ".btn_del", deleteItem);
    $("body").on("click", "#testListAction", uploadFiles);



    var rolate = 0;
     function rolateLeft(o){
         rolate =  rolate + 90;
         $("#img").css('transform', 'rotate(' + rolate + 'deg)');
     }

    function deleteItem(e){

        var id = $(e.currentTarget).attr("id");
        var oj =$("#upload-"+id).remove();
        files.splice(id,1);
        uploadAttactment = uploadAttactment - 1;
    }
     function reloadURL(){

        if($("#pageType").val()=='view'){
            submitURL = "${ctx}/api/v1/voucher/save";
        }else if($("#pageType").val()=='edit'){
            submitURL = "${ctx}/api/v1/voucher/save";
        }else{
            submitURL = "${ctx}/api/v1/voucher/save";
        }
     }

    function downFile(e) {

        var path = $(e.currentTarget).attr("id");
        window.location.href = "${ctx}/api/v1/file" + path;

    }

    function uploadFiles(){

        var trindex = 0
        for(var i=0;i<files.length;i++){
            $("#loading_i").css("display","inline-block");//设置上传进度显示
            if(files[i].status==undefined||files[i].status==0){
                var size =searchFileInfo(files[i].Name.split(".")[0])

                $.ajax({
                    type: 'POST',
                    url: "${ctx}/api/v1/file/uploadBase64",
                    data: {
                        filepath:files[i].str,
                        size:size,
                        orname:files[i].Name,
                        voucherid:$("#kid").val()
                    },
                    success:function (res) {
                        var str =  $("#filename").val();
                        $("#filename").val(str+"," + res.data.name);
                        for(var i = 0;i<res.length;i++){
                            if(res!=null&&res.length>0){
                                files.push(res[i]);
                            }

                        }

                        for(var i = 0;i<files.length;i++){

                            var uploadstatus = "";
                            if(res.msg ==0){
                                uploadstatus = '上传成功！';
                            }else{
                                uploadstatus = '上传失败！'
                            }
                            var tr = $(['<tr id="upload-'+ trindex +'">'
                                ,'<td>'+ files[i].Name +'</td>'
                                ,'<td>'+ (files[i].size/1014).toFixed(1) +'kb</td>'
                                ,'<td>'+ '<font style="color: #5FB878;">'+uploadstatus+ '</font>' +'</td>'
                                ,'<td>'
                                ,''
                                ,'<a id="/upload/' + res.data.name + '.jpg" class="layui-btn layui-btn-xs btn_view" >预览</a>'
                                /*<a style="color: #5FB878;"  class="btn_download" id="/fileDowload/'+files[i].filepath+'"  download="" href="javascript:;" >'+uploadstatus + '</a>*/
                               /* <a class="layui-btn layui-btn-normal layui-btn-sm btn_view" id="/upload/'+res[i].filepath+'.jpg" >预览</a><a class="layui-btn layui-btn-normal layui-btn-sm btn_download" id="/fileDowload/'+res[i].filepath+'" >下载</a>*/
                                ,'</td>'
                                ,'</tr>'].join(''));
                            $("#upload-"+trindex).remove()
                            $("#demoList").append(tr);

                            files.splice(i,1);
                            trindex = trindex + 1 ;

                        }
                        uploadAttactment = uploadAttactment -1;
                        if(uploadAttactment<1){
                            $("#loading_i").css("display","none");//移除进度条
                        }
                    },
                    dataType: 'json'
                });
            }
        }

    }

    function init(){
        var kid = $("#kid").val();
        if(kid==""){
            return;
        }

        $.ajax({
            type: 'POST',
            url: "${ctx}/api/v1/file/initUpload",
            data: {
                kid:kid
            },
            beforeSend: function () {
                // 禁用
                $("#btn_submit").addClass('layui-btn-disabled');
            },
            complete: function () {
                // 启用
                $("#btn_submit").removeClass('layui-btn-disabled');
            },
            success:function (res) {
                for(var i = 0;i<res.length;i++){
                    if(res!=null&&res.length>0){
                        files.push(res[i]);
                    }

                }
                for(var i = 0;i<files.length;i++){
                    var uploadstatus = "";
                    if(files[i].status ==1){
                       uploadstatus = '上传成功！';
                    }else{
                        uploadstatus = '上传失败！'
                    }
                    var tr = $(['<tr id="upload-'+ i +'">'
                        ,'<td>'+ files[i].orname +'</td>'
                        ,'<td>'+ (files[i].size/1014).toFixed(1) +'kb</td>'
                        ,'<td>'+ '<a style="color: #5FB878;"  class="btn_download" id="/fileDowload/'+files[i].filepath+'"  download="" href="javascript:;" >'+uploadstatus + '</a>' +'</td>'
                        ,'<td>'
                        ,'<a class="layui-btn layui-btn-normal layui-btn-sm btn_view" id="/upload/'+files[i].filepath+'.jpg" >预览</a><a class="layui-btn layui-btn-normal layui-btn-sm btn_download" id="/fileDowload/'+files[i].filepath+'" >下载</a>'
                        ,'</td>'
                        ,'</tr>'].join(''));
                    $("#demoList").append(tr);

                }
            },
            dataType: 'json'
        });



    }
    var fileindex = 0;
    function fileList(data){


        var index = 1;
            if(data!=null){

                files.push({
                    id:fileindex,
                    Name:data.imgid+".jpg",
                    str:data.basecode,
                    size:searchFileInfo(data.imgid)
                })
            }
            index = index + 1;
            var tr = $(['<tr id="upload-'+ fileindex +'">'
                ,'<td>'+ data.imgid+".jpg" +'</td>'
                ,'<td>'+ (searchFileInfo(data.imgid)/1014).toFixed(1) +'kb</td>'
                ,'<td>等待上传</td>'
                ,'<td>'
                ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                ,'<a id="' + fileindex + '" class="layui-btn layui-btn-xs layui-btn-danger btn_del">删除</a>'
                ,'</td>'
                ,'</tr>'].join(''));
            /*var tr = $(['<tr id="upload-'+ i +'">'
                ,'<td>'+ files[i].orname +'</td>'
                ,'<td>'+ (files[i].size/1024).toFixed(1) +'kb</td>'
                ,'<td>'+ '<a style="color: #5FB878;"  class="btn_download" id="/fileDowload/'+files[i].filepath+'"  download="" href="javascript:;" >'+uploadstatus + '</a>' +'</td>'
                ,'<td>'
                ,'<a class="layui-btn layui-btn-normal layui-btn-sm btn_view" id="/upload/'+files[i].filepath+'.jpg" >预览</a><a class="layui-btn layui-btn-normal layui-btn-sm btn_download" id="/fileDowload/'+files[i].filepath+'" >下载</a>'
                ,'</td>'
                ,'</tr>'].join(''));*/
            $("#demoList").append(tr);
            fileindex = fileindex + 1;
            uploadAttactment = uploadAttactment +1;


    }

    layui.use(['form', 'layedit', 'laydate','upload'], function() {

        var form = layui.form,
            layer = layui.layer,
            layedit = layui.layedit,
            laydate = layui.laydate,
            upload = layui.upload;
        reloadURL();
        form.on('submit(btn_submit)', function(data){

            <%--if($("#pageType").val()=="view"){--%>
                <%----%>

                <%--if($("#type").val()=='3'){//查看开库单分别跳转卡表和重发表--%>
                    <%--window.location.href = "${ctx}/voucher/reuploads/"+$("kid").val();--%>

                <%--}else{--%>
                    <%--window.location.href = "${ctx}/voucher/cardlist/"+$("kid").val();--%>
                <%--}--%>


            <%--}else{--%>
                if(uploadAttactment>0){
                    if(!confirm("有"+uploadAttactment+"个附件未上传,确认保存开库单？")){
                        return false;
                    }

                }
                if(data.elem.id=="btn_confirm"){

                    data.field.status = 2;
                }else{
                    data.field.subType = 1;
                }
                $.ajax({
                    url:submitURL,
                    data:data.field,
                    type:"post",
                    success:function(result){
                        if(result){

                           /* if(confirm('数据保存成功！')){ //只有当点击confirm框的确定时，

                            }*/
                           if(data.elem.id=="btn_confirm"){
                               window.location.href = "${ctx}/voucher/list/";
                           }else{

                                   if($("#type").val()=='3'){//开库单保存之后分别跳转卡表和重发表
                                       window.location.href = "${ctx}/voucher/reuploads/"+result;
                                   }else if($("#type").val()=='4'){
                                       return;
                                   }else{
                                       window.location.href = "${ctx}/voucher/cardlist/"+result;
                                   }


                           }


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
            // }


        });

        //日期
        laydate.render({
            elem: '#date',
            value: new Date()
        });
        var date = new Date();
        date.setMonth(date.getMonth()+1);
        laydate.render({
            elem: '#dateline',
            // value: date
        });
        init();
        //多文件列表
        /*var demoListView = $('#demoList')
            ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: '${ctx}/api/v1/file/fileUpload'
            ,accept: 'images'
            ,multiple: true
            ,auto: false
            ,bindAction: '#testListAction'
            ,choose: function(obj){
                files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                    uploadAttactment = uploadAttactment +1;
                });
            }
            ,before:function (obj) {
                $("#loading_i").css("display","inline-block");//设置上传进度显示
            }
            ,done: function(res, index, upload){

                if(res.code == 0){ //上传成功
                    var location = res.data.src;
                    var str =  $("#filename").val();
                    $("#filename").val(str+"," + res.data.name);

                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<a style="color: #5FB878;" class="btn_download" id="'+location+'"  download="" href="javascript:;" >上传成功!</a>');
                    tds.eq(3).html('<a class="layui-btn layui-btn-normal layui-btn-sm btn_view">预览</a><a class="layui-btn layui-btn-normal layui-btn-sm btn_download" id="'+location+'" download="" href="javascript:;">下载</a>'); //清空操作

                    uploadAttactment = uploadAttactment -1;
                    if(uploadAttactment<1){
                        $("#loading_i").css("display","none");//移除进度条
                    }
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }



                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });*/

        //自定义验证规则
        form.verify({
            title: function(value) {
                if(value.length < 1) {
                    return '开库单编号不能为空！';
                }
            },
            type: function(value) {
                if(value.length < 1) {
                    return '开库类型不能为空！';
                }
            },
            pass: [/(.+){6,12}$/, '密码必须6到12位'],
            content: function(value) {
                layedit.sync(editIndex);
            },
            person:function(value) {
                if(value.length < 1) {
                    return '提出人不能为空！';
                }
            },
            area:function(value) {
                if(value.length < 1) {
                    return '主题不能为空！';
                }
            },
            organization:function(value) {
                if(value.length < 1) {
                    return '机构不能为空！';
                }
            }
        });





    });
</script>
    <script language="javascript">
        var params = {
            zoomVal:1,
            left: 0,
            top: 0,
            currentX: 0,
            currentY: 0,
            flag: false
        };
        //图片缩放
        function bbimg(o){

            var o=o.getElementsByTagName("img")[0];
            params.zoomVal+=event.wheelDelta/1200;
            if (params.zoomVal >= 0.2) {
                o.style.transform="scale("+params.zoomVal+")";
            } else {
                params.zoomVal=0.2;
                o.style.transform="scale("+params.zoomVal+")";
                return false;
            }
        }
        //获取相关CSS属性
        var getCss = function(o,key){
            return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key];
        };
        //拖拽的实现
        /*var startDrag = function(bar, target, callback){
            debugger
            if(getCss(target, "left") !== "auto"){
                params.left = getCss(target, "left");
            }
            if(getCss(target, "top") !== "auto"){
                params.top = getCss(target, "top");
            }
            //o是移动对象
            bar.onmousedown = function(event){
                params.flag = true;
                if(!event){
                    event = window.event;
                    //防止IE文字选中
                    bar.onselectstart = function(){
                        return false;
                    }
                }
                var e = event;
                params.currentX = e.clientX;
                params.currentY = e.clientY;
            };
            document.onmouseup = function(){
                params.flag = false;
                if(getCss(target, "left") !== "auto"){
                    params.left = getCss(target, "left");
                }
                if(getCss(target, "top") !== "auto"){
                    params.top = getCss(target, "top");
                }
            };
            document.onmousemove = function(event){
                var e = event ? event: window.event;

                if(params.flag){
                    var nowX = e.clientX, nowY = e.clientY;
                    var disX = nowX - params.currentX, disY = nowY - params.currentY;
                    target.style.left = parseInt(params.left) + disX+ "px";
                    target.style.top = parseInt(params.top) + disY+ "px";

                    if (typeof callback == "function") {
                        callback((parseInt(params.left) || 0) + disX, (parseInt(params.top) || 0) + disY);
                    }

                    if (event.preventDefault) {
                        event.preventDefault();
                    }
                    return false;
                }


            }
        };*/
        // startDrag(document.getElementById("img"),document.getElementById("img"))
    </script>


</body>
</html>
