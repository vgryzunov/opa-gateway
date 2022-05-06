import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OAuthModule } from 'angular-oauth2-oidc'
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthConfigModule } from "./init.module";
import { HelloDemo } from './hello-demo/hello-demo.component';
import { MeetingsDemoComponent } from './meetings-demo/meetings-demo.component';

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
        allowedUrls: ['http://my-wst:3000/api'],
        sendAccessToken: true
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
