package com.pagos.sdk;

import org.javalite.http.Http;
import org.javalite.http.Post;

public class IdentitiesSdk {
    private String environment;

    
    public IdentitiesSdk(String environment) {
        this.environment = environment;
    }
    
    public IdentitiesSdk() {
        this("dev");
    }

    public String getAccessToken(String grantType, String clientId, String clientSecret) {
        String baseUrl = getBaseUrl();
        String url = baseUrl + "/v1/token";
        String body = "GrantType=" + grantType + "&ClientId=" + clientId + "&ClientSecret=" + clientSecret;
        Post post = Http.post(url, body);

        if (post.responseCode() != 200) {
            return null;
        }

        return post.text();
    }

    private String getBaseUrl() {
        switch(environment.toUpperCase()) {
            case "PROD":
                return "https://identities-pagos.herokuapp.com";
            default:
                return "http://localhost:8080";
        }
    }
}
