import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammeUpdateComponent } from './programme-update.component';

describe('ProgrammeUpdateComponent', () => {
  let component: ProgrammeUpdateComponent;
  let fixture: ComponentFixture<ProgrammeUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgrammeUpdateComponent]
    });
    fixture = TestBed.createComponent(ProgrammeUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
