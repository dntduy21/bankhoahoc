# Stage 1: Build
FROM maven:3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests && rm -rf /root/.m2/repository

# Stage 2: Run
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy jar từ stage build vào container, đặt tên là app.jar
COPY --from=build /app/target/*.jar app.jar

# Tạo thư mục upload tạm thời
RUN mkdir -p /app/uploads
VOLUME ["/app/uploads"]

# Expose port để Render hiểu app chạy cổng nào
EXPOSE 8080

# Chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
