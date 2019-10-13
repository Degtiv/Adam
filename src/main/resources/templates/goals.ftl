<#import "parts/common.ftl" as c>
<@c.page>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="title" placeholder="title" required/>
            <input type="date" name="dateText" required/>
            <input type="text" name="amount" placeholder="amount" required/>
            <input type="text" name="description" placeholder="description"/>
            <input type="text" name="status" placeholder="status"/>
            <input type="text" name="url" placeholder="url"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Add</button>
        </form>
    </div>

<#list goals as goal>
<div>
    <b>${goal.uuid} </b>
    <i>${goal.title} </i>
    <i>${goal.dateString}</i>
    <b>${goal.amount} ${goal.currency}</b>
    <i>${goal.description}</i>
    <i>${goal.status}</i>
    <i>${goal.pictureUrl}</i>
    <i><a href="${goal.url}">Ссылка</a></i>
</div>
<#else>
No goals
</#list>
</@c.page>