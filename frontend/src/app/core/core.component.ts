import {Component} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {lyl, StyleRenderer, ThemeRef, ThemeVariables} from '@alyle/ui';
import {UserinfoService} from './services/userinfo/userinfo.service';

const STYLES = (theme: ThemeVariables, ref: ThemeRef) => {
  const __ = ref.selectorsOf(STYLES);
  return {
    $global: lyl`{
      body {
        background-color: ${theme.background.default}
        color: ${theme.text.default}
        font-family: ${theme.typography.fontFamily}
        margin: 0
        direction: ${theme.direction}
      }
    }`,
    root: lyl`{
      display: block
    }`
  };
};

@Component({
  selector: 'app-root',
  templateUrl: './core.component.html',
  styleUrls: ['./core.component.scss'],
  providers: [StyleRenderer]
})
/**
 * Setting of the template and loading the keycloak-service
 */
export class AppComponent {
  readonly classes = this.sRenderer.renderSheet(STYLES, true);

  title = 'frontend';

  constructor(readonly sRenderer: StyleRenderer,
              private keycloakService: KeycloakService, private userInfo: UserinfoService) {
    console.log('Initialized AppComponent');

  }

  logout(): void {
    this.keycloakService.logout();
  }

}
