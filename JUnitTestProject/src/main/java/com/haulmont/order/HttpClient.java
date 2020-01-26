package com.haulmont.order;

public interface HttpClient {
    String executeRequest(String url, Object object);
}
