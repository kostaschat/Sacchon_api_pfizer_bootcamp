import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersInactiveComponent } from './users-inactive.component';

describe('UsersInactiveComponent', () => {
  let component: UsersInactiveComponent;
  let fixture: ComponentFixture<UsersInactiveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersInactiveComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersInactiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});