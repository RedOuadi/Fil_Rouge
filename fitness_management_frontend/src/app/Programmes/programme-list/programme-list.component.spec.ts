import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammeListComponent } from './programme-list.component';

describe('ProgrammeListComponent', () => {
  let component: ProgrammeListComponent;
  let fixture: ComponentFixture<ProgrammeListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgrammeListComponent]
    });
    fixture = TestBed.createComponent(ProgrammeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
