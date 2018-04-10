

layui.use(['jquery','table', 'laypage', 'layer'], function(){

    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;

    table.render({
        elem: '#table_user'
        ,url: '/sys/adminList?department='+$("#department").val()+"&username="+$("#username").val()
        ,cellMinWidth: 80
        ,cols: [[ //表头
            {checkbox:true, fixed:'left', sort: true}
            ,{field:'id', title:'ID', width:80, sort: true}
            ,{field:'username', title:'用户名',width:150, sort: true}
            ,{field:'phone', title:'电话/手机',width:80,sort: true}
            ,{field:'email', title:'邮箱',width:80,sort: true}
            ,{field:'state', title:'状态',width:80}
            ,{fixed: 'right',title: '操作', width:500, align:'center', toolbar: '#toolBar'}
        ]]
        ,page: true //开启分页
        ,limit:6   //默认十条数据一页
        ,limits:[6,10,20,30,50]  //数据分页条
        ,id: 'testReload'
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
                ,"phone": data.field.phone
                ,"email": data.field.email
                ,"roleId": strIds
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

                alert(xml);
                alert(status);
                alert(e);
            }

        });
    });

    //编辑和单个删除
    table.on('tool(table_demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        var cloneUrl= curWwwPath.substring(0, pos)

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
            });
        } else if(layEvent === 'edit' || layEvent === 'detail'){ //编辑
            roleOperation("编辑", "/sys/userEdit?id=" + data.id+"&operate=edit");
        }else if(layEvent === 'clone1'){ //编辑
            getUrl(data.username, 1, cloneUrl);
        }else if(layEvent === 'clone2'){ //编辑
            getUrl(data.username, 2, cloneUrl);
        }else if(layEvent === 'clone3'){ //编辑
            getUrl(data.username, 3, cloneUrl);
        }

    });

});


function getUrl(name, number, cloneUrl) {
    var url= cloneUrl+"/visit/research?name="+name+"&role="+number;
    layer.alert(url, {
        area: ['300px', '200px']
        ,skin: 'layui-layer-lan'
        ,closeBtn: 0
        ,anim: 1
        ,title: "clone"
    });
}
function searchUserPage() {

    var department = $("#department").val();
    var username = $("#username").val();

    table.reload('testReload', {
        where: { //设定异步数据接口的额外参数，任意设
            department: department
            ,username: username
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
            data: JSON.stringify({ids:ids}),
            contentType: "application/json;charset=UTF-8",
            success: function(res) {
                layer.msg('删除成功', {
                    time: 1000, //20s后自动关闭
                });

            },error: function(xml, status, e) {
                alert(e+"error");
            }
        });
        location.replace(location.href);
    });
}

