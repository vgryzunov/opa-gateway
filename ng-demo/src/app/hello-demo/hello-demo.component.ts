import {Component, Input, OnInit} from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-hello-demo',
  templateUrl: './hello-demo.component.html',
  styleUrls: [ './hello-demo.component.css' ]
})
export class HelloDemo implements OnInit {
  id = '';
  content = '';
  loading = false;

  @Input() oauthService: OAuthService | undefined;

  constructor( private http: HttpClient ) {
  }

  ngOnInit() {
  }

  doGet(): void {
    console.log("==> oauthService.hasValidAccessToken: " + this.oauthService?.hasValidAccessToken());
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
