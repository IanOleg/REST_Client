package com.restclient;

import com.restclient.modal.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.CookieStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    //private String URL = "http://localhost:8080/admin";
    private String URL = "http://91.241.64.178:7081/api/users";

    @Autowired
    private HttpHeaders requestHttpHeaders;

    private String response = "";

    public String getResponse() {
        return response;
    }

    public void getAllUsers(){

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>(){});

        //java.net.HttpCookie.parse(responseEntity.getHeaders().getFirst("Set-Cookie"))
        String[] cookies = responseEntity.getHeaders().getFirst("Set-Cookie").split(";");

        String cookie = "";

        for (String c: cookies) {
            if (c.contains("JSESSIONID")){
                cookie = c.trim();
                break;
            }
        }

        requestHttpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHttpHeaders.set("Content-Type", "application/json");
        requestHttpHeaders.set("Cookie", cookie+";");
    }

    public void addUser(){

        User user = new User(3L, "James", "Brown", (byte)35);

        HttpEntity<User> httpEntity = new HttpEntity<>(user, requestHttpHeaders);
        response = response + restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class).getBody();
    }

    public void updateUser(){

        ResponseEntity<String> responseEntity = null;
        User user = new User(3L, "Thomas", "Shelby", (byte)35);

        HttpEntity<User> httpEntity = new HttpEntity<>(user, requestHttpHeaders);
        try {
             responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class);
        }catch (RuntimeException e){
            System.out.println(e);
        }
        response = response + responseEntity.getBody();
    }

    public void deleteUser(){

        ResponseEntity<String> responseEntity = null;
        HttpEntity<User> httpEntity = new HttpEntity<>(requestHttpHeaders);
        try{
            responseEntity = restTemplate.exchange("http://91.241.64.178:7081/api/users/3", HttpMethod.DELETE, httpEntity, String.class);
        }catch (RuntimeException e){
            System.out.println(e);
        }
        response = response + responseEntity.getBody();
    }
}
