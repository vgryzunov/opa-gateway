import { APP_INITIALIZER, NgModule } from '@angular/core';
import { AuthConfigService } from './auth-config.service';
import { AuthConfig, OAuthModule, OAuthStorage } from "angular-oauth2-oidc";
import { environment } from "../environments/environment"

const configAuthZero: AuthConfig = environment.idp;

export function storageFactory(): OAuthStorage {
  return localStorage
}

// TODO:
// fix code like in https://thecodemon.com/angular-oauth2-or-open-id-connect-using-angular-oauth2-oidc-tutorial-with-example-application/
//
@NgModule({
  imports: [
    OAuthModule.forRoot() ],
  providers: [
    AuthConfigService,
    { provide: AuthConfig, useValue: configAuthZero },
    { provide: OAuthStorage, useFactory: storageFactory },
    {
      provide: APP_INITIALIZER,
      useFactory:(initService:AuthConfigService) => () => initService.initAuth(),
      deps:[ AuthConfigService ],
      multi: true
    }
  ]
})
export class AuthConfigModule { }
