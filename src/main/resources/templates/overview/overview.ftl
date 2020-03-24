<#import "/parts/common.ftl" as c>
<@c.page>
<#import "/parts/pageTitle.ftl" as pt>
<@pt.pageTitle "Overview"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="public/scripts/overview-diagram-data.js" type="text/javascript"></script>
<script src="public/scripts/overviewDiagram.js" type="text/javascript"></script>

<div id="overview-diagram">
</div>

<button id="debug-button" csrf="${_csrf.token}">Debug button</button>

</@c.page>