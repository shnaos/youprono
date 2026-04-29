# Étape 1 : Build avec Maven dans un conteneur
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copier le pom.xml et télécharger les dépendances (cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source et compiler l'application
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Image plus légère avec juste le JAR
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
