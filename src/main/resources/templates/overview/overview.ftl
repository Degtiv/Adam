<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="public/scripts/overview-diagram-data.js" type="text/javascript"></script>
<script src="public/scripts/overviewDiagram.js" type="text/javascript"></script>

<div class="input-group mb-3">
    <form class="input-group-prepend" method="post" action="/overview/filter" id="filter_overview_form">
        <input type="date" class="form-control my-1 mr-sm-2" aria-label="From" aria-describedby="from-label"
               id="overview_diagram_date_from" name="fromDateText"
                <#if overviewFilter?? && overviewFilter.getIsActive()>
                    value="${overviewFilter.fromDateField}"
                </#if>
               required>

        <input type="date" class="form-control my-1 mr-sm-2" aria-label="To" aria-describedby="to-label"
               id="overview_diagram_date_to" name="toDateText"
                <#if overviewFilter?? && overviewFilter.getIsActive()>
                    value="${overviewFilter.toDateField}"
                </#if>
               required>

        <h6 class="ml-1">
            <#if overviewFilter?? && overviewFilter.getIsActive()>
                <span class="badge badge-success">saved dates</span>
            </#if>
        </h6>

        <div class="form-inline col-4">
            <button type="submit" class="btn btn-outline-primary btn-sm rounded ml-1">Save dates</button>
            <a href="/overview/clear_filter" class="btn btn-outline-dark btn-sm rounded ml-1">Clear dates</a>
        </div>

        <input type="hidden" id="refresh-input" csrf="${_csrf.token}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <div class="custom-control custom-checkbox col-12" style="margin: auto;">
            <input type="checkbox" class="custom-control-input" id="show-dots-checkbox"
                   <#if overviewFilter?? && overviewFilter.showDots>
                       checked
                   </#if>
                   name="showDots">
            <label class="custom-control-label" for="show-dots-checkbox">Show dots</label>
        </div>
    </form>
</div>

<div id="overview-diagram">
</div>


<div  class="">
</div>

<div id="overview-info" class="card overview-info">
    <div class="card-header pb-0">
        <div id="balance-info">
            <div class="icon-block-right"><i class="bi-x-lg" act="hide" target="#overview-info"></i></div>
            <h5 class="card-title"></h5>
            <div id="date-info">
                <h6 class="card-subtitle mb-2 text-muted"></h6>
            </div>
        </div>
    </div>

    <div class="card-body">
        <div id="goals-info">
            <h7><b>Goals:</b></h7>
            <p class=""></p>
        </div>

        <div id="transactions-info">
            <h7><b>Transactions:</b></h7>
            <p class=""></p>
        </div>
    </div>
</div>

<div id="goal-info" class="card overview-info">
    <div class="card-header pb-0">
        <div id="goal-title">
            <div class="icon-block-right"><i class="bi-x-lg" act="hide" target="#goal-info"></i></div>
            <h5 class="card-title"></h5>
            <div id="goal-date">
                <h6></h6>
            </div>
        </div>
    </div>

    <div class="card-body">
        <div id="goal-amount">
            <h6></h6>
        </div>
        <div id="goal-image">
            <img src="https://imgholder.ru/500x500/8493a8/adb9ca&text=Изображение/n+++отсутствует&font=matias" class="w-100" alt="Заглушка">
        </div>
    </div>
</div>

    <script>
        $(function () {
            $("i").on('click', function () {
                const act = $(this).attr('act');
                if (act === 'hide') {
                    $($(this).attr('target')).css('display', 'none');
                }
            });
        })
    </script>
</@c.page>
