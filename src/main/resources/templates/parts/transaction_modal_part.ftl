<#macro type>
    <div class="btn-group btn-group-toggle" data-toggle="buttons">
        <label class="btn btn-outline-success active">
            <input type="radio" name="transactionType" id="option1" autocomplete="off" checked value="Income"/>Income
        </label>
        <label class="btn btn-outline-warning">
            <input type="radio" name="transactionType" id="option2" autocomplete="off" value="Cost"/>Cost
        </label>
    </div>
</#macro>

<#macro repeat_rule>
<div class="custom-control custom-checkbox col-12 mb-2" style="margin: auto;">
    <input type="checkbox" class="custom-control-input" id="create_rule_checkbox"
           data-toggle="collapse" data-target="#collapseExample"
           aria-expanded="false" aria-controls="collapseExample" name="isRepeated">
    <label class="custom-control-label" for="create_rule_checkbox">Create repeat rule</label>
</div>

<div class="collapse" id="collapseExample">
    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <label for="startDateInput" class="mr-3 col-form-label">Start Date</label>
        </div>
        <input id="startDateInput" type="date" class="req form-control  " aria-label="date" aria-describedby="start_date_input"
               name="startDateText" req>
    </div>

    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <label for="endDateInput" class="mr-3 col-form-label">End Date</label>
        </div>
        <input id="endDateInput" type="date" class="req form-control  " aria-label="date" aria-describedby="end_date_input"
               name="endDateText" req>
    </div>
    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <label for="ruleInput" class="mr-3 col-form-label">Rule</label>
        </div>
        <select class="custom-select form-control  " id="ruleInput" name="ruleStrategy">
            <#list ruleStrategies as ruleStrategy>
            <option value="${ruleStrategy.title}">${ruleStrategy.title}</option>
        </#list>
        </select>
    </div>

    <div class="input-group mb-2">
        <div class="input-group-prepend">
            <label for="ruleParameterInput" class="mr-3 col-form-label">Rule Parameter</label>
        </div>
        <input id="ruleParameterInput" type="text" class="req form-control  " placeholder="Rule parameter" aria-label="ruleParameter" aria-describedby="rule_parameter_input"
               name="ruleParameter">
    </div>
</div>
</#macro>

<#macro script type>
/* https://stackoverflow.com/a/27829144 прочитано, понято и изменено под нужды*/
    $(document).ready(function() {
        check_inputs();

        function check_inputs() {
            var empty = false;

            $("#add_${type}_modal input.req").each(function() {
                if ($(this).val() == '') {
                    empty = true;
                }
            });

            if (!$('#create_rule_checkbox').prop('checked'))
                empty = false;

            if (empty) {
                $('#submit_button').attr('disabled', 'disabled');
            } else {
                $('#submit_button').removeAttr('disabled');
            }

            setTimeout(check_inputs, 200);
        }
    });
</#macro>