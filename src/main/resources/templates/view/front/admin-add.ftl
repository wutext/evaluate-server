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
    <script type="text/javascript" src="/layui/js/admin_list.js"></script>

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
                  <span class="x-red">*</span>登录名
              </label>
              <div class="layui-input-inline">
                  <input type="text" id="username" name="username" required="" lay-verify="required"
                  autocomplete="off" class="layui-input"  />
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>将会成为您唯一的登入名
              </div>
          </div>

            <div class="layui-form-item">
                <label for="company" class="layui-form-label">
                    <span class="x-red">*</span>所属公司
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="company" name="company" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span>
                </div>
            </div>

            <div class="layui-form-item">
                <label for="department" class="layui-form-label">
                    <span class="x-red">*</span>所属部门
                </label>
                <div class="layui-input-inline">
                    <select id="department" name="department" lay-filter="department">
                        <option select="" value="">请选择部门</option>
                    <#list departmentList as depart>
                        <option value="${depart.id}">${depart.name}</option>
                    </#list>
                    </select>
                </div>
                <div class="layui-input-inline">
                    <select id="departUtil" name="departUtil" lay-filter="departUtil" required="" lay-verify="required">

                    </select>
                </div>
            </div>


            <div class="layui-form-item">
                <label for="project" class="layui-form-label">
                    <span class="x-red">*</span>所属项目
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="project" name="project" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
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
                  autocomplete="off" class="layui-input" />
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
                  autocomplete="off" class="layui-input" >
              </div>
              <div class="layui-form-mid layui-word-aux">
                  <span class="x-red">*</span>
              </div>
          </div>
          <div class="layui-form-item">
              <label class="layui-form-label"><span class="x-red">*</span>角色</label>
              <div class="layui-input-block">
                <#list roles as role>
                    <input type="checkbox" lay-filter="description"
                           name="description" lay-skin="primary" title="${role.description}" value="${role.id}" />
                </#list>
              </div>
          </div>

          <div class="layui-form-item">
              <label for="L_pass" class="layui-form-label">
                  <span class="x-red">*</span>密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="L_pass" name="pass" required="" lay-verify="pass"
                  autocomplete="off" class="layui-input" />
              </div>
              <div class="layui-form-mid layui-word-aux">
                  6到16个字符
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
                  <span class="x-red">*</span>确认密码
              </label>
              <div class="layui-input-inline">
                  <input type="password" id="L_repass" name="repass" required="" lay-verify="repass"
                  autocomplete="off" class="layui-input" />
              </div>
          </div>
          <div class="layui-form-item">
              <label for="L_repass" class="layui-form-label">
              </label>
              <button  class="layui-btn" lay-filter="add" lay-submit="">
                  增加
              </button>
          </div>
      </form>
    </div>

    <script>
        layui.use(['form','layer', 'jquery'], function(){

            var form = layui.form
                    ,layer = layui.layer;

            //自定义验证规则
            form.verify({
                nikename: function(value){
                    if(value.length*1 < 5){
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