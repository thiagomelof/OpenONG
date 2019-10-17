import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
  selector: 'cdk-line-graph',
  templateUrl: './line-graph.component.html',
  styleUrls: ['./line-graph.component.scss']
})
export class LineGraphComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    setTimeout(() => {
        this.createLineChart();
    },500)
  }
  createLineChart() {
      new Chart('line-graph', {
                type: 'line',
                data: {
                    labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago","Set",'Out'],
                    datasets: [{
                        backgroundColor: 'rgba(92, 107, 192, 0.36)',
                        borderColor: 'rgba(92, 107, 192,.5)',
                        data: [7000, 10000, 9500, 15000, 5000.85, 13000.91, 20000.36, 12000.66, 3000.36, 7000.66],
                        label: 'Dataset',
                        fill: 'start'
                    }]
                },
                options: {
                    elements : {
                        line: {
                            tension: 0.000001
                        }
                    },
                    legend: {
                        display: false
                    },
                    maintainAspectRatio: false,
                    plugins: {
                        filler: {
                            propagate: false
                        }
                    },
                    title: {
                        display: true,
                        text: 'DOAÇÕES MENSAIS'
                    }
                }
        })
  }

}
