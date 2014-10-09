#!/bin/bash

set -x


StartNodes() {
   env RABBITMQ_NODE_PORT=5672 RABBITMQ_NODENAME=rabbit   rabbitmq-server -detached
   env RABBITMQ_NODE_PORT=5673 RABBITMQ_NODENAME=rabbit_1 rabbitmq-server -detached
   env RABBITMQ_NODE_PORT=5674 RABBITMQ_NODENAME=rabbit_2 rabbitmq-server -detached
}


AddRabbit1ToCluster() {
   rabbitmqctl -n rabbit_1@lerma cluster rabbit@lerma rabbit_1@lerma
}
