<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta context="text/html" charset="UTF-8" />
    <title>辩题</title>

    <script type="text/javascript" src="/researchstyle/starScore.js"></script>
    <script type="text/javascript" src="/jquery/js/jquery-3.1.1.min.js"></script>
    <style>
    body,li,p,ul {
        margin: 0;
        padding: 0;
        font: 12px/1 Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif;
    }
    ul, li, ol { list-style: none; }

    /* 重置文本格式元素 */
    a {
        text-decoration: none;
        cursor: pointer;
        color:#333333;
        font-size:14px;
    }
    a:hover {
        text-decoration: none;
    }
    .clearfix::after{
        display:block;
        content:'';
        height:0;
        overflow:hidden;
        clear:both;
    }

    /*星星样式*/
    .content{
        width:600px;
        margin:0 auto;
        padding-top:20px;
    }
    .title{
        font-size:14px;
        background:#dfdfdf;
        padding:10px;
        margin-bottom:10px;
    }
    .block{
        width:100%;
        margin:0 0 20px 0;
        padding-top:10px;
        padding-left:50px;
        line-height:21px;
    }
    .block .star_score{
        float:left;
    }
    .star_list{
        height:21px;
        margin:50px;
        line-height:21px;
    }
    .block p,.block .attitude{
        padding-left:20px;
        line-height:21px;
        display:inline-block;
    }
    .block p span{
        color:#C00;
        font-size:16px;
        font-family:Georgia, "Times New Roman", Times, serif;
    }

    .star_score {
        background:url(/image/stark2.png);
        width:160px;
        height:21px;
        position:relative;
    }

    .star_score a{
        height:21px;
        display:block;
        text-indent:-999em;
        position:absolute;
        left:0;
    }

    .star_score a:hover{
        background:url(/image/stars2.png);
        left:0;
    }

    .star_score a.clibg{
        background:url(/image/stars2.png);
        left:0;
    }


</style>
</head>
<body>

<div class="content">

    <div id="startone"  class="block clearfix" >
        <div class="star_score"></div>
        <p style="float:left;">您的评分：<span class="fenshu"></span> 分</p>

    </div>
</div>

<script>
    scoreFun($("#startone"))
</script>

</body>
</html>