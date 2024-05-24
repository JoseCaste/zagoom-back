# Usa una imagen base con JDK y Maven
FROM maven:3.8.5-openjdk-8 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y los archivos de configuración de Maven
COPY pom.xml /app/
COPY dao/pom.xml /app/dao/
COPY web-service/pom.xml /app/web-service/

# Copia el código fuente de los módulos
COPY dao /app/dao/
COPY web-service /app/web-service/

# Ejecuta mvn clean install para compilar los módulos y generar los artefactos
RUN mvn clean install
CMD ["ls -lah"]
# Segunda etapa: imagen de ejecución
FROM openjdk:8-jre

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos JAR desde la etapa de construcción
COPY --from=build /app/dao/target/dao.jar /app/dao.jar
COPY --from=build /app/web-service/target/web-service.jar /app/web-service.jar

# Define el comando para ejecutar tu aplicación
CMD ["java", "-jar", "/app/web-service.jar"]