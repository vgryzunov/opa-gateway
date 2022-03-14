package authz

test_allowed_resources_staff {
    resources_allowed == { { "name": "meetings", "method": "GET" } ,
                           { "name": "assignments", "method": "GET"} }
                      with client_roles as ["staff"]
                      with input as {
                         "headers": { "Authorization": [ auth_header ] },
                         "resources" : { "assignments", "meetings" }
                       }
                      with data.certs as data_certs
                      with data.authz.resources as [
                                     {
                                       "name": "assignments",
                                       "method": "GET",
                                       "roles": [ "staff", "managers" ]
                                     },
                                     {
                                        "name": "assignments",
                                        "method": "PUT",
                                        "roles": [ "managers" ]
                                     },
                                     {
                                       "name": "meetings",
                                       "method": "GET",
                                       "roles": [ "staff", "managers" ]
                                     },
                                     {
                                        "name": "managers-only",
                                        "method": "GET",
                                        "roles": [ "managers" ]
                                     }
                        ]
}

test_allowed_resources_managers {
    resources_allowed == { { "name": "meetings", "method": "GET" } ,
                           { "name": "assignments", "method": "GET"},
                           { "name": "assignments", "method": "PUT"},
                           { "name": "managers-only", "method": "GET" }
                         }
                      with client_roles as ["managers"]
                      with input as {
                         "headers": { "Authorization": [ auth_header ] },
                         "resources" : { "assignments", "managers-only",  }
                       }
                      with data.certs as data_certs
                      with data.authz.resources as [
                                     {
                                       "name": "assignments",
                                       "method": "GET",
                                       "roles": [ "staff", "managers" ]
                                     },
                                     {
                                        "name": "assignments",
                                        "method": "PUT",
                                        "roles": [ "managers" ]
                                     },
                                     {
                                       "name": "meetings",
                                       "method": "GET",
                                       "roles": [ "staff", "managers" ]
                                     },
                                     {
                                        "name": "managers-only",
                                        "method": "GET",
                                        "roles": [ "managers" ]
                                     }
                        ]
}