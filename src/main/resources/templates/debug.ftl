<#import "parts/common.ftl" as c>
<@c.page>
    <div>
        <form method="post">
            <input type="text" name="name" placeholder="name"/>
            <input type="date" name="dateText"/>
            <input type="text" name="value" placeholder="value"></input>
            <input type="text" name="description" placeholder="description"></input>
            <input type="text" name="status" placeholder="status"/>
            <input type="text" name="categoryName" placeholder="category"></input>
            <input type="hidden" name="_csrf" value="${_csrf.token}"></input>
            <button type="submit">Добавить</button>
        </form>
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
        <u>${transaction.dateString}</u>
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