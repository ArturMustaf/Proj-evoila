import {Component, OnInit} from '@angular/core';
import {NavService} from '../../../../core/services/nav/nav.service';

/**
 * Dashboard component
 */
@Component({
  selector: 'app-dash',
  templateUrl: './dash.component.html',
  styleUrls: ['./dash.component.scss']
})
export class DashComponent implements OnInit {

  constructor(private navService: NavService) {
    navService.isAdmin = false;
  }


  ngOnInit(): void {
  }
}
