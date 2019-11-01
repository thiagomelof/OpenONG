import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';


@Component({
  selector: 'cdk-bar-graph',
  templateUrl: './bar-graph.component.html',
  styleUrls: ['./bar-graph.component.scss']
})
export class BarGraphComponent implements OnInit {

  constructor() { }

  ngOnInit() {
      setTimeout(() => {
          this.createBarGraph();
      },500)
  }

  createBarGraph() {
      new Chart('dash-bar-graph', {
            type: 'bar',
            data: {
                labels: [ "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out"],
                datasets: [
                    {
                        backgroundColor: 'rgba(102, 187, 106, .7)',
                        borderColor: 'rgba(255, 99, 132)',
                        data: [10, 11, 13, 25, 4, 1, 5, 6,2,15],
                        label: 'Doadores',
                        fill: 'true'
                    }
                ]
            },
            options: {
                legend: {
                    display: false
                },
                elements : {
                    line: {
                        tension: 0.000001
                    }
                },
                maintainAspectRatio: false,
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'NOVOS DOADORES'
                }
            }
        })
  }
}
