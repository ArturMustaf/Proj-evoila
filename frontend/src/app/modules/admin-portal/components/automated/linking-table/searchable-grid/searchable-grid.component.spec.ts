import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchableGridComponent} from './searchable-grid.component';

describe('SearchableGridComponent', () => {
  let component: SearchableGridComponent;
  let fixture: ComponentFixture<SearchableGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchableGridComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchableGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
