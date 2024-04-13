FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY testNg.xml .
CMD ["mvn", "test"]