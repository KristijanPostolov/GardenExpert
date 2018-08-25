import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class HubsService {

  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  searchHubsByMac(mac: string): Observable<SensorHub[]> {
    console.log('http ' + mac);
    return this.http.get<SensorHub[]>(`api/hubs?mac=${mac}`);
  }

  searchHubsByName(name: string): Observable<SensorHub[]> {
    console.log('http ' + name);
    return this.http.get<SensorHub[]>(`api/hubs?name=${name}`);
  }

}
