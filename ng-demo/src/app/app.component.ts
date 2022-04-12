import {Component, OnInit} from '@angular/core';
import {HttpClient,
  HttpRequest,
  HttpInterceptor
} from '@angular/common/http';
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

  constructor(private oauthService: OAuthService,
              private http: HttpClient,
              private authConfig: AuthConfig) {
    //this.configure();
    this.doGet();
  }


  ngOnInit() {
  }

  public login() {
    this.oauthService.initLoginFlow();
  }

  public logoff() {
    this.oauthService.logOut();
  }

  private configure() {
    console.log( "==> configure()");
    this.oauthService.configure(this.authConfig);
    this.oauthService.tokenValidationHandler = new NullValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(r => { console.log( "==> LOGGED IN: " + r)});
  }

  doGet(): void {
    this.http.get('http://localhost:8888/hello').subscribe(data => {
      console.log('==> DATA: ' + data)
      },
      err => {
        console.log('==> ERROR: ' + err)
      });
  }

}
