package com.example.cinemasite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SmsServiceImpl implements SmsService{

    @Value("${sms.ru.url}")
    private String smsUrl;

    @Value("${sms.ru.api_id}")
    private String apiId;

    @Value("${sms.ru.msg}")
    private String msgUrl;

    @Value("${sms.ru.json}")
    private String msmJson;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendSms(String phone, String text) {
        String url = smsUrl + apiId + phone + msgUrl + text + msmJson;
        log.info(url);
        String result = restTemplate.getForObject(url, String.class);
        log.info(result);
    }
}