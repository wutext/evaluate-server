

layui.use(['jquery','table', 'laypage', 'layer'], function(){


    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;
    var batchNumber = $("#batchNumber").val();
    table.render({
        elem: '#batch_data'
        ,url: '/batch/batchList?batchNumber='+ batchNumber //数据接口
        ,cellMinWidth: 80
        ,cols: [[ //表头
            {checkbox:true, fixed:'left', sort: true}
            ,{field: 'batchNumber', title: '批次号', align:'center', width:500, }
            ,{fixed: 'right',title: '操作', width:553, align:'center', toolbar: '#toolBar'}

        ]]
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
                    url: "/batch/deleteSingleBatch",
                    data: JSON.stringify({"id": data.id}),
                    contentType: "application/json;charset=UTF-8",
                    success: function(res) {

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
        } else if(layEvent === 'edit'){ //编辑
            batchOperation("编辑", "/batch/editBatchView?id=" + data.id);
        }

    });

});

function searchBatchPage() {
    var batchNumber = $("#batchNumber").val();

    table.reload('testReload', {
        where: { //设定异步数据接口的额外参数，任意设
            batchNumber: batchNumber
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
            url: "/batch/deleteBatchByIds",
            data: {ids:ids},
            contentType: "application/json;charset=UTF-8",
            success: function(res) {

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
                layer.msg('删除失败', {
                    time: 1000, //20s后自动关闭
                });
            }
        });
        location.replace(location.href);
        return false;
    });

}

function batchOperation(title,url,w,h){

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

