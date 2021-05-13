import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
/**
 * This class is used to request,put and post data from the grids / forms to the backend
 */
export class RequestServiceService {

  constructor(private httpService: HttpClient) {

  }

  loadSearchData(url: string, query = '', from = '0', size = '10'): Promise<SearchResponseType> {
    url = url.trim();
    from = from.trim();
    size = size.trim();
    return this.httpService.get<SearchResponseType>(environment.backend.address
      + '' + url + '/' + query + '/' + from + '/' + size).toPromise();
  }

  async loadId(url: string, id: string): Promise<ResponseType> {
    return this.httpService.get<ResponseType>(environment.backend.address + url + id).toPromise();
  }

  putData(url: string, id: string, data: any): Promise<ResponseType> {
    const database = environment.backend.address + url + id;
    return this.httpService.put<ResponseType>(database, data).toPromise();
  }

  postData(url: string, data): Promise<ResponseType> {
    return this.httpService.post<ResponseType>(environment.backend.address + url, data).toPromise();
  }

}


export interface ResponseType {
  _embedded: { any };
  _links: {};
  page: {
    size: number;
    totalElements: number;
  };
}

export interface SearchResponseType {
  data: any[];
  resultSize: number;
}
