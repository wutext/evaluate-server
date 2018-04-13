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
    <script type="text/javascript" src="/layui/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/treeTwo/jquery.treeTable.min.js"></script>
    <script type="text/javascript" src="/layui/js/admin_common.js"></script>
    <script type="text/javascript" src="/layui/js/admin_rule.js"></script>

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
            <a href="">管理员管理</a>
            <a>
              <cite>导航元素</cite>
            </a>
          </span>
          <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
              <i class="layui-icon" style="line-height:30px">ဂ</i></a>
      </div>
        <div class="x-body">

            <table id="treeTable" class="layui-table" lay-size="sm" style="width:100%">
                <thead>
                  <tr>
                      <th>名称</th>
                      <th>链接</th>
                      <th>排序</th>
                      <th>权限类型</th>
                      <th>权限标识</th>
                      <th>操作</th>
                  </tr>
                </thead>

                <#list perList as perm>
                    <#--<#if perm.id!=1>-->
                        <tr id="${perm.id}" <#if perm.par??>pId="${perm.par.id!""}"</#if>>
                            <td>${perm.name!""}</td>
                            <td>${perm.url!""}</td>
                            <td>${perm.sort!""}</td>
                            <td>${perm.resourceType!""}</td>
                            <td>${perm.permission!""}</td>


                            <td>
                            <#if perm.id!=1>
                                <!-- 修改 -->
                                <a class="layui-btn layui-btn-xs layui-btn-xs" onclick="addOperation('权限修改', '/per/permEdit?id=${perm.id}')" lay-event="edit">
                                    <i class="layui-icon">&#xe642;</i>
                                </a>

                                <!-- 删除 -->
                                <a class="layui-btn layui-btn-danger" lay-event="del" onclick="delPerm('/per/deleteSinglePerm?id=${perm.id}')" >
                                    <i class="layui-icon">&#xe640;</i>
                                </a>
                            </#if>
                                <!-- 添加 -->
                                <a class="layui-btn layui-btn-primary layui-btn-xs" onclick="addOperation('添加下级菜单', '/per/addPermView?id=${perm.id}')" lay-event="add">
                                    <i class="layui-icon">&#xe61f</i>
                                </a>
                            </td>


                        </tr>
                    <#--</#if>-->
                </#list>
            </table>
        </div>
    <#--<div class="x-nav">
      <span class="layui-breadcrumb">
        <a href="">首页</a>
        <a href="">演示</a>
        <a>
          <cite>导航元素88</cite></a>
      </span>
      <a class="layui-btn layui-btn-small" style="line-height:1.6em;margin-top:3px;float:right" href="javascript:location.replace(location.href);" title="刷新">
        <i class="layui-icon" style="line-height:30px">ဂ</i></a>
    </div>
    <div class="x-body">
      <div class="layui-row">
        <form class="layui-form layui-col-md12 x-so layui-form-pane">
          <div class="layui-input-inline">
            <select name="cateid">
              <option>规则分类</option>
              <option>文章</option>
              <option>会员</option>
              <option>权限</option>
            </select>
          </div>
          <div class="layui-input-inline">
            <select name="contrller">
              <option>请控制器</option>
              <option>Index</option>
              <option>Goods</option>
              <option>Cate</option>
            </select>
          </div>
          <div class="layui-input-inline">
            <select name="action">
              <option>请方法</option>
              <option>add</option>
              <option>login</option>
              <option>checklogin</option>
            </select>
          </div>
          <input class="layui-input" placeholder="权限名" name="cate_name" />
          <button class="layui-btn"  lay-submit="" lay-filter="sreach"><i class="layui-icon"></i>增加</button>
        </form>
      </div>
      <xblock>
        <button class="layui-btn layui-btn-danger" onclick="delAll()"><i class="layui-icon"></i>批量删除</button>
        <span class="x-right" style="line-height:40px">共有数据：88 条</span>
      </xblock>-->


  </body>

</html>