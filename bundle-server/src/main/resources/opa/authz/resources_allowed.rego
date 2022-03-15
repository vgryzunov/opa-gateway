package authz

import input
import data.rules.resources

# empty set as default
default resources_allowed = set()

resources_allowed = { {"name" : r.name, "method": r.method  } |
    some x
    r = resources[x]

    some i, j
    client_roles[i] == r.roles[j]
}



