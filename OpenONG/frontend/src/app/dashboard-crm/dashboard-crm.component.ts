import { ConvenioService } from './../services/convenio.service';
import { DashboardService } from './../services/dashboard.service';
import { Component, OnInit } from '@angular/core';
import { TipoParceiro } from '../model-view/const/tipoparceiro';

@Component({
    selector: 'app-dashboard-crm',
    templateUrl: './dashboard-crm.component.html',
    styleUrls: ['./dashboard-crm.component.scss']
})

export class DashboardCrmComponent implements OnInit {
    countBeneficiados: number;
    countDoadores: number;
    countConvenio: number;

    public dashCard = [];

    constructor(private dashboardService: DashboardService, private convenioService: ConvenioService) { }

    ngOnInit() {
        this.dashboardService.listarParceirosAtivosPorTipo(TipoParceiro.Beneficiado).subscribe(b => {
            this.countBeneficiados = b.length;
            this.dashCard.push({ colorDark: '#5C6BC0', colorLight: '#7986CB', number: this.countBeneficiados, title: 'BENEFICIADOS', icon: 'supervisor_account' })
        })

        this.dashboardService.listarParceirosAtivosPorTipo(TipoParceiro.Doador).subscribe(d => {
            this.countDoadores = d.length;
            this.dashCard.push({ colorDark: '#66BB6A', colorLight: '#81C784', number: this.countDoadores, title: 'DOADORES', icon: 'favorite' })
        })

        this.convenioService.listarAtivos().subscribe(c => {
            this.countConvenio = c.length;
            this.dashCard.push({ colorDark: '#26A69A', colorLight: '#4DB6AC', number: this.countConvenio, title: 'CONVÊNIOS', icon: 'assignments' });
        })
    }

}
