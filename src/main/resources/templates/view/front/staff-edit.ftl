<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8" />
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="/layui/css/font.css" />
    <link rel="stylesheet" href="/layui/css/xadmin.css" />

    <script language="JavaScript" type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js" charset="utf-8"></script>
    <script language="JavaScript" type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/layui/js/staff_list.js"></script>

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="x-body">
    <form class="layui-form">
        <div class="layui-form-item">
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>姓名
            </label>
            <div class="layui-input-inline">

                <input type="hidden" id="id" name="id" required=""
                       autocomplete="off"  readonly="readonly" value="${user.id!}"/>

                <input type="text" id="staffName" name="staffName" required=""
                       autocomplete="off" class="layui-input"  value="${user.staffName!""}"/>
            </div>
            <#--<div class="layui-form-mid layui-word-aux">-->
                <#--<span class="x-red">*</span>将会成为您唯一的登入名-->
            <#--</div>-->
        </div>

        <div class="layui-form-item">
            <label for="username" class="layui-form-label">
                <span class="x-red">*</span>工号
            </label>
            <div class="layui-input-inline">
                <input type="text" id="staffNo" name="staffNo" required=""  lay-verify="staffNo"
                       autocomplete="off" class="layui-input" value="${user.staffNo!""}" />
            </div>
            <div class="layui-form-mid layui-word-aux unique-name">
                <span class="x-red">*</span>将会成为您唯一的工号
            </div>
        </div>

        <div class="layui-form-item">
            <label for="phone" class="layui-form-label">
                <span class="x-red">*</span>所属公司
            </label>
            <div class="layui-input-inline">
                <input type="text" id="company" name="company" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${user.company!""}"/>
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="company" class="layui-form-label">
                <span class="x-red">*</span>职位
            </label>
            <div class="layui-input-inline">
                <input type="text" id="position" name="position" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${user.position!""}"/>
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>
            </div>
        </div>

        <input id="departmentId" name="departmentId" type="hidden" value="${departmentId!""}">
        <input id="departUtilId" name="departUtilId" type="hidden" value="${departUtilId!""}">
        <div class="layui-form-item">
            <label for="department" class="layui-form-label">
                <span class="x-red">*</span>所属部门
            </label>

            <div class="layui-input-inline">
                <select id="department" name="department" lay-filter="department">
                    <option select="" value="">请选择部门</option>
                <#list departmentList as depart>
                    <option
                        <#if departmentId?? && depart.id==departmentId>select="" </#if>
                        value="${depart.id}">${depart.name}
                    </option>
                </#list>
                </select>
            </div>
            <div class="layui-input-inline">
                <select id="departUtil" name="departUtil" lay-filter="departUtil" required="" lay-verify="required">

                        <#list departmentList as depart>
                            <#if departmentId?? && depart.id==departmentId && (depart.departUtil?? && (depart.departUtil?size>0))>
                                <#list depart.departUtil as util>
                                <option
                                    <#if departUtilId?? && util.id==departUtilId>select="" </#if>
                                            value="${util.id}">${util.name}
                                    </option>
                                </#list>
                            </#if>
                        </#list>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="phone" class="layui-form-label">
                <span class="x-red">*</span>所属项目
            </label>
            <div class="layui-input-inline">
                <input type="text" id="project" name="project" required="" lay-verify="required"
                       autocomplete="off" class="layui-input" value="${user.project!""}"/>
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="phone" class="layui-form-label">
                <span class="x-red">*</span>手机
            </label>
            <div class="layui-input-inline">
                <input type="text" id="phone" name="phone" required="" lay-verify="phone"
                       autocomplete="off" class="layui-input" value="${user.phone!""}"/>
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>
            </div>
        </div>
        <div class="layui-form-item">
            <label for="L_email" class="layui-form-label">
                <span class="x-red">*</span>邮箱
            </label>
            <div class="layui-input-inline">
                <input type="text" id="L_email" name="email" required="" lay-verify="email"
                       autocomplete="off" class="layui-input" value="${user.email!""}" />
            </div>
            <div class="layui-form-mid layui-word-aux">
                <span class="x-red">*</span>
            </div>
        </div>



    <#if operate == "edit">
        <div class="layui-form-item">
            <label for="L_repass" class="layui-form-label">
            </label>
            <button  class="layui-btn" lay-filter="add" lay-submit="">
                修改
            </button>
        </div>
    </#if>
    </form>
</div>

<script>
    layui.use(['form','layer', 'jquery'], function(){

        var form = layui.form
                ,layer = layui.layer;

        //自定义验证规则
        form.verify({
            username: function(value){
                if(value.length*1 < 2){
                    return '昵称至少得5个字符啊';
                }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
        });

        $("#department").val($("#departmentId").val());
        $("#departUtil").val($("#departUtilId").val());
        form.render();
    });
</script>
<#--<script>var _hmt = _hmt || []; (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
  })();
</script>-->
</body>

</html>