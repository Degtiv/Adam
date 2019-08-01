<#import "parts/common.ftl" as c>
<@c.page>
<div>
    <b>Users's transactions</b>
</div>

<#list transactions as transaction>
<div>
    <#if transaction.id??>
    <b>${transaction.id} </b>
</#if>
<#if transaction.name??>
<i>${transaction.name} </i>
</#if>
<#if transaction.date??>
<u>${transaction.date}</u>
</#if>
<#if transaction.value??>
<b>${transaction.value}</b>
</#if>
<#if transaction.description??>
<i>${transaction.description}</i>
</#if>
<#if transaction.status??>
<u>${transaction.status}</u>
</#if>
<#if transaction.milestoneId??>
<b>${transaction.milestoneId}</b>
</#if>
<#if transaction.category??>
<i>${transaction.category}</i>
</#if>
</div>
<#else>
No transaction
</#list>
</@c.page>