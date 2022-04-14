import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OAuthModule } from 'angular-oauth2-oidc'
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthConfigModule } from "./init.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AuthConfigModule,
    HttpClientModule,
    OAuthModule.forRoot( {
      resourceServer: {
        allowedUrls: ['http://localhost:3000/api'],
        sendAccessToken: true
      }
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
