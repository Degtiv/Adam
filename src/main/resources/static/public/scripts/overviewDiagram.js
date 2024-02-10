function drawDiagram(rawData) {
    var svgHeight = 500,
        svgWidth = 1200,
        margin = 50,
        yOffset = 10,
        pastLine = [],
        futureLine = [],
        baseTransactions = [],
        goalsTotal = [],
        min = d3.min(rawData.dayReports, function (d) { return Math.min(d.startDayBalance, d.endDayBalance); }),
        max = d3.max(rawData.dayReports, function (d) { return Math.max(d.startDayBalance, d.endDayBalance); }),
        xOffset = 100 + Math.abs(0.3 * (max - min)),
        startD = parseDate(rawData.start, "%Y-%m-%d"),
        todayD = parseDate(rawData.now, "%Y-%m-%d"),
        endD = parseDate(rawData.end, "%Y-%m-%d");

        console.log(rawData);
    $('#overview-diagram').empty();
    var svg = d3.select("#overview-diagram").append("svg")
        .classed("axis", true)
        .attr("width", svgWidth)
        .attr("height", svgHeight);

    // длина оси X= ширина контейнера svg - отступ слева и справа
    var xAxisLength = svgWidth - 2 * margin;

    // длина оси Y = высота контейнера svg - отступ сверху и снизу
    var yAxisLength = svgHeight - 2 * margin;

    var scaleX = d3.time.scale()
        .domain([startD, endD])
        .range([0, xAxisLength]);

    // функция интерполяции значений на ось Y
    var scaleY = d3.scale.linear()
        .domain([max + xOffset, min - xOffset])
        .range([0, yAxisLength]);

    for (i = 0; i < rawData.dayReports.length; i++) {
        var dayDate = parseDate(rawData.dayReports[i].dateTime, "%Y-%m-%d");
        var startDayBalance = rawData.dayReports[i].startDayBalance;
        var transactions = rawData.dayReports[i].transactions;
        var goals = rawData.dayReports[i].goals;
        var tense = "";

        //Следующие три ифа - индусский код, не знаю как переписать по-нормальному
        if (!(todayD > dayDate) && !(todayD < dayDate)) {
            pastLine.push({ date: dayDate, balance: startDayBalance });
            futureLine.push({ date: dayDate, balance: startDayBalance });
            tense = "now";
        }

        if (todayD > dayDate) {
            pastLine.push({ date: dayDate, balance: startDayBalance });
            tense = "past";
        }

        if (todayD < dayDate) {
            futureLine.push({ date: dayDate, balance: startDayBalance });
            tense = "future";
        }

        if (transactions.length > 0 || goals.length > 0) {
            baseTransactions.push({
                date: dayDate,
                balance: startDayBalance,
                transactions: transactions,
                goals: goals,
                tense: tense});
        }

        if (typeof goals !== 'undefined' && goals.length > 0) {
            goalsTotal = [].concat(goalsTotal, goals);
        }
    }

    // создаем ось X
    var xAxis = d3.svg.axis()
        .scale(scaleX)
        .orient("bottom")
        .tickFormat(d3.time.format('%e.%m'));
    // создаем ось Y
    var yAxis = d3.svg.axis()
        .scale(scaleY)
        .orient("left");

    // отрисовка оси Х
    svg.append("g")
        .classed("x-axis", true)
        .attr("transform",  // сдвиг оси вниз и вправо
            "translate(" + margin + "," + (svgHeight - margin) + ")")
        .call(xAxis);

    // отрисовка оси Y
    svg.append("g")
        .classed("y-axis", true)
        .attr("transform", // сдвиг оси вниз и вправо на margin
            "translate(" + margin + "," + margin + ")")
        .call(yAxis);

    var g = svg.append("g");
    var overviewInfo = d3.select("#overview-info");
    var goalInfo = d3.select("#goal-info");
    createChart(pastLine, "past", g);
    createChart(futureLine, "future", g);
    createDots(baseTransactions);

    //Линия сегодняшнего дня
    g.append("line")
        .attr("x1", scaleX(todayD) + margin)
        .attr("y1", 0 + margin)
        .attr("x2", scaleX(todayD) + margin)
        .attr("y2", yAxisLength + margin)
        .classed("line", true)
        .classed("line-now", true);

    //Линия нуля, отрисовывает, только если она выше нижней границы (то есть её глубина меньше глубины нижней границы)
    if (scaleY(0) < yAxisLength)
        g.append("line")
            .attr("x1", 0 + margin)
            .attr("y1", scaleY(0) + margin)
            .attr("x2", xAxisLength + margin)
            .attr("y2", scaleY(0) + margin)
            .classed("line", true)
            .classed("line-zero", true);

    // общая функция для создания графиков
    function createChart(data, type, g) {
        // функция, создающая по массиву точек линии
        var line = d3.svg.line()
            .interpolate("monotone")
            .x(function (d) { return scaleX(d.date) + margin; })
            .y(function (d) { return scaleY(d.balance) + margin; });

        g.append("path")
            .attr("d", line(data))
            .classed("line", true)
            .classed("line-" + type, true);
    };

    function createDots(data) {
        // добавляем отметки к точкам
        svg.selectAll(".dot")
            .data(data)
            .enter().append("circle")
            .each(function (d) {
                var thisDot = d3.select(this);
                thisDot.classed("dot", true)
                thisDot.classed("dot-" + d.tense, true);
            })
            .attr("r", 2.5)
            .attr("cx", function (d) { return scaleX(d.date) + margin; })
            .attr("cy", function (d) { return scaleY(d.balance) + margin; })
            .attr("id", function (d) { return "dot-" + formatDate(d.date, "%Y-%m-%d"); })
            .on("mouseover", function (d) {
                $('#goal-info').css('display', 'none');

                printBaseTransactions("#transactions-info", d.transactions);
                printBaseTransactions("#goals-info", d.goals);

                $('#balance-info h5').text("Balance: " + d.balance + " RUR");
                $('#date-info h6').text("Date: " + formatDate(d.date, "%Y-%m-%d"));

                svg.select('#dot-' + formatDate(d.date, "%Y-%m-%d"))
                    .attr("r", 7)
                    .classed("dot-" + d.tense, false)
                    .classed("dot-active", true);
                overviewInfo.style("display", "block");
                overviewInfo
                    .transition()
                    .duration(500)
                    .style("opacity", .95);
                overviewInfo
                    .style("left", (d3.event.pageX + 50) + "px")
                    .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function (d) {
                svg.select('#dot-' + formatDate(d.date, "%Y-%m-%d"))
                    .attr("r", 2.5)
                    .classed("dot-active", false)
                    .classed("dot-"+ d.tense, true);

                // Старый код по скрытию окошка
                //
                // overviewInfo
                //     .transition()
                //     .duration(20000)
                //     .style("opacity", 0);
                // overviewInfo
                //     .transition()
                //     .delay(20500)
                //     .style("display", "none")
                //     .style("left", - 100 + "%")
                //     .style("top", - 100 + "%");
            });

        svg.selectAll(".dot-goal")
            .data(goalsTotal)
            .enter().append("polygon")
            .attr("points", function (d) {
                    var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                    var cy = scaleY(d.amount) + margin;

                    var triangleCoordinates = getTriangleCoordinates(cx, cy, 6);
                    return triangleCoordinates;
                })
            .classed("dot", true)
            .classed("dot-goal", true)
            .attr("id", function (d) { return "dot-goal-" + d.uuid; })
            .on("mouseover", function (d) {
                $('#overview-info').css('display', 'none');
                svg.select("#dot-goal-" + d.uuid)
                    .attr("points", function (d) {
                        var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                        var cy = scaleY(d.amount) + margin;

                        var triangleCoordinates = getTriangleCoordinates(cx, cy, 10);
                        return triangleCoordinates;
                    })
                    .classed("dot-goal", false)
                    .classed("dot-goal-active", true);

                $('#goal-title h5').text("Goal: " + d.title + " (" + d.status.toLowerCase() + ")");
                $('#goal-date h6').text("Date: " + formatDate(parseDate(d.date, "%Y-%m-%dT%H:%M:%S"), "%Y-%m-%d"));
                $('#goal-amount h6').text("Amount: " + d.amount + " RUR");

                var src = d.image === null || d.image === "" ?
                    "https://imgholder.ru/500x500/8493a8/adb9ca&text=Изображение/n+++отсутствует&font=matias" :
                    "/img/" + d.image;

                $('#goal-image img')
                    .attr('src', src)
                    .attr('alt', "Image for goal " + d.title);


                goalInfo.style("display", "block");
                goalInfo
                    .transition()
                    .duration(500)
                    .style("opacity", .95);
                goalInfo
                    .style("left", (d3.event.pageX + 50) + "px")
                    .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function (d) {
                svg.select("#dot-goal-" + d.uuid)
                    .attr("points", function (d) {
                        var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                        var cy = scaleY(d.amount) + margin;

                        var triangleCoordinates = getTriangleCoordinates(cx, cy, 6);
                        return triangleCoordinates;
                    })
                    .classed("dot-goal-active", false)
                    .classed("dot-goal", true);

                // Старый код по скрытию окошка
                //
                // goalInfo
                //     .transition()
                //     .duration(20000)
                //     .style("opacity", 0);
                // goalInfo
                //     .transition()
                //     .delay(20500)
                //     .style("display", "none")
                //     .style("left", - 100 + "%")
                //     .style("top", - 100 + "%");
            });;


        function printBaseTransactions(baseTransactionId, baseTransactions) {

            //Enter
            d3.select(baseTransactionId)
                .selectAll("p")
                .data(baseTransactions)
                .enter()
                .append("p")
                .attr('class', 'card-text');

            //Update
            d3.select(baseTransactionId)
                .selectAll("p")
                .data(baseTransactions)
                .html(function (d) {
                    var transactionTypeIcon;
                    if (d.transactionType == 'INCOME')
                        transactionTypeIcon = '<i class="material-icons btn-outline-dark" style="border-radius:20px; margin-bottom: 3px; color: #00C000;">keyboard_arrow_up</i>';
                    if (d.transactionType == 'COST')
                        transactionTypeIcon = '<i class="material-icons btn-outline-dark" style="border-radius:20px; margin-bottom: 3px; color: #C00000;">keyboard_arrow_down</i>';
                    return transactionTypeIcon + d.title + " " + d.amount + " " + d.currency + " (" + d.status.toLowerCase() + ")"; })

            //Exit
            d3.select(baseTransactionId)
                .selectAll("p")
                .data(baseTransactions)
                .exit()
                .remove();
        }
    }

    function getTriangleCoordinates(cx, cy, size) {
        return  (cx - size) + ',' + (cy + 2 * size) + ' ' +
                (cx - size * 0.5) + ',' + (cy + 1.2 * size) + ' ' +
                cx + ',' + (cy) + ' ' +
                (cx + size * 0.5) + ',' + (cy + 1.2 * size) + ' ' +
                (cx + size) + ',' + (cy + 2 * size) + ' ' +
                cx + ',' + (cy + 1.5 * size);
    }
}

function formatDate(d, dateFormat) {
    var formatD = d3.time.format(dateFormat);
    return formatD(d);
}

function parseDate(d, dateFormat) {
    return d3.time.format(dateFormat).parse(d);
}

$(document).ready(function () {
    $('#overview_diagram_date_to').on('input', function () {
        getOverviewDiagramData();
    });
});
