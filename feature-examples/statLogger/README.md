# Stats Logging 

```
> cd scripts
> ./startGemFire.sh

> gfsh
    _________________________     __
   / _____/ ______/ ______/ /____/ /
  / /  __/ /___  /_____  / _____  /
 / /__/ / ____/  _____/ / /    / /  
/______/_/      /______/_/    /_/    10.1.0

Monitor and Manage VMware GemFire

gfsh>connect
Connecting to Locator at [host=localhost, port=10334] ..
Connecting to Manager at [host=cblack-z01, port=1099] ..
Successfully connected to: [host=cblack-z01, port=1099]

You are connected to a cluster of version 10.1.0.

gfsh>deploy --jar ../build/libs/statLogger.jar

Deploying files: statLogger.jar
Total file size is: 0.01MB

Continue?  (Y/n): Y
Member  |      JAR       | JAR Location
------- | -------------- | ----------------------------------------------------------------------------------------------------------------------------------
server1 | statLogger.jar | /private/var/folders/96/bgs8ylk14m5cbz7twpvwb9hr0000gq/T/gemfire-extensions15485094576384966966/statLogger/main/lib/statLogger.jar
server2 | statLogger.jar | /private/var/folders/96/bgs8ylk14m5cbz7twpvwb9hr0000gq/T/gemfire-extensions10447278891195337945/statLogger/main/lib/statLogger.jar

gfsh>execute function --id=StatLogger
Member  | Status | Message
------- | ------ | -----------------------------------------------------------
server1 | OK     | [Logging Metric count 1 with timer interval set to 6000 ms]
server2 | OK     | [Logging Metric count 1 with timer interval set to 6000 ms]


```
## Example Logging on Each Member  

```
[info 2024/07/29 20:06:50.266 PDT server1 <StatLogger> tid=0x164] DistributionStats.replyWaitsInProgress = 0

[info 2024/07/29 20:06:51.265 PDT server1 <StatLogger> tid=0x164] StatSampler.delayDuration = 999

[info 2024/07/29 20:06:51.265 PDT server1 <StatLogger> tid=0x164] DistributionStats.replyWaitsInProgress = 0

[info 2024/07/29 20:06:52.268 PDT server1 <StatLogger> tid=0x164] StatSampler.delayDuration = 1013

[info 2024/07/29 20:06:52.268 PDT server1 <StatLogger> tid=0x164] DistributionStats.replyWaitsInProgress = 0

[info 2024/07/29 20:06:53.264 PDT server1 <StatLogger> tid=0x164] StatSampler.delayDuration = 1001

[info 2024/07/29 20:06:53.264 PDT server1 <StatLogger> tid=0x164] DistributionStats.replyWaitsInProgress = 0

[info 2024/07/29 20:06:54.279 PDT server1 <StatLogger> tid=0x164] StatSampler.delayDuration = 1011
```
