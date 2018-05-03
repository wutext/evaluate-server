

layui.use(['jquery','table', 'laypage', 'layer'], function(){


    var form = layui.form
                ,layer = layui.layer
                ,table = layui.table
                ,$ = layui.$;

    //批量删除
    $('#deleteAll').on('click', function(){

        var checkStatus = table.checkStatus('testReload');
        console.log(checkStatus.data) //获取选中行的数据

        console.log(checkStatus.isAll ) //表格是否全选
        var data = checkStatus.data;

        if(data.length==0){
            layer.msg('请选择数据');
            return;
        }
        var ids="";
        for(var i=0;i<data.length;i++){
            if(i!=(data.length-1)){
                ids+=data[i].id+",";
            }else{
                ids+=data[i].id;
            }
        }

        deleteAllDo(ids);
    });

});


function addOperation(title,url,w,h){

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
