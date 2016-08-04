<#assign content>
    <div class="content">
        <h1>jfork</h1>
        <p>Token:${token!}</p>
        <p>JSESSIONID:${jsessionid!}</p>
        <p>User:<#if (user.userId)??>${user.userId}<#else>NULL</#if></p>
        <form action="doLogin" method="post">
            <input type="text" name="user">
            <input type="password" name="passwd">
            <input type="submit">
        </form>
    </div>
</#assign>
<#include "public/layout.ftl"/> 