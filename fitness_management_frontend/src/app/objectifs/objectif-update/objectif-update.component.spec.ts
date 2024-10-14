import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjectifUpdateComponent } from './objectif-update.component';

describe('ObjectifUpdateComponent', () => {
  let component: ObjectifUpdateComponent;
  let fixture: ComponentFixture<ObjectifUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ObjectifUpdateComponent]
    });
    fixture = TestBed.createComponent(ObjectifUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
