<#import "/parts/common.ftl" as c>
<@c.page>

<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Rules"/>
<hr>
<#list rules as rule>
<form class="form-inline" method="post" action="/rules/save/${rule.uuid}" id="save_rule_form">
    <div class="clearfix input-group-sm">

        <#list rule.transactions as transaction>
            <label class="sr-only" for="title_input_${rule.uuid}">Title</label>
            <input type="text" class="form-control my-1 mr-sm-2" id="title_input_${rule.uuid}" value="${transaction.title}">
            <#break>
        </#list>
        <label class="sr-only" for="start_date_input_${rule.uuid}">Start date</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="start_date_input_${rule.uuid}" value="${rule.startDateField}" name="startDateText" required>

        <label class="sr-only" for="end_date_input_${rule.uuid}">End date</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="end_date_input_${rule.uuid}" value="${rule.endDateField}" name="endDateText" required>

        <#list rule.transactions as transaction>
            <label class="sr-only" for="amount_input_${rule.uuid}">Amount</label>
            <input type="number" step="0.01" class="form-control" id="amount_input_${rule.uuid}" value="${transaction.amountString}">
            <small id="passwordHelpInline" class="text-muted my-1 mr-sm-2">${transaction.currency}</small>
            <#break>
        </#list>
        <label class="sr-only" for="rule_strategy_input_${rule.uuid}">Rule</label>
        <select class="custom-select my-1 mr-sm-2" id="rule_strategy_input_${rule.uuid}" name="ruleStrategy">
            <#list ruleStrategies as ruleStrategy>
                <option value="${ruleStrategy.title}" <#if rule.ruleStrategyString == ruleStrategy.title> selected</#if>>${ruleStrategy.title}</option>
            </#list>
        </select>

        <label class="sr-only" for="rule_parameter_input_${rule.uuid}">Rule parameter</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="rule_parameter_input_${rule.uuid}" value="${rule.ruleParameter}" name="ruleParameter" required>

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