Boundary RabbitMQ Plugin
========================

**Awaiting Certification**

Collects performance metrics from an instance of RabbitMQ.

### Prerequisites
* Python 2.6.6 or later installed on the target system.
* Credentials for a RabbitMQ user with monitor rights.

### Plugin Setup
None

### Plugin Configuration Fields

|Field Name   |Description                                                                         |
|:------------|:-----------------------------------------------------------------------------------|
|Poll Interval|The Poll Interval to call the command in milliseconds. Defaults to 1000 milliseconds|
|Hostname     |IP Address or hostname that contains the RabbitMQ instance                          |
|Port         |Listening port of the RabbitMQ management plugin                                    |
|User         |User name to use for authenticate against the RabbitMQ management plugin            |
|Password     |Password to use for authenticate against the RabbitMQ management plugin             |

### Metrics Collected

|Metric Name                                                |Description                                                |
|:----------------------------------------------------------|:----------------------------------------------------------|
|RabbitMQ - Total Queues                                    |RabbitMQ - Total Queues                                    |
|RabbitMQ - Total Channels                                  |RabbitMQ - Total Channels                                  |
|RabbitMQ - Total Exchanges                                 |RabbitMQ - Total Exchanges                                 |
|RabbitMQ - Total Consumers                                 |RabbitMQ - Total Consumers                                 |
|RabbitMQ - Total Connections                               |RabbitMQ - Total Connections                               |
|RabbitMQ - Messages Delivered                              |RabbitMQ - Messages Delivered                              |
|RabbitMQ - Messages Delivered Rate                         |RabbitMQ - Messages Delivered Rate                         |
|RabbitMQ - Messages Delivered No Ack                       |RabbitMQ - Messages Delivered No Ack                       |
|RabbitMQ - Messages Delivered No Ack Rate                  |RabbitMQ - Messages Delivered No Ack Rate                  |
|RabbitMQ - Messages Delivered Get                          |RabbitMQ - Messages Delivered Get                          |
|RabbitMQ - Messages Delivered Get Rate                     |RabbitMQ - Messages Delivered Get Rate                     |
|RabbitMQ - Messages Redelivered                            |RabbitMQ - Messages Redelivered                            |
|RabbitMQ - Messages Redelivered Rate                       |RabbitMQ - Messages Redelivered Rate                       |
|RabbitMQ - Messages Published                              |RabbitMQ - Messages Published                              |
|RabbitMQ - Messages Published Rate                         |RabbitMQ - Messages Published Rate                         |
|RabbitMQ - Queue Total Messages                            |RabbitMQ - Queue Total Messages                            |
|RabbitMQ - Queue Total Messages Rate                       |RabbitMQ - Queue Total Messages Rate                       |
|RabbitMQ - Queue Total Messages Ready                      |RabbitMQ - Queue Total Messages Ready                      |
|RabbitMQ - Queue Total Messages Ready Rate                 |RabbitMQ - Queue Total Messages Ready Rate                 |
|RabbitMQ - Queue Total Messages Unacknowledged             |RabbitMQ - Queue Total Messages Unacknowledged             |
|RabbitMQ - Queue Total Messages Unacknowledged Details Rate|RabbitMQ - Queue Total Messages Unacknowledged Details Rate|
|RabbitMQ - Memory Used                                     |RabbitMQ - Memory Used                                     |
|RabbitMQ - Disk Free                                       |RabbitMQ - Disk Free                                       |


