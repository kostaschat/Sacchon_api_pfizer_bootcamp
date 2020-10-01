import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediUpdateComponent } from './medi-update.component';

describe('MediUpdateComponent', () => {
  let component: MediUpdateComponent;
  let fixture: ComponentFixture<MediUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MediUpdateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MediUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});