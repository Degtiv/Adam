<div class="custom-control custom-checkbox col-12 mb-2" style="margin: auto;">
    <input type="checkbox" class="custom-control-input" id="create_rule_checkbox"
           data-toggle="collapse" data-target="#collapseExample"
           aria-expanded="false" aria-controls="collapseExample" name="isRepeated">
    <label class="custom-control-label" for="create_rule_checkbox">Create repeat rule</label>
</div>

<div class="collapse" id="collapseExample">
    <div class="input-group mb-2">
        <div class="col-2">
            <div class="input-group-prepend">
                <label for="startDateInput" class="col-form-label">Start Date</label>
            </div>
        </div>

        <div class="col-10">
            <input id="startDateInput" type="date" class="req form-control" aria-label="date" aria-describedby="start_date_input"
                   name="startDateText" req>
        </div>
    </div>

    <div class="input-group mb-2">
        <div class="col-2">
            <div class="input-group-prepend">
                <label for="endDateInput" class="col-form-label">End Date</label>
            </div>
        </div>

        <div class="col-10">
            <input id="endDateInput" type="date" class="req form-control" aria-label="date" aria-describedby="end_date_input"
                   name="endDateText" req>
        </div>
    </div>
    <div class="input-group mb-2">
        <div class="col-2">
            <div class="input-group-prepend">
                <label for="ruleInput" class="col-form-label">Rule</label>
            </div>
        </div>

        <div class="col-10">
            <select class="custom-select form-control" id="ruleInput" name="ruleStrategy">
                <#list ruleStrategies as ruleStrategy>
                <option value="${ruleStrategy.title}">${ruleStrategy.title}</option>
            </#list>
            </select>
        </div>
    </div>

    <div class="input-group mb-2">
        <div class="col-2">
            <div class="input-group-prepend">
                <label for="ruleParameterInput" class="col-form-label">Parameter</label>
            </div>
        </div>
        <div class="col-10">
            <input id="ruleParameterInput" type="text" class="req form-control  " placeholder="Rule parameter" aria-label="ruleParameter" aria-describedby="rule_parameter_input"
                   name="ruleParameter">
        </div>
    </div>
</div>