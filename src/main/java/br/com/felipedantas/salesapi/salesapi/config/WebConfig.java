package br.com.felipedantas.salesapi.salesapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings( CorsRegistry registry ) {
        registry.addMapping( "/**" )
            .allowedMethods( "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT" );
    }

    @Override
    public void configureContentNegotiation( ContentNegotiationConfigurer configurer ) {
        configurer.favorPathExtension( false )
                .favorParameter( false )
                .ignoreAcceptHeader( false )
                .useRegisteredExtensionsOnly( false )
                .defaultContentType( MediaType.APPLICATION_JSON )
                .mediaType( "json", MediaType.APPLICATION_JSON )
                .mediaType( "xml", MediaType.APPLICATION_XML );
    }
}
