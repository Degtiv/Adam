function drawDiagram(rawData) {
    var height = 500,
        width = 1000,
        margin = 50,
        yOffset = 10,
        pastLine = [],
        futureLine = [],
        events = [],
        min = d3.min(rawData.dayReports, function (d) { return d.dayBalance; }),
        max = d3.max(rawData.dayReports, function (d) { return d.dayBalance; }),
        xOffset = 0.3 * (max - min),
        startD = parseDate(rawData.start),
        todayD = parseDate(rawData.now),
        endD = parseDate(rawData.end),
        pastDotColor = "#10de9f",
        nowDotColor = "#fe1010",
        futureDotColor = "#dddddd";
    console.log(rawData);
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
        var dayDate = parseDate(rawData.dayReports[i].dateTime);
        var dayBalance = rawData.dayReports[i].dayBalance;
        var transactions = rawData.dayReports[i].transactions;
        var goals = rawData.dayReports[i].goals;
        var tense = "";
        var dotColor = "";

        //Следующие три ифа - индусский код, не знаю как переписать по-нормальному
        if (!(todayD > dayDate) && !(todayD < dayDate)) {
            pastLine.push({ date: dayDate, balance: dayBalance });
            futureLine.push({ date: dayDate, balance: dayBalance });
            tense = "now";
            dotColor = nowDotColor;
        }

        if (todayD > dayDate) {
            pastLine.push({ date: dayDate, balance: dayBalance });
            tense = "past";
            dotColor = pastDotColor;
        }

        if (todayD < dayDate) {
            futureLine.push({ date: dayDate, balance: dayBalance });
            tense = "future";
            dotColor = futureDotColor;
        }

        if (transactions.length > 0 || goals.length > 0)
            events.push({ 
                date: dayDate, 
                balance: dayBalance, 
                transactions: transactions, 
                goals: goals, 
                tense: tense, 
                dotColor: dotColor});
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
    createChart(pastLine, pastDotColor, g);
    createChart(futureLine, futureDotColor, g);
    createDots(events)

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
            .attr("id", function (d) { return "dot-" + formatDate(d.date); })
            .on("mouseover", function (d) {
                printEvents("#transactions-info", d.transactions);
                printEvents("#goals-info", d.goals);

                $('#balance-info h5').text("Balance: " + d.balance + " RUR");
                $('#date-info h6').text("Date: " + formatDate(d.date));
                svg.select('#dot-' + formatDate(d.date))
                    .attr("r", 7)
                    .style("fill", "ff4d8e");
                overviewInfo
                    .transition()
                    .duration(500)	
                    .style("opacity", .9);	
                overviewInfo	 
                    .style("left", (d3.event.pageX + 50) + "px")			 
                    .style("top", (d3.event.pageY - 100) + "px");
            })
            .on("mouseout", function (d) {
                svg.select('#dot-' + formatDate(d.date))
                    .attr("r", 3.5)
                    .style("fill", d.dotColor);
                overviewInfo
                    .style("opacity", 0);
                overviewInfo
                    .style("left", -100 + "%")			 
                    .style("top", -100 + "%");
            });

        function printEvents(eventId, events) {
            //Enter
            d3.select(eventId)
                .selectAll("p")
                .data(events)
                .enter()
                .append("p")
                .attr('class', 'card-text');

            //Update
            d3.select(eventId)
                .selectAll("p")
                .data(events)
                .text(function (d) { return d.title + " " + d.amount + d.currency; })

            //Exit
            d3.select(eventId)
                .selectAll("p")
                .data(events)
                .exit()
                .remove();
        }
    }
}

function formatDate(d) {
    var formatD = d3.time.format("%Y-%m-%d");
    return formatD(d);
}

function parseDate(d) {
    return d3.time.format("%Y-%m-%d").parse(d);
}

$(document).ready(function () {
    $('#overview_diagram_date_to').on('input', function () {
        getOverviewDiagramData();
    });
});