import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiserviceService {

  constructor(private httpService: HttpClient) {
  }

  sendRequest(url, from, size, linkedId): Promise<ApiResponse<any>> {
    url = url.trim();
    from = from.trim();
    size = size.trim();
    linkedId = linkedId.trim();

    return this.httpService.get<ApiResponse<any>>(environment.backend.address
      + '' + url + '/' + linkedId + '/' + from + '/' + size).toPromise();
  }

}

export interface ApiResponse<T> {
  data: [T];
  size: number;
}
