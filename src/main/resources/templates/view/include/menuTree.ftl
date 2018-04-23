<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset="UTF-8">
    <title>menuTree</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <link rel="stylesheet" href="/zTree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="/zTree/js/jquery.ztree.excheck.js"></script>

    <script type="text/javascript">

        var tree, setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback:{

            }
        };

        $(document).ready(function(){

            $.ajax({
                type: "post",
                url: "/tree/menuTreeList?roleId=${roleId}",
                success: function(res) {

                    var data = res.menu;
                    tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                    var nodes = tree.getNodesByParam("level", 0);
                    for(var i=0; i<nodes.length; i++) {
                        tree.expandNode(nodes[i], true, false, false);
                    }

                    // 默认选择节点
                    //判断是添加页面还是编辑页面
                    var attr = $("#attr").val();
                    var ids = res.selectIds;
                    if(attr=='add') {

                        tree.checkAllNodes(true);
                    }else if(attr == 'edit') {

                        for(var i=0; i<ids.length; i++) {
                            var node = tree.getNodeByParam("id", ids[i]);

                            if(!node.isParent && node.id == ids[i]) {

                                tree.checkNode(node, true, true);
                            }
                        }
                    }


                }
            });
        });

        function getIds() {

            var treeObj = $.fn.zTree.getZTreeObj("treeDemo"), nodes = treeObj
                    .getCheckedNodes(true), v = "";
            for (var i = 0; i < nodes.length; i++) {
                v += nodes[i].id + ",";
                //alert(nodes[i].name); //获取选中节点的名称
            }
            return v;
        }
    </script>
</head>
<body>

<div class="content_wrap" style="width:100%;height:100%">
        <input id="attr" name="attr" type="hidden" value="${attr}" />
        <ul id="treeDemo" class="ztree"></ul>
</div>
</body>
</html>