import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyPatientListComponent } from './my-patient-list.component';

describe('MyPatientListComponent', () => {
  let component: MyPatientListComponent;
  let fixture: ComponentFixture<MyPatientListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyPatientListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyPatientListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
