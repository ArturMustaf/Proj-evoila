import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserModel} from '../../../shared/models/user-models/user-model';
import {environment} from '../../../../environments/environment';
import {ResourceOverviewModel} from '../../../shared/models/resource-models/resourceOverview-model';


@Injectable({
  providedIn: 'root'
})
/**
 * This service is the communicator for the resource overview
 */
export class ResourceCommunicatorService {
  constructor(private httpService: HttpClient) {

  }


  loadResourceUsage(id: number, start: string, end: string): Promise<ResourceOverviewModel[]> {
    const today = new Date();
    if (start === '' && end !== '') {
      const endDate2 = this.toDate(end);
      if (!this.isToday(endDate2, today)) {
        endDate2.setHours(23, 59, 59);
      }
      return this.httpService.get<ResourceOverviewModel[]>(environment.backend.address +
        '/resourceview?project=' + id + '&to=' + endDate2.getTime()).toPromise();
    }
    if (end === '' && start !== '') {
      const startDate2 = this.toDate(start);
      return this.httpService.get<ResourceOverviewModel[]>(environment.backend.address +
        '/resourceview?project=' + id + '&from=' + startDate2.getTime()).toPromise();
    }
    if (start === '' && end === '') {
      return this.httpService.get<ResourceOverviewModel[]>(environment.backend.address + '/resourceview?project=' + id).toPromise();
    }
    const startDate = this.toDate(start);
    const endDate = this.toDate(end);
    if (!this.isToday(endDate, today)) {
      endDate.setHours(23, 59, 59);
    }

    return this.httpService.get<ResourceOverviewModel[]>(environment.backend.address + '/resourceview?project=' + id
      + '&from=' + startDate.getTime() + '&to=' + endDate.getTime()).toPromise();
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

interface ResponseType {
  customers: UserModel[];
}
