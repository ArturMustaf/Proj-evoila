import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashComponent } from './dash.component';
import {ResourceModel} from '../../../../shared/models/resource-models/resource-model';
import {ResTypeModel} from '../../../../shared/models/resource-models/res-type-model';
import {PricesModel} from '../../../../shared/models/prices-models/prices-model';

describe('DashComponent', () => {
  let component: DashComponent;
  let fixture: ComponentFixture<DashComponent>;

  let allCurrentResources_testValues : ResourceModel[] = [
    {
      id : 'id_0',
      vdcID : '',
      orgID: '',
      resourceTypeID: '0',
      startDate: 0,
      endDate: 1000
    },
    {
      id : 'id_1',
      vdcID : '',
      orgID: '',
      resourceTypeID: '0',
      startDate: 250,
      endDate: 1000
    },{
      id : 'id_2',
      vdcID : '',
      orgID: '',
      resourceTypeID: '0',
      startDate: 500,
      endDate: 1000
    }];
  let allCurrentResourceTypes_testValues : ResTypeModel[] = [{
    id : 'id_0',
    typeOfResource: 'RAM',
    description: ''
  },{
    id : 'id_1',
    typeOfResource: 'CPU',
    description: ''
  },{
    id : 'id_0',
    typeOfResource: 'RAM',
    description: ''
  }];

  let allCurrentPrices_testValues : PricesModel[];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashComponent ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit('should create', () => {
    expect(component).toBeTruthy();
  });

  xit('tesst', () => {
    component.allCurrentResources = allCurrentResources_testValues;
  });
});
