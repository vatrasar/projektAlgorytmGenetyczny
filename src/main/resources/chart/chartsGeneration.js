var ctx = document.getElementById('chart').getContext('2d');

var chart=null;
function makeChart(content) {

    var json=JSON.parse(content);
    var datas=[];
    var labelData1='Wartości funkcji';
    if(json.length>1)
        labelData1='Średnie wartości funkcji';
    var data1={
        label: 'Wartości funkcji',
        fill:false,
        borderColor: 'rgb(120,185,250)',
        data: json[0]
    };
    datas.push(data1);
    var lab=[];
    for(i=0;i<json[0].length;i++)
    {
        lab.push(i);
    }

    if(json.length>1)
    {
        var standardDeviation1={
            label: 'Odchylenie standardowe',
            fill:0,
            borderColor:'rgb(195,105,240)',
            data: json[1]
        };
        var standardDeviation2={
            label: '',
            fill:0,
            borderColor: 'rgb(194,105,240)',
            data: json[2]
        };
        datas.push(standardDeviation1);
        datas.push(standardDeviation2);
    }



    chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',
        backgroundColor: ['rgb(2, 200, 29)'],
        // The data for our dataset
        data: {
            labels:lab,
            datasets: datas
        },

        // Configuration options go here
        options: {
            legend: {
                display: true,

            },
            aspectRatio:2,
            scales: {
                xAxes:[
                    {
                        ticks:{

                            autoSkip:true,
                            autoSkipPadding:20



                        }
                    }
                ],
                yAxes:[
                    {
                        ticks:{


                        }
                    }
                ]
            }
        }
    });


}
function resetContent() {

    $("#chart").html("");

}





