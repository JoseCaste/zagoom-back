# Usa una imagen base con Maven
FROM maven:latest

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml al directorio de trabajo
COPY pom.xml .

# Copia todo el código fuente al directorio de trabajo
COPY . .

# Ejecuta el comando 'mvn clean install'
RUN mvn clean install -DskipTests

# Copia los artefactos generados a ubicaciones específicas
COPY dao/target/dao.jar dao.jar
COPY web-service/target/web-services.jar web-services.jar
COPY web-service/target/web-services.jar app.jar


# Command to run the jar file
CMD ["java", "-jar", "app.jar"]

