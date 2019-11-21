import { DespesaParameters } from './../model-view/dto/despesa-parameters';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, NgModule } from '@angular/core';
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})

@NgModule({
  providers: [DatePipe]
})

export class RelatoriosService {
  doacaoUrl = 'http://localhost:8084/backend/api/doacao/relatorio'
  despesaUrl = 'http://localhost:8084/backend/api/despesa/relatorio'

  constructor(private http: HttpClient, public datepipe: DatePipe) { }

  getRelatorioDespesa(parametros: DespesaParameters) {

    let params = new HttpParams();

    params = params.append('convenio', parametros.convenio.id.toString());
    params = params.append('parceiro', parametros.parceiro.id.toString());
    params = params.append('dtinicio', this.datepipe.transform(parametros.dataInicio, 'yyyy-MM-dd'));
    params = params.append('dtfim', this.datepipe.transform(parametros.dataFim, 'yyyy-MM-dd'));

    return this.http.get<any[]>(this.despesaUrl, { params: params });
  }

}
