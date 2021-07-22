import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashCostsDiagramComponent } from './dash-costs-diagram.component';

xdescribe('DashCostsDiagramComponent', () => {
  let component: DashCostsDiagramComponent;
  let fixture: ComponentFixture<DashCostsDiagramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashCostsDiagramComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashCostsDiagramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
