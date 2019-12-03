import { DoacoesPorPeriodoMessage } from './../../model-view/dto/doacoes-por-periodo-message';
import { DashboardService } from './../../services/dashboard.service';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'cdk-line-graph',
    templateUrl: './line-graph.component.html',
    styleUrls: ['./line-graph.component.scss']
})
export class LineGraphComponent implements OnInit {
    valoresAtual: number[];
    valoresPassado: number[];
    doacoesPorPeriodoMessage: DoacoesPorPeriodoMessage[];
    constructor(private dashboardService: DashboardService) { }

    ngOnInit() {
        this.valoresAtual = [];
        this.valoresPassado = [];
        this.doacoesPorPeriodoMessage = [];

        this.dashboardService.listarDoacoesPorPeriodo(new Date().getFullYear())

            .pipe(finalize(() => {


                this.dashboardService.listarDoacoesPorPeriodo(new Date().getFullYear() - 1).subscribe(d => {
                    this.doacoesPorPeriodoMessage = d;

                    this.doacoesPorPeriodoMessage.forEach(element => {
                        this.valoresPassado.push(element.valor);
                    });

                    setTimeout(() => {
                        this.createLineChart();
                    }, 500)
                });




            }))


            .subscribe(d => {
                this.doacoesPorPeriodoMessage = d;

                this.doacoesPorPeriodoMessage.forEach(element => {
                    this.valoresAtual.push(element.valor);
                });
            });


    }

    createLineChart() {
        new Chart('line-graph', {
            type: 'line',
            data: {
                labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", 'Out', 'Nov', 'Dez'],
                datasets: [{
                    backgroundColor: 'rgba(38, 166, 154,.7)',
                    borderColor: 'rgba(38, 166, 154,.7)',
                    data: this.valoresAtual,
                    label: new Date().getFullYear(),
                    fill: 'start'
                },
                {
                    backgroundColor: 'rgba(255, 99, 132,.7)',
                    borderColor: 'rgba(255, 99, 132,.7)',
                    data: this.valoresPassado,
                    label: new Date().getFullYear() - 1,
                    fill: 'start'
                }
                ]
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
                plugins: {
                    filler: {
                        propagate: false
                    }
                },
                title: {
                    display: true,
                    text: 'COMPARATIVO DE DOAÇÕES ' + (new Date().getFullYear() - 1) + ' x ' + new Date().getFullYear()
                }
            }
        })
    }

}
