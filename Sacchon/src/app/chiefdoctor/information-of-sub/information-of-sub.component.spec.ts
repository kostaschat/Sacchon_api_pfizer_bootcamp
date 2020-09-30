import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InformationOfSubComponent } from './information-of-sub.component';

describe('InformationOfSubComponent', () => {
  let component: InformationOfSubComponent;
  let fixture: ComponentFixture<InformationOfSubComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InformationOfSubComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InformationOfSubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
