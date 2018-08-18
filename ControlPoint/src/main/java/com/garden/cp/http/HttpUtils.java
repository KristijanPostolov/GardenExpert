package com.garden.cp.http;

import com.google.gson.Gson;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private final String apiUrl;
    private final Gson gsonParser;

    public HttpUtils(@Value("${api_url}") String apiUrl, Gson gsonParser) {
        this.apiUrl = apiUrl;
        this.gsonParser = gsonParser;
    }

    public void patch(String endpoint, String body) {
        String uri = apiUrl + endpoint;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPatch patch = new HttpPatch(uri);
            StringEntity status = new StringEntity(body);
            patch.setEntity(status);
            httpClient.execute(patch);
        } catch (IOException e) {
            log.error("Could not execute PUT request on [{}]", uri, e);
        }
    }

    public void post(String endpoint, Object object) {
        String uri = apiUrl + endpoint;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(gsonParser.toJson(object));
            post.setEntity(stringEntity);
            post.setHeader("Content-type", "application/json");
            httpClient.execute(post);
        } catch (UnsupportedEncodingException e) {
            log.error("Could not parse object to json", e);
        } catch (IOException e) {
            log.error("Could not execute POST request on [{}]", uri, e);
        }
    }
}
