<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<#list balances as balance>
<div class="clearfix input-group-sm">
<p>
    <u>${balance.dateString}</u>
    <b>${balance.amount}</b>
    ${balance.currency}
</p>
</div>
</#list>
</@c.page>