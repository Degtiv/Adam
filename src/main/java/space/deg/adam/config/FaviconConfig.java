package space.deg.adam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Collections;

@Configuration
public class FaviconConfig
{
//    @Bean
//    public SimpleUrlHandlerMapping myFaviconHandlerMapping()
//    {
//        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
//        mapping.setOrder(Integer.MIN_VALUE);
//        mapping.setUrlMap(Collections.singletonMap("/static/public/favicon.ico",
//                myFaviconRequestHandler()));
//        return mapping;
//    }
//
//    @Autowired
//    ApplicationContext applicationContext;
//
//    @Bean
//    protected ResourceHttpRequestHandler myFaviconRequestHandler()
//    {
//        ResourceHttpRequestHandler requestHandler =
//                new ResourceHttpRequestHandler();
//        requestHandler.setLocations(Collections.singletonList(applicationContext.getResource("/")));
//        requestHandler.setCacheSeconds(0);
//        return requestHandler;
//    }
}
