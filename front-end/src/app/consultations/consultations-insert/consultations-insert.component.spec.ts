import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultationsInsertComponent } from './consultations-insert.component';

describe('ConsultationsInsertComponent', () => {
  let component: ConsultationsInsertComponent;
  let fixture: ComponentFixture<ConsultationsInsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultationsInsertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultationsInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
