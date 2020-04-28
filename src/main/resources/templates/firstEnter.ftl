<#import "/parts/common.ftl" as c>
<@c.page>
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
    Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle">Начнем с главного</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">
                <div class="container-fluid">
                    <div class="row">
                        <div id="setGoalBodyDiv" class="modal-body first-enter-modal-showed-div col-lg-12 col-md-12">
                            <h5 style="text-align:center;">Постановка цели</h5>
                            <hr>
                            <div id="whatDoYouWantDiv" class="form-group first-enter-primary-div">
                                <label for="whatDoYouWantLabel" id="whatDoYouWantLabel">Что вы хотите?</label>
                                <input type="text" class="form-control" id="whatDoYouWantInput"
                                       placeholder="Купить ноутбук">
                            </div>

                            <div id="whatDateDiv" class="form-group first-enter-hidden-div">
                                <label for="whatDateInput" id="whatDateLabel">К какому числу?</label>
                                <input type="date" class="form-control" id="whatDateInput" placeholder="50000.00">
                            </div>

                            <div id="howMuchItCostDiv" class="form-group first-enter-hidden-div">
                                <label for="howMuchItCostInput" id="howMuchItCostLabel">Сколько это стоит?</label>
                                <input type="number" class="form-control" id="howMuchItCostInput"
                                       placeholder="50000.00">
                            </div>
                        </div>

                        <div id="balanceDiv" class="modal-body first-enter-modal-hidden-div col-lg-12 col-md-12">
                            <h5 style="text-align:center;">Баланс</h5>
                            <hr>
                            <div id="howManyYouHaveDiv" class="form-group first-enter-hidden-div">
                                <label for="howMuchItCostInput" id="howManyYouHaveInput">Сколько денег есть
                                    сейчас?</label>
                                <input type="number" class="form-control" id="howManyYouHaveLabel" placeholder="100.00">
                            </div>
                        </div>

                        <div id="operationsDiv" class="modal-body first-enter-modal-hidden-div col-lg-12 col-md-12">
                            <h5 style="text-align:center;">Регулярные операции</h5>
                            <hr>
                            <div id="incomeDiv" class="form-group first-enter-hidden-div">
                                <label for="howMuchItCostInput" id="incomeLabel">Доход</label>
                                <input type="number" class="form-control" id="incomeInput" placeholder="100.00">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button id="next-btn" step="1" type="button" class="btn btn-primary">Далее</button>
            </div>
        </div>
    </div>
</div>

</@c.page>
<script>
    $(document).ready(function () {
        $('#myModal').modal('show')
        $('#next-btn').click(function(){
            var step = Number($('#next-btn').attr('step'));

            console.log(step);
            if (step == 1) {
                $("#whatDoYouWantDiv").toggleClass('first-enter-primary-div first-enter-secondary-div');
                $("#whatDateDiv").toggleClass('first-enter-hidden-div first-enter-primary-div');
            };


            if (step == 2) {
                $("#whatDateDiv").toggleClass('first-enter-primary-div first-enter-secondary-div');
                $("#howMuchItCostDiv").toggleClass('first-enter-hidden-div first-enter-primary-div');
            };

            if (step == 3) {
                $("#setGoalBodyDiv").toggleClass('col-lg-12 col-lg-6');
                $("#balanceDiv").toggleClass('col-lg-12 col-lg-6');
                $("#operationsDiv").toggleClass('col-lg-12 col-lg-6');
                $("#howMuchItCostDiv").toggleClass('first-enter-primary-div first-enter-secondary-div');
                $("#balanceDiv").toggleClass('first-enter-modal-hidden-div first-enter-modal-showed-div');
                $("#howManyYouHaveDiv").toggleClass('first-enter-hidden-div first-enter-primary-div');
            };

            if (step == 4) {
                $("#setGoalBodyDiv").toggleClass('col-lg-6 col-lg-4');
                $("#balanceDiv").toggleClass('col-lg-6 col-lg-4');
                $("#operationsDiv").toggleClass('col-lg-6 col-lg-4');
                $("#operationsDiv").toggleClass('first-enter-modal-hidden-div first-enter-modal-showed-div');
                $("#howManyYouHaveDiv").toggleClass('first-enter-primary-div first-enter-secondary-div');
                $("#incomeDiv").toggleClass('first-enter-hidden-div first-enter-primary-div');
            };

            $('#next-btn').attr('step', step + 1);
        })
    });


</script>

