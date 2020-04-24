<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="public/scripts/overview-diagram-data.js" type="text/javascript"></script>
<script src="public/scripts/overviewDiagram.js" type="text/javascript"></script>

<div class="input-group mb-3">
    <div class="input-group-prepend">

        <input type="date" class="form-control my-1 mr-sm-2" aria-label="From" aria-describedby="from-label"
               id="overview_diagram_date_from" value="2020-03-01" required>

        <input type="date" class="form-control my-1 mr-sm-2" aria-label="To" aria-describedby="to-label"
               id="overview_diagram_date_to" value="2020-03-02" required>

        <input type="hidden" id="refresh-input" csrf="${_csrf.token}"/>
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
</@c.page>