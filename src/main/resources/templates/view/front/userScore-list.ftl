<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8"/>
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layui/css/font.css" media="all">
    <link rel="stylesheet" href="/layui/css/xadmin.css" media="all">

    <script language="JavaScript" type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/js/admin_userScore.js"></script>
    <script type="text/javascript" src="/layui/js/admin_common.js"></script>
    <script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <style>

        body {
            overflow-y: scroll;
        }
    </style>
    <![endif]-->
</head>

<body>
<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">演示</a>
        <a>
          <cite>导航元素</cite></a>
      </span>
    <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">
            时间:<input type="text" name="time" id="time" class="layui-input"
                      onclick="WdatePicker()" value="${time!""}"/>
            用户名:<input type="text" id="username" name="username"  autocomplete="off"
                       class="layui-input" value="${username!""}"/>
            <button class="layui-btn search" onclick="searchUserScorePage()" lay-submit="" lay-filter="sreach"><i
                    class="layui-icon">&#xe615;</i></button>
        </form>
    </div>

    <table class="layui-table" lay-size="sm" id="table_userScore" lay-filter="table_demo"></table>

</div>


<script type="text/html" id="toolBar">
    <a class="layui-btn layui-btn-normal" lay-event="detail">
        <i class="layui-icon">详情</i>
    </a>
</script>


</body>

</html>