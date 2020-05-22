<button class="btn btn-outline-primary ml-2" type="button" id="add-transaction-button">Add Goal</button>

<div class="modal fade" id="createTransactionModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle" style="text-align:center">New Goal</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="add_goal_modal" method="post" enctype="multipart/form-data">
                <div class="modal-body">
                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="titleInput" class="mr-3 col-form-label"><b>Title</b></label>
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
                                <label for="dateInput" class="mr-3 col-form-label">Date</label>
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
                                <label for="amountInput" class="mr-3 col-form-label">Amount</label>
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
                                <label for="categoryInput" class="mr-3 col-form-label">Category</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <select class="custom-select" style="border-radius:15px;" id="categoryInput" name="category">
                                <#list categories as category>
                                    <option value="${category.title}">${category.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="statusInput" class="mr-3 col-form-label">Status</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <select class="custom-select"  style="border-radius:15px;" id="statusInput" name="status">
                                <#list statuses as status>
                                    <option value="${status.title}">${status.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="descriptionInput" class="mr-3 col-form-label">Description</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <textarea id="descriptionInput" class="form-control  " aria-label="description"
                                aria-describedby="description_input"
                                placeholder="Description" id="exampleFormControlTextarea1" rows="3"
                                name="description"
                                style="border-radius:0px;"></textarea>
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <div class="input-group-prepend">
                                <label for="url_input" class="mr-3 col-form-label">URL</label>
                            </div>
                        </div>
                        <div class="col-10">
                            <input id="url_input" type="text" class="req form-control" placeholder="http://..." aria-label="url" aria-describedby="url_input"
                                   name="url">
                        </div>
                    </div>

                    <div class="input-group mb-2">
                        <div class="col-2">
                            <label for="url_input" class="mr-3 col-form-label">Image</label>
                        </div>
                        <div class="col-10">
                            <div class="input-group-prepend">
                                <input type="file" class="custom-file-input" id="image_input" aria-describedby="image" name="image">
                                <label class="custom-file-label input-group-prepend form-control" for="image_input">Add image</label>
                            </div>
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
</script>