<#import "parts/common.ftl" as c>
<@c.page>
<a href="/users" xmlns="http://www.w3.org/1999/html">All users</a>
    <form method="post" action="/user">

        <input type="text" value="${user.username}" name="username">
        <#list roles as role>
            <div>
                <label>
                    <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>${role}
                </label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" value="Save"/>

    </form>
</@c.page>