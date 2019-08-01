<#macro login path text>
<form action="${path}" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="${text}"/></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"></input>
</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="submit" value="Sign Out"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}"></input>
</form>
</#macro>