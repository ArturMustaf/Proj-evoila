import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchableGridComponent} from './searchable-grid.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {CoreRoutingModule} from '../../../../../../core/core-routing.module';

xdescribe('SearchableGridComponent', () => {
  let component: SearchableGridComponent;
  let fixture: ComponentFixture<SearchableGridComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CoreRoutingModule
      ],
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
