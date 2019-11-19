
import { RouterModule } from '@angular/router';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { FormHeaderComponent } from './../form-header/form-header.component';
import { LogModificacaoComponent } from './../log-modificacao/log-modificacao.component';
import { NgModule } from '@angular/core';

@NgModule
    ({
        imports: [
            CommonModule,
            MatInputModule,
            MatIconModule,
            MatToolbarModule,
            MatFormFieldModule,
            MatButtonModule,
            ReactiveFormsModule,
            FormsModule,
            FlexLayoutModule,
            RouterModule,
        ],
        declarations: [FormHeaderComponent, LogModificacaoComponent],
        exports: [FormHeaderComponent, LogModificacaoComponent]
    })

export class SharedModule {

    public titulo: String = "";
    public rota: String = "";
    public icone: String = ""
}