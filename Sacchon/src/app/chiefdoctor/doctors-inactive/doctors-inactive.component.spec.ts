import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorsInactiveComponent } from './doctors-inactive.component';

describe('DoctorsInactiveComponent', () => {
  let component: DoctorsInactiveComponent;
  let fixture: ComponentFixture<DoctorsInactiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorsInactiveComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorsInactiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});