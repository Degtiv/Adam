function drawDiagram(rawData) {
    var height = 500,
        width = 1000,
        margin = 50,
        yOffset = 10,
        dataPast = [],
        dataFuture = [],
        min = d3.min(rawData.dayReports, function (d) { return d.dayBalance; }),
        max = d3.max(rawData.dayReports, function (d) { return d.dayBalance; }) * 1.2,
        xOffset = 0.3 * (max - min),
        startD = d3.time.format("%Y-%m-%d").parse(rawData.start),
        todayD = d3.time.format("%Y-%m-%d").parse(rawData.now),
        endD = d3.time.format("%Y-%m-%d").parse(rawData.end);

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
        var dayDate = d3.time.format("%Y-%m-%d").parse(rawData.dayReports[i].dateTime),
            dayBalance = rawData.dayReports[i].dayBalance;

        if (!(todayD > dayDate) && !(todayD < dayDate)) {
            dataPast.push({ date: dayDate, balance: dayBalance });
            dataFuture.push({ date: dayDate, balance: dayBalance });
        }

        if (todayD > dayDate)
            dataPast.push({ date: dayDate, balance: dayBalance });
        else
            dataFuture.push({ date: dayDate, balance: dayBalance });
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

    createChart(dataPast, "#10de9f", "past", g);
    createChart(dataFuture, "#dddddd", "future", g);

    g.append("line")
        .attr("x1", scaleX(todayD) + margin)
        .attr("y1", 0 + margin)
        .attr("x2", scaleX(todayD) + margin)
        .attr("y2", yAxisLength + margin)
        .style("stroke", "#30d5c8")
        .style("stroke-width", 2);

    // общая функция для создания графиков
    function createChart(data, colorStroke, label, g) {
        // функция, создающая по массиву точек линии
        var line = d3.svg.line()
            .interpolate("monotone")
            .x(function (d) { return scaleX(d.date) + margin; })
            .y(function (d) { return scaleY(d.balance) + margin; });

        g.append("path")
            .attr("d", line(data))
            .style("stroke", colorStroke)
            .style("stroke-width", 2);

        // добавляем отметки к точкам
        svg.selectAll(".dot " + label)
            .data(data)
            .enter().append("circle")
            .style("stroke", "000")
            .style("stroke-width", 1)
            .style("fill", colorStroke)
            .attr("class", "dot " + label)
            .attr("r", 3.5)
            .attr("cx", function (d) { return scaleX(d.date) + margin; })
            .attr("cy", function (d) { return scaleY(d.balance) + margin; });

        svg.selectAll(".dot " + label).data(data).enter().append('div');
        svg.selectAll(".dot " + label).data(data)
            .on("mouseover", function(d) {
                div.transition()
                   .duration(500)
                   .style("opacity", .9);
                div.html('')
                    .style("left", (d3.event.pageX) + "px")
                    .style("top", (d3.event.pageY - 30) + "px")
            })
            .on("mouseout", function() {
                div.transition()
                   .duration(500)
                   .style("opacity", 0)
                   .on('end', function() {
                       div.html('');
                   });
            });
        svg.selectAll(".dot " + label).data(data).exit().remove();
    };
}

$(document).ready(function () {
    $('#overview_diagram_date_to').on('input', function () {
            getOverviewDiagramData();
        });
});