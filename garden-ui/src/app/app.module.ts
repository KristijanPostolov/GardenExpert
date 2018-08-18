import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { SearchComponent } from './components/search/search.component';
import {FormsModule} from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import {HubsService} from './services/hubs.service';
import { AppRoutingModule } from './/app-routing.module';
import { HubDetailsComponent } from './components/hub-details/hub-details.component';


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
    AppRoutingModule
  ],
  providers: [
    HubsService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
