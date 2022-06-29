package com.pagos.identities.controllers.v1;

import com.pagos.identities.entities.AccessToken;
import com.pagos.identities.services.IdentityService;
import com.pagos.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class IdentitiesController {
    private IdentityService identityService;

    @Autowired
    public IdentitiesController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @PostMapping(path = "/v1/token", consumes={MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public AccessToken createToken(@RequestParam Map<String, String> requestProperties) throws BadRequestException {
        identityService.validateRequest(requestProperties);
        return identityService.createToken(requestProperties);
    }
}
