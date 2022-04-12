import { APP_INITIALIZER, NgModule } from '@angular/core';
import { AuthConfigService } from './auth-config.service';
import { HttpClientModule } from "@angular/common/http";
import { AuthConfig, OAuthModule } from "angular-oauth2-oidc";
import {OAuthModuleConfig} from "./auth.config";
import { authConfig} from "./auth.config";

@NgModule({
  imports: [ HttpClientModule, OAuthModule.forRoot() ],
  providers: [
    AuthConfigService,
    { provide: AuthConfig, useValue: authConfig },
    OAuthModuleConfig,
    {
      provide: APP_INITIALIZER,
      useFactory:(initService:AuthConfigService) => () => initService.initAuth(),
      deps:[ AuthConfigService ],
      multi: true
    }
  ]
})
export class AuthConfigModule { }
