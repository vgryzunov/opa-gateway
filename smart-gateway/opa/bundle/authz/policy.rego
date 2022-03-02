package authz

import input
import data.authz
import data.certs

default authorized = { "allowed" : false, "name" : null, "tokenValid": null}

authorized = { "allowed": true, "name": jwt.payload.preferred_username,
               "tokenValid": token_valid} {
	authz.resources[x].url == input.path[3]
	authz.resources[x].method == input.method

	some i, j
	client_roles[i] == roles_allowed[j]
}

client := "smart-gateway"

client_roles := jwt.payload.resource_access[client].roles

roles_allowed = result {
    authz.resources[x].url = input.path[3]
    result = authz.resources[x].roles
}

# Cert with corresponding kid
cert := { "keys": [key] } {
    some i
    certs.keys[i].kid == jwt.header.kid
    key := certs.keys[i]
}

# cert
jwks = json.marshal(cert)

jwt_encoded := split(input.headers.Authorization[0], " ")[1]
token_valid := io.jwt.verify_rs256(jwt_encoded, jwks)

# Helper to get token header and payload.
jwt = {"header": header, "payload": payload} {
	[header, payload, _] := io.jwt.decode(jwt_encoded)
}



