#!/usr/bin/env bash
docker run --rm -d --name=logbase -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=access -e MYSQL_USER=access -e MYSQL_PASSWORD=access123 mysql:8.0.12