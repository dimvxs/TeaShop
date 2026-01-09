# ---- build stage ----
FROM gradle:8.3-jdk17 AS build
WORKDIR /app

# Копируем все файлы
COPY . .

# Собираем проект без тестов
RUN gradle build -x test --no-daemon

# ---- run stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Копируем готовый jar
COPY --from=build /app/build/libs/*.jar app.jar

# Открываем порт
EXPOSE 8080

# Запуск
CMD ["java", "-jar", "app.jar"]
