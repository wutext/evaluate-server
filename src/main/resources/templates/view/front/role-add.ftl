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
    <script type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
      <script type="text/javascript" src="/layui/js/admin_common.js"></script>
      <script type="text/javascript" src="/layui/js/admin_role.js"></script>

    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body>
    <div class="x-body">
        <form action="" method="post" class="layui-form layui-form-pane">
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>角色名
                    </label>
                    <div class="layui-input-inline">
                        <input type="hidden" id="id" name="id" required=""
                               autocomplete="off" class="layui-input"  <#if role??>value="${role.id}" </#if>/>

                        <input type="text" id="name" name="name" required="" lay-verify="required"
                                autocomplete="off" class="layui-input" <#if role?? >value="${role.role!""}" </#if>/>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text">
                    <label class="layui-form-label">
                        拥有权限
                    </label>
                    <table  class="layui-table layui-input-block">
                        <tbody>
                            <tr>
                                <td>
                                    用户管理
                                    <#--<input name="id[]" type="checkbox" value="">-->
                                </td>
                                <td>
                                    <div class="layui-input-block">

                                        <#if role??>
                                            <#list permissions as permis>
                                                <input name="perId" <#list role.perIds as perId><#if perId == permis.id>checked=""</#if></#list>  type="checkbox" value="${permis.id}" /> ${permis.name}
                                            </#list>
                                        <#else>

                                           <#list permissions as permis>
                                               <input name="perId"  type="checkbox" value="${permis.id}" /> ${permis.name}
                                           </#list>

                                        </#if>

                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="layui-form-item">
                    <label for="desc" class="layui-form-label">
                        描述
                    </label>
                    <div class="layui-input-inline">

                        <input type="text" id="description" name="description" required="" lay-verify="required"
                               autocomplete="off" class="layui-input" <#if role?? >value="${role.description!""}" </#if>/>

                    </div>
                </div>

                <div class="layui-input-inline">
                    <input type="button" id="permTree" <#--onclick="roleOperation('分配权限', '/tree/menuTreeView', 200,300)"--> value="选择权限" />
                </div>

                <div class="layui-form-item">
                    <button  class="layui-btn" lay-submit="" lay-filter="add">增加</button>
                </div>
            </form>
    </div>
    <script>
        layui.use(['form','layer'], function(){
            $ = layui.jquery;
          var form = layui.form
          ,layer = layui.layer;


          form.verify({
            nikename: function(value){
              if(value.length < 5){
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

          form.on('submit(add)', function(data){

              var perIds=new Array();
              $('input[name="perId"]:checked').each(function(){
                  perIds.push($(this).val());
              });

              $.ajax({
                  type: "post",
                  url: "/role/addRoleDo",
                  data: JSON.stringify({
                      id: data.field.id,
                      role: data.field.name,
                      description: data.field.description,
                      perIds: perIds
                  }),
                  contentType: "application/json;charset=UTF-8",
                  success: function(res) {

                      layer.alert("增加成功", {icon: 6});
                          // 获得frame索引
                      var index = parent.layer.getFrameIndex(window.name);
                      //关闭当前frame
                      parent.layer.close(index);
                  },
                  error: function(data, status, e){
                      alert(e+".......error");

                  }
              });
              return false;
          });
        });

        $(function() {

            $("#permTree").on("click", function() {
                roleOperation('分配权限', '/tree/menuTreeView', 200,300);
            });

        })
    </script>
    <script>var _hmt = _hmt || []; (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
      })();
    </script>
  </body>

</html>