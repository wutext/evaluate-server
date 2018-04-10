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
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
      <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">.table td i{margin:0 2px;}</style>
    <script type="text/javascript">
        $(document).ready(function() {

            $("#treeTable").treeTable({ expandable: true });
        });
    </script>
  </head>
  
  <body>

    <div>
        <table id="treeTable" class="layui-table" lay-size="sm" style="width:100%">
            <thead>
              <tr>
                  <th>名称</th>
                  <th>链接</th>
                  <th>排序</th>
                  <th>可见</th>
                  <th>权限标识</th>
                  <th>操作</th>
              </tr>
            </thead>
            <tr id="0">
                <td>用户管理</td>
                <td>http://localhost:9999</td>
                <td>user:view</td>
                <td>
                    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">
                        <i class="layui-icon">&#xe63c;</i>
                    </a>
                    <a class="layui-btn layui-btn-xs" lay-event="edit" >
                        <i class="layui-icon">&#xe642;</i>
                    </a>
                    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">
                        <i class="layui-icon">&#xe640;</i>
                    </a>
                </td>
            </tr>
            <tr id="1">
                <td><span controller="true">1</span></td>
                <td>内容</td></tr>
            <tr id="2" pId="1">
                <td><span controller="true">2</span></td>
                <td>内容</td></tr>
            <tr id="3" pId="2">
                <td>3</td>
                <td>内容</td>
            </tr>
            <tr id="4" pId="2">
                <td>4</td>
                <td>内容</td>
            </tr>
            <tr id="5" pId="4">
                <td>4.1</td>
                <td>内容</td>
            </tr>
            <tr id="6" pId="1" hasChild="true">
                <td>5</td>
                <td>注意这个节点是动态加载的</td>
            </tr>
            <tr id="7">
                <td>8</td>
                <td>内容</td>
            </tr>
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