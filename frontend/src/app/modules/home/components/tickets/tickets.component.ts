import {Component, OnInit} from '@angular/core';
import {ClrDatagridStateInterface} from '@clr/angular';
import {MockCostsCommunicatorService} from '../../../../core/mocks/tickets-mocks';
import {TicketModel} from '../../../../shared/models/ticket-models/ticket-model';
import {environment} from '../../../../../environments/environment';


@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.scss']
})
/**
 * Currently only mocked => could not be implemented
 */
export class TicketsComponent implements OnInit {
  loading: boolean;
  tickets: TicketModel[];
  projectId = '';

  constructor(private communicator: MockCostsCommunicatorService) {
    this.ticketLoading();
  }

  refresh(state: ClrDatagridStateInterface): void {
  }

  async ngOnInit(): Promise<void> {
    this.loading = true;
    this.tickets = await this.communicator.ticketLoading();
    this.loading = false;
  }

  async ticketLoading(): Promise<void> {
    this.loading = true;
    this.tickets = await this.communicator.ticketLoading();
    this.loading = false;
  }

  onClick(): void {
    window.open(environment.atlassian.jira.customerPortalURL);
  }
}
