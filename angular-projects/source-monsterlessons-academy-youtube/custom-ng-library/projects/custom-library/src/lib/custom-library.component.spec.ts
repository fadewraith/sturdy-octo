import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomLibraryComponent } from './custom-library.component';

describe('CustomLibraryComponent', () => {
  let component: CustomLibraryComponent;
  let fixture: ComponentFixture<CustomLibraryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomLibraryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomLibraryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
