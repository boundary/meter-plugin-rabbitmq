#!/usr/bin/env python
"""
This script extracts metrics from a RabbitMQ instance.
The usage of this script is as follows:

    rabbitmq_monitoring.py <hostname> <port> <user> <password>

"""
import json
from time import sleep
import collections
import sys
from os.path import basename
import urllib2
from base64 import b64encode

KEY_MAPPING = [
  ("rabbitmq_version", "RabbitMQ_Version"),
  ("object_totals_queues", "RabbitMQ_Total_Queues"),
  ("object_totals_channels", "RabbitMQ_Total_Channels"),
  ("object_totals_exchanges", "RabbitMQ_Total_Exchanges"),
  ("object_totals_consumers", "RabbitMQ_Total_Consumers"),
  ("object_totals_connections", "RabbitMQ_Total_Connections"),
  ("message_stats_deliver", "RabbitMQ_Messages_Push_Total"),
  ("message_stats_deliver_details_rate", "RabbitMQ_Messages_Push_Rate"),
  ("message_stats_deliver_no_ack", "RabbitMQ_Messages_Pushed_With_No_Ack"),
  ("message_stats_deliver_no_ack_details_rate", "RabbitMQ_Messages_Pushed_With_No_Ack_Rate"),
  ("message_stats_deliver_get", "RabbitMQ_Messages_Pulled"),
  ("message_stats_deliver_get_details_rate", "RabbitMQ_Messages_Total_Push_Pull_Rate"),
  ("message_stats_redeliver", "RabbitMQ_Messages_Redelivered"),
  ("message_stats_redeliver_details_rate", "RabbitMQ_Messages_Redelivery_Rate"),
  ("message_stats_publish", "RabbitMQ_Messages_Published"),
  ("message_stats_publish_details_rate", "RabbitMQ_Messages_Publish_Rate"),
  ("queue_totals_messages", "RabbitMQ_Queue_Total_Messages"),
  ("queue_totals_messages_details_rate", "RabbitMQ_Queue_Message_Rate"),
  ("queue_totals_messages_ready", "RabbitMQ_Queue_Messages_Ready"),
  ("queue_totals_messages_ready_details_rate", "RabbitMQ_Queue_Ready_Messages_Rate"),
  ("queue_totals_messages_unacknowledged", "RabbitMQ_Queued_Un_Ack_Messages"),
  ("queue_totals_messages_unacknowledged_details_rate","RabbitMQ_Queue_Un_Ack_Messages_Rate"),
  ("mem_used", "RabbitMQ_Memory_Used"),
  ("disk_free", "RabbitMQ_Disk_Free")
  ]

class RabitMQMonitoring():

  def __init__(self,host,port,user,password):
     self.host = host
     self.port = port
     self.user = user
     self.password = password
     self.url = "http://" + self.host + ":" + self.port + "/api/"

  def send_get(self,url):
    response = requests.get(url, auth=(self.user, self.password))
    return response.json()

  def call_api(self, endpoint):
    url = self.url + endpoint
    auth = b64encode(self.user + ":" + self.password)
    headers = {
        "Accept": "application/json",
        "Authorization": "Basic %s" % auth,
    }
    request = urllib2.Request(url,headers=headers)
    try:
        response = urllib2.urlopen(request)
    except urllib2.HTTPError as e:
        sys.stderror.write("Error getting data from AWS Cloud Watch API: %s (%d), Error: %s",
                  getattr(e, "reason", "Unknown Reason"),e.code, e.read())
        raise

    return json.load(response)

  def print_dict(self, dic):
    for (key, value) in KEY_MAPPING:
      if dic.get(key,"-") != "-":
        print("%s %10s %s" % (value.upper(), dic.get(key, "-"), dic.get("name")))

  def get_details(self):
    overview = self.call_api("overview")
    nodes = self.call_api("nodes")

    if nodes:
      overview.update(nodes[0])

    if overview:
      data = self.flatten_dict(overview)
      self.print_dict(data)

  def flatten_dict(self, dic, parent_key='', sep='_'):
    items = []
    for k, v in dic.items():
      new_key = parent_key + sep + k if parent_key else k
      if isinstance(v, collections.MutableMapping):
        items.extend(self.flatten_dict(v, new_key, sep).items())
      else:
        items.append((new_key, v))
    return dict(items)

  def extractMetrics(self):
    self.get_details()

  def continuous_monitoring(self, secs):
    print("Continuously monitoring at every %d seconds" % (secs))
    while True:
      self.get_details(self.print_dict)
      print("\n\n")
      sleep(secs)

if __name__ == "__main__":
  if len(sys.argv) != 5:
    sys.stderr.write("usage: " + basename(sys.argv[0]) + "<host> <port> <user> <password>\n")
    sys.exit(1)
  
  monitor = RabitMQMonitoring(sys.argv[1],sys.argv[2],sys.argv[3],sys.argv[4])
  monitor.get_details()
