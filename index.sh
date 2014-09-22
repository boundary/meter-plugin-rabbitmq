#!/bin/bash

if [ $# -ne 4 ]
then
  echo "usage: $(basename $0) <hostname> <port> <user> <password>" >&2
  exit 1
fi

typeset -r HOSTNAME=$1
typeset -r PORT=$2
typeset -r USER=$3
typeset -r PASSWORD=$4

echo "BOUNDARY_RANDOM_NUMBER $RANDOM $(hostname)"
