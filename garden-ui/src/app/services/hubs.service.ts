import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class HubsService {

  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  searchHubs(term: string): Observable<SensorHub[]> {
    console.log('http ' + term);
    return this.http.get<SensorHub[]>(`api/hubs?query=${term}`);
  }

}
