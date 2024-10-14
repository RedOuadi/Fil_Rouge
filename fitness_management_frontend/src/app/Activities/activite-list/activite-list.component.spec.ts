import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiviteListComponent } from './activite-list.component';

describe('ActiviteListComponent', () => {
  let component: ActiviteListComponent;
  let fixture: ComponentFixture<ActiviteListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActiviteListComponent]
    });
    fixture = TestBed.createComponent(ActiviteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
