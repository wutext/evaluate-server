

//修改

//删除
//增加

layui.use(['jquery','table', 'laypage', 'layer'], function(){

    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;

    //新增permission
    form.on('submit(add)', function(data){

        $.ajax({
            type: "post"
            ,url: ""
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
                alert(e);
            }

        });
        return false;
    });

    //编辑和单个删除
    table.on('tool(table_demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'del'){ //删除
            layer.confirm('是否要删除数据', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存

                alert(data.id+"................");
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    type: "post",
                    url: "/per/deleteSinglePerm",
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
            content: url,
            end: function () {
                location.reload();
            }
        });
    });

}

function delPerm(url) {

    layer.confirm('是否要删除数据,其子也将被删除', function(index){

        layer.close(index);
        //向服务端发送删除指令
        $.ajax({
            type: "post",
            url: url,
            data: {},
            contentType: "application/json;charset=UTF-8",
            success: function(res) {

                if(res == "success") {
                    layer.msg('删除成功', {
                        time: 10000, //20s后自动关闭
                    });
                    location.replace(location.href);
                }
            }
        });
        return false;
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
                location.replace(location.href);
            },error: function(xml, status, e) {
                alert(e+"error");
            }
        });
    });
}

