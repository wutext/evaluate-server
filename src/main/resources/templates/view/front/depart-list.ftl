<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="/layui/css/font.css" />
    <link rel="stylesheet" href="/layui/css/xadmin.css" />
    <link rel="stylesheet" href="/treeTwo/themes/vsStyle/treeTable.min.css" />
    <script type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/layui/js/admin_common.js"></script>
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/treeTwo/jquery.treeTable.min.js"></script>
    <script type="text/javascript" src="/layui/js/admin_depart.js"></script>

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">.table td i{margin:0 2px;}</style>
    <script type="text/javascript">
        $(document).ready(function() {

            $("#treeTable").treeTable({ expandLevel: 3 });
        });
    </script>
</head>

<body>

<div class="x-nav">
          <span class="layui-breadcrumb">
            <a href="">首页</a>
            <a href="">部门管理</a>
            <a>
              <cite>导航元素</cite>
            </a>
          </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">

    <xblock>

        <button class="layui-btn" onclick="addOperation('添加部门', '/department/addDepartView?id=&type=addNext')"><i class="layui-icon"></i>添加部门</button>

    </xblock>

    <table id="treeTable" class="layui-table" lay-size="sm" style="width:100%">
        <thead>
        <tr>
            <th>名称</th>
            <th>操作</th>
        </tr>
        </thead>

    <#list departments as depart>

        <tr id="${depart.id}" pId="">
            <td>${depart.name!""}</td>
            <td >

                <!-- 修改 -->
                <a class="layui-btn layui-btn-xs layui-btn-xs" onclick="addOperation('部门修改', '/department/addDepartView?id=${depart.id}&type=editDepart')" lay-event="edit">
                    <i class="layui-icon">&#xe642;</i>
                </a>

                <!-- 删除 -->
                <a class="layui-btn layui-btn-danger" lay-event="del" onclick="delPerm('/department/deleteSingleDepart?id=${depart.id}')" >
                    <i class="layui-icon">&#xe640;</i>
                </a>

                <!-- 添加 -->

                <a class="layui-btn layui-btn-primary layui-btn-xs" onclick="addOperation('添加下级处', '/department/addDepartView?id=${depart.id}&&type=addNext')" lay-event="add">
                    <i class="layui-icon">&#xe61f</i>
                </a>
            </td>
        </tr>
        <!-- 当前部门所有处 -->
        <#if depart.departUtil?? && (depart.departUtil?size > 0)>
            <#list depart.departUtil as departUtil>
            <tr id="${departUtil.id}" pId="${depart.id}">
                <td>${departUtil.name!""}</td>
                <td >
                    <!-- 修改 -->
                    <a class="layui-btn layui-btn-xs layui-btn-xs" onclick="addOperation('处修改', '/department/addDepartView?id=${departUtil.id}&type=editUtil')" lay-event="edit">
                        <i class="layui-icon">&#xe642;</i>
                    </a>

                    <!-- 删除 -->
                    <a class="layui-btn layui-btn-danger" lay-event="del" onclick="delPerm('/department/deleteSingleUtil?id=${departUtil.id}')" >
                        <i class="layui-icon">&#xe640;</i>
                    </a>
                    <!-- 添加 -->
                    <#--<a class="layui-btn layui-btn-primary layui-btn-xs" onclick="addOperation('添加下级处', '/department/addDepartView?id=${departUtil.id}&type=addNext')" lay-event="add">
                        <i class="layui-icon">&#xe61f</i>
                    </a>-->
                </td>
            </tr>
            </#list>
        </#if>

    </#list>
    </table>
</div>


</body>

</html>