import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleVDCComponent } from './single-vdc.component';

describe('SingleVDCComponent', () => {
  let component: SingleVDCComponent;
  let fixture: ComponentFixture<SingleVDCComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleVDCComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleVDCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
