package com.annotationtypes.springbootannotation;

import com.annotationtypes.springbootannotation.controller.MyController;
import com.annotationtypes.springbootannotation.lazy.LazyLoader;
import com.annotationtypes.springbootannotation.propertysource.PropertySourceDemo;
import com.annotationtypes.springbootannotation.repository.MyRepository;
import com.annotationtypes.springbootannotation.scope.PrototypeBean;
import com.annotationtypes.springbootannotation.scope.SingletonBean;
import com.annotationtypes.springbootannotation.service.MyService;
import com.annotationtypes.springbootannotation.value.ValueAnnotationDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootannotationApplication {
	// source - https://www.youtube.com/playlist?list=PLGRDMO4rOGcN-8NFRfIBkbdJuifnWkPCR

	public static void main(String[] args) {
		var context = SpringApplication.run(SpringbootannotationApplication.class, args);
//		PizzaController pizzaController = context.getBean(PizzaController.class);
//		PizzaController pizzaController = (PizzaController) context.getBean("pizzaDemo");
//		PizzaController pizzaController = (PizzaController) context.getBean("pizzaController");
//		System.out.println(pizzaController.getPizza());

////		MyController
//		MyController myController = context.getBean(MyController.class);
//		System.out.println(myController.hello());
//
////		MyService
//		MyService myService = context.getBean(MyService.class);
//		System.out.println(myService.hello());
//
////		MyRepository
//		MyRepository myRepository = context.getBean(MyRepository.class);
//		System.out.println(myRepository.hello());
//
////		@Lazy
//		LazyLoader lazyLoader = context.getBean(LazyLoader.class);
//		System.out.println(lazyLoader);

//		SingletonBean singletonBean1 = context.getBean(SingletonBean.class);
//		System.out.println(singletonBean1.hashCode());
//		SingletonBean singletonBean2 = context.getBean(SingletonBean.class);
//		System.out.println(singletonBean2.hashCode());
//		SingletonBean singletonBean3 = context.getBean(SingletonBean.class);
//		System.out.println(singletonBean3.hashCode());
//
//		PrototypeBean prototypeBean1 = context.getBean(PrototypeBean.class);
//		System.out.println(prototypeBean1.hashCode());
//		PrototypeBean prototypeBean2 = context.getBean(PrototypeBean.class);
//		System.out.println(prototypeBean2.hashCode());
//		PrototypeBean prototypeBean3 = context.getBean(PrototypeBean.class);
//		System.out.println(prototypeBean3.hashCode());

//		ValueAnnotationDemo valueAnnotationDemo = context.getBean(ValueAnnotationDemo.class);
//		System.out.println(valueAnnotationDemo.getDefaultName());
//		System.out.println(valueAnnotationDemo.getHost());
//		System.out.println(valueAnnotationDemo.getEmail());
//		System.out.println(valueAnnotationDemo.getPassword());
//		System.out.println(valueAnnotationDemo.getJavaHome());
//		System.out.println(valueAnnotationDemo.getHomeDir());

		PropertySourceDemo propertySourceDemo = context.getBean(PropertySourceDemo.class);
		System.out.println(propertySourceDemo.getEmail());
		System.out.println(propertySourceDemo.getPassword());
		System.out.println(propertySourceDemo.getHost());
		System.out.println(propertySourceDemo.getAppName());
		System.out.println(propertySourceDemo.getAppDescription());


//		VegPizza vegPizza = context.getBean(VegPizza.class);
//		VegPizza vegPizza = (VegPizza) context.getBean("vegPizzaBean");
//		VegPizza vegPizza = (VegPizza) context.getBean("vegPizza");
//		System.out.println(vegPizza.getPizza());
	}


}
