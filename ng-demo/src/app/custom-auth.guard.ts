import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {OAuthService} from "angular-oauth2-oidc";

@Injectable({
  providedIn: 'root'
})
export class CustomAuthGuard implements CanActivate {

  constructor(private oauthService: OAuthService, protected router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const hasIdToken = this.oauthService.hasValidIdToken();
      const hasAccessToken = this.oauthService.hasValidAccessToken();

      if (this.oauthService.hasValidAccessToken()) {
        return (hasIdToken && hasAccessToken);
      }

      this.router.navigate([this.router.url]).then(r => { console.log(r)},
        err => { console.log(err)});
      return this.oauthService.loadDiscoveryDocumentAndLogin()
  }



}
