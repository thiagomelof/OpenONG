import { SharedModule } from './../shared/shared.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { RouterModule, Routes } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule, MatIconModule, MatToolbarModule, MatSelectModule, MatCheckboxModule, MatPaginatorModule, MatSortModule, MatTooltipModule, MatChipsModule, MatButtonToggleModule } from '@angular/material';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatTabsModule } from '@angular/material/tabs';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { UsuarioComponent } from './usuario.component';
import { ListComponent } from './list/list.component';
const appRoutes: Routes = [
  { path: '', component: UsuarioComponent },
  { path: 'list', component: ListComponent, data: { animation: 'list' } }
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
    MatChipsModule,
    MatButtonToggleModule,
    RouterModule.forChild(appRoutes),
    SharedModule
  ],
  exports: [],
  declarations: [UsuarioComponent, ListComponent]
})
export class UsuarioModule { }
