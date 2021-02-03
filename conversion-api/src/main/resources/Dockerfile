FROM alpine/git AS GIT_REPO
WORKDIR /app
RUN git clone https://github.com/Vinodh-thimmisetty/ayoholdings.git

FROM maven:3.5-jdk-8-alpine AS MAVEN_BUILD
WORKDIR /app/ayoholdings/conversion-api
COPY --from=GIT_REPO /app/ayoholdings /app/ayoholdings
RUN mvn clean install

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /app/ayoholdings/conversion-api/target/conversion-api.jar /app
EXPOSE 9898
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/conversion-api.jar"]


# docker pull vinodh5052/conversion_api
# docker run -d -p 9898:9898 conversion_ui