import { DateUtils } from './../../shared/date-utils';
import { DespesasPorCategoriaMessage } from './../../model-view/dto/despesas-por-categoria-message';
import { DashboardService } from './../../services/dashboard.service';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';

@Component({
    selector: 'cdk-doughnut-graph',
    templateUrl: './doughnut-graph.component.html',
    styleUrls: ['./doughnut-graph.component.scss']
})
export class DoughnutGraphComponent implements OnInit {
    despesaItem: DespesasPorCategoriaMessage[];
    categorias: string[];
    valores: number[];
    mes: Date;
    constructor(private dashboardService: DashboardService) { }

    ngOnInit() {
        this.despesaItem = [];
        this.categorias = [];
        this.valores = [];
        this.mes = new Date();

        this.dashboardService.listarDespesasAtivasPorCategoria().subscribe(d => {
            this.despesaItem = d;

            this.despesaItem.forEach(cat => {
                if (this.categorias.length <= 5) {
                    this.categorias.push(cat.categoria);
                    this.valores.push(cat.valor);
                }
            });

            setTimeout(() => {
                this.createDoughnutGraph();
            }, 500)

        });




    }

    createDoughnutGraph() {
        new Chart('doughnut-graph-graph', {
            type: 'doughnut',
            data: {
                labels: this.categorias,
                datasets: [{
                    data: this.valores,
                    backgroundColor: [
                        'rgba(255, 99, 132,.7)',
                        'rgba(92, 107, 192,.7)',
                        'rgba(66, 165, 245,.7)',
                        'rgba(38, 166, 154,.7)',
                        'rgba(102, 187, 106,.7)'
                    ],
                }]
            },
            options: {
                elements: {
                    line: {
                        tension: 0.000001
                    }
                },
                legend: {
                    display: false
                },
                maintainAspectRatio: false,
                responsive: true,
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'DESPESAS POR CATEGORIA EM ' + DateUtils.getMes(this.mes.getMonth() + 1).toUpperCase()
                }
            }

        })
    }

}
