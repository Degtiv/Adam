<#include "security.ftl">
<#import "/parts/login.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <b><a class="navbar-brand" href="/">Adam</a></b>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/overview">Overview</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/transactions">Transactions</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/goals">Goals</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/rules">Rules</a>
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
            <#if isLogin>
                <@l.enterForm "/login" "Sign out"/>
            <#else>
                <@l.enterForm "/logout" "Sign in"/>
            </#if>
        </div>
    </div>
</nav>