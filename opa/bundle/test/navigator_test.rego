package authz

test_to_string {
    to_string(100) == "100"
    to_string("abc") == "abc"
    to_string("") == ""
}

# groups missing in input data
test_ui_flter_groups_missing {
    ui_filter == set()
    with input as {
        "groups": []
    }
    with data.navigator as {
        "root": [
            {
                "property": "value",
                "AccessGroup": ["a", "b"]
            }
        ]
    }
}

# not in groups
test_ui_flter_groups_missing {
    ui_filter == set()
    with input as {
        "groups": ["a", "b"]
    }
    with data.navigator as {
        "root": [
            {
                "property": "value",
                "AccessGroup": ["c", "d"]
            }
        ]
    }
}

test_ui_filter_root_0 {
    ui_filter == { "root/0" }
    with input as {
        "groups": ["a", "b"]
    }
    with data.navigator as {
                "root": [
                    {
                        "property": "value1",
                        "AccessGroup": ["a", "b"]
                    },
                    {
                        "property": "value2",
                        "AccessGroup": ["d", "e"]
                    }
                ]
    }
}

test_ui_filter_child_0 {
    ui_filter == { "root/0",
                    "root/0/children/0"
    }
    with input as {
        "groups": ["a", "b"]
    }
    with data.navigator as {
                "root": [
                    {
                        "property": "value1",
                        "AccessGroup": ["a", "b"],
                        "children": [
                            {
                                "property": "value2",
                                "AccessGroup": ["a", "b"]
                            }
                        ]
                    }
                ]
    }
}

test_ui_filter_only_child_0 {
    ui_filter == {
        "root/0",
        "root/0/children/0"
    }
    with input as {
        "groups": ["a", "b"]
    }
    with data.navigator as {
                "root": [
                    {
                        "property": "value1",
                        "AccessGroup": ["c", "d"],
                        "children": [
                            {
                                "property": "value2",
                                "AccessGroup": ["a", "b"]
                            }
                        ]
                    }
                ]
    }
}

