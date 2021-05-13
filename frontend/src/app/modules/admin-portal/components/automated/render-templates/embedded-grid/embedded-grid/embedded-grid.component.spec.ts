import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmbeddedGridComponent} from './embedded-grid.component';

describe('EmbeddedGridComponent', () => {
  let component: EmbeddedGridComponent;
  let fixture: ComponentFixture<EmbeddedGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmbeddedGridComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmbeddedGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
