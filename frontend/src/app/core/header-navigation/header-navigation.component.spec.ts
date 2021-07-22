import {fakeAsync, TestBed, tick} from '@angular/core/testing';
import {HeaderNavigationComponent} from './header-navigation.component';
import {Router} from '@angular/router';
import {RouterTestingModule} from '@angular/router/testing';
import {Location} from '@angular/common';
import {routes} from '../../modules/home/home-routing.module';
import {KeycloakAngularModule} from 'keycloak-angular';
import {HttpClientModule} from '@angular/common/http';
import {CostsComponent} from '../../modules/home/components/costs/costs.component';

xdescribe('HeaderNavigationComponent', () => {
  let location: Location;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, KeycloakAngularModule, HttpClientModule, RouterTestingModule.withRoutes(routes)],
      declarations: [HeaderNavigationComponent, CostsComponent]
    });
    router = TestBed.inject(Router);
    location = TestBed.inject(Location);
    router.initialNavigation();
  });

  xit('should navigate admin clicking on evoila brand navigation link to dashboard', fakeAsync(() => {
      router.navigate(['costs']);
      tick();
      expect(location.path()).toBe('/costs');
    }));
});
