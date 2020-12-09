#!/bin/sh
./mvnw package
docker-compose down -v
docker-compose up --remove-orphan