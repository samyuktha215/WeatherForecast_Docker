# WeatherForecast_Docker

Docker Setup commands:

DockerFile:
first we should create dockerfile with some basic commands like:
FROM       for choosing image that suits for our application .
WORKDIR    for creating the directory in docker.
COPY       to copy our generated source.jar files in target folder to the working directory in docker.
ENTRYPOINT to start executing the app from source to working directory in docker.
EXPOSE     to show or expose which ports our app is listening.

Docker Commands:
docker build -t weather-forecast-app .
docker run -p 8080:8080 weather-forecast-app
docker stop container_id
docker rename old_id new
docker start new


application.properties:
server.address=0.0.0.0
server.port=8080


How to generate jar files?
mvn clean
mvn compile
mvn package
After running these commands in the terminal we see the target folder with .jar file generated. so use this for COPY command.

Docker Commands to create Snapshot from the same image:
docker image build -t custom-weather1:df .
docker container run -it -rm -p 8082:8080 custom-weather1:df

 graceful shutdown ctrl+c