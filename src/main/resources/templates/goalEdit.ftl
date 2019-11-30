<#import "parts/common.ftl" as c>
<@c.page>

<form method="post" action="/goals">
    <div class="input-group mb-2 justify-content-center">
        <h2 class="display-4">${goal.title}</h2>
    </div>

    <div class="input-group-append mb-2">
        <button class="btn btn-primary rounded-right" type="submit" id="submit_button">Save</button>
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
            <input type="text" class="form-control" value="${goal.amount}" aria-label="amount"
                   aria-describedby="amount_input" name="amount" required>
            <div class="input-group-prepend">
                <span class="input-group-text" id="currency_label">${goal.currency}</span>
            </div>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text" for="status_input">Status</label>
            </div>
            <select class="custom-select" id="status_input" name="status">
                <option value="Planned">Planned</option>
                <option value="In progress">In progress</option>
                <option value="Come true">Come true</option>
            </select>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text" for="category_input">Category</label>
            </div>
            <select class="custom-select" id="category_input" name="category">
                <#list categories as category>
                    <option value="${category.title}" <#if goal.category == category.title> selected</#if>>${category.title}</option>
                </#list>
            </select>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="url_input">URL</span>
            </div>
            <input type="text" class="form-control" value="${goal.url}" aria-label="url"
                   aria-describedby="url_input" name="url">
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="description_input">Description</span>
            </div>
            <textarea class="form-control" placeholder="Description" aria-label="description"
                      aria-describedby="description_input"
                      name="description">${goal.description}</textarea>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="image_input">Image</span>
            </div>
            <#if goal.image??>
                <div class="input-group-prepend ml-1">
                    <button class="btn btn-primary rounded-right" type="submit" id="change_button">Change</button>
                </div>
                <div class="input-group-prepend ml-1">
                    <button class="btn btn-primary rounded-right" type="submit" id="remove_button">Delete</button>
                </div>
                <a href="${goal.url}">
                    <img class="card-img-top" src="/img/${goal.image}" alt="Card image cap">
                </a>
            <#else>
                <div class="input-group-prepend ml-1">
                    <button class="btn btn-primary rounded-right" type="submit" id="Add_button">Add</button>
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
</form>

</@c.page>