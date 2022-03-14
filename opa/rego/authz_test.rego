package authz

auth_header := "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkWXd6bkY3WkxxMWFoVGN6RzNWOEdqSVZVYlFrY01yWVIzWF9KeVJ6RHA4In0.eyJleHAiOjE2NDY5MjcxNDEsImlhdCI6MTY0NjkyNjg0MSwiYXV0aF90aW1lIjoxNjQ2OTI2ODQxLCJqdGkiOiI3ZjM2YTY5Yi0zYzUzLTRlNTAtYTM5Mi0xYjUzMzIyNTYzODQiLCJpc3MiOiJodHRwczovL2tleWNsb2FrLjE5Mi4xNjguNDkuMi5uaXAuaW8vaWFtL2F1dGgvcmVhbG1zL2RlbW8iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZDA4YmIzYzktYzZjYS00M2MwLTg0MWQtMWEwZTYzOTg5ODNlIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoic21hcnQtZ2F0ZXdheSIsInNlc3Npb25fc3RhdGUiOiI2Zjk0OTdhMy05OTg2LTQ2MTAtYmE0YS05ZTRkYjAyMjU3ODEiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRlbW8iXX0sInJlc291cmNlX2FjY2VzcyI6eyJzbWFydC1nYXRld2F5Ijp7InJvbGVzIjpbInN0YWZmIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiI2Zjk0OTdhMy05OTg2LTQ2MTAtYmE0YS05ZTRkYjAyMjU3ODEiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiIxIEl2YW5vdiIsInByZWZlcnJlZF91c2VybmFtZSI6Iml2YW5vdjEiLCJnaXZlbl9uYW1lIjoiMSIsImZhbWlseV9uYW1lIjoiSXZhbm92In0.JPXbACaClQFfRn680uOcdOeElXaLABebhJPEswsom_u5K9t3onvU-ZklVSoEyvhxMP-dX9-ULRGm1w6QTDPXQu23k1yMyEvLAJvBdza4shDuXFOCKhQqgU0sweaTN66Y5RssVBLfhwtJBRROPGh48xWpSQBeAVIKzK5VD-8deScJmii02tW0jVHeO3PZiWwIzt1JyZRdHq3UUaI0v5jOc0GQ-rRXNjKseAiOWgwoVooGPs7fjbIUPC31Su4_W2SBstANFDj4Venbodmlp7WmxKe6QREI3CAxwz243_0j5hZ3fpUgqvmgNAgkFXq2nmL_1q4P-0VQluJQf89rheAQ4w"
data_certs := {
              "keys": [
                    {
                      "kid": "dYwznF7ZLq1ahTczG3V8GjIVUbQkcMrYR3X_JyRzDp8",
                      "kty": "RSA",
                      "alg": "RS256",
                      "use": "sig",
                      "n": "iQ4xGuePtbf1lZILcITu3nmYpToHIbGiFabEv_pS_vkGQnswnaUyQBQIo5H85otZoL2ZkzNOPU1V98YHvos6KQ_Ige5lyt_FMxFMIaXlY8Wp4FFMC2XsxlgWPFCXKE5Q2zsG5DpCIlCAdMbq09hBu3Md7O57vImYiZncxvBTALpwEz-lMWnKjBwfREW2wG_RBOQBpQgGHZPmKzLlcwuUgIX-3EY6qYg6LaK0HEhjq2Qs3vyFiy38DyV8ezPxpPf1Zo6opVWWGNYAv7Aue0rRj5wxQeRgLDpZQXUgTMSjAj5-sY0O5YtTnlZVgrTXLl79TQISiKEDwnmvnkc8jv60gQ",
                      "e": "AQAB",
                      "x5c": [
                        "MIIClzCCAX8CBgF+kdb8HDANBgkqhkiG9w0BAQsFADAPMQ0wCwYDVQQDDARkZW1vMB4XDTIyMDEyNTE1MjMxMloXDTMyMDEyNTE1MjQ1MlowDzENMAsGA1UEAwwEZGVtbzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIkOMRrnj7W39ZWSC3CE7t55mKU6ByGxohWmxL/6Uv75BkJ7MJ2lMkAUCKOR/OaLWaC9mZMzTj1NVffGB76LOikPyIHuZcrfxTMRTCGl5WPFqeBRTAtl7MZYFjxQlyhOUNs7BuQ6QiJQgHTG6tPYQbtzHezue7yJmImZ3MbwUwC6cBM/pTFpyowcH0RFtsBv0QTkAaUIBh2T5isy5XMLlICF/txGOqmIOi2itBxIY6tkLN78hYst/A8lfHsz8aT39WaOqKVVlhjWAL+wLntK0Y+cMUHkYCw6WUF1IEzEowI+frGNDuWLU55WVYK01y5e/U0CEoihA8J5r55HPI7+tIECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAGidsRzK5j0yHMgYOvrcGGP3liDrYlpdOJwyaLox6M4GFqJSoEDtnwzl6MCV2bjNOYmxZasy39tyOzC3APBeShjJK3+8q5TjvBce4aE9MbsSs+cW1q55t/1zJs2DifR/tc/PdZXTx7gZlIx3S6j8hX/ESBffDZiUNCCJxTDeTOt+rLMtYZBTfqcPKzqo5ZyadRSIyN0Sj+usFfYPDI9iiIupP5I7iXKpOXS1eNOa1v+yPqBBV02r942boVvg80imx+fDYzgclX60R/Zkf4UcI8noVqLOh30LtddaTfAehJkgX7NRHCm4WyMEYjG9LyUuWXJyAGL5gkvJvPDNkdztLPw=="
                      ],
                      "x5t": "otSLwc-Fef4Kg9YQWGBXrOdwiTI",
                      "x5t#S256": "LeKHuS63vFkZQ7oMQJ0d1N0AeFbnQNZIpGWrGBt5oVQ"
                    }
                ]
              }

test_jwt_encoded {
    jwt_encoded == "xyz" with input as {
                          "headers": { "Authorization": [ "Bearer xyz" ] }
                        }
}

test_jwt {
    jwt.header.kid == "dYwznF7ZLq1ahTczG3V8GjIVUbQkcMrYR3X_JyRzDp8" with input as {
                        "headers": { "Authorization": [ auth_header ] }
                      }
}

test_token_valid {
    token_valid == true with input as {
                                "headers": { "Authorization": [ auth_header ] }
                              }
                        with data.certs as data_certs
}

test_roles_allowed {
    roles_allowed = ["staff","managers"]
                  with input as {
                     "path": [
                              "http:",
                              "",
                              "localhost:8888",
                              "meetings"
                            ],
                     "headers": { "Authorization": [ auth_header ] }
                     }
                  with data.certs as data_certs
                  with data.authz.resources as [
                       {
                         "url": "assignments",
                         "method": "GET",
                         "roles": [
                             "staff",
                             "managers"
                           ]
                         },
                       {
                         "url": "meetings",
                         "method": "GET",
                         "roles": [
                           "staff",
                           "managers"
                         ]
                       }
                     ]
}
