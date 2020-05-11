<#import "/parts/common.ftl" as c>
<@c.page>

<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Rules"/>

<form method="post" enctype="multipart/form-data">
    <div class="input-group mb-2">
        <div class="btn-group btn-group-toggle mr-1" data-toggle="buttons">
            <label class="btn btn-outline-success active">
                <input type="radio" name="transactionType" id="option1" autocomplete="off" checked value="Income"/>Income
            </label>
            <label class="btn btn-outline-warning">
                <input type="radio" name="transactionType" id="option2" autocomplete="off" value="Cost"/>Cost
            </label>
        </div>

        <div class="input-group-prepend">
            <span class="input-group-text" id="title_input">Title</span>
        </div>
        <input type="text" class="form-control" placeholder="Title" aria-label="title" aria-describedby="title_input"
               name="title" required>

        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="start_date_input">Start Date</span>
        </div>
        <input type="date" class="form-control" aria-label="date" aria-describedby="start_date_input" name="startDateText"
               required>

        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="end_date_input">End Date</span>
        </div>
        <input type="date" class="form-control" aria-label="date" aria-describedby="end_date_input" name="endDateText"
               required>
    </div>
    <div class="input-group mb-2">
        <div class="input-group-prepend ml-1">
            <span class="input-group-text" id="amount_input">Amount</span>
        </div>
        <input type="number" class="form-control" placeholder="0.00" aria-label="amount"
               aria-describedby="amount_input" name="amount" required>

        <div class="input-group-prepend ml-1">
            <label class="input-group-text" for="rule_strategy_input">Rule</label>
        </div>
        <select class="custom-select" id="rule_strategy_input" name="ruleStrategy">
            <#list ruleStrategies as ruleStrategy>
                <option value="${ruleStrategy.title}">${ruleStrategy.title}</option>
            </#list>
        </select>

        <div class="input-group-prepend">
            <span class="input-group-text" id="rule_parameter_input">Rule parameter</span>
        </div>
        <input type="text" class="form-control" placeholder="Rule parameter" aria-label="ruleParameter" aria-describedby="rule_parameter_input"
               name="ruleParameter" required>
    </div>
    <div class="input-group mb-2">
        <div class="input-group-prepend ml-1">
            <label class="input-group-text" for="category_input">Category</label>
        </div>
        <select class="custom-select" id="category_input" name="category">
            <#list categories as category>
                <option value="${category.title}">${category.title}</option>
            </#list>
        </select>

        <div class="input-group-prepend">
            <span class="input-group-text" id="description_input">Description</span>
        </div>
        <input type="text" class="form-control" placeholder="Description" aria-label="description"
               aria-describedby="description_input"
               name="description">

        <div class="input-group-append ml-1">
            <button class="btn btn-primary rounded-right" type="submit" id="submit_button">Add</button>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </div>
</form>

<hr>
<#list rules as rule>
<form class="form-inline" method="post" action="/rules/save/${rule.uuid}" id="save_rule_form">
    <div class="clearfix input-group-sm">

        <div class="btn-group btn-group-toggle ml-1" data-toggle="buttons">
            <label class="btn btn-outline-success <#if rule.transactionType.title == "Income">active</#if>"
                    style="padding: 1px; border-bottom-left-radius:0px;">
                <input type="radio" name="transactionType" autocomplete="off"
                    <#if rule.transactionType.title == "Income">checked</#if>
                    value="Income"/>+
            </label>
            <label class="btn btn-outline-warning <#if rule.transactionType.title == "Cost">active</#if>"
                    style="padding: 3px; border-top-right-radius:0px;">
                <input type="radio" name="transactionType" autocomplete="off"
                    <#if rule.transactionType.title == "Cost">checked</#if>
                    value="Cost"/>-
            </label>
        </div>

        <label class="sr-only" for="title_input_${rule.uuid}">Title</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="title_input_${rule.uuid}" value="${rule.title}" name="title" required>

        <label class="sr-only" for="start_date_input_${rule.uuid}">Start date</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="start_date_input_${rule.uuid}" value="${rule.startDateField}" name="startDateText" required>

        <label class="sr-only" for="end_date_input_${rule.uuid}">End date</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="end_date_input_${rule.uuid}" value="${rule.endDateField}" name="endDateText" required>

        <label class="sr-only" for="amount_input_${rule.uuid}">Amount</label>
        <input type="number" step="0.01" class="form-control" id="amount_input_${rule.uuid}" value="${rule.amountString}" name="amount" required>
        <small id="passwordHelpInline" class="text-muted my-1 mr-sm-2">${rule.currency}</small>

        <label class="sr-only" for="category_input_${rule.uuid}">Category</label>
        <select class="custom-select my-1 mr-sm-2" id="category_input_${rule.uuid}" name="category">
            <#list categories as category>
                <option value="${category.title}" <#if rule.category == category.title> selected</#if>>${category.title}</option>
            </#list>
        </select>

        <label class="sr-only" for="rule_strategy_input_${rule.uuid}">Rule</label>
        <select class="custom-select my-1 mr-sm-2" id="rule_strategy_input_${rule.uuid}" name="ruleStrategy">
            <#list ruleStrategies as ruleStrategy>
                <option value="${rule.title}" <#if rule.ruleStrategyString == ruleStrategy.title> selected</#if>>${ruleStrategy.title}</option>
            </#list>
        </select>

        <label class="sr-only" for="rule_parameter_input_${rule.uuid}">Rule parameter</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="rule_parameter_input_${rule.uuid}" value="${rule.ruleParameter}" name="ruleParameter" required>

        <label class="sr-only" for="description_input_${rule.uuid}">Description</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="description_input_${rule.uuid}" value="${rule.description}" name="description">

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-outline-primary btn-sm rounded" formaction="/rules/transactions/${rule.uuid}">Transactions</button>
        <button type="submit" class="btn btn-outline-success btn-sm rounded" formaction="/rules/save/${rule.uuid}">Save</button>
        <button type="submit" class="btn btn-outline-danger btn-sm rounded" formaction="/rules/delete/${rule.uuid}">Delete</button>
    </div>
</form>
<#else>
Add your first rule
</#list>

</@c.page>