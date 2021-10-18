 <div class="navigation">
  <#if currentUser??>
     <div style="text-align: center;">
      <a href="/"><button class="button button1">Home</button></a>
    <form id="signout" action="/signout" method="post">
      <a href="#" onclick="event.preventDefault(); signout.submit();"><button class="button button1">
              Sign Out - ${currentUser}</button></a>
    </form>
         </div>

  <#else>
      <div style="text-align: center;"><a href="/signin"><button class="button button1">Sign In</button></a></div>
  </#if>
 </div>
