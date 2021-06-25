import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CostsDiagramComponent} from './costs-diagram.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('CostsDiagramComponent', () => {
  let component: CostsDiagramComponent;
  let fixture: ComponentFixture<CostsDiagramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      declarations: [CostsDiagramComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CostsDiagramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  xit('should get data', () => {
    //component.ngOnInit();
    expect(component.totalCost).toEqual(460);
    expect(component.chartLabels).toEqual(['VMs', 'Storage', 'Kubernetes', 'IPs', 'Cloudfoundry']);
    expect(component.chartData).toEqual([140, 60, 150, 70, 40]);
  });
});
