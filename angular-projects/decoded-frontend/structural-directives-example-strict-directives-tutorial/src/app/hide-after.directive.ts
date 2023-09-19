import {
  Directive,
  Input,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from "@angular/core";

// context property, should not match the selector but rather directive input where we want to use it
// s11 - creating a diff class for context
class HideAfterContext {
  public get $implicit() { //s20 for default value, this is setting the default value
    return this.hideAfter;
  };
  public hideAfter = 0;
  public counter = 0; // s16
  public hideAfterThen = 1000;
}

@Directive({
  selector: "[hideAfter]",
})
export class HideAfterDirective implements OnInit {
  @Input("hideAfter")
  set delay(value: number | null) { //s15
    this._delay = value ?? 0;
    this.context.hideAfter = this.context.counter = this._delay / 1000;
  }
  private _delay = 0; // s14

  @Input("hideAfterThen") // for then placeholder, its used 'then' in app component, if we use hideAfterHello, then inside app component file - we will use 'hello'
  // s21 - replaced 'any' type to 'HideAfterContext' from 'TemplateRef'
  placeholder: TemplateRef<HideAfterContext> | null = null;

  private context = new HideAfterContext(); // s12 - creating an instance of the class

  // step - 8
  // inject the reference to the container, where we're going to render the template
  constructor(
    private viewContainerRef: ViewContainerRef,
    private template: TemplateRef<HideAfterContext>
  ) {}

  ngOnInit(): void {
    //  s9 - we've to instruct the angular to render our injected template in the injected view container wrap 'createEmbeddedView'
    // s10 - we can use context as a second paramtere and its optional and context could be any object like {} and inside it, we can define some variables
    // s13 - adding param this.context
    this.viewContainerRef.createEmbeddedView(this.template, this.context);
    const intervalId = setInterval(() => { //s17
      this.context.counter--;
    },1000);

    setTimeout(() => { // clearing the container after whatever time is being passed
      this.viewContainerRef.clear();
      if (this.placeholder) {
        // s19 - passing this.counter, for the #placeholder in app.component.ts file, we can use another context, but here we're reusing the this.context
        // this.viewContainerRef.createEmbeddedView(this.placeholder, this.context).context.;
        this.viewContainerRef.createEmbeddedView(this.placeholder, this.context);
      }
      clearInterval(intervalId); // s18
    }, this._delay);
  }

  // s22 - to make it more strict ,w're declaring static method here - 
  /**
   * ngTemplateContextGuard - this makes 2 argumentss
   * @param dir HideAfterDirective
   * @param ctx unknown
   * @returns ctx is HideAfterContext
   * return type is type predicate, we use it if we want to check that is something,
   * belongs to some certain type
   * 
   */

  static ngTemplateContextGuard(dir: HideAfterDirective, ctx: unknown): ctx is HideAfterContext { return true }
}
