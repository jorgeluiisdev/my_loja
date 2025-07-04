FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copia o jar backend pronto
COPY backend/app-web/target/app-web-0.0.0.1-SNAPSHOT.jar app-web.jar

# Copia build frontend pronto
COPY frontend/myloja/dist ./static

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app-web.jar"]