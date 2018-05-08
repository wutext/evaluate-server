<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8"/>
    <title>员工管理</title>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="/layui/css/font.css" media="all">
    <link rel="stylesheet" href="/layui/css/xadmin.css" media="all">

    <script language="JavaScript" type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js" charset="utf-8"></script>
    <script language="JavaScript" type="text/javascript" src="/jquery/js/clipBoard.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/js/staff_list.js"></script>
    <script type="text/javascript" src="/layui/js/admin_common.js"></script>
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
    <a class="layui-btn layui-btn-small fsh" style="line-height:1.6em;margin-top:3px;float:right"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
</div>
<div class="x-body">
    <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so">

            <input id="batchId" name="batchId" type="hidden" value="${batchId!""}">
            <input id="departmentId" name="departmentId" type="hidden" value="${departmentId!""}">
            <input id="departUtilId" name="departUtilId" type="hidden" value="${departUtilId!""}">

            <div class="layui-form-item">

                <label class="layui-form-label" style="width:50px">选择批次</label>
                <div class="layui-input-inline">
                    <select id="batch" name="batch" lay-filter="batch">
                    <#list batchList as batch>
                        <option
                            <#if batch.id==batchId>select="" </#if>
                            value="${batch.id}">${batch.batchNumber}
                        </option>
                    </#list>
                    </select>
                </div>

                <label class="layui-form-label" style="width:25px">部门</label>
                <div class="layui-input-inline layui-form">
                    <select id="department" name="department" lay-filter="department">
                        <option select="" value="">请选择部门</option>
                    <#list departments as depart>
                        <option
                            <#if departmentId?? && depart.id==departmentId>select="" </#if>
                            value="${depart.id}">${depart.name}
                        </option>
                    </#list>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <select id="departUtil" name="departUtil" lay-filter="departUtil">

                    </select>
                </div>
                <div class="layui-input-inline">
                    <input type="text" id="staffName" name="staffName" placeholder="请输入用户名" autocomplete="off"
                           class="layui-input" value="${staffName!""}"/>
                </div>

                <div class="layui-input-inline" style="width:0px">
                    <button class="layui-btn search" onclick="searchUserPage()" lay-submit="" lay-filter="sreach"><i
                            class="layui-icon">&#xe615;</i></button>
                </div>
            </div>
        </form>
    </div>
    <xblock>
        <button class="layui-btn delAll layui-btn-danger" id="deleteAll">批量删除</button>
        <button class="layui-btn" onclick="addOperation('用户添加', '/staff/addStaffView')"><i class="layui-icon"></i>添加
        </button>
    </xblock>

    <table class="layui-table" id="table_user" lay-filter="table_demo"></table>

</div>

<script type="text/html" id="toolBar">
    <#--<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">
        <i class="layui-icon">&#xe63c;</i>
    </a>-->

    <a class="layui-btn layui-btn-xs" lay-event="edit">
        <i class="layui-icon">&#xe642;</i>
    </a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">
        <i class="layui-icon">&#xe640;</i>
    </a>
    |

    <#-- true代表可执行，false代表不可执行; 此处根据user_score中type的状态来判断是否存在此
        某个身份人对该用户打过分，yes：按钮不可用并置灰；no:按钮正常
    -->
    {{#
    var clone1 = true;
    var clone2 = true;
    var clone3 = true;
    var clone4 = true;
    }}
    <a

            {{# layui.each(d.raters, function(index, item){ }}

            {{# if(item=== 1){ }}

            {{# clone1=false }}
            {{# } }}

            {{# }); }}

            {{# if(clone1){ }}

            lay-event="clone1"
            {{# } }}

            class="layui-btn

         {{#  if(clone1){ }}
            layui-bg-cyan
            {{#  } else { }}
            layui-bg-gray
         {{#  } }}
          layui-btn-xs clone"
    >
        客户
    </a>


    <a

            {{# layui.each(d.raters, function(index, item){ }}

            {{# if(item=== 2){ }}

            {{# clone2=false }}
            {{# } }}

            {{# }); }}

            {{# if(clone2){ }}

            lay-event="clone2"
            {{# } }}

            class="layui-btn

         {{#  if(clone2){ }}
            layui-bg-cyan
            {{#  } else { }}
            layui-bg-gray
         {{#  } }}
          layui-btn-xs clone"
    >
        经理
    </a>
    <a
            {{# layui.each(d.raters, function(index, item){ }}

            {{# if(item=== 3){ }}

            {{# clone3=false }}
            {{# } }}

            {{# }); }}

            {{# if(clone3){ }}

            lay-event="clone3"
            {{# } }}

            class="layui-btn

         {{#  if(clone3){ }}
            layui-bg-cyan
            {{#  } else { }}
            layui-bg-gray
         {{#  } }}
          layui-btn-xs clone"

    >
        人事
    </a>
    <a
            {{# layui.each(d.raters, function(index, item){ }}

            {{# if(item=== 4){ }}

            {{# clone4=false }}
            {{# } }}

            {{# }); }}

            {{# if(clone3){ }}

            lay-event="clone4"
            {{# } }}

            class="layui-btn

         {{#  if(clone4){ }}
            layui-bg-cyan
            {{#  } else { }}
            layui-bg-gray
         {{#  } }}
          layui-btn-xs clone"

    >
        销售类型
    </a>

</script>

<script>
    layui.use(['laydate', 'form', 'layer'], function () {
        var laydate = layui.laydate
                , $ = layui.jquery, layer = layui.layer, form = layui.form;


        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });


</script>
<script>var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();
</script>


</body>

</html>