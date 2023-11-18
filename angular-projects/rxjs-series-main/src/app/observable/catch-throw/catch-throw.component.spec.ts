import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatchThrowComponent } from './catch-throw.component';

describe('CatchThrowComponent', () => {
  let component: CatchThrowComponent;
  let fixture: ComponentFixture<CatchThrowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CatchThrowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CatchThrowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
