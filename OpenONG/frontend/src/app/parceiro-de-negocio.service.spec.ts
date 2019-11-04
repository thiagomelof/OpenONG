import { TestBed, inject } from '@angular/core/testing';

import { ParceiroDeNegocioService } from './parceiro-de-negocio.service';

describe('ParceiroDeNegocioService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ParceiroDeNegocioService]
    });
  });

  it('should be created', inject([ParceiroDeNegocioService], (service: ParceiroDeNegocioService) => {
    expect(service).toBeTruthy();
  }));
});
