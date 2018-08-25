import {Component, OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {HubsService} from '../../services/hubs.service';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private searchTerms = new Subject<string>();
  hubs: SensorHub[];
  searchType = 'Name';

  constructor(private hubsService: HubsService) {
  }

  ngOnInit() {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => {
        if (this.searchType === 'Name') {
          return this.hubsService.searchHubsByName(term);
        } else {
          return this.hubsService.searchHubsByMac(term);
        }
      })
    ).subscribe(hubs => this.hubs = hubs);
  }

  updateTerm(term: string) {
    this.searchTerms.next(term);
  }

}
