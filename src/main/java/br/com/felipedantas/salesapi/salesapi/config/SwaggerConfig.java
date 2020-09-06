package br.com.felipedantas.salesapi.salesapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add( new CollectionJsonLinkDiscoverer() );
        return new LinkDiscoverers( SimplePluginRegistry.create( plugins ) );
    }

    @Bean
    public Docket docket(){
        return new Docket( DocumentationType.SWAGGER_2 )
                    .select()
                    .apis( RequestHandlerSelectors.basePackage("br.com.felipedantas.salesapi.salesapi.api.resource") )
                    .paths( PathSelectors.any() )
                    .build()
                    .apiInfo( apiInfo() );
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Sales API")
                .description("API de Controle de Vendas")
                .version("1.0")
                .contact( contact() )
                .build();
    }

    private Contact contact(){
        return new Contact("Felipe Dantas",
                "http://github.com/FelipeRBDantas",
                "feliperbdantas@outlook.com");
    }
}
