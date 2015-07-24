#!/bin/bash

echo "Starting ..."

./verified_data_old_remove.sh

while (true)
do
    sleep 24h
    ./verified_data_old_remove.sh
done

echo "Finishing ..."


