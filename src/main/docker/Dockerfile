FROM java:8
VOLUME /tmp
EXPOSE 8080
ADD csv-generator-1.0-SNAPSHOT.jar app.jar
RUN mkdir -p /opt/tr/csvgen/output/
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
