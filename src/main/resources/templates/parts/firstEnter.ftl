<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">

            <div class="modal-header">
                <h3 class="modal-title" id="exampleModalLongTitle" style="text-align:center">Начнем с главного.
                    Постановка цели</h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form id="finish-first-enter" method="post" action="/firstEnter/finish">
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">

                            <div id="setGoalBodyDiv"
                                 class="modal-body first-enter-modal-showed-div col-lg-12 col-md-12">
                                <div id="whatDoYouWantDiv" class="form-group first-enter-primary-div">
                                    <label for="whatDoYouWantLabel" id="whatDoYouWantLabel">Что вы хотите?</label>
                                    <input type="text" class="form-control" id="whatDoYouWantInput"
                                           placeholder="Например: Купить ноутбук" name="title" required>
                                </div>

                                <div id="whatDateDiv" class="form-group first-enter-hidden-div">
                                    <label for="whatDateInput" id="whatDateLabel">К какому числу?</label>
                                    <input type="date" class="form-control" id="whatDateInput" name="dateText" required>
                                </div>

                                <div id="howMuchItCostDiv" class="form-group first-enter-hidden-div">
                                    <label for="howMuchItCostInput" id="howMuchItCostLabel">Сколько это стоит?</label>
                                    <input type="number" step="0.01" class="form-control"
                                           id="howMuchItCostInput"
                                           placeholder="50000.00" name="amount" required>
                                </div>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </div>
                    </div>
                </div>
            </form>

            <form id="over-first-enter" method="post" action="/firstEnter/hide" hidden>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
            <div class="modal-footer">
                <div class="col-md-6">
                    <button id="over-btn" type="button"
                            class="btn btn-outline-danger float-left">Больше не показывать
                    </button>
                </div>

                <div class="col-md-6">
                    <button id="next-btn" step="1" type="button" class="btn btn-outline-primary float-right">Далее
                    </button>
                    <button id="finish-btn" type="button"
                            class="btn btn-outline-success float-right"
                            disabled
                            style="display:none;">Завершить
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#myModal').modal('show');
        $('#over-btn').click(function() {
            $( "#over-first-enter" ).submit();
        });

        $('#finish-btn').click(function() {
            $( "#finish-first-enter" ).submit();
        });

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
                $("#howMuchItCostDiv").toggleClass('first-enter-primary-div first-enter-secondary-div');
                $("#next-btn").css('display', 'none');
                $("#finish-btn").css('display', 'block');
            };

            $('#next-btn').attr('step', step + 1);
        });
    });

    /* https://stackoverflow.com/a/27829144 */
    $(document).ready(function() {
        $('#finish-first-enter input').keyup(function() {
            var empty = false;

            $("#finish-first-enter input[required]").each(function() {
                if ($(this).val() == '') {
                    empty = true;
                }
            });

            if (empty) {
                $('#finish-btn').attr('disabled', 'disabled');
            } else {
                $('#finish-btn').removeAttr('disabled');
            }
        });
    });

</script>