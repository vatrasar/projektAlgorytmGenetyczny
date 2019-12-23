var ctx = document.getElementById('chart').getContext('2d');
data1={
    label: 'My First dataset',
    fill:false,
    borderColor: 'rgb(255, 99, 132)',
    data: [0, 10, 5, 2, 20, 30, 45]
};
var lab=[];
for(i=0;i<300;i++)
{
    lab.push(i);
}


data2={
    label: 'My First dataset',
    fill:0,
    borderColor: 'rgb(2, 200, 29)',
    data: [0, 5, 2, 7, 15, 14, 1,5,6,7,8,8,9,9,24,300]
};
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',
    backgroundColor: ['rgb(2, 200, 29)'],
    // The data for our dataset
    data: {
        labels:lab,
        datasets: [data1,data2]
    },

    // Configuration options go here
    options: {
        aspectRatio:1.9,
        scales: {
            xAxes:[
                {
                    ticks:{

                        autoSkip:true,



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


