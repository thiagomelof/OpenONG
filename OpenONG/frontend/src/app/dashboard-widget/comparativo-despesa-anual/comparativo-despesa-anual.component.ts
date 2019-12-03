import { DespesasPorPeriodoMessage } from '../../model-view/dto/despesas-por-periodo-message';
import { DashboardService } from '../../services/dashboard.service';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { finalize } from 'rxjs/operators';

@Component({
    selector: 'cdk-comparativo-despesa-anual',
    templateUrl: './comparativo-despesa-anual.component.html',
    styleUrls: ['./comparativo-despesa-anual.component.scss']
})
export class ComparativoDespesaLineGraphComponent implements OnInit {
    valoresAtual: number[];
    valoresPassado: number[];
    despesasPorPeriodoMessage: DespesasPorPeriodoMessage[];
    constructor(private dashboardService: DashboardService) { }

    ngOnInit() {
        this.valoresAtual = [];
        this.valoresPassado = [];
        this.despesasPorPeriodoMessage = [];

        this.dashboardService.listarDespesasPorPeriodo(new Date().getFullYear())
        
            .pipe(finalize(() => {

                this.dashboardService.listarDespesasPorPeriodo(new Date().getFullYear() - 1).subscribe(d => {
                    this.despesasPorPeriodoMessage = d;

                    this.despesasPorPeriodoMessage.forEach(element => {
                        this.valoresPassado.push(element.valor);
                    });

                    setTimeout(() => {
                        this.createLineChart();
                    }, 1000)
                });




            }))


            .subscribe(d => {
                this.despesasPorPeriodoMessage = d;

                this.despesasPorPeriodoMessage.forEach(element => {
                    this.valoresAtual.push(element.valor);
                });
            });


    }

    createLineChart() {
        new Chart('cdk-comparativo-despesa-anual', {
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
                    text: 'COMPARATIVO DE DESPESAS ' + (new Date().getFullYear() - 1) + ' x ' + new Date().getFullYear()
                }
            }
        })
    }

}
