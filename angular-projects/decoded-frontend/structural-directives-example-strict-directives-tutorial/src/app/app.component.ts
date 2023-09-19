import { Component } from "@angular/core";
import { LoadingState, Person } from "./if-loaded.directive";

@Component({
  selector: "app-root",
  template: `
    <main>
      <h1>Structural Directives</h1>
      <!-- then name came from "hideAfterThen" @Input for placeholder -->
      <!-- the 'counter' is from hide-after.directive.ts file for HideAfterContext class -->
      <!-- for showing the default value, from hideAfterThen variable in HideAfterContext class, syntax - let default -->
      <!-- and here in this case, both the default and time values are same -->
      <section *hideAfter="5000 as time; then placeholder; let counter = counter; let default" class="banner primary">
        <h2>Temporary content</h2>
        <p>This layout should disappear in {{default}} seconds. It disappears in: {{counter}}</p>
      </section>
      <!-- this is placeholder -->
      <!-- for reading the context from the hide-after.directive.ts file, syntax is slightly different for the ng-templates, start with let, but instead of space we'll give '-' and we can give any name we want, let-hiddenAfter="hideAfter", where hideAfter is the property we're reading from the hide-after.directive.ts file HideAfterContext class -->
      <!-- let-myDefault is used for the default value -->
      <ng-template #placeholder let-hiddenAfter="hideAfter" let-myDefault>
        <section class="banner placeholder">
          <h2>Placeholder</h2>
          <!-- hiddenAfter - this is the same variable as declared above in ng-template -->
          <p>Here was some content. It was visible for {{hiddenAfter}} seconds.</p>
        </section>
      </ng-template>

      <!-- if-loaded.directive.ts, this directive takes some loading state -->
      <section *ifLoaded="state">Data was loaded. User name:
        {{state.data.name}}</section>
    </main>
  `,
  styles: [
    `
      .banner {
        display: block;
        padding: 15px 15px;
        border-radius: 10px;
      }
      .primary {
        color: white;
        background-color: #2e2473;
      }
      .placeholder {
        background-color: #ededed;
        border: dashed #e0e0e0 2px;
      }
    `,
  ],
})
export class AppComponent {
  // Person is from if-loaded.directive.ts
  state: LoadingState<Person> = {
    type: 'loading'
  }
  constructor() {
    setTimeout(() => {
      this.state = {
        type: 'loaded',
        // Person is from if-loaded.directive.ts
        data: {
          name: 'Daria'
        }
      }
    }, 3000)
  }
}


// https://www.youtube.com/playlist?list=PLX7eV3JL9sfmuWwKB2zP_1w7NhLVN10d7
// source1 - https://youtu.be/07CaGlbMPbw?list=PLX7eV3JL9sfmuWwKB2zP_1w7NhLVN10d7
// source2 - https://youtu.be/zpVVHI21TAo?list=PLX7eV3JL9sfmuWwKB2zP_1w7NhLVN10d7
// source3 - https://youtu.be/fsGzxyHi6yY?list=PLX7eV3JL9sfmuWwKB2zP_1w7NhLVN10d7