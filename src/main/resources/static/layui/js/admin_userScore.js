

layui.use(['jquery','table', 'laypage', 'layer','util'], function(){
    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;

    table.render({
        elem: '#table_userScore'
        ,url: '/visit/userScoreList?time='+$("#time").val()+"&username="+$("#username").val()
        ,cellMinWidth: 80
        ,cols: [[ //表头
            {checkbox:true, fixed:'left', sort: true}
            ,{field:'userName', title:'用户名',width:250,align:'center', sort: true}
            ,{field:'total', title:'得分',width:250,align:'center',sort: true}
            ,{field:'createTime', title:'时间',width:250,align:'center',sort: true}
            ,{fixed: 'right',title: '操作', width:250, align:'center',toolbar: '#toolBar' }
        ]]
        ,page: true //开启分页
        ,limit:5   //默认十条数据一页
        ,limits:[5,10,20,30,50]  //数据分页条
        ,id: 'testReload'
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

            detailOperation("得分详情查看", "/visit/userScoreDetail?userName=" + data.userName+"&time="+data.createTime);
        }

    });

});





function searchUserScorePage() {

    var time = $("#time").val();
    var username = $("#username").val();

    table.reload('testReload', {
        where: { //设定异步数据接口的额外参数，任意设
            time: time
            ,username: username
        }
        ,page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}

function detailOperation(title,url,w,h){

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
        return false;
    });
}

