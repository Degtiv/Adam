<#import "parts/common.ftl" as c>
<@c.page>

<form method="post" action="/goals">
    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <span class="input-group-text" id="title_input">Title</span>
        </div>
        <input type="text" class="form-control" placeholder="Title" aria-label="title" aria-describedby="title_input"
               name="title" value="${goal.title}" required>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="status_input">Status</span>
            </div>
            <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" value="${goal.status}">
        </div>
        <div class="card-columns my-5">
            <div class="card">
                <div class="card-header h-auto clearfix">
                    <div class="float-left">

                    </div>
                    <div class="float-right">
                        <a href="/goals/${goal.uuid}">
                            <i class="material-icons btn-outline-dark" style="border-radius:20px; font-size: 15px; padding:3px;">create</i>
                        </a>
                    </div>
                </div>
                <div class="card-body">

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

        <div class="input-group-append ml-1">
            <button class="btn btn-primary rounded-right" type="submit" id="submit_button">Save</button>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
</form>

</@c.page>