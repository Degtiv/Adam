$(document).ready(function () {
    var today = new Date(),
        weekAgoDate = new Date(),
        weekAfterDate = new Date();

    weekAgoDate.setDate(today.getDate() - 7);
    weekAfterDate.setDate(today.getDate() + 7);

    var weekAgo = weekAgoDate.toISOString().split('T')[0];
    var weekAfter = weekAfterDate.toISOString().split('T')[0];
    $('#overview_diagram_date_from').val(weekAgo);
    $('#overview_diagram_date_to').val(weekAfter);

    getOverviewDiagramData();
});

$(document).ready(function () {
    $('#overview_diagram_date_to').on('input', function () {
        getOverviewDiagramData();
    });
});

$(document).ready(function () {
    $('#overview_diagram_date_from').on('input', function () {
        getOverviewDiagramData();
    });
});

function getOverviewDiagramData() {
    var startDate = $('#overview_diagram_date_from').val(),
        endDate = $('#overview_diagram_date_to').val();

    const data = {
        start: startDate,
        end: endDate
    };

    var csrfToken = $("#refresh-input").attr("csrf");

    $.ajax({
        type: 'POST',
        url: "status",
        data: JSON.stringify(data),
        headers: {
            "x-csrf-token": csrfToken,
            "Content-Type": "application/json"
        },
        dataType: 'json'
    }).done(function (data) {
        drawDiagram(data);
    })
}

function download(data, filename, type) {
    var file = new Blob([JSON.stringify(data, null, 2)], { type: type });
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        var a = document.createElement("a"),
            url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function () {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}