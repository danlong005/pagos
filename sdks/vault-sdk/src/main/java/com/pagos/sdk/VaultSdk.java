package com.pagos.sdk;

import org.javalite.http.Get;
import org.javalite.http.Http;

public class VaultSdk {
    private String environment;

    public VaultSdk(String environment) {
        this.environment = environment;
    }

    public VaultSdk() {
        this("dev");
    }

    public String getPaymentMethod(String accessToken, String id) {
        String baseUrl = getBaseUrl();
        String url = baseUrl + "/v1/paymentMethods/" + id + "/admin";
        Get get = Http.get(url).header("Authorization", "Bearer " + accessToken);

        if (get.responseCode() != 200) {
            return null;
        }

        return get.text();
    }

    public String getBaseUrl() {
        switch (environment.toUpperCase()) {
            case "PROD":
                return "https://vault-pagos.herokuapp.com";
            default:
                return "http://localhost:8081";
        }
    }
}
