<#assign content>
    <div class="content">
        <h1><img src="img/nut.png" alt="LOGO" style="height:48px">jfork</h1>
        <hr/>
        <form action="doLogin" method="post">
            <input type="text" name="user">
            <input type="password" name="passwd">
            <input type="submit" value="Login">
        </form>
        <br/>
    </div>
</#assign>
<#include "public/layout.ftl"/> 