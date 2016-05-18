FROM java:8
MAINTAINER Roman Margolin roman.margolin@thetaray.com
EXPOSE 8080
ADD tr-service-1.0.jar app.jar
RUN mkdir -p /opt/tr/csvgen/output/
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
