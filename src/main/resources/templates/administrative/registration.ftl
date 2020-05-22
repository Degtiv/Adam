<#import "/parts/common.ftl" as c>
<#import "/parts/login.ftl" as l>
<#import "/parts/pageTitle.ftl" as pt>
<@c.page>
<@pt.pageTitle "Registration"/>
<div class="col-6 container-fluid">
    <div>

        <p style="color:red;">
            ${message?ifExists}
        </p>
    </div>
    <@l.login "/registration" "Register"/>
</div>
</@c.page>