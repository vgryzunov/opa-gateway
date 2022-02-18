package resource.service

default allow = false

allow  {
    input.method == "GET"
    input.path[3] == "meetings"

    some i
    jwt.payload.resource_access["smart-gateway"].roles[i] == "staff2"
    }

# Helper to get the token payload.
jwt = {"payload": payload} {
    auth_header := input.headers["Authorization"][0]
    [_, jwt] := split(auth_header, " ")
    [_, payload, _] := io.jwt.decode(jwt)
    }
