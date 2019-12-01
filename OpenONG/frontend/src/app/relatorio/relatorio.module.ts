import { RelDoacaoComponent } from './doacao/rel-doacao.component';
import { RelDespesaComponent } from './despesa/rel-despesa.component';
import { SharedModule } from './../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RouterModule, Routes } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import {
    MatSnackBarModule, MatIconModule, MatToolbarModule, MatSelectModule,
    MatCheckboxModule, MatPaginatorModule, MatSortModule, MatTooltipModule,
    MatChipsModule, MatButtonToggleModule, MatAutocompleteModule
} from '@angular/material';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatTabsModule } from '@angular/material/tabs';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { RelatorioComponent } from './relatorio.component';
import { NgxPrintModule } from 'ngx-print';
import { RelConsumoConvenioComponent } from './consumo-convenio/rel-consumo-convenio.component';


const appRoutes: Routes = [
    { path: '', component: RelatorioComponent },
    { path: 'reldespesa', component: RelDespesaComponent, data: { animation: 'reldespesa' } },
    { path: 'reldoacao', component: RelDoacaoComponent, data: { animation: 'reldoacao' } },
    { path: 'relconsumo', component: RelConsumoConvenioComponent, data: { animation: 'relconsumo' } }
]

@NgModule({
    imports: [
        CommonModule,
        MatInputModule,
        MatIconModule,
        MatSnackBarModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatSelectModule,
        MatCheckboxModule,
        MatButtonModule,
        ReactiveFormsModule,
        FormsModule,
        FlexLayoutModule,
        MatTableModule,
        MatProgressBarModule,
        MatTabsModule,
        MatListModule,
        MatStepperModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatPaginatorModule,
        MatSortModule,
        MatTooltipModule,
        MatAutocompleteModule,
        MatChipsModule,
        MatButtonToggleModule,
        RouterModule.forChild(appRoutes),
        SharedModule,
        NgxPrintModule
    ],
    exports: [],
    declarations: [RelatorioComponent, RelDoacaoComponent, RelDespesaComponent, RelConsumoConvenioComponent]
})
export class RelatorioModule { }
