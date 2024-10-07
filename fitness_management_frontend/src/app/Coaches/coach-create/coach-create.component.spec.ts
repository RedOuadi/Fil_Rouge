import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CoachCreateComponent } from './coach-create.component';

describe('CoachCreateComponent', () => {
  let component: CoachCreateComponent;
  let fixture: ComponentFixture<CoachCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CoachCreateComponent]
    });
    fixture = TestBed.createComponent(CoachCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
