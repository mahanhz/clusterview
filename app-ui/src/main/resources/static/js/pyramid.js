$(document).ready(function() {

    // Convert the table into a javascript object
    var coursesData = $('.coursesTable').tableToJSON({ ignoreHiddenRows: false });

    // Convert values from string to float
    $.each(coursesData, function(index, value){
        if (value.y) {
            coursesData[index].y = parseFloat(value.y);
        }
    });

    Highcharts.chart('coursesPyramid', {
        chart: {
            type: 'pyramid',
            marginRight: 100
        },
        credits: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            pyramid: {
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b> (<span style="color:#009cf7">{point.y:,.0f}</span>)',
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
                    softConnector: true,
                    distance: 15
                }
            }
        },
        series: [{
            name: 'Total',
            data: coursesData
        }],
        title: {
            text: null
        },
    });

});