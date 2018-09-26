import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class HubsService {

  constructor(private http: HttpClient) { }

  searchHubsByMac(mac: string): Observable<SensorHub[]> {
    console.log('http ' + mac);
    return this.http.get<SensorHub[]>(`api/hubs?mac=${mac}`);
  }

  searchHubsByName(name: string): Observable<SensorHub[]> {
    console.log('http ' + name);
    return this.http.get<SensorHub[]>(`api/hubs?name=${name}`);
  }

  findById(id: number): Observable<SensorHub> {
    console.log('http GET by id' + id);
    return this.http.get<SensorHub>(`api/hubs/${id}`);
  }

  updateStatus(id: number, status: HubStatus): Observable<HubStatus> {
    return this.http.patch<HubStatus>(`api/hubs/${id}/status`, status);
  }

  updateConfiguration(id: number, config: HubConfiguration): Observable<HubConfiguration> {
    return this.http.patch<HubConfiguration>(`api/hubs/${id}/configuration`, config);
  }

}
