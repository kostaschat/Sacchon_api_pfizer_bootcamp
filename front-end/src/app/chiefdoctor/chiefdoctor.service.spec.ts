import { TestBed } from '@angular/core/testing';

import { ChiefdoctorService } from './chiefdoctor.service';

describe('ChiefdoctorService', () => {
  let service: ChiefdoctorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChiefdoctorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});