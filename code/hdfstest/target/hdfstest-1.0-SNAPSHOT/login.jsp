<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>登录注册页面</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container right-panel-active">
  <div class="container__form container--signup">
    <form action="RegisterServlet" class="form" method="post">
      <h2 class="form__title">注册</h2>
      <input type="text" placeholder="用户名" class="input" name="username"/>
      <input type="password" placeholder="密码" class="input" name="password1"/>
      <input type="password" placeholder="确认密码" class="input" name="password2"/>
      <button class="btn" type="submit">注册</button>
    </form>
  </div>
  <div class="container__form container--signin">
    <form action="LoginServlet" class="form">
      <h2 class="form__title">登录</h2>
      <input type="text" placeholder="用户名" class="input" name="user"/>
      <input type="password" placeholder="密码" class="input" name="password"/>
      <button class="btn" type="submit">登录</button>
    </form>
  </div>
  <div class="container__overlay">
    <div class="overlay">
      <div class="overlay__panel overlay--left">
        <button class="btn" id="signIn">登录</button>
      </div>
      <div class="overlay__panel overlay--right">
        <button class="btn" id="signUp">注册</button>
      </div>
    </div>
  </div>
</div>
<script  src="js/script.js"></script>
</body>
</html>
