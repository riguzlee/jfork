<#assign content>
    <div class="content">
        <h1><img src="img/nut.png" alt="LOGO" style="height:48px">jfork</h1>
        <hr/>
        <form action="doLogin" method="post">
            <table class="login-table">
                <tr>
                    <td><label>User Name:</label></td>
                    <td><input type="text" name="user" placeholder="User name"></td>
                </tr>
                <tr>
                    <td><label>Password:</label></td>
                    <td><input type="password" name="passwd" placeholder="Password"></td>
                </tr>
                <tr>
                    <td><label>Remember Me:</label></td>
                    <td><input type="checkbox" name="remember" style="margin-right:0"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </table>
        </form>
        <br/>
    </div>
</#assign>
<#include "public/layout.ftl"/> 