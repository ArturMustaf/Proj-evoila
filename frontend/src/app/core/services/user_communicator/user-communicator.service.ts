import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserModel} from '../../../shared/models/user-models/user-model';
import {environment} from '../../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
/**
 * This is the communicator for loading user data out of the backend. Used for the admin-portal
 */
export class UserCommunicatorService {
  constructor(private httpService: HttpClient) {

  }


  loadUsers(amount: number, page: number): Promise<ResponseType> {
    return this.httpService.get<ResponseType>(environment.backend.address + '/customers/?page=' + page
      + '&' + 'size=' + amount).toPromise();
  }


}

interface ResponseType {
  _embedded: { customers: UserModel[] };
  page: {
    size: number;
    totalElements: number;
  };

}
