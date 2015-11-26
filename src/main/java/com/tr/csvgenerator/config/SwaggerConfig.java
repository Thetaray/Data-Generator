package com.tr.csvgenerator.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by erez on 11/25/15.
 */
@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {
    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                //This info will be used in Swagger. See realisation of ApiInfo for more details.
                .apiInfo(new ApiInfo(
                        "Csv Generator API",
                        "This app is for creating data source in the form of csv file",
                        null,
                        "erezlevy26@gmail.com",
                        null,
                        null
                ));
                        //Here we disable auto generating of responses for REST-endpoints
//                .useDefaultResponseMessages(false)
                        //Here we specify URI patterns which will be included in Swagger docs. Use regex for this purpose.
//                .includePatterns("/csvgen.*");
    }
}

