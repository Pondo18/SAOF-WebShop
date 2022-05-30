FROM maven:3.8.2-jdk-11-slim

# First install dependencies
WORKDIR /webshop
ADD pom.xml /webshop
RUN mvn verify clean --fail-never

# Then build artifact
COPY . /webshop
RUN mvn clean install -DskipTests
EXPOSE 8080

ENTRYPOINT ["java","-jar","target/webshop-0.0.1-SNAPSHOT.jar"]

