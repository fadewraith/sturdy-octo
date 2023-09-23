package com.annotationtypes.springbootannotation.config;

import com.annotationtypes.springbootannotation.controller.PizzaController;
import com.annotationtypes.springbootannotation.service.NonVegPizza;
import com.annotationtypes.springbootannotation.service.Pizza;
import com.annotationtypes.springbootannotation.service.VegPizza;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
//    @Bean(name="vegPizzaBean")
//    @Bean(name="vegPizza")
    public Pizza vegPizza() {
        return new VegPizza();
    }

    @Bean
    public Pizza nonVegPizza() {
        return new NonVegPizza();
    }

//    @Bean
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PizzaController pizzaController() {
        return new PizzaController(vegPizza()); // inject vegPizza or nonVegPizza
    }


}
