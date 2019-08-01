<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>

    <div>
        <p> Add new user</p>
        <p style="color:red;">
            ${message}
        </p>
    </div>
<@l.login "/registration" "Registration"/>
</@c.page>