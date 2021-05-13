import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GenericDisplayComponent} from './generic-display.component';

describe('GenericDisplayComponent', () => {
  let component: GenericDisplayComponent;
  let fixture: ComponentFixture<GenericDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GenericDisplayComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
