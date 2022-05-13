import { Injectable } from '@angular/core';
import {AuthConfig, NullValidationHandler, OAuthService} from "angular-oauth2-oidc";
import {filter} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthConfigService {
  private decodedAccessToken: any;
  private decodedIDToken: any;

  constructor(
    private readonly oauthService: OAuthService,
    private readonly authConfig: AuthConfig
  ) {
  }

  async initAuth(): Promise<any> {
    return new Promise<void>((resolveFn, rejectFn) => {
      console.log("==> AuthConfigService.initAuth")

      // setup oauthService

      console.log("==> this.authConfig: " + JSON.stringify(this.authConfig));
      this.oauthService.configure(this.authConfig);
      this.oauthService.setStorage(localStorage);
      this.oauthService.tokenValidationHandler = new NullValidationHandler();

      // subscribe to token events
      this.oauthService.events
        .pipe(filter((e: any) => {
          return e.type === 'token_received';
        }))
        .subscribe(() => this.handleNewToken());

      // continue initializing app or redirect to login-page

      console.log("==> AuthConfigService loadDiscoveryDocumentAndLogin()");
      this.oauthService.loadDiscoveryDocumentAndLogin().then(
        isLoggedIn => {
          if (isLoggedIn) {
            console.log("==> LOGGED IN")
            this.oauthService.setupAutomaticSilentRefresh();
            resolveFn();
          } else {
            console.log("==> NOT LOGGED IN")
            this.oauthService.initLoginFlow();
            rejectFn();
          }
        },
        error => {
          console.log({ error });
          if (error.status === 400) {
            location.reload();
          }
        }
      );
    });
  }

  private handleNewToken() {
    this.decodedAccessToken = this.oauthService.getAccessToken();
    console.log("==> ACCESS TOKEN: " + this.decodedAccessToken)
    this.decodedIDToken = this.oauthService.getIdToken();
  }

}

