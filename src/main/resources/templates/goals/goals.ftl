<#import "/parts/common.ftl" as c>
<@c.page>

    <#import "/parts/pageTitle.ftl" as pt>
    <@pt.pageTitle "Goals"/>

    <#import "/parts/basetransaction_filter.ftl" as btf>
    <@btf.basetransaction_filter "goals"/>

    <hr>

    <#import "/parts/create_basetransaction_modal.ftl" as btm>
    <@btm.add_basetransaction "Goal"/>

    <div class="card-columns my-5">
        <#list goals as goal>
            <div class="card card-${goal.status.title?lowerCase}">
                <div class="card-header h-auto clearfix">
                    <div class="float-left">
                        ${goal.status.title}
                    </div>
                    <div class="float-right">
                        <a href="/goals/edit/${goal.uuid}">
                            <i class="material-icons btn-outline-dark" style="border-radius:20px; font-size: 15px; padding:3px;">edit</i>
                        </a>
                    </div>
                </div>
                <div class="card-body">

                    <h5 class="card-title">${goal.title}</h5>

                    <p class="card-text small">${goal.category.title}</p>
                    <#if goal.image??>
                        <img class="card-img-top" src="/img/${goal.image}" alt="Card image cap">
                    </#if>
                    <p class="card-text my-2">${goal.description?ifExists}</p>
                    <#if goal.url??>
                        <#if goal.url != ''>
                            <a href="${goal.url}">
                                Link
                            </a>
                        </#if>
                    </#if>
                </div>

                <div class="card-footer text-muted">
                    <div class="row">
                        <div class="col-sm-6 small text-left">
                            <em>${goal.amount} ${goal.currency}</em>
                        </div>
                        <div class="col-sm-6 small text-right">
                            ${goal.dateString}
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@c.page>
