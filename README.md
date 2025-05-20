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

application.properties:
server.address=0.0.0.0
server.port=8080


How to generate jar files?
mvn clean
mvn compile
mvn package
After running these commands in the terminal we see the target folder with .jar file generated. so use this for COPY command.

 