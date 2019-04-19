FROM java:alpine
WORKDIR /app
COPY ./build/libs/cammunda-servc-1.0-SNAPSHOT.jar app.jar
CMD java -jar /app/app.jar
RUN ["sh"]