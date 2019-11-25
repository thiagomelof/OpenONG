import { Status } from './../../model-view/const/status';
import { ParceiroDeNegocioService } from './../../services/parceiro-de-negocio.service';
import { ParceirosPorPeriodoMessage } from './../../model-view/dto/parceiros-por-periodo-message';
import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import { TipoParceiro } from '../../model-view/const/tipoparceiro';


@Component({
    selector: 'cdk-bar-graph',
    templateUrl: './bar-graph.component.html',
    styleUrls: ['./bar-graph.component.scss']
})
export class BarGraphComponent implements OnInit {
    data: number[];
    parceirosPorPeriodoMessage: ParceirosPorPeriodoMessage[];

    constructor(private parceiroService: ParceiroDeNegocioService) { }



    ngOnInit() {
        this.data = [];
        this.parceiroService.listarParceirosQuantidadePorTipoEPeriodo(TipoParceiro.Doador, Status.Ativo).subscribe(d => {
            this.parceirosPorPeriodoMessage = d;

            this.parceirosPorPeriodoMessage.forEach(element => {
                this.data.push(element.quantidade);
            });

            setTimeout(() => {
                this.createBarGraph();
            }, 500)

        });




    }

    createBarGraph() {
        new Chart('dash-bar-graph', {
            type: 'bar',
            data: {
                labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
                datasets: [
                    {
                        backgroundColor: 'rgba(102, 187, 106, .7)',
                        borderColor: 'rgba(255, 99, 132)',
                        data: this.data,
                        label: 'Doadores',
                        fill: 'true'
                    }
                ]
            },
            options: {
                legend: {
                    display: false
                },
                elements: {
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
