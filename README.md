# opa-gateway
This is a technology demo project for using Policies to manage access to Web applications.

The following technologies are used:

- Spring Cloud Gateway to implement Policy Enforcer Point
- Open Policy Agent (OPA) https://www.openpolicyagent.org/ as a policy application engine
- Angular-oauth2-oidc for authentication in Angular UI
- Spring boot to implement (simple) Bundle server used to provide policies to OPA. 
- Spring Boot for resource services
- Keycloak server installed in k8s (minikube or docker desktop) for Oauth2 authentication

It seems I should create some text describing everything in details, starting from creating needed realms and clients
in Keycloak server.
