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


      <script language=javascript>
          function doPrint() {
              bdhtml=window.document.body.innerHTML;
              sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
              eprnstr="<!--endprint-->"; //结束打印标识字符串
              prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
              prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
              window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
              window.print(); //调用浏览器的打印功能打印指定区域
              window.document.body.innerHTML=bdhtml;//重新给页面内容赋值；
          }
      </script>
    <style>
        .ipt_none{
            border: none;
            background-color: #f9f9f9;
        }

    </style>
</head>
<body>

<div class="layui-fluid" >
    <div class="layui-card" style="background: #f9f9f9">

        <div class="layui-card-header" style="border-bottom:none">开库单信息</div>
        <hr>
        <input type="hidden" value="${type}" id="pageType">
        <input type="hidden" value="${voucher.id}" id="kid">
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action=""  id="info_form" >
                <!--startprint-->
                <h2 align="center">湖北高速开库管理系统回执单</h2>
                <div class="layui-form-item ">
                    <label class="layui-form-label">开库单编号&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-inline" style="width: 80%">
                        <input type="text" name="voucherid" readonly lay-verify="title" autocomplete="off" placeholder="请输入开库单编号" value="${voucher.voucherid}"  class="layui-input ipt_none" style="width: 40%;float: left"  >

                    </div>
                </div>

                <div class="layui-form-item" style="">
                    <label class="layui-form-label">开库类型&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-block" style="width: 32%;display: inline-block;float: left;margin-left: 0px">
                        <c:if test="${'0' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="车道流水丢失处理" ></c:if>
                        <c:if test="${'1' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="车道重复交易" ></c:if>
                        <c:if test="${'2' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="银行充值长款" ></c:if>
                        <c:if test="${'3' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="记账文件重发" ></c:if>
                        <c:if test="${'4' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="跨行30天处理" ></c:if>
                        <c:if test="${'5' eq voucher.type}"><input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="其他" ></c:if>
                    </div>

                </div>
                <div class="layui-form-item" style="width: 40%;">
                    <label class="layui-form-label">提出单位&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-inline" style="width: 32%">
                        <input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="${voucher.orgnization}" >
                    </div>

                </div>

                <div class="layui-form-item" style="width: 40%;">
                    <label class="layui-form-label">提出人&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-inline" style="width: 32%">
                        <input type="text" readonly autocomplete="off" class="layui-input ipt_none" value="${voucher.owner}" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">提出时间&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-inline" style="width: 32%">
                        <input type="text" name="creTime" id="date" lay-verify="date" placeholder="" autocomplete="off" class="layui-input ipt_none"  value="${voucher.creTime}" <c:if test="${'view' eq type}">disabled</c:if>>
                    </div>
                </div>

                <div class="layui-form-item layui-form-text" style="width: 40%;">
                    <label class="layui-form-label">申请主题&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-block" style="width: 80%;">

                        <input placeholder="请输入开库单主题内容" name="theme" lay-verify="area" class="layui-input ipt_none" <c:if test="${'view' eq type}">readonly</c:if> value="${voucher.theme}"</input>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">申请描述&nbsp;<font color="black">:</font></label>
                    <div class="layui-input-block">
                        <textarea placeholder="请输入开库单内容描述" name="description" lay-verify="area" class="layui-textarea ipt_none" >${voucher.description}</textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">状态：&nbsp;<font color="black">:</font></label>
                    <input type="text" name="voucherid" readonly lay-verify="title" autocomplete="off" value="${voucher.processTime}已办结"  class="layui-input ipt_none" style="width: 40%;float: left"  >
                </div>

                <!--endprint-->

                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div class="layui-footer" style="left: 0;">
                            <a  id="btn_print" style=" <c:if test="${'2' ne voucher.status}">display: none </c:if>;margin-left: 40%" class="layui-btn">打印</a>
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

    function  goback(){
        window.location.href = "${ctx}/voucher/info/"+$("#kid").val();
    }

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

    $("body").on("click", "#btn_print", doPrint);

    $("body").on("click", "#btn_back", goback);

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
            imgHtml = "<img src='${ctx}" + src + "' width='600px' height='550px'/>";
        }else{
            src = name + '.jpg';
            imgHtml = "<img src='" + src + "' width='600px' height='550px'/>";
        }



        //弹出层
        layer.open({
            type: 1,
            shade: 0,
            offset: 'auto',
            area: [600 + 'px',600+'px'],
            shadeClose:true,
            scrollbar: true,
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

</body>
</html>
