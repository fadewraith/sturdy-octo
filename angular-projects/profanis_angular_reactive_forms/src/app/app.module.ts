import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SelectMenuDemoComponent } from './select-menu-demo/select-menu-demo.component';
import { DisplayFormErrorMessagesComponent } from './display-form-error-messages/display-form-error-messages.component';
import { AsyncValidatorFormComponent } from './async-validator-form/async-validator-form.component';
import { CustomValidatorDemoComponent } from './custom-validator-demo/custom-validator-demo.component';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { FormarrayDemoComponent } from './formarray-demo/formarray-demo.component';
import { DynamicValidatorDemoComponent } from './dynamic-validator-demo/dynamic-validator-demo.component';
import { SplitReactiveFormToChildCDemoComponent } from './split-reactive-form-to-child-c-demo/split-reactive-form-to-child-c-demo.component';
import { AddressComponent } from './address/address.component';
import { BasicInfoComponent } from './basic-info/basic-info.component';
import { DynamicReactiveFormsDemoComponent } from './dynamic-reactive-forms-demo/dynamic-reactive-forms-demo.component';
import { DynamicFormFieldComponent } from './dynamic-form-field/dynamic-form-field.component';


@NgModule({
  declarations: [
    AppComponent,
    SelectMenuDemoComponent,
    DisplayFormErrorMessagesComponent,
    AsyncValidatorFormComponent,
    CustomValidatorDemoComponent,
    FormarrayDemoComponent,
    DynamicValidatorDemoComponent,
    SplitReactiveFormToChildCDemoComponent,
    AddressComponent,
    BasicInfoComponent,
    DynamicReactiveFormsDemoComponent,
    DynamicFormFieldComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    AppRoutingModule,
    AngularMaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
