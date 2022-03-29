package authz

import input
import data.navigator

default filtered = []

# filter for all nodes with appropriate groups
ui_primary_filter[string_path] {
    [path, value] := walk(navigator)
    raw_path = array.slice(path, 0, count(path) - 1)
    string_path = concat("/", [ to_string(x) | x := raw_path[_]])

    path[count(path) - 1] == "AccessGroup"
    some i, j
    value[i] == input.groups[j]
}

# filter for root nodes of nodes with apprpriate groups
ui_root_nodes_filter[s] {
    ss := ui_primary_filter[_]
    # iterating over all substring indexes
    i := indexof_n(ss, "children")[_]
    # add path string preceeding "children" node
    s := substring(ss, 0, i - 1)
}

# resulting filter
ui_filter := ui_primary_filter | ui_root_nodes_filter

path_array := [x | x := ui_filter[_]]

filtered = json.filter(navigator, path_array).root

# Helper function
to_string(x) = y {
    is_number(x)
    y := format_int(x, 10)
}
to_string(x) = y {
    is_string(x)
    y := x
}
