<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    <h2>Login page</h2>
    <@l.login "/login" "Sign in"/>
    <a href = "/registration">Add new user</a>
</@c.page>