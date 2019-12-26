var ctx = document.getElementById('chart').getContext('2d');

var chart=null;
function makeChart(content) {

    var json=JSON.parse(content);

    var data1={
        label: 'Uruchomienie 1',
        fill:false,
        borderColor: 'rgb(120,185,250)',
        data: json[0]
    };
    var lab=[];
    for(i=0;i<json[0].length;i++)
    {
        lab.push(i);
    }



    chart = new Chart(ctx, {
        // The type of chart we want to create
        type: 'line',
        backgroundColor: ['rgb(2, 200, 29)'],
        // The data for our dataset
        data: {
            labels:lab,
            datasets: [data1]
        },

        // Configuration options go here
        options: {
            aspectRatio:1.9,
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





