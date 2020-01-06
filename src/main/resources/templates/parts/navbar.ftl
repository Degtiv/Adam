<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <b><a class="navbar-brand" href="/">Adam</a></b>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/transactions">Transactions</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/goals">Goals</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-2">
            ${name}
        </div>

        <div class="form-inline my-2 my-lg-0">
            <form action="/logout" method="post">
                <input type="submit" value="Sign Out"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"></input>
            </form>
        </div>
    </div>
</nav>