import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediInsertComponent } from './medi-insert.component';

describe('MediInsertComponent', () => {
  let component: MediInsertComponent;
  let fixture: ComponentFixture<MediInsertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MediInsertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MediInsertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
