import { CategoriaService } from './services/categoria.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms'
import { AppComponent } from './app.component';
import { LazyLoadModule } from './lazy-load/lazy-load.module';
import { CoreModule } from './core/core.module';
import { ParceiroDeNegocioService } from './services/parceiro-de-negocio.service';
import { LoginService } from './services/login.service';
import { ItemService } from './services/item.service';

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
  providers: [ParceiroDeNegocioService, LoginService, CategoriaService, ItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
