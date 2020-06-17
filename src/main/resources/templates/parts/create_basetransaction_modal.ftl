
<#macro add_basetransaction type>
<#import "transaction_modal_part.ftl" as t>
<#import "goal_modal_part.ftl" as g>
<div class="col-12 center-block">
    <button class="btn btn-outline-primary center-button mb-4" type="button" id="add-${type}-button">Add ${type}</button>
</div>
<div class="modal fade" id="create${type}Modal" tabindex="-1" role="dialog" aria-labelledby="create${type}Modal"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="title" style="text-align:center">New ${type}</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="add_${type}_modal" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <#if type=='Transaction'>
                        <@t.type/>
                    </#if>
                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="titleInput" class="col-form-label"><b>Title</b></label>
                            </div>
                        </div>
                        <div class="col-10">
                            <input id="titleInput" type="text" class="  form-control" placeholder="Title" aria-label="title" aria-describedby="title_input"
                                name="title" required>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="dateInput" class="col-form-label">Date</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <input id="dateInput" type="date" class="  form-control" aria-label="date" aria-describedby="date_input" name="dateText"
                                required>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="amountInput" class="col-form-label">Amount</label>
                            </div>
                        </div>
                        <div class="col-9">
                            <input id="amountInput" type="number" step="0.01" class="  form-control" placeholder="0.00" aria-label="amount"
                                aria-describedby="amount_input" name="amount" required>

                        </div>
                        <div class="col-1">
                            <div class="input-group-append">
                                <span class="input-group-text" id="currency_label">RUR</span>
                            </div>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="categoryInput" class="col-form-label">Category</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <select class="custom-select" style="border-radius:13px;" id="categoryInput" name="category">
                                <#list categories as category>
                                    <option value="${category.title}">${category.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="statusInput" class="col-form-label">Status</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <select class="custom-select"  style="border-radius:13px;" id="statusInput" name="status">
                                <#list statuses as status>
                                    <option value="${status.title}">${status.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="descriptionInput" class="col-form-label">Description</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <textarea id="descriptionInput" class="form-control" aria-label="description"
                                aria-describedby="description_input"
                                placeholder="Description" id="exampleFormControlTextarea1" rows="3"
                                name="description"
                                style="border-radius:0px;"></textarea>
                        </div>
                    </div>

                    <#if type=='Goal'>
                        <@g.url_and_image/>
                    <#else>
                        <@t.repeat_rule/>
                    </#if>
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
        $('#add-${type}-button').click(function() {
            $('#create${type}Modal').modal('show');
        });
    });

    <#if type=='Transaction'>
        <@t.script type/>
    </#if>
</script>
</#macro>