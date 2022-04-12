import {Injectable, Optional} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {OAuthModuleConfig, OAuthResourceServerErrorHandler, OAuthService, OAuthStorage} from "angular-oauth2-oidc";

@Injectable()
export class OAuthInterceptor implements HttpInterceptor {

  constructor(
    private authStorage: OAuthStorage,
    private oauthService: OAuthService,
    private errorHandler: OAuthResourceServerErrorHandler,
    @Optional() private moduleConfig: OAuthModuleConfig
  ) {}

  private checkUrl(url: string): boolean {
    const found = this.moduleConfig && this.moduleConfig.resourceServer &&
      this.moduleConfig.resourceServer.allowedUrls &&
      this.moduleConfig.resourceServer.allowedUrls.find(u => url.startsWith(u)) || false;
    return !!found;
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    console.log('==> INTERCEPTOR');

    const url = request.url.toLowerCase();

    if (!this.moduleConfig) { return next.handle(request); }
    if (!this.moduleConfig.resourceServer) { return next.handle(request); }
    if (!this.moduleConfig.resourceServer.allowedUrls) { return next.handle(request); }
    if (!this.checkUrl(url)) { return next.handle(request); }

    const sendAccessToken = this.moduleConfig.resourceServer.sendAccessToken;

    if (sendAccessToken) {
      // const token = this.authStorage.getItem('access_token');
      const token = this.oauthService.getIdToken();
      const header = 'Bearer ' + token;

      console.log('==> TOKEN in INTERCEPTOR : ' + token);

      const headers = request.headers
        .set('Authorization', header);

      request = request.clone({ headers });
    }

    return next.handle(request);
  }
}
