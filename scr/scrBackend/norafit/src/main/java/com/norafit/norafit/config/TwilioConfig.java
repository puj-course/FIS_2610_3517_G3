package com.norafit.norafit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

    private static final Logger log = LoggerFactory.getLogger(TwilioConfig.class);

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
        log.info("[SMS] Twilio inicializado correctamente. AccountSID: {}****", accountSid.substring(0, 6));
    }
}
