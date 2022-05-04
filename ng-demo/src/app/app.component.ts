import { Component, OnInit } from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ng-oauth-demo';

  constructor(public oauthService: OAuthService
  ) {
  }

  ngOnInit() {
  }

  public login() {
    this.oauthService.initLoginFlow();
  }

  public logoff() {
    this.oauthService.logOut();
  }

  public  hasValidAccessToken(): boolean {
    return this.oauthService.hasValidAccessToken();
  }

}
