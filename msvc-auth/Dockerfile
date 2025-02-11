ARG MSVC_NAME=msvc-auth


# Manual de DockerFile
FROM openjdk:17-jdk-alpine AS builder
ARG MSVC_NAME
# WORKDIR es para indicar el directorio de trabajo
WORKDIR /app/$MSVC_NAME



# COPY es para copiar archivos de la maquina local a la imagen de docker
COPY ./pom.xml  /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw ./
COPY ./$MSVC_NAME/pom.xml ./

# todas estás instrucciónes -Dmaven son para saltar los test y no ejecutarlos en la construcción de l
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
# RUN ./mvnw dependency:go-offline , para descargar las dependencias de maven y no tener que descargarlas cada vez que se construye la imagen
COPY ./$MSVC_NAME/src ./src

# RUN es para ejecutar comandos en la terminal
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
ARG MSVC_NAME

WORKDIR /app

RUN mkdir ./logs
COPY --from=builder /app/$MSVC_NAME/target/msvc-auth-0.0.1-SNAPSHOT.jar .


# EXPOSE es indicar el puerto que se va a exponer
EXPOSE 9000

# ENTRYPOINT es para indicar el comando que se va a ejecutar cuando se inicie el contenedor
CMD ["java", "-jar", "msvc-auth-0.0.1-SNAPSHOT.jar"]

# docker build .  : para construir la imagen