import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HubsService} from '../../services/hubs.service';

@Component({
  selector: 'app-hub-details',
  templateUrl: './hub-details.component.html',
  styleUrls: ['./hub-details.component.css']
})
export class HubDetailsComponent implements OnInit {

  id: number;
  hub: SensorHub;

  constructor(private route: ActivatedRoute, private hubsService: HubsService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = +params['id'];
      this.hubsService.findById(this.id)
        .subscribe(hub => {
          this.hub = hub;
        });
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
