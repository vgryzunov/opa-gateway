package authz

import input
import data.navigator

default ui_visible = set()

input_groups := ["MDM_ADMIN"]

ui_visible = [ x |
    x = navigator.ui_auth[_]
    some i, j
    x.AccessGroup[i] = input_groups[j]
    ]

tu := { i | count(navigator.ui_auth[i].children) > 0 }

el1 = { c |
    c = navigator.ui_auth[15].children[_]
    some i, j
    c.AccessGroup[i] = input.groups[j]
    }

el2 =
    { c2 |
    c2 = navigator.ui_auth[15].children[_]
    some k
    c2.AccessGroup[k] = "SYS_USERS_VIEW"
    }
ui_walk[res] {
    [path, value] := walk(navigator.ui_auth)
    res := [path, value]
    }

ui_filter[full_path] {
    [path, value] := walk(navigator.ui_auth)
    raw_path = array.slice(path, 0, count(path) - 1)
    string_path = concat("/", [ to_string(x) | x := raw_path[_]])
    full_path = concat("/", ["ui_auth", string_path])

    path[count(path) - 1] == "AccessGroup"
    some i, j
    value[i] == input_groups[j]
}

path_array := [x | x := ui_filter[_]]

filtered := json.filter(navigator, path_array)

n = navigator.ui_auth

to_string(x) = y {
    is_number(x)
    y := format_int(x, 10)
}
to_string(x) = y {
    is_string(x)
    y := x
}

r = to_string("100")