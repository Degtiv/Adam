<#import "/parts/common.ftl" as c>
<#import "/parts/pageTitle.ftl" as pt>
<@c.page>

<form method="post" action="/goals/edit/${goal.uuid}">
    <@pt.pageTitle "${goal.title}"/>

    <div class="input-group-append mb-2">
        <button class="btn btn-outline-success rounded" type="submit" id="submit_button">Save</button>
        <button class="btn btn-outline-danger rounded ml-2" type="submit" id="delete_button" formaction="/goals/delete/${goal.uuid}">Delete</button>
    </div>

    <div class="input-group mb-2">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="title_input">Title</span>
            </div>
            <input type="text" class="form-control" placeholder="Title" aria-label="title"
                   aria-describedby="title_input"
                   name="title" value="${goal.title}" required>

            <div class="input-group-prepend ml-1">
                <span class="input-group-text" id="date_input">Date</span>
            </div>
            <input type="date" class="form-control" aria-label="date" aria-describedby="date_input" name="dateText"
                   value="${goal.dateField}" required>

            <div class="input-group-prepend ml-1">
                <span class="input-group-text" id="amount_input">Amount</span>
            </div>
            <input type="number" step="0.01" class="form-control" value="${goal.amountString}" aria-label="amount"
                   aria-describedby="amount_input" name="amount" required>
            <div class="input-group-prepend">
                <span class="input-group-text" id="currency_label">${goal.currency}</span>
            </div>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text" for="category_input">Category</label>
            </div>
            <select class="custom-select" id="category_input" name="category">
                <#list categories as category>
                    <option value="${category.title}" <#if goal.category == category> selected</#if>>${category.title}</option>
                </#list>
            </select>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text" for="status_input">Status</label>
            </div>
            <select class="custom-select" id="status_input" name="status">
                <#list statuses as status>
                    <option value="${status.title}" <#if goal.status == status> selected</#if>>${status.title}</option>
                </#list>
            </select>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="url_input">URL</span>
            </div>
            <input type="text" class="form-control" value="${goal.url?ifExists}" aria-label="url"
                   aria-describedby="url_input" name="url">
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="description_input">Description</span>
            </div>
            <textarea class="form-control" placeholder="Description" aria-label="description"
                      aria-describedby="description_input"
                      name="description">${goal.description?ifExists}</textarea>
        </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

<div style="padding: 40px; width:100%; border: 1px solid #0069d9; border-radius: 70px;">
    <form method="post" style="display:inline-block" enctype="multipart/form-data" action="/goals/edit/addImage/${goal.uuid}" id="add_image_form">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Image</span>
            </div>
            <div class="custom-file">
                <input type="file" class="custom-file-input" id="image_input" aria-describedby="image" name="image"
                       required>
                <label class="custom-file-label input-group-prepend" for="image_input">Image</label>
            </div>
            <div class="input-group-prepend">
                <button class="btn btn-outline-success rounded ml-2" type="submit" id="Add_button">Upload</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
    </form>

    <#if goal.image??>
    <form method="post" style="display: inline-block" id="delete_image_form">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <button class="btn btn-outline-danger rounded" type="submit" id="remove_button" formaction="/goals/edit/deleteImage/${goal.uuid}">Delete</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
    </form>

    <div class="input-group mb-3">
        <img class="card-img-top" src="/img/${goal.image}" alt="Card image cap">
    </div>
    </#if>
</div>

</@c.page>