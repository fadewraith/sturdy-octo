import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AsyncSubjectComponent } from "./observable/async-subject/async-subject.component";
import { CatchThrowComponent } from "./observable/catch-throw/catch-throw.component";
import { CombineLatestComponent } from "./observable/combine-latest/combine-latest.component";
import { ConcatComponent } from "./observable/concat/concat.component";
import { ConcatmapComponent } from "./observable/concatmap/concatmap.component";
import { Concatmap2Component } from "./observable/concatmap2/concatmap2.component";
import { CustomComponent } from "./observable/custom/custom.component";
import { DebounceTimeComponent } from "./observable/debounce-time/debounce-time.component";
import { ExhaustMapComponent } from "./observable/exhaust-map/exhaust-map.component";
import { FilterComponent } from "./observable/filter/filter.component";
import { FromEventComponent } from "./observable/from-event/from-event.component";
import { IntervalComponent } from "./observable/interval/interval.component";
import { ListComponent } from "./observable/list/list.component";
import { MapComponent } from "./observable/map/map.component";
import { MergeMapComponent } from "./observable/merge-map/merge-map.component";
import { MergeComponent } from "./observable/merge/merge.component";
import { ObservableComponent } from "./observable/observable.component";
import { OfFromComponent } from "./observable/of-from/of-from.component";
import { PluckComponent } from "./observable/pluck/pluck.component";
import { ReplaySubjectComponent } from "./observable/replay-subject/replay-subject.component";
import { RetryComponent } from "./observable/retry/retry.component";
import { ShareReplayComponent } from "./observable/share-replay/share-replay.component";
import { SubjectComponent } from "./observable/subject/subject.component";
import { SwitchmapComponent } from "./observable/switchmap/switchmap.component";
import { Switchmap2Component } from "./observable/switchmap2/switchmap2.component";
import { TakeComponent } from "./observable/take/take.component";
import { TapComponent } from "./observable/tap/tap.component";
import { ToArrayComponent } from "./observable/to-array/to-array.component";
import { ZipForkjoinComponent } from "./observable/zip-forkjoin/zip-forkjoin.component";
import { PromiseComponent } from "./promise/promise.component";

const routes: Routes = [
    { path: 'promise', component: PromiseComponent },
    { path: 'observable', component: ObservableComponent, children:[
        { path: '', component: ListComponent },
        { path: 'fromEvent', component: FromEventComponent },
        { path: 'interval', component: IntervalComponent },
        { path: 'of-from', component: OfFromComponent },
        { path: 'to-array', component: ToArrayComponent },
        { path: 'custom', component: CustomComponent },
        { path: 'map', component: MapComponent },
        { path: 'pluck', component: PluckComponent },
        { path: 'filter', component: FilterComponent },
        { path: 'tap', component: TapComponent },
        { path: 'take', component: TakeComponent },
        { path: 'retry', component: RetryComponent },
        { path: 'debouncetime', component: DebounceTimeComponent },
        { path: 'subject', component: SubjectComponent },
        { path: 'replay-subject', component: ReplaySubjectComponent },
        { path: 'async-subject', component: AsyncSubjectComponent },
        { path: 'concat', component: ConcatComponent },
        { path: 'merge', component: MergeComponent },
        { path: 'mergemap', component: MergeMapComponent },
        { path: 'concatmap', component: ConcatmapComponent },
        { path: 'concatmap2', component: Concatmap2Component },
        { path: 'switchmap', component: SwitchmapComponent },
        { path: 'switchmap2', component: Switchmap2Component },
        { path: 'exhaustmap', component: ExhaustMapComponent },
        { path: 'sharereplay', component: ShareReplayComponent },
        { path: 'combinelatest', component: CombineLatestComponent },
        { path: 'zip', component: ZipForkjoinComponent },
        { path: 'catchthrow', component: CatchThrowComponent },
    ] },
    { path: '**', redirectTo: 'promise' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }