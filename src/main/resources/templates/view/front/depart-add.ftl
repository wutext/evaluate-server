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
                    <span class="x-red">*</span><#if departUtil??>上级</#if>部门名称
                </label>
                <div class="layui-input-inline">
                    <input type="hidden" id="departId" name="departId" required=""
                           autocomplete="off" class="layui-input" value="${department.id!""}"/>

                    <input type="text" name="departmentName" autocomplete="off" <#if departUtil??>readonly="readonly"</#if> class="layui-input" value="${department.name!""}" />
                </div>
            </div>

        <#if type=='editUtil' || (type=='addNext' && department.id??)>
            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>处名称
                </label>
                <div class="layui-input-inline">

                    <input type="hidden" id="utilId" name="utilId" required=""
                           autocomplete="off" class="layui-input" <#if departUtil??>value="${departUtil.id!""}"</#if>/>

                    <input type="text" id="name" name="utilName" required="" lay-verify="required"
                           autocomplete="off" class="layui-input" <#if departUtil??>value="${departUtil.name!""}"</#if>/>
                </div>
            </div>

        </#if>

            <div class="layui-form-item">
                <label for="name" class="layui-form-label">
                    <span class="x-red">*</span>排序
                </label>
                <div class="layui-input-inline">

                    <#if type=='editDepart'>
                        <input type="text" id="sort" name="sort" required="" lay-verify="number"
                           autocomplete="off" class="layui-input" value="${department.sort!""}"/>
                    <#elseif type=='editUtil'>
                        <input type="text" id="sort" name="sort" required="" lay-verify="number"
                               autocomplete="off" class="layui-input" value="${departUtil.sort!""}"/>
                    <#else>
                        <input type="text" id="sort" name="sort" required="" lay-verify="number"
                               autocomplete="off" class="layui-input" value=""/>
                    </#if>
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

          form.on('submit(add)', function(data){

              var params = {};
              params.type = '${type}';
              params.departId = data.field.departId;
              params.departName = data.field.departmentName;
              params.utilId = data.field.utilId;
              params.utilName = data.field.utilName;
              params.sort = data.field.sort;

              $.ajax({
                  type: "post",
                  url: "/department/addDepartDo",
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
                      alert(e);
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