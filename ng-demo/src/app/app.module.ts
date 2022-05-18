import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OAuthModule } from 'angular-oauth2-oidc'
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthConfigModule } from "./auth/auth.module";
import { HelloDemo } from './hello-demo/hello-demo.component';
import { MeetingsDemoComponent } from './meetings-demo/meetings-demo.component';
import {environment} from "../environments/environment";

@NgModule({
  declarations: [
    AppComponent,
    HelloDemo,
    MeetingsDemoComponent
  ],
  imports: [
    BrowserModule,
    AuthConfigModule,
    HttpClientModule,
    OAuthModule.forRoot( {
      resourceServer: {
        allowedUrls: [environment.apiUrl],
        sendAccessToken: true
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
