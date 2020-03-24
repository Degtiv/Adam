$(document).ready(function () {
    $("#debug-button").click(function () {
        getOverviewDiagramData();
    });
});

function getOverviewDiagramData() {
    const data = {
        foo: {
            bar: [1, 2, 3]
        }
    };

    var csrfToken = $("#debug-button").attr("csrf");

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
        console.log(data);
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