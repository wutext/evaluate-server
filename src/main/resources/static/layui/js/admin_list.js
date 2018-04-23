

layui.use(['jquery','table', 'laypage', 'layer'], function(){

    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,form = layui.form
                ,$ = layui.$;

    table.render({
        elem: '#table_user'
        ,url: '/sys/adminList?departmentId='+$("#departmentId").val()+"&username="+$("#username").val()+"&departUtilId="+$("#departUtilId").val()+"&batchId="+$("#batchId").val()
        ,cellMinWidth: 80
        ,cols: [[ //表头
            {field:'checkbox',checkbox:true, fixed:'left', sort: true}
            ,{field:'departmentUtil', title:'部门',align:'center',width:150}
            ,{field:'username', title:'用户名',align:'center',width:100, sort: true}
            ,{field:'company', title:'所在公司',align:'center',width:183,sort: true}
            ,{field:'project', title:'项目名称', align:'center',width:200,sort: true}
            ,{fixed: 'right',title: '操作', width:400, align:'center', templet: '#toolBar'/* toolbar: '#toolBar'*/}
        ]]
        ,page: true //开启分页
        ,limit:6   //默认十条数据一页
        ,limits:[6,10,20,30,50]  //数据分页条
        ,id: 'testReload'
        ,done: function (res,e,d) {

            $("#batch").val($("#batchId").val());
            $("#department").val($("#departmentId").val());
            setDepartUtilSelect($("#departmentId").val(), $("#departUtilId").val());
            form.render();
        }

    });

    //新增user
    form.on('submit(add)', function(data){

        var strIds=new Array();
        $('input[name="description"]:checked').each(function(){
            strIds.push($(this).val());
        });
        $.ajax({
            type: "post"
            ,url: "/sys/addUserDo"
            ,data: JSON.stringify({
                "id":  data.field.id
                ,"username": data.field.username
                ,"password": data.field.pass
                ,"company" : data.field.company
                ,"project" : data.field.project
                ,"phone": data.field.phone
                ,"email": data.field.email
                ,"roleId": strIds
                ,"utilId": data.field.departUtil
            })
            ,contentType: "application/json;charset=UTF-8"
            ,success: function(res) {

                if(res=="success") {
                    layer.alert("操作成功", {icon: 6},function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                    });
                }else {
                    layer.alert("操作失败", {icon: 6},function () {
                        // 获得frame索引
                        var index = parent.layer.getFrameIndex(window.name);
                        //关闭当前frame
                        parent.layer.close(index);
                    });
                }
            },error: function(xml, status, e) {

                alert("error : " +e);
            }

        });
        return false;
    });

    <!-- start deal the department show -->
    form.on('select(department)', function(data){

        var id = data.value;
        setDepartUtilSelect(id);
        $("#departmentId").val(id, null);
/*
        return false;*/
    });

    form.on('select(batch)', function(data){

        var id = data.value;
        $("#batchId").val(id);
    });

    form.on('select(departUtil)', function(data){

        var id = data.value;
        $("#departUtilId").val(id);
    });
    <!-- end -->
    //编辑和单个删除
    table.on('tool(table_demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        var cloneUrl= curWwwPath.substring(0, pos);
        var batchNumber = $("#batchId").val();

        if(layEvent === 'detail'){ //查看

            addOperation("编辑", "/sys/userEdit?id=" + data.id+"&operate=detail");
        } else if(layEvent === 'del'){ //删除
            layer.confirm('是否要删除数据', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    type: "post",
                    url: "/sys/deleteSingleUser",
                    data: JSON.stringify({"id": data.id}),
                    contentType: "application/json;charset=UTF-8",
                    success: function(res) {

                        if(res == "success") {
                            layer.msg('删除成功', {
                                time: 10000, //20s后自动关闭
                            });
                        }
                    }
                });
                return false;
            });
        } else if(layEvent === 'edit' || layEvent === 'detail'){ //编辑
            roleOperation("编辑", "/sys/userEdit?id=" + data.id+"&operate=edit");
        }else if(layEvent === 'clone1'){ //编辑
            getUrl(data.id, 1, cloneUrl, batchNumber);
        }else if(layEvent === 'clone2'){ //编辑
            getUrl(data.id, 2, cloneUrl, batchNumber);
        }else if(layEvent === 'clone3'){ //编辑
            getUrl(data.id, 3, cloneUrl, batchNumber);
        }
    });
});


function getUrl(userId, number, cloneUrl, batchNumber) {

    <!-- java.net.URLEncoder.encode(url)-->
    var url= cloneUrl+"/visit/research?userId="+userId+"&role="+number+"&batch="+batchNumber;
    var urlEncode = encodeURI(url);
    layer.alert("", {
        content: urlEncode
        ,area: ['300px', '200px']
        ,btn: ['关闭', '复制']
        ,btn1: function(index, layero){
            layer.close(index);
        },btn2: function(index, layero){
            //按钮【按钮二】的回调
           copyMethod(urlEncode);
            //return false 开启该代码可禁止点击该按钮关闭
        }
        ,skin: 'layui-layer-lan'
        ,closeBtn: 0
        ,anim: 1
        ,title: "clone"

    });
}

function copyMethod(url) {
    new clipBoard("", {
        copy: function() {
            return url;
        },
        afterCopy: function() {
            layer.msg('复制成功', {offset: 't',time: 1000});
        }
    });

}
function searchUserPage() {

    var departmentId = $("#departmentId").val();
    var username = $("#username").val();
    var departUtilId = $("#departUtilId").val();
    var batchId = $("#batchId").val();

    table.reload('testReload', {
        where: { //设定异步数据接口的额外参数，任意设
            department: departmentId
            ,username: username
            ,departUtil: departUtilId
            ,batchId : batchId
        }
        ,page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}

function roleOperation(title,url,w,h){

    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };

    layui.use(['laydate', 'layer'], function() {
        var laydate = layui.laydate
            ,layer = layui.layer;
        layer.open({
            type: 2,
            area: [w+'px', h +'px'],
            fix: false, //不固定
            maxmin: true,
            shadeClose: true,
            shade:0.4,
            title: title,
            content: url
        });
    });

}

function deleteAllDo(ids) {

    layer.confirm('是否要删除数据', function(index){
        $.ajax({
            type: "post",
            url: "/sys/deleteUsers",
            data: JSON.stringify(ids),
            contentType: "application/json;charset=UTF-8",
            success: function(res) {
                layer.msg('删除成功', {
                    time: 1000, //20s后自动关闭
                });

            },error: function(xml, status, e) {
                alert(e+"error");
            }

        });
        return false;
    });
}

function setDepartUtilSelect(id, val) {

    $.ajax({
        type: "post",
        url: "/department/findUtil?id="+id,
        success: function(res) {

            $("#departUtil").html("<option select=\"\" value=\"\">请选择处</option>");
            if(res.length>0) {
                for(var i=0;i<res.length;i++) {
                    $("#departUtil").append("<option value='"+res[i].id+"'>"+res[i].name+"</option>");
                }
            }else{
                $("#departUtilId").val("");
            }

            if(val!=null || val!="") {
                $("#departUtil").val(val);
            }

            layui.use('form', function() {

                var form = layui.form;
                form.render('select');
            });

        },error: function(xml, status, e) {
            alert(e+"error");
        }
    });
}