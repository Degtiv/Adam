<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<div>
    <b>-Greetings, your majesty! Adam, financial consultant at your service!</b>
</div>
<div>
    <a href="/main">See transactions</a></br>
    <a href="/user">See users</a>
    <@l.logout />
</div>
</@c.page>