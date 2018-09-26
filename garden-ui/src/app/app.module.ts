import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { SearchComponent } from './components/search/search.component';
import {FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import {HubsService} from './services/hubs.service';
import { AppRoutingModule } from './/app-routing.module';
import { HubDetailsComponent } from './components/hub-details/hub-details.component';
import {ChartsModule} from 'ng2-charts';
import {MeasurementsService} from './services/measurements.service';


@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    HubDetailsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ChartsModule
  ],
  providers: [
    HubsService,
    MeasurementsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
