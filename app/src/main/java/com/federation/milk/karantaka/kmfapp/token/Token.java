package com.federation.milk.karantaka.kmfapp.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {

    private final String token; // = "eyJraWQiOiJzZWxmLWRpZ25lZC1jZXJ0aWZpY2F0ZSIsImFsZyI6IlJTMjU2In0.eyJhdWQiOiJrbWYtc2VydmVyIiwic3ViIjoiaXJhbm5hLnBhdGlsIiwicm9sZSI6InN1YnNjcmliZXIiLCJpc3MiOiJrbWYuZGV2IiwiZXhwIjoxNTA4NTk2OTU4LCJkYWlyeUlkIjoia2hhanVyaSIsImlhdCI6MTUwODU5MzM1OH0.khmwew_AVUgZwAl4Si_XWboSe3Fy2mM-OeM-tQo1tiCQEcmb4YIxL7DyoT0AgY0uqubMZmxYQSM0rTUGpiP5svZnDWgBbVyfIJeGQ6n-ivKZ5nu9YQIMVfnurKiX-ptZw9UerINYla_DZm5xntAUBft8Lpfy45ziMj99s-ibLdv0JFxvumIYr7AIJO8nOMWQR5Uwi9GPbgF_3WKG2rk9XFckid56CuoMnK9OWv9kwiYjgdBdhklVIi_ZVbu9BhbKMP5u1AHaUbd3kwR4a7CR-xgSAEv3cl1ffKxvM4t9nFwL30E1wyxshH60XixNpv_Jwdi3zV9g9_3SgqcdDBpQhQ";
    private final String refreshToken;// = "935970b9-87a3-45c6-9674-c4650fbe6585";

    @JsonCreator
    public Token(@JsonProperty("token") final String token,
                 @JsonProperty("refreshToken") final String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
