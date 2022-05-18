// This is replacement file for "minikube" configuration

let clientId ='ng-ui-client';
let issuer = 'http://my-wst:3000/iam/auth/realms/demo';
let apiHost = 'http://my-wst:3000';
let apiPath = '/api/'

export const environment = {
  production: false,
  idp: {
    issuer: issuer,
    redirectUri: window.location.origin + '/ng/',
    clientId: clientId,
    scope: 'openid profile email offline_access',
    responseType: 'code',
    disableAtHashCheck: true,
    showDebugInformation: true,
    requireHttps: false,
    strictDiscoveryDocumentValidation: false
  },
  apiPath: apiPath,
  apiUrl: apiHost + apiPath,
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
