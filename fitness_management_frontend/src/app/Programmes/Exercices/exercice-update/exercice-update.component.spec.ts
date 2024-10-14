import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExerciceUpdateComponent } from './exercice-update.component';

describe('ExerciceUpdateComponent', () => {
  let component: ExerciceUpdateComponent;
  let fixture: ComponentFixture<ExerciceUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciceUpdateComponent]
    });
    fixture = TestBed.createComponent(ExerciceUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
