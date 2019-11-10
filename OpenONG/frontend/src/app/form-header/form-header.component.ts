import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-form-header',
  templateUrl: './form-header.component.html',
  styleUrls: ['../shared/shared-form.component.scss']
})
export class FormHeaderComponent implements OnInit {

  @Input() public titulo: String = "";
  @Input() public rota: String = "";
  @Input() public icone: String = ""

  constructor() {
  }

  ngOnInit() {
  }

}
