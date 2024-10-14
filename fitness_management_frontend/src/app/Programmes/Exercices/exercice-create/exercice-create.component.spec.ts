import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExerciceCreateComponent } from './exercice-create.component';

describe('ExerciceCreateComponent', () => {
  let component: ExerciceCreateComponent;
  let fixture: ComponentFixture<ExerciceCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciceCreateComponent]
    });
    fixture = TestBed.createComponent(ExerciceCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
