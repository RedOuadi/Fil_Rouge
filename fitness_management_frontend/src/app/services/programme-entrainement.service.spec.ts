import { TestBed } from '@angular/core/testing';

import { ProgrammeEntrainementService } from './programme-entrainement.service';

describe('ProgrammeEntrainementService', () => {
  let service: ProgrammeEntrainementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgrammeEntrainementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
