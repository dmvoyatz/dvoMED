### Project Utopia by dvo

## Getting started

## Firing it up
- cd /Velocity
- Reset docker: docker-compose down -v
- Open a terminal in that directory and run: docker-compose build or docker-compose build --no-cache
- next run docker-compose up
- Access phpMyAdmin in your browser at http://localhost:8081 and connect with credentials of mysql server

- Initialize explicitly the DB by running: 
docker exec -i mysql8 mysql -uxefternos -pxeF5@r6os utopiaDB < schema.sql

Spring Boot App: http://localhost:8087
phpMyAdmin: http://localhost:8081