<#macro login path text>
<form action="${path}" method="post">
    <div class="form-group">
        <label for="usernameInput">User Name :</label>
        <input type="text" name="username" class="form-control" id="usernameInput" aria-describedby="username">
    </div>
    <div class="form-group">
        <label for="passwordInput">Password: </label>
        <input type="password" name="password" class="form-control" id="passwordInput">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"></input>
    <button type="submit" class="btn btn-primary">${text}</button>
</form>
</#macro>

<#macro enterForm path text>
<form class="form-inline my-2 my-lg-0" action="${path}" method="post">
    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">${text}</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>
</#macro>