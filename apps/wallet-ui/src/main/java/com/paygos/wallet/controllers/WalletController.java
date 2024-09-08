package com.paygos.wallet.controllers;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.paygos.wallet.layouts.ModelAndViewManager;
import com.paygos.wallet.models.PaymentMethod;
import com.paygos.wallet.services.PaymentMethodService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WalletController {
    private PaymentMethodService paymentMethodService;
    private Logger logger = LoggerFactory.getLogger(WalletController.class);

    public WalletController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/v1/owner/{id}/wallet")
    public ModelAndView showWallet(
        @PathVariable("id") String id, 
        @RequestParam("language") String language
        ) {
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("id", id.toString());
        properties.put(
            "paymentMethods", paymentMethodService.getPaymentMethods(id).stream().map(pm -> pm.toMap()).toList()
        );
        properties.put("language", language);
        properties.put("title", "Index");

        return ModelAndViewManager.render("wallet/index", properties);
    }
    


    @PostMapping("/v1/owner/{id}/wallet")
    public ModelAndView updateWallet(@PathVariable("id") String id) {
        return ModelAndViewManager.redirect("/v1/owner/" + id + "/wallet");
    }
    


    @GetMapping("/v1/owner/{id}/wallet/new")
    public ModelAndView displayNewPaymentMethodScreen(
        @PathVariable("id") String id, 
        @RequestParam("language") String language
        ) {
        Map<String, Object> properties = new HashMap<String, Object>();
        
        properties.put("id", id);
        properties.put("language", language);
        properties.put("title", "New");

        return ModelAndViewManager.render("wallet/new", properties);
    }



    @PostMapping(path="/v1/owner/{id}/wallet/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createNewPaymentMethod(
        @PathVariable("id") String id, 
        @RequestParam("language") String language,
        @ModelAttribute PaymentMethod paymentMethod
        ) {
        String redirectUrl = "/v1/owner/" + id + "/wallet?language=" + language;

        try {
            paymentMethodService.createPaymentMethod(id, paymentMethod);
        } catch (Exception ex) {
            redirectUrl = redirectUrl + "&error=" + ex.getMessage();
        }

        return ModelAndViewManager.redirect(redirectUrl);
    }
}
