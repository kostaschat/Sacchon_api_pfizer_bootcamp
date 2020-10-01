import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailablePatientListComponent } from './available-patient-list.component';

describe('AvailablePatientListComponent', () => {
  let component: AvailablePatientListComponent;
  let fixture: ComponentFixture<AvailablePatientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailablePatientListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailablePatientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
