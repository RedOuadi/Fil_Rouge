import { TestBed } from '@angular/core/testing';

import { ObjectifFitnessService } from './objectif-fitness.service';

describe('ObjectifFitnessService', () => {
  let service: ObjectifFitnessService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObjectifFitnessService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
