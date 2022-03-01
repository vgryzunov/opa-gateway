package authz

import input
import data.authz

default authorized = { "allowed" : false, "name" : "" }

authorized = { "allowed": true, "name": jwt.payload.preferred_username } {
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

# Helper to get token header and payload.
jwt = {"header": header, "payload": payload, "signature": signature} {
	auth_header := input.headers.Authorization[0]
	[_, jwt] := split(auth_header, " ")
	[header, payload, signature] := io.jwt.decode(jwt)
}

r2 = authz.resources[0].url
p = input.path


