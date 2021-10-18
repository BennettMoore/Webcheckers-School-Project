<!DOCTYPE html>

<head>
    <link rel='icon' href='../img/favicon.ico' type='image/x-icon'/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>

<body>
<div class="page">

    <div style="text-align: center;"><h1><img src="../img/logo.png" alt="logo" class ="logo"></h1></div>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

    <#if userCount??>
      <#if userCount == 1>
        <div>There is ${userCount} user signed in.</div>
        <div>There is ${gameCount} game being played.</div>
      <#else>
        <div>There are ${userCount} users signed in.</div>
        <div>There are ${gameCount} games being played.</div>
      </#if>
    </#if>
      <#if signedInUsers??>
          <div>
              Signed-in Users:
              <ul class="signedInUsers">
                <#list signedInUsers as signedInUsername>
                  <li>
                    ${signedInUsername}
                    <#if signedInUsername != currentUser>
                      <form id="makegame", action="/make", method="post">
			            <input type="hidden" name="username" id="username" value="${signedInUsername}">
                        <input class="inbutton" type="submit" value="Play">
                      </form>
			        </#if>
		          </li>
                </#list>
              </ul>
              Created Games:
              <ul>
                  <#list createdGames as createdGamename>
    		      <form id="getgame", action="/game", method="get">
			<div>
			  <input type="hidden" name="gameid" id="gameid" value="${createdGamename}">
			</div>
			<div>
                    	  <li>${createdGamename}
			  <input class="inbutton" type="submit" value="Enter Game">
			</div>
    		      </form>
		    </li>
                  </#list>
              </ul>
          </div>
      </#if>

  </div>

</div>
</body>

</html>
