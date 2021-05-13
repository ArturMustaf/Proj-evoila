import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ResourceViewComponent} from './resource-view.component';
import {ResourceViewCommunicatorMock} from '../../../../core/mocks/resource-view-communicator-mock';
import {ResourceCommunicatorService} from '../../../../core/services/resource_communicator/resource-communicator.service';
import {NavService} from '../../../../core/services/nav/nav.service';
import {ClarityModule} from '@clr/angular';

describe('ResourceViewComponent', () => {
  let component: ResourceViewComponent;
  let fixture: ComponentFixture<ResourceViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResourceViewComponent],
      providers: [{provide: ResourceCommunicatorService, useClass: ResourceViewCommunicatorMock}, NavService],
      imports: [ClarityModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
