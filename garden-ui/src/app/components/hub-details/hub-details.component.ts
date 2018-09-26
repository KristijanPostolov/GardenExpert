import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HubsService} from '../../services/hubs.service';
import {MeasurementsService} from '../../services/measurements.service';

@Component({
  selector: 'app-hub-details',
  templateUrl: './hub-details.component.html',
  styleUrls: ['./hub-details.component.css']
})
export class HubDetailsComponent implements OnInit {

  id: number;
  hub: SensorHub;
  measurements: Measurement[];
  timestamps: string[];
  chartOptions = {
    responsive: true
  };
  chartData = undefined;


  constructor(private route: ActivatedRoute, private hubsService: HubsService, private measurementsService: MeasurementsService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.hubsService.findById(this.id)
        .subscribe(hub => {
          this.hub = hub;
        });
      this.measurementsService.getMeasurements(this.id)
        .subscribe( measurements => {
          console.log(measurements);
          this.chartData = [
            { data: measurements.map(m => m.value), label: 'Soil Moisture' }
          ];
          this.timestamps = measurements.map(m => m.timestamp);
          this.measurements = measurements;
        })
    });
  }

  updateStatus() {
    console.log('Updating status');
    this.hubsService.updateStatus(this.id, this.hub.hubStatus)
      .subscribe(status => this.hub.hubStatus = status);
  }

  updateConfiguration() {
    console.log('Updating configuration');
    this.hubsService.updateConfiguration(this.id, this.hub.hubConfiguration)
      .subscribe(config => this.hub.hubConfiguration = config);
  }

}
