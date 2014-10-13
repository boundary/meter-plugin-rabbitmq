Boundary RabbitMQ Plugin
========================

**Awaiting Certification**

Boundary RabbitMQ plugin extracts key performance metrics from an instance of RabbitMQ instances

## Prerequisites
* Python 2.6.6 or later installed on the target system.
* Credentials for a RabbitMQ user with monitor rights.

## Metrics
Here are the list of metrics created when the RabbitMQ plugin is installed along with their descriptions.

|Metric Name                                                 |Metric Identifier                                               |Description                                                 |
|:-----------------------------------------------------------|:---------------------------------------------------------------|:-----------------------------------------------------------|
|RabbitMQ - Object Totals Channels                           |RABBITMQ\_OBJECT\_TOTALS\_CHANNELS                              |RabbitMQ - Object Totals Channels                           |
|RabbitMQ - Object Totals Exchanges                          |RABBITMQ\_OBJECT\_TOTALS\_EXCHANGES                             |RabbitMQ - Object Totals Exchanges                          |
|RabbitMQ - Object Totals Consumers                          |RABBITMQ\_OBJECT\_TOTALS\_CONSUMERS                             |RabbitMQ - Object Totals Consumers                          |
|RabbitMQ - Object Totals Connections                        |RABBITMQ\_OBJECT\_TOTALS\_CONNECTIONS                           |RabbitMQ - Object Totals Connections                        |
|RabbitMQ - Message Deliver                                  |RABBITMQ\_MESSAGE\_STATS\_DELIVER                               |RabbitMQ - Message Deliver                                  |
|RabbitMQ - Message Stats Deliver Rate                       |RABBITMQ\_MESSAGE\_STATS\_DELIVER\_DETAILS\_RATE                |RabbitMQ - Message Stats Deliver Details Rate               |
|RabbitMQ - Message Stats Deliver No Ack                     |RABBITMQ\_MESSAGE\_STATS\_DELIVER\_NO\_ACK                      |RabbitMQ - Message Stats Deliver No Ack                     |
|RabbitMQ - Message Stats Deliver No Ack                     |RABBITMQ\_MESSAGE\_STATS\_DELIVER\_NO\_ACK\_DETAILS\_RATE       |RabbitMQ - Message Stats Deliver No Ack                     |
|RabbitMQ - Message Deliver Get                              |RABBITMQ\_MESSAGE\_STATS\_DELIVER\_GET                          |RabbitMQ - Message Deliver Get                              |
|RabbitMQ - Message Deliver Get Rate                         |RABBITMQ\_MESSAGE\_STATS\_DELIVER\_GET\_DETAILS\_RATE           |RabbitMQ - Message Deliver Get Rate                         |
|RabbitMQ - Message Stats Redeliver                          |RABBITMQ\_MESSAGE\_STATS\_REDELIVER                             |RabbitMQ - Message Stats Redeliver                          |
|RabbitMQ - Message Stats Redeliver Details Rate             |RABBITMQ\_MESSAGE\_STATS\_REDELIVER\_DETAILS\_RATE              |RabbitMQ - Message Stats Redeliver Details Rate             |
|RabbitMQ - Message Stats Publish                            |RABBITMQ\_MESSAGE\_STATS\_PUBLISH                               |RabbitMQ - Message Stats Publish                            |
|RabbitMQ - Message Stats Publish Details Rate               |RABBITMQ\_MESSAGE\_STATS\_PUBLISH\_DETAILS\_RATE                |RabbitMQ - Message Stats Publish Details Rate               |
|RabbitMQ - Queue Totals Messages                            |RABBITMQ\_QUEUE\_TOTALS\_MESSAGES                               |RabbitMQ - Queue Totals Messages                            |
|RabbitMQ - Queue Totals Messages Details Rate               |RABBITMQ\_QUEUE\_TOTALS\_MESSAGES\_DETAILS\_RATE                |RabbitMQ - Queue Totals Messages Details Rate               |
|RabbitMQ - Totals Messages Ready                            |RABBITMQ\_QUEUE\_TOTALS\_MESSAGES\_READY                        |RabbitMQ - Totals Messages Ready                            |
|RabbitMQ - Queue Totals Messages Ready Details Rate         |RABBITMQ\_QUEUE\_TOTALS\_MESSAGES\_READY\_DETAILS\_RATE         |RabbitMQ - Queue Totals Messages Ready Details Rate         |
|RabbitMQ - Queue Totals Messages Unacknowledged             |RABBITMQ\_QUEUE\_TOTALS\_MESSAGES\_UNACKNOWLEDGED               |RabbitMQ - Queue Totals Messages Unacknowledged             |
|RabbitMQ - Queue Totals Messages Unacknowledged Details Rate|RABBITMQ\_QUEUE\_TOTALS\_MESSAGES\_UNACKNOWLEDGED\_DETAILS\_RATE|RabbitMQ - Queue Totals Messages Unacknowledged Details Rate|
|RabbitMQ - Memory Used                                      |RABBITMQ\_MEM\_USED                                             |RabbitMQ - Memory Used                                      |
|RabbitMQ - Disk Free                                        |RABBITMQ\_DISK\_FREE                                            |RabbitMQ - Disk Free                                        |

## Dashboards
Here are the list of dashboards that are created when the RabbitMQ plugin is installed with their descriptions.

|Dashboard           | Description                                      |
|:-------------------|--------------------------------------------------|
|_RabbitMQ Summary_  |Contains summary metrics related to RabbitMQ      |
|_RabbitMQ Messages_ |Contains metrics related to RabbitMQ messages     |
|_RabbitMQ Queues_   |Contains metrics related to RabbitMQ queues       |
|_RabbitMQ Resources_|Contains metrics related to the RabbitMQ resources|

## Adding the RabbitMQ Plugin to Premium Boundary

1. Login into Boundary Premium
2. Display the settings dialog by clicking on the _settings icon_: ![](src/main/resources/settings_icon.png)
3. Click on the _Plugins_ in the settings dialog.
4. Locate the _rabbitmq_ plugin item and click on the _Install_ button.
5. A confirmation dialog is displayed indicating the plugin was installed sucessfully, along with the metrics and the dashboards.
6. Click on the _OK_ button to dismiss the dialog.

## Removing the RabbitMQ Plugin from Premium Boundary

1. Login into Boundary Premium
2. Display the settings dialog by clicking on the _settings icon_: ![](src/main/resources/settings_icon.png)
3. Click on the _Plugins_ in the settings dialog which lists the installed plugins.
4. Locate the _rabbit_ plugin and click on the item, which then displays the uninstall dialog.
5. Click on the _Uninstall_ button which displays a confirmation dialog along with the details on what metrics and dashboards will be removed.
6. Click on the _Uninstall_ button to perfom the actual uninstall and then click on the _Close_ button to dismiss the dialog.

## Configuration

Once the RabbitMQ plugin is install, metric collection requires that a _relay_ is installed on the target system. Instructions on how to install a relay for linux/unix can found[here](http://premium-documentation.boundary.com/relays), and for Windows [here](http://premium-support.boundary.com/customer/portal/articles/1656465-installing-relay-on-windows).

General operations for plugins are describe in this [article](http://premium-support.boundary.com/customer/portal/articles/1635550-plugins---how-to)
