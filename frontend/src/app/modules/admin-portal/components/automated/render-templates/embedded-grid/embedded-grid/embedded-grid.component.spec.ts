import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EmbeddedGridComponent} from './embedded-grid.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('EmbeddedGridComponent', () => {
  let component: EmbeddedGridComponent;
  let fixture: ComponentFixture<EmbeddedGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
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
