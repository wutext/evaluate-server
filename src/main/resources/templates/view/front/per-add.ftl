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
                    <span class="x-red">*</span>上级菜单名称
                </label>
                <div class="layui-input-inline">
                    <input type="hidden" id="parId" name="parId" required=""
                           autocomplete="off" class="layui-input" value="${permission.id}"/>

                    <input type="text" autocomplete="off" readonly="readonly" class="layui-input"value="${permission.name!""}" />
                </div>
            </div>

            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>名称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="name" name="name" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
                </div>
            </div>

            <#if permission.id!=1>
            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>链接
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="url" name="url" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
                </div>
            </div>
            </#if>
            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>权限类型
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="resourceType" name="resourceType" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
                </div>
            </div>

            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>权限标识
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="permission" name="permission" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" />
                </div>
            </div>

            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>排序
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="sort" name="sort" required="" lay-verify="number"
                           autocomplete="off" class="layui-input" />
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <span class="x-red">*只能填写数字</span>
                </div>

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
          ,layer = layui.layer,element = layui.element;


          form.verify({
           number: [/^[1-9]\d*$/, '只能填写数字']
          });

            $("#name").blur(function() {
                var name = "";
                name=$(this).val();
                if(name!="") {
                    $.ajax({
                        type: "post",
                        url: "/per/verifyPermissionName",
                        data: JSON.stringify(name),
                        contentType: "application/json;charset=UTF-8",
                        success: function(res) {
                            if(res) {
                                layer.alert("权限名已存在");
                            }
                        },error: function(xml, status, e) {
                            alert(e+"error");
                        }

                    });
                    return false;
                }
            });

          form.on('submit(add)', function(data){

              var params = {};
              params.parId = data.field.parId;
              params.name = data.field.name;
              params.permission = data.field.permission;
              params.resourceType = data.field.resourceType;
              params.sort = data.field.sort;
              params.url = data.field.url;
              $.ajax({
                  type: "post",
                  url: "/per/addPermDo",
                  data: JSON.stringify(params),
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

                  },
                  error: function(data, status, e){
                      alert(data+"......."+status+"....."+e );

                  }
              });
              return false;
          });
        });
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