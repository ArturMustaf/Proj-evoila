import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';
import {AuthServiceService} from '../auth-service/auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class UserinfoService {


  constructor(private http: HttpClient, private authService: AuthServiceService) {
    this.getUserData().then((res) => console.log(res));
  }


  async getUserData(): Promise<UserData> {
    let response = await this.http.get<UserData>(environment.backend.address + '/auth/user').toPromise();
    if (response === null) {
      const profile = await this.authService.getProfile();
      response = await this.postData(
        {
          userId: null,
          name: profile.firstName,
          surname: profile.lastName,
          contactInformation: {
            email: profile.email,
            address: {
              address: '',
              city: '',
              plz: ''
            }
          }
        });
    }

    return response;
  }

  async postData(userData: UserData): Promise<UserData> {
    const create = await this.http.post<UserData>(environment.backend.address + '/auth/update', userData).toPromise();

    return create;

  }
}

export interface UserData {
  userId: string;
  name: string;
  surname: string;
  contactInformation: {
    address: {
      address: string,
      city: string,
      plz: string
    };
    email: string;
  };
}
