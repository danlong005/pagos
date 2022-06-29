package com.pagos.identities.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("identities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Identity {
    @Id
    public String id;
    public String clientId;
    public String clientSecret;
    public int authorizationLevel;
    public String merchantId;
}
