import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetUserDataComponent } from './get-user-data.component';

describe('GetUserDataComponent', () => {
  let component: GetUserDataComponent;
  let fixture: ComponentFixture<GetUserDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetUserDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetUserDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
