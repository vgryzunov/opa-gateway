import {AuthConfig} from "angular-oauth2-oidc";

export const authConfig: AuthConfig = {
  issuer: 'http://localhost:3000/iam/auth/realms/demo',
  redirectUri: window.location.origin + "/ng/",
  clientId: 'smart-gateway',
  scope: 'openid profile email offline_access',
  responseType: 'code',
  // at_hash is not present in JWT token
  disableAtHashCheck: true,
  showDebugInformation: true,
  requireHttps: false,
  strictDiscoveryDocumentValidation: false
}

export class OAuthModuleConfig {
  resourceServer: OAuthResourceServerConfig = {sendAccessToken: false};
}

export class OAuthResourceServerConfig {
  /**
   * Urls for which calls should be intercepted.
   * If there is an ResourceServerErrorHandler registered, it is used for them.
   * If sendAccessToken is set to true, the access_token is send to them too.
   */
  allowedUrls?: Array<string>;
  sendAccessToken = true;
  customUrlValidation?: (url: string) => boolean;
}
