package br.com.jeff.example.tilematching.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.faces.webapp.FacesServlet;

@Configuration
@ComponentScan(basePackages={"br.com.jeff.example.tilematching.web"})
@EnableAutoConfiguration
public class WebMain extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebMain.class, ServletInitializer.class);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.jsf");
    }

}
