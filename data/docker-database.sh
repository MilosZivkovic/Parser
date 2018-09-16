docker run --rm -d --name=logbase -p 3306:3306 -v `pwd`/sql:/docker-entrypoint-initdb.d -e MYSQL_ROOT_PASSWORD=root mysql:8.0.12
