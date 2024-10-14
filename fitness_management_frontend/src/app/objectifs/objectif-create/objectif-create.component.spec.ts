import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjectifCreateComponent } from './objectif-create.component';

describe('ObjectifCreateComponent', () => {
  let component: ObjectifCreateComponent;
  let fixture: ComponentFixture<ObjectifCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ObjectifCreateComponent]
    });
    fixture = TestBed.createComponent(ObjectifCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
