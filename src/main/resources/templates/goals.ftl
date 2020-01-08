<#import "parts/common.ftl" as c>
<@c.page>

<form method="post" enctype="multipart/form-data">
    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <span class="input-group-text" id="title_input">Title</span>
        </div>
        <input type="text" class="form-control" placeholder="Title" aria-label="title" aria-describedby="title_input"
               name="title" required>

        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="date_input">Date</span>
        </div>
        <input type="date" class="form-control" aria-label="date" aria-describedby="date_input" name="dateText"
               required>

        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="amount_input">Amount</span>
        </div>
        <input type="number" class="form-control" placeholder="0.00" aria-label="amount"
               aria-describedby="amount_input" name="amount" required>

        <div class="input-group-prepend ml-1">
            <label class="input-group-text" for="category_input">Category</label>
        </div>
        <select class="custom-select" id="category_input" name="category">
            <#list categories as category>
                <option value="${category.title}">${category.title}</option>
            </#list>
        </select>
    </div>

    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <span class="input-group-text" id="description_input">Description</span>
        </div>
        <input type="text" class="form-control" placeholder="Description" aria-label="description"
               aria-describedby="description_input"
               name="description">

        <div class="input-group-prepend ml-1">
            <label class="input-group-text" for="status_input">Status</label>
        </div>
        <select class="custom-select" id="status_input" name="status">
            <#list statuses as status>
            <option value="${status.title}">${status.title}</option>
        </#list>
        </select>

        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="url_input">URL</span>
        </div>
        <input type="text" class="form-control" placeholder="http://..." aria-label="url"
               aria-describedby="url_input" name="url">

        <div class="custom-file ml-1">
            <input type="file" class="custom-file-input" id="image_input" aria-describedby="image" name="image">
            <label class="custom-file-label" for="image_input">Image</label>
        </div>

        <div class="input-group-append ml-1">
            <button class="btn btn-primary rounded-right" type="submit" id="submit_button">Add</button>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
</form>

<div class="card-columns my-5">
    <#list goals as goal>
    <div class="card">
        <div class="card-header h-auto clearfix">
            <div class="float-left">
                ${goal.status}
            </div>
            <div class="float-right">
                <a href="/goals/edit/${goal.uuid}">
                    <i class="material-icons btn-outline-dark" style="border-radius:20px; font-size: 15px; padding:3px;">edit</i>
                </a>
            </div>
        </div>
        <div class="card-body">
                <a href="${goal.url}">
                    <h5 class="card-title">${goal.title}</h5>
                </a>
                <p class="card-text small">${goal.category}</p>
            <#if goal.image??>
                <a href="${goal.url}">
                    <img class="card-img-top" src="/img/${goal.image}" alt="Card image cap">
                </a>
            </#if>
            <p class="card-text my-2">${goal.description}</p>
        </div>

        <div class="card-footer text-muted">
            <div class="row">
                <div class="col-sm-8 text-left">
                    <em>${goal.amount} ${goal.currency}</em>
                </div>
                <div class="col-sm-4 text-right">
                    ${goal.dateString}
                </div>
            </div>
        </div>
    </div>
    <#else>
    No goals
</#list>
</div>
</@c.page>