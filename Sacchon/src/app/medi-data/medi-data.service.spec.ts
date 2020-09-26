import { TestBed } from '@angular/core/testing';

import { MediDataService } from './medi-data.service';

describe('MediDataService', () => {
  let service: MediDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MediDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
