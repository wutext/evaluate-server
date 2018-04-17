<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset="UTF-8">
    <title>menuTree</title>

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
                url: "/tree/menuTreeList",
                success: function(res) {

                    var data = res.menu;
                    tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                    var nodes = tree.getNodesByParam("level", 0);
                    for(var i=0; i<nodes.length; i++) {
                        tree.expandNode(nodes[i], true, false, false);
                    }

                    // 默认选择节点
                    var ids = res.selectIds;
                    for(var i=0; i<ids.length; i++) {
                        var node = tree.getNodeByParam("id", ids[i]);
                        tree.checkNode(node, true, true);
                        /*if(node.id!=1 && node.id == ids[i]) {
                            tree.checkNode(node, true, true);
                        }*/
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
        <ul id="treeDemo" class="ztree"></ul>
</div>
</body>
</html>