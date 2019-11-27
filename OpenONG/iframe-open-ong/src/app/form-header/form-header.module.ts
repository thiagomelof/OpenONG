import { MatTabsModule } from '@angular/material/tabs';
import { MatListModule } from '@angular/material/list';
import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormHeaderComponent } from './form-header.component';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatToolbarModule, MatSelectModule, MatCheckboxModule, MatPaginatorModule, MatSortModule, MatTooltipModule, MatChipsModule, MatButtonToggleModule } from '@angular/material';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatTableModule } from '@angular/material/table';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@NgModule({
    imports: [
        CommonModule,
        MatInputModule,
        MatIconModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatCardModule,
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
        MatButtonToggleModule
    ],
    exports: [],
    declarations: [FormHeaderComponent]
})
export class FormHeaderModule { }
