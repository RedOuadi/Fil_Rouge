import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammeCreateComponent } from './programme-create.component';

describe('ProgrammeCreateComponent', () => {
  let component: ProgrammeCreateComponent;
  let fixture: ComponentFixture<ProgrammeCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgrammeCreateComponent]
    });
    fixture = TestBed.createComponent(ProgrammeCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
