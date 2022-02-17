package resource.service

default allow = false

allow = true
#allow = true {
#    input.method = "GET"
#    input.path[3] = "meetings"
#
#    jwt.payload.resource_access["smart_gateway"].roles[_] = "staff"
#    #jwt.payload.azp = "smart-gateway"
#}

# Helper to get the token payload.
jwt = {"payload": payload} {
auth_header := input.request.headers["Authorization"]
            [_, jwt] := split(auth_header, " ")
            [_, payload, _] := io.jwt.decode(jwt)}