# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:17
# define few things
ARG PROFILE=dev
ARG APP_VERSION=1.0.3

WORKDIR /app
COPY --from=build /build/target/book-network-*.jar /app/

EXPOSE 8088

ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

#issue for the below cmd -  - JSONArgsRecommended: JSON arguments recommended for CMD to prevent unintended behavior related to OS signals (line 24)
#The shell form (CMD java ...) is interpreted by the shell (/bin/sh), which can lead to issues when handling signals or environment variables.
#The JSON array form (CMD ["java", "-jar", ...]) avoids the shell, allowing Docker to handle the command more directly and safely. This ensures that the arguments are passed exactly as expected.
#CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar
#recommended way
# When using the CMD as a JSON array (CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "book-network-${JAR_VERSION}.jar"]), the variable substitution for ${JAR_VERSION} does not happen at runtime, and instead, the literal string ${JAR_VERSION} is passed.
#CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "book-network-${JAR_VERSION}.jar"]
CMD ["sh", "-c", "java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}.jar"]
