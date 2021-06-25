import {ComponentFixture, TestBed} from '@angular/core/testing';

import {GenericDisplayComponent} from './generic-display.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {CoreRoutingModule} from '../../../../../../core/core-routing.module';

describe('GenericDisplayComponent', () => {
  let component: GenericDisplayComponent;
  let fixture: ComponentFixture<GenericDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CoreRoutingModule
      ],
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
