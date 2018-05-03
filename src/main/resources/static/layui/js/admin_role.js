

layui.use(['jquery','table', 'laypage', 'layer'], function(){


    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;
    var roleSearch = $("#searchRole").val();

    $("#name").blur(function() {
        var name = "";
        name=$(this).val();
        if(name!="") {
            $.ajax({
                type: "post",
                url: "/role/verifyRoleName",
                data: JSON.stringify(name),
                contentType: "application/json;charset=UTF-8",
                success: function(res) {
                    if(res) {
                        layer.alert("角色名已存在");
                    }
                },error: function(xml, status, e) {
                    alert(e+"error");
                }

            });
            return false;
        }
    });
    table.render({
        elem: '#role_data'
        ,url: '/role/roleList?roleSearch='+ roleSearch //数据接口
        ,cellMinWidth: 80
        ,cols: [[ //表头
            {checkbox:true, fixed:'left', sort: true}
            ,{field: 'role', title: '角色名称', align:'center', width:300}
            ,{field: 'description', title: '角色描述',align:'center', width:300, sort: true}
            ,{fixed: 'right',title: '操作', width:435, align:'center', toolbar: '#toolBar'}

        ]]
        ,done: function(res, curr, count){
            $('#num').html(count);
        }
        ,page: true //开启分页
        ,limit:6   //默认十条数据一页
        ,limits:[6,10,20,30,50]  //数据分页条
        ,id: 'testReload'
    });


    //编辑和单个删除
    table.on('tool(table_demo)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'detail'){ //查看
            //do somehing
        } else if(layEvent === 'del'){ //删除
            layer.confirm('是否要删除数据', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                $.ajax({
                    type: "post",
                    url: "/role/deleteSingleRole",
                    data: JSON.stringify({"id": data.id}),
                    contentType: "application/json;charset=UTF-8",
                    success: function(res) {
                        layer.msg('删除成功', {
                            time: 1000, //20s后自动关闭
                        });
                        location.replace(location.href);
                    },error: function(xml, status, e) {
                        alert(e);
                    }
                });
                return false;
            });
        } else if(layEvent === 'edit'){ //编辑
            roleOperation("编辑", "/role/roleEdit?id=" + data.id);
        }

    });

});

function searchRolePage() {
    var roleName = $("#searchRole").val();

    table.reload('testReload', {
        where: { //设定异步数据接口的额外参数，任意设
            searchRole: roleName
        }
        ,page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}


function deleteAllDo(ids) {

    layer.confirm('是否要删除数据', function(index){
        $.ajax({
            type: "get",
            url: "/role/deleteRole",
            data: {ids:ids},
            contentType: "application/json;charset=UTF-8",
            success: function(res) {
                layer.msg('删除成功', {
                    time: 1000, //20s后自动关闭
                });
                location.replace(location.href);
            },error: function(xml, status, e) {
                alert(e);
            }
        });
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

