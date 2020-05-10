function drawDiagram(rawData) {
    var height = 500,
        width = 1000,
        margin = 50,
        yOffset = 10,
        pastLine = [],
        futureLine = [],
        baseTransactions = [],
        goalsTotal = [],
        min = d3.min(rawData.dayReports, function (d) { return d.endDayBalance; }),
        max = d3.max(rawData.dayReports, function (d) { return d.endDayBalance; }),
        xOffset = 0.3 * (max - min),
        startD = parseDate(rawData.start, "%Y-%m-%d"),
        todayD = parseDate(rawData.now, "%Y-%m-%d"),
        endD = parseDate(rawData.end, "%Y-%m-%d"),
        pastDotColor = "#10de9f",
        nowDotColor = "#fe1010",
        futureDotColor = "#dddddd",
        activeDotColor = "#EA0037",
        goalDotColor = "#6FF8AD";
    $('#overview-diagram').empty();

    var svg = d3.select("#overview-diagram").append("svg")
        .attr("class", "axis")
        .attr("width", width)
        .attr("height", height);

    // длина оси X= ширина контейнера svg - отступ слева и справа
    var xAxisLength = width - 2 * margin;

    // длина оси Y = высота контейнера svg - отступ сверху и снизу
    var yAxisLength = height - 2 * margin;

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
        var dotColor = "";

        //Следующие три ифа - индусский код, не знаю как переписать по-нормальному
        if (!(todayD > dayDate) && !(todayD < dayDate)) {
            pastLine.push({ date: dayDate, balance: startDayBalance });
            futureLine.push({ date: dayDate, balance: startDayBalance });
            tense = "now";
            dotColor = nowDotColor;
        }

        if (todayD > dayDate) {
            pastLine.push({ date: dayDate, balance: startDayBalance });
            tense = "past";
            dotColor = pastDotColor;
        }

        if (todayD < dayDate) {
            futureLine.push({ date: dayDate, balance: startDayBalance });
            tense = "future";
            dotColor = futureDotColor;
        }

        if (transactions.length > 0 || goals.length > 0) {
            baseTransactions.push({
                date: dayDate, 
                balance: startDayBalance,
                transactions: transactions, 
                goals: goals, 
                tense: tense, 
                dotColor: dotColor});
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
        .attr("class", "x-axis")
        .attr("transform",  // сдвиг оси вниз и вправо
            "translate(" + margin + "," + (height - margin) + ")")
        .call(xAxis);

    // отрисовка оси Y
    svg.append("g")
        .attr("class", "y-axis")
        .attr("transform", // сдвиг оси вниз и вправо на margin
            "translate(" + margin + "," + margin + ")")
        .call(yAxis);

    var g = svg.append("g");
    var overviewInfo = d3.select("#overview-info");
    var goalInfo = d3.select("#goal-info");
    createChart(pastLine, pastDotColor, g);
    createChart(futureLine, futureDotColor, g);
    createDots(baseTransactions);

    g.append("line")
        .attr("x1", scaleX(todayD) + margin)
        .attr("y1", 0 + margin)
        .attr("x2", scaleX(todayD) + margin)
        .attr("y2", yAxisLength + margin)
        .style("stroke", "#30d5c8")
        .style("stroke-width", 2);

    // общая функция для создания графиков
    function createChart(data, colorStroke, g) {
        // функция, создающая по массиву точек линии
        var line = d3.svg.line()
            .interpolate("monotone")
            .x(function (d) { return scaleX(d.date) + margin; })
            .y(function (d) { return scaleY(d.balance) + margin; });

        g.append("path")
            .attr("d", line(data))
            .style("stroke", colorStroke)
            .style("stroke-width", 2);
    };

    function createDots(data) {
        // добавляем отметки к точкам
        svg.selectAll(".dot")
            .data(data)
            .enter().append("circle")
            .style("stroke", "000")
            .style("stroke-width", 1)
            .style("fill", function (d) { return d.dotColor; })
            .attr("class", "dot " + function (d) { return d.tense; })
            .attr("r", 3.5)
            .attr("cx", function (d) { return scaleX(d.date) + margin; })
            .attr("cy", function (d) { return scaleY(d.balance) + margin; })
            .attr("id", function (d) { return "dot-" + formatDate(d.date, "%Y-%m-%d"); })
            .on("mouseover", function (d) {
                printBaseTransactions("#transactions-info", d.transactions);
                printBaseTransactions("#goals-info", d.goals);

                $('#balance-info h5').text("Balance: " + d.balance + " RUR");
                $('#date-info h6').text("Date: " + formatDate(d.date, "%Y-%m-%d"));
                svg.select('#dot-' + formatDate(d.date, "%Y-%m-%d"))
                    .attr("r", 7)
                    .style("fill", activeDotColor);
                overviewInfo
                    .transition()
                    .duration(500)	
                    .style("opacity", .9);	
                overviewInfo	 
                    .style("left", (d3.event.pageX + 50) + "px")			 
                    .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function (d) {
                svg.select('#dot-' + formatDate(d.date, "%Y-%m-%d"))
                    .attr("r", 3.5)
                    .style("fill", d.dotColor);
                overviewInfo
                    .style("opacity", 0);
                overviewInfo
                    .style("left", - 100 + "%")
                    .style("top", - 100 + "%");
            });

        svg.selectAll(".goal-dot")
            .data(goalsTotal)
            .enter().append("polygon")
            .style("stroke", "#DDDDDD")
            .style("stroke-width", "2")
            .attr("points", function (d) {
                    var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                    var cy = scaleY(d.amount) + margin;

                    var triangleCoordinates = getTriangleCoordinates(cx, cy, 6);
                    return triangleCoordinates;
                })
            .style("stroke", "000")
            .style("stroke-width", 1)
            .style("fill", goalDotColor)
            .attr("class", "goal-dot")
            .attr("id", function (d) { return "goal-dot-" + d.uuid; })
            .on("mouseover", function (d) {
                svg.select("#goal-dot-" + d.uuid)
                    .attr("points", function (d) {
                        var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                        var cy = scaleY(d.amount) + margin;

                        var triangleCoordinates = getTriangleCoordinates(cx, cy, 10);
                        return triangleCoordinates;
                    })
                    .style("fill", activeDotColor);

                $('#goal-title h5').text("Goal: " + d.title);
                $('#goal-date h6').text("Date: " + formatDate(parseDate(d.date, "%Y-%m-%dT%H:%M:%S"), "%Y-%m-%d"));
                $('#goal-amount h6').text("Amount: " + d.amount + " RUR");

                goalInfo
                    .transition()
                    .duration(500)
                    .style("opacity", .9);
                goalInfo
                    .style("left", (d3.event.pageX + 50) + "px")
                    .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function (d) {
                svg.select("#goal-dot-" + d.uuid)
                    .attr("points", function (d) {
                        var cx = scaleX(parseDate(d.date, "%Y-%m-%dT%H:%M:%S")) + margin;
                        var cy = scaleY(d.amount) + margin;

                        var triangleCoordinates = getTriangleCoordinates(cx, cy, 6);
                        return triangleCoordinates;
                    })
                    .style("fill", goalDotColor);

                goalInfo
                    .style("opacity", 0);
                goalInfo
                    .style("left", - 100 + "%")
                    .style("top", - 100 + "%");
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
                    return transactionTypeIcon + d.title + " " + d.amount + d.currency; })

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