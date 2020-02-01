<#import "/parts/common.ftl" as c>
<#import "/parts/login.ftl" as l>
<#import "/parts/pageTitle.ftl" as pt>
<@c.page>
<@pt.pageTitle "Registration"/>
    <div>

        <p style="color:red;">
            ${message?ifExists}
        </p>
    </div>
<@l.login "/registration" "Registration"/>
</@c.page>