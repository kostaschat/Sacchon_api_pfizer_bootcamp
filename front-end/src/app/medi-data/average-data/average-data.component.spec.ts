import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AverageDataComponent } from './average-data.component';

describe('AverageDataComponent', () => {
  let component: AverageDataComponent;
  let fixture: ComponentFixture<AverageDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AverageDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AverageDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
