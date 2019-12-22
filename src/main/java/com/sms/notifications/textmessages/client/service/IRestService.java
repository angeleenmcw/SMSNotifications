package com.sms.notifications.textmessages.client.service;

import org.springframework.http.HttpMethod;

public interface IRestService {

    <T> T exchange(String url, HttpMethod method, Class<T> responseType, String token);

    <T> T exchange(String url, HttpMethod method, T body, Class<T> responseType, String token);

    <T, R> R exchangeCustomResponse(String url, HttpMethod method, T body, Class<R> responseType, String token);

}
