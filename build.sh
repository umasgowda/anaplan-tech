#!/bin/bash

cd bring-your-own-interview
./mvnw package
cd ..
docker-compose build
