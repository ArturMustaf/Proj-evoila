import {ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {ProjectDropdownComponent} from './project-dropdown.component';
import {CoreRoutingModule} from '../../core-routing.module';

describe('ProjectDropdownComponent', () => {
  let component: ProjectDropdownComponent;
  let fixture: ComponentFixture<ProjectDropdownComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        CoreRoutingModule
        ],
      declarations: [ProjectDropdownComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectDropdownComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
