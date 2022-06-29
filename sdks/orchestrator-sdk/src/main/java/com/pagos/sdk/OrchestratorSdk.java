package com.pagos.sdk;

import org.javalite.http.Http;
import org.javalite.http.Post;

public class OrchestratorSdk {
    private String environment;

    public OrchestratorSdk(String environment) {
        this.environment = environment;
    }

    public OrchestratorSdk() {
        this("dev");
    }

    public String getVendor(String body) {
        String baseUrl = getBaseUrl();
        String url = baseUrl + "/v1/rules";
        Post post = Http.post(url, body).header("Content-Type", "application/json");

        if (post.responseCode() != 200) {
            return null;
        }

        return post.text();
    }

    public String getBaseUrl() {
        switch (environment.toUpperCase()) {
            case "PROD":
                return "https://orchestrator-pagos.herokuapp.com";
            default:
                return "http://localhost:8083";
        }
    }
}
