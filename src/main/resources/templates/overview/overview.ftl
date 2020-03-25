<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="public/scripts/overview-diagram-data.js" type="text/javascript"></script>
<script src="public/scripts/overviewDiagram.js" type="text/javascript"></script>

<div class="input-group mb-3">
    <div class="input-group-prepend">
        <label class="sr-only" for="overview_diagram_date_from">From</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="overview_diagram_date_from" required>

        <label class="sr-only" for="overview_diagram_date_to">To</label>
        <input type="date" class="form-control my-1 mr-sm-2" id="overview_diagram_date_to" required>

        <button class="btn btn-outline-primary btn-sm rounded" id="refresh-button" csrf="${_csrf.token}">Refresh</button>
    </div>
</div>

<div id="overview-diagram">
</div>

</@c.page>