import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedidataChartsComponent } from './medidata-charts.component';

describe('MedidataChartsComponent', () => {
  let component: MedidataChartsComponent;
  let fixture: ComponentFixture<MedidataChartsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedidataChartsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedidataChartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
