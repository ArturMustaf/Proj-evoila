import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CfAppsViewComponent } from './cf-apps-view.component';

xdescribe('CfAppsViewComponent', () => {
  let component: CfAppsViewComponent;
  let fixture: ComponentFixture<CfAppsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CfAppsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CfAppsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
