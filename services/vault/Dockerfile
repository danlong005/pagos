FROM maven:3.9.0-eclipse-temurin-17 as build

ARG GIT_USERNAME
ARG GIT_PERSONAL_ACCESS_TOKEN
ARG COMMIT_SHA
ARG JAVA_ENV

WORKDIR /app
COPY pom.xml ./
COPY settings.xml ./

RUN mvn -s settings.xml clean dependency:analyze
COPY . ./

RUN mvn -s settings.xml clean install


FROM maven:3.9.0-eclipse-temurin-17

ARG JAVA_ENV
ENV JAVA_ENV=${JAVA_ENV}
ARG COMMIT_SHA
ENV COMMIT_SHA=${COMMIT_SHA}
ARG GIT_USERNAME
ARG GIT_PERSONAL_ACCESS_TOKEN
ENV GIT_USERNAME=${GIT_USERNAME}
ENV GIT_PERSONAL_ACCESS_TOKEN=${GIT_PERSONAL_ACCESS_TOKEN}

COPY --from=build /app/target/*.jar ./
ENTRYPOINT ["java", "-jar", "vault-0.0.1.jar"]