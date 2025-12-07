Latency - time to completion of a task. measured in time units
Throughput - amount of tasks completed in a given period. measured in tasks/time unit

Hyperthreading - virtual cores vs physical cores

Inherent cost of parallelization and aggregation
breaking task into multiple tasks -> thread creation, passing tasks to threads -> time bt thread.start() to thread getting scheduled -> time unitl the last thread finished and signlas

time uuntil the aggregating thread runs -> aggregation of the subresults into a single artifact