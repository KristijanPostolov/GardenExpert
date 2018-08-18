import { TestBed, inject } from '@angular/core/testing';

import { HubsService } from './hubs.service';

describe('HubsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HubsService]
    });
  });

  it('should be created', inject([HubsService], (service: HubsService) => {
    expect(service).toBeTruthy();
  }));
});
