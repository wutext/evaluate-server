<!DOCTYPE html>
<html lang="zh-CN" xmlns="http://www.thymeleaf.org">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>登陆丨Sharelink</title>
<link rel="stylesheet" href="/css/style.css"/>
<body>

<div class="login-container">
    <h1>人员月度考评微服务</h1>

    <div class="connect">
        <p>Link the world. Share to world.</p>
    </div>

    <form action="/login" method="post" id="loginForm">
        <div>
            <input type="text" name="username" class="username" placeholder="用户名" autocomplete="off"/>
        </div>
        <div>
            <input type="password" name="password" class="password" placeholder="密码" oncontextmenu="return false" onpaste="return false" />
        </div>
        <button id="submit" type="submit">登 陆</button>
    </form>

    <a href="/sys/register">
        <button type="button" class="register-tis">还有没有账号？</button>
    </a>

</div>

</body>
<script src="/jquery/js/jquery-3.1.1.min.js"></script>
<script src="/jquery/js/common.js"></script>
<!--背景图片自动更换-->
<script src="/jquery/js/supersized.3.2.7.min.js"></script>
<script src="/jquery/js/supersized-init.js"></script>
<!--表单验证-->
<script src="/jquery/js/jquery.validate.min.js?var1.14.0"></script>

</html>