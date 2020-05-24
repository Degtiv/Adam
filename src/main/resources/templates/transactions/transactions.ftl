<#import "/parts/common.ftl" as c>
<@c.page>

<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Transactions"/>

<#import "/parts/create_basetransaction_modal.ftl" as btm>
<@btm.add_basetransaction "Transaction"/>

<hr>
<#list transactions as transaction>
<form class="form-inline" method="post" action="/transactions/save/${transaction.uuid}" id="save_transaction_form">
    <div class="clearfix input-group-sm">
        <div class="btn-group btn-group-toggle ml-1" data-toggle="buttons">
            <label class="btn btn-outline-success <#if transaction.transactionType.title == "Income">active</#if>"
                    style="padding: 1px; border-bottom-left-radius:0px;">
                <input type="radio" name="transactionType" autocomplete="off"
                       <#if transaction.transactionType.title == "Income">checked</#if>
                       value="Income"/>+
            </label>
            <label class="btn btn-outline-warning <#if transaction.transactionType.title == "Cost">active</#if>"
                   style="padding: 3px; border-top-right-radius:0px;">
                <input type="radio" name="transactionType" autocomplete="off"
                       <#if transaction.transactionType.title == "Cost">checked</#if>
                       value="Cost"/>-
            </label>
        </div>

        <label class="sr-only" for="title_input_${transaction.uuid}">Title</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="title_input_${transaction.uuid}" value="${transaction.title}" name="title" required>

        <label class="sr-only" for="date_input_${transaction.uuid}">Date</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="date_input_${transaction.uuid}" value="${transaction.dateField}" name="dateText" required>

        <label class="sr-only" for="amount_input_${transaction.uuid}">Amount</label>
        <input type="number" step="0.01" class="form-control" id="amount_input_${transaction.uuid}" value="${transaction.amountString}" name="amount" required>
        <small id="passwordHelpInline" class="text-muted my-1 mr-sm-2">${transaction.currency}</small>

        <label class="sr-only" for="category_input_${transaction.uuid}">Category</label>
        <select class="custom-select my-1 mr-sm-2" id="category_input_${transaction.uuid}" name="category">
            <#list categories as category>
                <option value="${category.title}" <#if transaction.category == category> selected</#if>>${category.title}</option>
            </#list>
        </select>

        <label class="sr-only" for="status_input_${transaction.uuid}">Status</label>
        <select class="custom-select my-1 mr-sm-2" id="status_input_${transaction.uuid}" name="status">
            <#list statuses as status>
                <option value="${status.title}" <#if transaction.status == status> selected</#if>>${status.title}</option>
            </#list>
        </select>

        <label class="sr-only" for="description_input_${transaction.uuid}">Description</label>
        <input type="text" class="form-control my-1 mr-sm-2" id="description_input_${transaction.uuid}" value="${transaction.description}" name="description">

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-outline-success btn-sm rounded" formaction="/transactions/save/${transaction.uuid}">Save</button>
        <button type="submit" class="btn btn-outline-danger btn-sm rounded" formaction="/transactions/delete/${transaction.uuid}">Delete</button>
    </div>
</form>
<#else>
Add your first transaction
</#list>
</@c.page>