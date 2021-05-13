import {Component, OnInit} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';
import {Router} from '@angular/router';

@Component({
  selector: 'app-card-component',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.scss']
})
/**
 * Basic card component
 */
// TODO remove ?
export class CardComponent implements OnInit {

  path: string;

  constructor(private keycloakService: KeycloakService, private router: Router) {
    this.path = router.url.split('/').slice(-1)[0];

  }

  ngOnInit(): void {


  }

}
