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

RUN apt update && apt install curl -y
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.3/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install 18.14.0
RUN . "$NVM_DIR/nvm.sh" && nvm use v18.14.0
RUN . "$NVM_DIR/nvm.sh" && nvm alias default v18.14.0
ENV PATH="/root/.nvm/versions/node/v18.14.0/bin/:${PATH}"
WORKDIR /app/client-app
RUN npm install && npm run build
WORKDIR /app

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

COPY --from=build /app/target/payment.war ./
ENTRYPOINT ["java", "-jar", "payment.war"]