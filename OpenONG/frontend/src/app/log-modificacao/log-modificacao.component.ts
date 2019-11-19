import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-log-modificacao',
  templateUrl: './log-modificacao.component.html',
  styleUrls: ['../shared/shared-form.component.scss'],
})
export class LogModificacaoComponent implements OnInit {

  @Input() public usuarioCriacao: String = "";
  @Input() public usuarioModificacao: String = "";
  @Input() public dataCriacao: Date;
  @Input() public dataModificacao: Date;

  constructor() { }

  ngOnInit() {
  }

}
