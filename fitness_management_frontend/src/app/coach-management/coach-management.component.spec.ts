import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachManagementComponent } from './coach-management.component';

describe('CoachManagementComponent', () => {
  let component: CoachManagementComponent;
  let fixture: ComponentFixture<CoachManagementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CoachManagementComponent]
    });
    fixture = TestBed.createComponent(CoachManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
