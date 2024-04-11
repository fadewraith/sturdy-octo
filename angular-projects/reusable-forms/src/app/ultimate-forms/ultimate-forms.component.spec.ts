import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UltimateFormsComponent } from './ultimate-forms.component';

describe('UltimateFormsComponent', () => {
  let component: UltimateFormsComponent;
  let fixture: ComponentFixture<UltimateFormsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UltimateFormsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UltimateFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
