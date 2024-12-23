# Используем более легкий образ
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR файл
COPY out/artifacts/OrderManagementSystem_jar/OrderManagementSystem.jar /app/OrderManagementSystem.jar

# Открываем порт 8080 (по умолчанию для Spring Boot)
EXPOSE 8181

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "OrderManagementSystem.jar"]
