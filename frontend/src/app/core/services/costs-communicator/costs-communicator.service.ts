import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {NavService} from '../nav/nav.service';


@Injectable({
  providedIn: 'root'
})
/**
 * This service communicates with the backend to retrive costs information for specified timeframes
 * Used in the costs menue
 */
export class CostsCommunicatorService {
  // TODO clean up
  constructor(private httpService: HttpClient, private navService: NavService) {

  }

  async loadCosts(start: string, end: string, id: number = 1): Promise<CostsResponse[]> {
    return this.loadForDiagram(start, end, id);

  }

  loadCostsInit(id: number = 1): Promise<CostsResponse[]> {
    return this.loadForDiagramInit(id);
  }

  loadForDiagramInit(id: number = 1): Promise<CostsResponse[]> {
    return this.httpService.get<CostsResponse[]>(environment.backend.address + '/costs?id=' + id).toPromise();
  }

  loadForDiagram(start: string, end: string, id: number = 1): Promise<CostsResponse[]> {
    const today = new Date();
    if (start === '' && end !== '') {
      const endDate2 = this.toDate(end);
      if (!this.isToday(endDate2, today)) {
        endDate2.setHours(23, 59, 59);
      }
      return this.httpService.get<CostsResponse[]>(environment.backend.address +
        '/costs?id=' + id + '&endTS=' + endDate2.getTime()).toPromise();
    }
    if (end === '' && start !== '') {
      const startDate2 = this.toDate(start);
      return this.httpService.get<CostsResponse[]>(environment.backend.address +
        '/costs?id=' + id + '&startTS=' + startDate2.getTime()).toPromise();
    }
    if (start === '' && end === '') {
      return this.loadForDiagramInit(id);
    }
    const startDate = this.toDate(start);
    const endDate = this.toDate(end);
    if (!this.isToday(endDate, today)) {
      endDate.setHours(23, 59, 59);
    }
    return this.httpService.get<CostsResponse[]>(environment.backend.address +
      '/costs?id=' + id + '&startTS=' + startDate.getTime() + '&endTS=' + endDate.getTime()).toPromise();
  }

  private toDate(dateStr: string): Date {
    const parts = dateStr.split('/');
    const dt = new Date(parseInt(parts[2], 10),
      parseInt(parts[0], 10) - 1,
      parseInt(parts[1], 10));
    return dt;
  }

  private isToday(dateToCheck: Date, todaysDate: Date): boolean {
    return (dateToCheck.getFullYear() === todaysDate.getFullYear() && dateToCheck.getMonth() === todaysDate.getMonth()
      && dateToCheck.getDate() === todaysDate.getDate());

  }

}

export interface CostsResponse {
  projectName: string;
  totalCosts: number;
  resourceTypes: { [key: string]: { type: string, price: number } };
}
