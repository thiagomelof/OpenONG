import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms'

import { AppComponent } from './app.component';
import { LazyLoadModule } from './lazy-load/lazy-load.module';
import { CoreModule } from './core/core.module';
import { ParceiroDeNegocioService } from './parceiro-de-negocio.service';
import { LoginService } from './login.service';




@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    BrowserModule,
    LazyLoadModule,
    CoreModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ParceiroDeNegocioService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
