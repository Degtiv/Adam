<#import "parts/common.ftl" as c>
<@c.page>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="title"/>
            <input type="date" name="dateText"/>
            <input type="text" name="amount" placeholder="amount"/>
            <input type="text" name="description" placeholder="description"/>
            <input type="text" name="status" placeholder="status"/>
            <input type="text" name="categoryName" placeholder="category"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Добавить</button>
        </form>
    </div>

<#list transactions as transaction>
<div>
    <b>${transaction.uuid} </b>
    <i>${transaction.title} </i>
    <i>${transaction.dateString}</i>
    <b>${transaction.amount} ${transaction.currency}</b>
    <i>${transaction.description}</i>
    <i>${transaction.status}</i>
    <i>${transaction.category}</i>
</div>
<#else>
No transaction
</#list>
</@c.page>