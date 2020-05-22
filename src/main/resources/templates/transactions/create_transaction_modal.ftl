<button class="btn btn-outline-primary ml-2" type="button" id="add-transaction-button">Add transaction</button>

<div class="modal fade" id="createTransactionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">

            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle" style="text-align:center">New transaction</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="add_transaction_modal" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="input-group mb-2">
                        <div class="btn-group btn-group-toggle" data-toggle="buttons">
                            <label class="btn btn-outline-success active">
                                <input type="radio" name="transactionType" id="option1" autocomplete="off" checked value="Income"/>Income
                            </label>
                            <label class="btn btn-outline-warning">
                                <input type="radio" name="transactionType" id="option2" autocomplete="off" value="Cost"/>Cost
                            </label>
                        </div>

                        <div class="input-group-prepend"  style="margin-left:5% !important;">
                            <label for="titleInput" class="mr-3 col-form-label"><b>Title</b></label>
                        </div>
                        <input id="titleInput" type="text" class="  form-control" placeholder="Title" aria-label="title" aria-describedby="title_input"
                            name="title" required>
                    </div>

                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <label for="dateInput" class="mr-3 col-form-label">Date</label>
                        </div>
                        <input id="dateInput" type="date" class="  form-control" aria-label="date" aria-describedby="date_input" name="dateText"
                            required>
                    </div>

                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <label for="amountInput" class="mr-3 col-form-label">Amount</label>
                        </div>
                        <input id="amountInput" type="number" step="0.01" class="  form-control" placeholder="0.00" aria-label="amount"
                            aria-describedby="amount_input" name="amount" required>
                        <div class="input-group-append">
                            <span class="input-group-text" id="currency_label">RUR</span>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <label for="categoryInput" class="mr-3 col-form-label">Category</label>
                        </div>
                        <select class="custom-select" style="border-radius:15px;" id="categoryInput" name="category">
                            <#list categories as category>
                                <option value="${category.title}">${category.title}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <label for="statusInput" class="mr-3 col-form-label">Status</label>
                        </div>
                        <select class="custom-select"  style="border-radius:15px;" id="statusInput" name="status">
                            <#list statuses as status>
                                <option value="${status.title}">${status.title}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="input-group mb-2">
                        <div class="input-group-prepend">
                            <label for="descriptionInput" class="mr-3 col-form-label">Description</label>
                        </div>
                        <textarea id="descriptionInput" class="form-control  " aria-label="description"
                            aria-describedby="description_input"
                            placeholder="Description" id="exampleFormControlTextarea1" rows="3"
                            name="description"
                            style="border-radius:0px;"></textarea>        
                    </div>

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
                </div>
                
                <div class="modal-footer">
                    <button class="btn btn-success float-right" type="submit" id="submit_button">Add</button>
                </div>

                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#add-transaction-button').click(function() {
            $('#createTransactionModal').modal('show');
        });
    });

    /* https://stackoverflow.com/a/27829144 прочитано, понято и изменено под нужды*/
    $(document).ready(function() {
        check_inputs();

        function check_inputs() {
            var empty = false;

            $("#add_transaction_modal input.req").each(function() {
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
</script>