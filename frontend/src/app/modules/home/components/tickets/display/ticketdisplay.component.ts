import {Component} from '@angular/core';
import {ClrDatagridStateInterface} from '@clr/angular';

@Component({
  selector: 'app-ticket-display',
  templateUrl: './ticketdisplay.component.html',
  styleUrls: ['./ticketdisplay.component.scss']
})
export class TicketDisplayComponent {

  constructor() {
  }

  refresh(state: ClrDatagridStateInterface): void {
  }

}
