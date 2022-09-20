FROM openjdk:18.0.2-jdk-slim as builder
WORKDIR app
ARG JAR_FILE=contact-service/target/*.jar
COPY ${JAR_FILE} contact-service.jar
RUN java -Djarmode=layertools -jar contact-service.jar extract
FROM openjdk:18.0.2-jdk-slim
WORKDIR apps
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
LABEL  developer=malvern