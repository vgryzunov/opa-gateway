import { Component, OnInit } from '@angular/core';
import {OAuthService} from "angular-oauth2-oidc";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-meetings-demo',
  templateUrl: './meetings-demo.component.html',
  styleUrls: ['./meetings-demo.component.css']
})
export class MeetingsDemoComponent implements OnInit {
  loading = false;
  public meetings: Array<any> = [ {"id": 123 , "name": "Josh Long", "managerId": "Philippe Kahn", "assignments": [] } ];

  constructor(private oauthService: OAuthService,
              private http: HttpClient ) { }

  ngOnInit(): void {
  }

  getMeetingList(): void {
    console.log("==> oauthService.hasValidAccessToken: " + this.oauthService?.hasValidAccessToken());
    console.log("==> START LOADING DATA...");
    this.loading = true;
    this.http.get<any>('http://localhost:3000/api/meetings').subscribe(data => {
        console.log('==> DATA: ' + JSON.stringify(data))
        this.loading = false;
        this.meetings = data;
      },
      err => {
        console.log('==> ERROR: ' + JSON.stringify(err))
        this.loading = false;
      });
  }


}
