version: "3"
services:
  mysqldb:
    image: mysql:8
    environment:
      - MYSQL_DATABASE=MehdiMaarefDB
      - MYSQL_ALLOW_EMPTY_PASSWORD=yes

  devopsproject:
    image: mehdiimage
    ports:
      - "5052:8080"
