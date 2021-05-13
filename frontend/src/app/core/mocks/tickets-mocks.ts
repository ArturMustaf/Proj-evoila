import {Injectable} from '@angular/core';
import {UserModel} from '../../shared/models/user-models/user-model';
import {TicketModel} from '../../shared/models/ticket-models/ticket-model';


@Injectable({
  providedIn: 'root'
})
/**
 * Mock for the tickets
 */
export class MockCostsCommunicatorService {
  constructor() {

  }
  ticketLoading(): Promise<TicketModel[]> {

  return new Promise<TicketModel[]>(((resolve, reject) => {
    const model = [{id: 1, title: 'Ticket 01',
      summary:  'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua',
      status: 'Active'},
      {id: 2, title: 'Ticket 02',
        summary:  'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua',
        status: 'Active'},
      {id: 3, title: 'Ticket 03',
        summary:  'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua',
        status: 'Closed'},
      {id: 4, title: 'Ticket 04',
        summary:  'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua',
        status: 'Closed'}];
    resolve(model);
  }));
}

}

interface ResponseType {
  customers: UserModel[];
}

