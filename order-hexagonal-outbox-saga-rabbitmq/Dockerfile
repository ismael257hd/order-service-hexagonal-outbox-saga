# Estágio 1: Build (Usando JDK 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código fonte e gera o jar
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Usando JRE 21 Alpine - leve e seguro)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o jar do estágio de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]