package com.cherry.jeeves.utils.rest;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @link https://stackoverflow.com/questions/22853321/resttemplate-client-with-cookies
 */
public class RestTemplateWithCookies extends RestTemplate {
    private final Set<String> cookies = new HashSet<>();

    public RestTemplateWithCookies() {
        super();
    }

    public RestTemplateWithCookies(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }

    private synchronized Set<String> getCookies() {
        return cookies;
    }

    public synchronized void resetCookies() {
        this.cookies.clear();
    }

    public synchronized RestTemplateWithCookies addCookies(String key, String value) {
        this.cookies.add(key + "=" + value);
        return this;
    }

    @Override
    protected <T extends Object> T doExecute(URI url, HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException {
        final Set<String> cookies = getCookies();

        return super.doExecute(url, method, chr -> {
            if (cookies != null) {
                for (String cookie : cookies) {
                    chr.getHeaders().add("Cookie", cookie);
                }
            }
            requestCallback.doWithRequest(chr);
        }, chr -> {
            List<String> newCookies = chr.getHeaders().get("Set-Cookie");
            if (newCookies != null && !newCookies.isEmpty()) {
                this.cookies.addAll(newCookies);
            }
            return responseExtractor.extractData(chr);
        });
    }

}