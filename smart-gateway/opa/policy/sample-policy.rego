package resource.service

import data

default authorizrd = { "allow" : false, "name" : "" }

client := "smart-gateway"

client_roles := jwt.payload.resource_access[client].roles

authorized = { "allow": true, "name": jwt.payload.preferred_username } {
	data.resources[x].url == input.path[3]
	data.resources[x].method == input.method

	some i, j
	client_roles[i] == roles_allowed[j]
}

roles_allowed = result {
    data.resources[x].url = input.path[3]
    result = data.resources[x].roles
}

# Helper to get token header and payload.
jwt = {"header": header, "payload": payload} {
	auth_header := input.headers.Authorization[0]
	[_, jwt] := split(auth_header, " ")
	[header, payload, _] := io.jwt.decode(jwt)
}
