package com.annotationtypes.springbootannotation.controller;

import com.annotationtypes.springbootannotation.service.NonVegPizza;
import com.annotationtypes.springbootannotation.service.Pizza;
import com.annotationtypes.springbootannotation.service.VegPizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
//@Component("pizzaDemo")
//@Component("pizzaController")
public class PizzaController {

//    @Autowired // field injection
    private VegPizza vegPizza;

    private Pizza pizza;

    private NonVegPizza nonVegPizza;


//    @Autowired // constructor injection
    public PizzaController(VegPizza vegPizza) {
        this.vegPizza = vegPizza;
    }

//    @Autowired
//    public PizzaController(@Qualifier("nonVegPizza") Pizza pizza) {
    public PizzaController(Pizza pizza) {
        this.pizza = pizza;
    }

//    @Autowired // setter injection
//    public void setVegPizza(VegPizza vegPizza) {
//        this.vegPizza = vegPizza;
//    }

    public void setNonVegPizza(NonVegPizza nonVegPizza) {
        this.nonVegPizza = nonVegPizza;
    }

    public String getPizza() {
//        return "Pizza";
//        return vegPizza.getPizza();
//        return nonVegPizza.getPizza();
        return pizza.getPizza();
    }

    public void init() {
        System.out.println("init");
    }

    public void destroy() {
        System.out.println("destroy");
    }
}

