import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientInsertDataComponent } from './patient-insert-data.component';

describe('PatientInsertDataComponent', () => {
  let component: PatientInsertDataComponent;
  let fixture: ComponentFixture<PatientInsertDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatientInsertDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientInsertDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
