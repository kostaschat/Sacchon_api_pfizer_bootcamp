import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MonitorUsersComponent } from './users-monitor.component';



describe('MonitorUsersComponent', () => {
  let component: MonitorUsersComponent;
  let fixture: ComponentFixture<MonitorUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonitorUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonitorUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
