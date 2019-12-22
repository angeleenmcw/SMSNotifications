package com.sms.notifications.textmessages.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.notifications.textmessages.client.service.IRestService;
import com.sms.notifications.textmessages.common.exception.CustomErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestService implements IRestService {

    private static final Logger logger = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    private HttpHeaders getDefaultHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!StringUtils.isEmpty(token)) headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private <T> HttpEntity<T> prepareDefaultHttpEntity(T object, String token) {
        HttpHeaders headers = this.getDefaultHeaders(token);
        HttpEntity<T> httpEntity;
        if (object != null) {
            httpEntity = new HttpEntity<>(object, headers);
        } else {
            httpEntity = new HttpEntity<>(headers);
        }
        return httpEntity;
    }

    @Override
    public <T> T exchange(String url, HttpMethod method, Class<T> responseType, String token) {
        return this.exchange(url, method, (T) null, responseType, token);
    }

    @Override
    public <T> T exchange(String url, HttpMethod method, T body, Class<T> responseType, String token) {
        logger.info("exchange URL: " + url + " Method: " + method.name());
        ResponseEntity<Object> r = this.exchange(url, method, body, token);
        T response = mapper.convertValue(r.getBody(), responseType);
        return response;
    }

    @Override
    public <T, R> R exchangeCustomResponse(String url, HttpMethod method, T body, Class<R> responseType, String token) {
        logger.info("exchangeCustomResponse URL: " + url + " Method: " + method.name());
        ResponseEntity<Object> r = this.exchange(url, method, body, token);
        R response = mapper.convertValue(r.getBody(), responseType);
        return response;
    }

    private ResponseEntity exchange(String url, HttpMethod method, Object body, String token) {
        try {
            ResponseEntity response = restTemplate.exchange(url, method, this.prepareDefaultHttpEntity(body, token), Object.class);
            logger.info("exchange STATUS: " + response.getStatusCode().value());
            return response;
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.REST_CLIENT_ERROR.name());
        }
    }

}
