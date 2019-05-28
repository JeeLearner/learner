package com.jee.demo.restremote;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public class JeeAuthenticationHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

    private final BasicHttpContext context;
    private final AuthCache cache;
    private final CredentialsProvider credentialsProvider;

    public JeeAuthenticationHttpComponentsClientHttpRequestFactory() {

        super();

        context = new BasicHttpContext();
        cache = new BasicAuthCache();
        credentialsProvider = new BasicCredentialsProvider();

        context.setAttribute(HttpClientContext.AUTH_CACHE, cache);
    }

    public void setCredentials(HttpHost host,
                               JeeAuthenticationScheme authenticationScheme,
                               String username,
                               String password) {

        AuthScheme scheme = null;

        if (authenticationScheme == JeeAuthenticationScheme.BASIC_AUTHENTICATION) {
            scheme = new BasicScheme();
        } else if (authenticationScheme == JeeAuthenticationScheme.DIGEST_AUTHENTICATION) {
            scheme = new DigestScheme();
        }

        cache.put(host, scheme);

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(new AuthScope(host), credentials);

        setHttpClient(HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider).build());
    }

    @Override
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {

        return context;
    }
}
