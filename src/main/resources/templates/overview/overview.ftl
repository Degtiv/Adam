<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
git
<script src="public/scripts/overview-diagram-data.js" type="text/javascript"></script>
<script src="public/scripts/overviewDiagram.js" type="text/javascript"></script>

<div class="input-group mb-3">
    <div class="input-group-prepend">
        <input type="date" class="form-control my-1 mr-sm-2" aria-label="From" aria-describedby="from-label"
               id="overview_diagram_date_from" value="2020-03-01" required>

        <input type="date" class="form-control my-1 mr-sm-2" aria-label="To" aria-describedby="to-label"
               id="overview_diagram_date_to" value="2020-03-02" required>

        <input type="hidden" id="refresh-input" csrf="${_csrf.token}"/>

        <div class="custom-control custom-checkbox col-12" style="margin: auto;">
            <input type="checkbox" class="custom-control-input" id="show-dots-checkbox" checked>
            <label class="custom-control-label" for="show-dots-checkbox">Show dots</label>
        </div>
    </div>
</div>

<div id="overview-diagram">
</div>


<div  class="">

</div>

<div id="overview-info" class="card overview-info" style="width: 18rem;">
    <div class="card-body">
        <div id="balance-info">
            <h5 class="card-title"></h5>
        </div>
        <div id="date-info">
            <h6 class="card-subtitle mb-2 text-muted"></h6>
        </div>
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

<div id="goal-info" class="card overview-info" style="width: 18rem;">
    <div class="card-body">
        <div id="goal-title">
            <h5 class="card-title"></h5>
        </div>
        <div id="goal-date">
            <h6></h6>
        </div>
        <div id="goal-amount">
            <h6></h6>
        </div>
    </div>
</div>
</@c.page>