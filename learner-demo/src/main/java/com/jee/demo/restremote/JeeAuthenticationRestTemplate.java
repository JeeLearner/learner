package com.jee.demo.restremote;

import org.apache.http.HttpHost;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component("restTemplate")
public class JeeAuthenticationRestTemplate extends RestTemplate implements RestOperations {

    public JeeAuthenticationRestTemplate() {

        super();

        setRequestFactory(new JeeAuthenticationHttpComponentsClientHttpRequestFactory());
    }

    public void setCredentials(HttpHost host,
                               JeeAuthenticationScheme authenticationScheme,
                               String username,
                               String password) {

        JeeAuthenticationHttpComponentsClientHttpRequestFactory requestFactory = (JeeAuthenticationHttpComponentsClientHttpRequestFactory) getRequestFactory();
        requestFactory.setCredentials(host, authenticationScheme, username, password);
    }
}
