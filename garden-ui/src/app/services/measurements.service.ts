import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class MeasurementsService {

  constructor(private http: HttpClient) { }

  getMeasurements(id: number): Observable<Measurement[]> {
    return this.http.get<Measurement[]>(`/api/hubs/${id}/measurements`);
  }
}
