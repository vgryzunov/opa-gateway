import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {AuthConfig, NullValidationHandler, OAuthService} from "angular-oauth2-oidc";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ng-demo';
  id = '';
  content = '';
  loading = false;

  constructor(private oauthService: OAuthService,
              private http: HttpClient,
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

  doGet(): void {
    console.log("==> START LOADING DATA...");
    this.loading = true;
    this.http.get<any>('http://localhost:3000/api/hello').subscribe(data => {
      console.log('==> DATA: ' + JSON.stringify(data))
      this.id = data.id;
      this.content = data.content;
      this.loading = false;
      },
      err => {
        console.log('==> ERROR: ' + JSON.stringify(err))
        this.loading = false;
      });
  }

}
