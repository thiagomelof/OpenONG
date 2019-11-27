import { Component, OnInit, Input } from '@angular/core';
import { MatFormField } from '@angular/material';

@Component({
  selector: 'app-form-header',
  templateUrl: './form-header.component.html',
  styleUrls: ['../shared/shared-form.component.scss']
})
export class FormHeaderComponent implements OnInit {

  @Input() public titulo: String = "";

  constructor() {
  }

  ngOnInit() {
  }

}
