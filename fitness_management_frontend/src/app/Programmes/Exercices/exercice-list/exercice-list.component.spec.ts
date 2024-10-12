import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExerciceListComponent } from './exercice-list.component';

describe('ExerciceListComponent', () => {
  let component: ExerciceListComponent;
  let fixture: ComponentFixture<ExerciceListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExerciceListComponent]
    });
    fixture = TestBed.createComponent(ExerciceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
