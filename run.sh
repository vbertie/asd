#!/bin/bash

cd user
mvn package
cd ..

cd future-stock
mvn package
cd ..

docker-compose up