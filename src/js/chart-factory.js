function create_chart_1() {
  console.log('START: chart 1');
  $('#chart-1').highcharts({
    chart: {
      type: 'areaspline'
    },
    title: {
      text: null
    },
    xAxis: {
      title: {
        text: 'Distance [m]'
      },
      labels: {
        enabled: false
      },
      tickLength: 0,
      crosshair: true,
      plotBands: [
        {
          from: 0,
          to: 250,
          color: 'green',
          label: {
            text: '2%',
            style: {
              color: '#FFF'
            }
          }
        },
        {
          from: 320,
          to: 540,
          color: 'green',
          label: {
            text: '5%',
            style: {
              color: '#FFF'
            }
          }
        },
        {
          from: 575,
          to: 680,
          color: 'orange',
          label: {
            useHTML: true,
            textAlign: 'center',
            text: '20%',
            style: {
              color: '#FFF'
            }
          }
        },
        {
          from: 680,
          to: 800,
          color: 'red',
          label: {
            useHTML: true,
            text: '22%',
            style: {
              color: '#FFF'
            }
          }
        },
        {
          from: 1250,
          to: 1400,
          color: 'orange',
          label: {
            useHTML: true,
            text: '9%',
            style: {
              color: '#FFF'
            }
          }
        },
        {
          from: 2000,
          to: 2250,
          color: 'red',
          label: {
            useHTML: true,
            text: '31%',
            style: {
              color: '#FFF'
            }
          }
        }
      ]
    },
    yAxis: {
      max: 50,
      min: 0,
      gridLineWidth: 0,
      crosshair: true,
      title: {
        text: 'Elevation [m]'
      }
    },
    legend: {
      enabled: false
    },
    plotOptions: {
      areaspline: {
        fillOpacity: 1,
        dataLabels: {
          enabled: true,
          y: 25
        },
        marker: {
          enabled: true,
          symbol: 'triangle',
          fillColor: '#CA1616'
        }
      }
    },
    series: [
      {
        name: 'Route #1',
        data: [
          [0, 24],
          [250, 28],
          [320, 26],
          [540, 31],
          [575, 29],
          [680, 36],
          [800, 43],
          [915, 35],
          [1100, 31],
          [1250, 21],
          [1400, 28],
          [2000, 27],
          [2250, 39],
          [2500, 29]
        ] 
      }
    ]
  });
  console.log('END: chart 1');
}
