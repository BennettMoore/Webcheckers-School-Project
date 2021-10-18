<!DOCTYPE html>

<head>
  <link rel='icon' href='../img/favicon.ico' type='image/x-icon'/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <form id="signin", action="/signin", method="post">
      <div>
        <input type="text" name="username" id="username" placeholder="Username" autofocus required>
      </div>
      <div>
        <input type="submit" value="Sign In">
      </div>
    </form>

  </div>

</div>
</body>

</html>
