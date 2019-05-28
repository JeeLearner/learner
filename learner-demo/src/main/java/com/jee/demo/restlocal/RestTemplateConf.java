package com.jee.demo.restlocal;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConditionalOnClass(value = {RestTemplate.class, HttpClient.class})
public class RestTemplateConf {

  @Autowired
  HttpClientProperties properties;
 
  /**
   * 配置HTTP客户端工厂
   * @return
   */
  private ClientHttpRequestFactory createFactory() {
    System.out.println("-------" + properties.getReadTimeout());
    if (properties.getMaxConnTotal() <= 0) {
      SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
      factory.setConnectTimeout(properties.getConnectTimeout());
      factory.setReadTimeout(properties.getReadTimeout());
      return factory;
    }
    HttpClient httpClient = HttpClientBuilder.create().setMaxConnTotal(properties.getMaxConnTotal())
        .setMaxConnPerRoute(properties.getMaxConnPerRoute()).build();
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        httpClient);
    factory.setConnectTimeout(properties.getConnectTimeout());
    factory.setReadTimeout(properties.getReadTimeout());
    return factory;
  }
 
  //初始化RestTemplate,并加入spring的Bean工厂，由spring统一管理
  @Bean
  @ConditionalOnMissingBean(RestTemplate.class)
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(this.createFactory());
    List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
 
    //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
    HttpMessageConverter<?> converterTarget = null;
    for (HttpMessageConverter<?> item : converterList) {
      if (StringHttpMessageConverter.class == item.getClass()) {
        converterTarget = item;
        break;
      }
    }
    if (null != converterTarget) {
      converterList.remove(converterTarget);
    }
    converterList.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    //加入FastJson转换器 根据使用情况进行操作，此段注释，默认使用jackson
    //converterList.add(new FastJsonHttpMessageConverter4());
    converterList.add(1, new FastJsonHttpMessageConvert5());
    return restTemplate;
  }

  /**
   * FastJsonHttpMessageConverter4有个bug，因为它默认支持MediaType.ALL
   * 而Spring在处理MediaType为ALL时，会用字节流进行处理，而不是处理成json
   */
  public static class FastJsonHttpMessageConvert5 extends FastJsonHttpMessageConverter {
    static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public FastJsonHttpMessageConvert5(){
      setDefaultCharset(DEFAULT_CHARSET);
      setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
    }
  }


}