<!-- source - https://www.youtube.com/watch?v=pA2pi2czXbg -->

1. Input/Output
2. template only communication (Template Reference Variable)
3. using ViewChildren
4. We want products component to have the knowledge of product-card components, we will be using ViewChildren in products component
5. inside the prdocut component, the ViewChildren and productCards are visible after the ViewInitHook, before the AfterViewInit hook, the productCards inside the product component file is not ready
6. another wasy of communicating is by using a service