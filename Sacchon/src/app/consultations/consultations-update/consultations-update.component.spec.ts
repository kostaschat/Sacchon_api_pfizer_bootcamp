import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultationsUpdateComponent } from './consultations-update.component';

describe('ConsultationsUpdateComponent', () => {
  let component: ConsultationsUpdateComponent;
  let fixture: ComponentFixture<ConsultationsUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultationsUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultationsUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
