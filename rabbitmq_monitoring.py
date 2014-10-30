#!/usr/bin/env python
"""
This script extracts metrics from a RabbitMQ instance.
The usage of this script is as follows:

    rabbitmq_monitoring.py

"""
import json
from time import sleep
import collections
import sys
from os.path import basename
import urllib2
from base64 import b64encode
from string import replace

#
# Maps the API path names to Boundary Metric Identifiers
#
KEY_MAPPING = [
  ("object_totals_queues", "RABBITMQ_OBJECT_TOTALS_QUEUES"),
  ("object_totals_channels", "RABBITMQ_OBJECT_TOTALS_CHANNELS"),
  ("object_totals_exchanges", "RABBITMQ_OBJECT_TOTALS_EXCHANGES"),
  ("object_totals_consumers", "RABBITMQ_OBJECT_TOTALS_CONSUMERS"),
  ("object_totals_connections", "RABBITMQ_OBJECT_TOTALS_CONNECTIONS"),
  ("message_stats_deliver", "RABBITMQ_MESSAGE_STATS_DELIVER"),
  ("message_stats_deliver_details_rate", "RABBITMQ_MESSAGE_STATS_DELIVER_DETAILS_RATE"),
  ("message_stats_deliver_no_ack", "RABBITMQ_MESSAGE_STATS_DELIVER_NO_ACK"),
  ("message_stats_deliver_no_ack_details_rate", "RABBITMQ_MESSAGE_STATS_DELIVER_NO_ACK_DETAILS_RATE"),
  ("message_stats_deliver_get", "RABBITMQ_MESSAGE_STATS_DELIVER_GET"),
  ("message_stats_deliver_get_details_rate", "RABBITMQ_MESSAGE_STATS_DELIVER_GET_DETAILS_RATE"),
  ("message_stats_redeliver", "RABBITMQ_MESSAGE_STATS_REDELIVER"),
  ("message_stats_redeliver_details_rate", "RABBITMQ_MESSAGE_STATS_REDELIVER_DETAILS_RATE"),
  ("message_stats_publish", "RABBITMQ_MESSAGE_STATS_PUBLISH"),
  ("message_stats_publish_details_rate", "RABBITMQ_MESSAGE_STATS_PUBLISH_DETAILS_RATE"),
  ("queue_totals_messages", "RABBITMQ_QUEUE_TOTALS_MESSAGES"),
  ("queue_totals_messages_details_rate", "RABBITMQ_QUEUE_TOTALS_MESSAGES_DETAILS_RATE"),
  ("queue_totals_messages_ready", "RABBITMQ_QUEUE_TOTALS_MESSAGES_READY"),
  ("queue_totals_messages_ready_details_rate", "RABBITMQ_QUEUE_TOTALS_MESSAGES_READY_DETAILS_RATE"),
  ("queue_totals_messages_unacknowledged", "RABBITMQ_QUEUE_TOTALS_MESSAGES_UNACKNOWLEDGED"),
  ("queue_totals_messages_unacknowledged_details_rate","RABBITMQ_QUEUE_TOTALS_MESSAGES_UNACKNOWLEDGED_DETAILS_RATE"),
  ("mem_used","RABBITMQ_MEM_USED"),
  ("disk_free","RABBITMQ_DISK_FREE")
  ]

class RabitMQMonitoring():

  def __init__(self):
     self.pollInterval = None
     self.hostname = None
     self.port = None
     self.user = None
     self.password = None
     self.url = None

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
    except urllib2.URLError as e:
        sys.stderr.write("Error connecting to host: {0} ({1}), Error: {2}".format(self.hostname,e.errno,e.message))
        raise
    except urllib2.HTTPError as e:
        sys.stderr.write("Error getting data from AWS Cloud Watch API: %s (%d), Error: %s",
                  getattr(h, "reason", "Unknown Reason"),h.code, h.read())
        raise

    return json.load(response)

  def print_dict(self, dic):
    for (key, value) in KEY_MAPPING:
      if dic.get(key,"-") != "-":
        name = dic.get("name")
        print("%s %s %s" % (value.upper(), dic.get(key, "-"), name))
        sys.stdout.flush()

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

  def get_configuration(self):
    '''
    1) Reads the param.json file that contains the configuration of the plugin.
    2) Sets the values to member variables of the class instance.
    '''
    with open('param.json') as f:
      parameters = json.loads(f.read())
      self.hostname = parameters['hostname']
      self.port = parameters['port']
      self.pollInterval = float(parameters['pollInterval'])/1000.0
      self.user = parameters['user']
      self.password = parameters['password']
      self.url = "http://" + self.hostname + ":" + self.port + "/api/"

  def continuous_monitoring(self):
    while True:
      self.get_details()
      sleep(float(self.pollInterval))

if __name__ == "__main__":
  monitor = RabitMQMonitoring()
  monitor.get_configuration()
  monitor.continuous_monitoring()
