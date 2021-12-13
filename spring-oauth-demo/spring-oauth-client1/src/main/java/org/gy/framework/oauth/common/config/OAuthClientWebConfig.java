//package org.gy.framework.oauth.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 功能描述：
// *
// * @author gy
// * @version 1.0.0
// */
//@Configuration
//@EnableWebMvc
//public class OAuthClientWebConfig implements WebMvcConfigurer {
//
//    @Bean
//    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
////    @Override
////    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
////        configurer.enable();
////    }
//
//    @Override
//    public void addViewControllers(final ViewControllerRegistry registry) {
//        registry.addViewController("/")
//            .setViewName("forward:/index");
//        registry.addViewController("/index");
//    }
//
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//            .addResourceLocations("/resources/");
//    }
//
//}
