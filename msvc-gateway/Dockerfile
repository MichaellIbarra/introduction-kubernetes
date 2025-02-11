ARG MSVC_NAME=msvc-gateway

# Manual de DockerFile multi-stage builds
FROM openjdk:17-jdk-alpine AS builder
ARG MSVC_NAME
# WORKDIR es para indicar el directorio de trabajo
WORKDIR /app/$MSVC_NAME


# COPY es para copiar archivos de la maquina local a la imagen de docker
COPY ./pom.xml  /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw ./
COPY ./$MSVC_NAME/pom.xml ./

# todas estás instrucciónes -Dmaven son para saltar los test y no ejecutarlos en la construcción de la imagen
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
# RUN ./mvnw dependency:go-offline , para descargar las dependencias de maven y no tener que descargarlas cada vez que se construye la imagen
COPY ./$MSVC_NAME/src ./src

# RUN es para ejecutar comandos en la terminal
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
ARG MSVC_NAME

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/$MSVC_NAME/target/msvc-gateway-0.0.1-SNAPSHOT.jar .

# EXPOSE es indicar el puerto que se va a exponer
EXPOSE $PORT

# ENTRYPOINT es para indicar el comando que se va a ejecutar cuando se inicie el contenedor
#CMD sleep 10 && java -jar msvc-course-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "msvc-gateway-0.0.1-SNAPSHOT.jar"]

# docker build .  : para construir la imagen


#Ejecuta el comando para inicializar el wrapper
#En la raíz del proyecto, ejecuta:
#mvn wrapper:wrapper


