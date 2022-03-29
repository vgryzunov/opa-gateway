package authz

import input
import data.navigator

default filtered = []

ui_filter[string_path] {
    [path, value] := walk(navigator)
    raw_path = array.slice(path, 0, count(path) - 1)
    string_path = concat("/", [ to_string(x) | x := raw_path[_]])

    path[count(path) - 1] == "AccessGroup"
    some i, j
    value[i] == input.groups[j]
}

path_array := [x | x := ui_filter[_]]

filtered = json.filter(navigator, path_array).ui_auth

to_string(x) = y {
    is_number(x)
    y := format_int(x, 10)
}
to_string(x) = y {
    is_string(x)
    y := x
}

