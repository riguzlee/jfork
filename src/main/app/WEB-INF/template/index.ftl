<#assign content>
    <div class="content">
        <h1><img src="img/nut.png" alt="LOGO" style="height:48px">jfork</h1>
        <hr/>
        <div class="debug">
            <table class="debug-table">
                <tr>
                    <th>Token:</th>
                    <td>${token!}</td>
                </tr>
                <tr>
                    <th>JSESSIONID:</th>
                    <td>${jsessionid!}</td>
                </tr>
                <tr>
                    <th>SESSIONID:</th>
                    <td>${jforksessionid!}</td>
                </tr>
                <tr>
                    <th>User:</th>
                    <td><#if (user.userId)??>${user.userId}<#else>NULL</#if></td>
                </tr>
            </table>
        </div>
        <hr/>
    </div>
</#assign>
<#include "public/layout.ftl"/> 