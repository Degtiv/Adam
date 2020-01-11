<#import "/parts/common.ftl" as c>
<@c.page>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="title" required/>
            <input type="date" name="dateText" required/>
            <input type="text" name="amount" placeholder="amount" required/>
            <input type="text" name="description" placeholder="description" required/>
            <input type="text" name="status" placeholder="status" required/>
            <input type="text" name="categoryName" placeholder="category" required/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
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