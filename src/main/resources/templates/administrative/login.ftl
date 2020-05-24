<#import "/parts/common.ftl" as c>
<#import "/parts/login.ftl" as l>
<#import "/parts/pageTitle.ftl" as pt>
<@c.page>
<@pt.pageTitle "Login page"/>
    <div class="col-6 container-fluid">
        <@l.login "/login" "Sign in"/>
        <hr>
        <a class="btn btn-outline-primary" href="/registration" role="button">Registration</a>
    </div>
</@c.page>