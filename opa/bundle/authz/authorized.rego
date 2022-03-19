##
##  authorized - Authorizing resource access
##
package authz

import input
import data.rules.resources
import data.certs

# allowed - acces is allowed
# name - subject name from JWT
# tokenValid - token is verified
default authorized = { "allowed" : false, "name" : null, "tokenValid": false}

authorized = { "allowed": true, "name": jwt.payload.preferred_username,
               "tokenValid": token_valid} {
	resources[x].name == input.path[3]
	resources[x].method == input.method

	some i, j
	client_roles[i] == roles_allowed[j]
}

# OIDC client
client := "smart-gateway"

# Roles in JWT
client_roles := jwt.payload.resource_access[client].roles

# Roles allowed for requested resource
roles_allowed = result {
    some x
    resources[x].name = input.path[3]
    resources[x].method = input.method
    resources[x].roles = result
}

# Certificate with corresponding kid
cert := { "keys": [key] } {
    some i
    certs.keys[i].kid == jwt.header.kid
    key := certs.keys[i]
}

# Certuficate as text
jwks = json.marshal(cert)

jwt_encoded := split(input.headers.Authorization[0], " ")[1]
token_valid := io.jwt.verify_rs256(jwt_encoded, jwks)

# Helper to get token header and payload.
jwt = {"header": header, "payload": payload} {
	[header, payload, _] := io.jwt.decode(jwt_encoded)
}



