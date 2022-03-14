package authz

import input
import data.authz.resources
import data.certs

# empty set as default
default resources_allowed = set()

t = data.authz.resources
res := { "assignments", "meetings" }

resources_allowed = { {"name" : r.name, "method": r.method  } |
    some x
    r = resources[x]

    some i, j
    client_roles[i] == r.roles[j]
}


roles =  [ "staff", "managers" ]
#roles as a set
sr = { x | x = roles[_] }



