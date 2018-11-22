#!/bin/bash

cd kafka

sudo rm -r /tmp/zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties &
gnome-terminal -- bin/kafka-server-start.sh config/server.properties
