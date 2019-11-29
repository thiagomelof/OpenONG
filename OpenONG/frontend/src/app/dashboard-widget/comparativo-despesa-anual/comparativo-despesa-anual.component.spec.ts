import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComparativoDespesaLineGraphComponent } from './comparativo-despesa-anual.component';

describe('ComparativoDespesaLineGraphComponent', () => {
  let component: ComparativoDespesaLineGraphComponent;
  let fixture: ComponentFixture<ComparativoDespesaLineGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComparativoDespesaLineGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComparativoDespesaLineGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
