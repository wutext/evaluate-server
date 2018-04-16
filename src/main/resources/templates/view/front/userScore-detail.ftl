<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/layui/css/font.css" media="all">
    <link rel="stylesheet" href="/layui/css/xadmin.css" media="all">
    <script language="JavaScript" type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
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


    <table class="layui-table">
        <thead>
        <tr>
            <th>员工姓名</th>
            <th>部门</th>
            <th>公司名称</th>
            <th>项目名称</th>
            <th>项目进度得分</th>
            <th>工作量得分</th>
            <th>工作质量得分</th>
            <th>工作效率得分</th>
            <th>工作态度得分</th>
            <th>出勤率得分</th>
            <th>进度偏差得分</th>
            <th>工作配合情况得分</th>
            <th>评分角色类型;1:处经理2:客户方3:平台</th>
            <th>创建时间</th>
            <th>项目经理签字</th>
            <th>合计得分</th>
        </tr>
        </thead>
        <tbody>
        <#list list as userScore>
        <tr>
            <td>${userScore.userName}</td>
            <td>${userScore.deptName}</td>
            <td>${userScore.companyName}</td>
            <td>${userScore.projectName}</td>
            <td>${userScore.progressCompletionScore}</td>
            <td>${userScore.workloadScore}</td>
            <td>${userScore.workQualityScore}</td>
            <td>${userScore.workEfficiencyScore}</td>
            <td>${userScore.workingAttitudeScore}</td>
            <td>${userScore.attendanceScore}</td>
            <td>${userScore.progressDeviationScore}</td>
            <td>${userScore.workCooperateScore}</td>
            <td style="text-align: center">
                <#if userScore.type == 1>处经理</#if>
                <#if userScore.type == 2>客户</#if>
                <#if userScore.type == 3>平台</#if>
            </td>
            <td>${userScore.createTime}</td>
            <td>${userScore.signName}</td>
            <td>${userScore.total}</td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>

</body>

</html>