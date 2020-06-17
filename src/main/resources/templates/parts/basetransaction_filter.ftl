<#macro basetransaction_filter type>
    <form class="form-inline" method="post" action="/${type}/filter" id="filter_${type}_form">
        <div class="input-group-prepend input-group-sm mr-4">
            <h5>Filter</h5>
            <h6 class="ml-1">
                <#if filter.getIsActive()>
                    <span class="badge badge-success">active</span>
                </#if>
            </h6>
        </div>
        <div class="clearfix input-group-sm">
            <label class="sr-only" for="from_date_input">From date</label>
            <input type="date" class="form-control my-1" id="from_date_input" name="fromDateText"
            <#if filter.getIsActive()>
                value="${filter.fromDateField}"
            </#if>
            required>
            -
            <label class="sr-only" for="to_date_input">To date</label>
            <input type="date" class="form-control my-1 mr-sm-2" id="to_date_input" name="toDateText"
            <#if filter.getIsActive()>
                value="${filter.toDateField}"
            </#if>
            required>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-primary btn-sm rounded ml-1">Filter</button>
            <a href="/${type}/clear_filter" class="btn btn-outline-dark btn-sm rounded ml-1">Clear filter</a>
        </div>
    </form>
</#macro>