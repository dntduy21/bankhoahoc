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

# Tạo thư mục uploads
RUN mkdir -p /app/uploads && chmod -R 777 /app/uploads
VOLUME ["/app/uploads"]

# Expose port để Render hiểu app chạy cổng nào
EXPOSE 8080

# Đảm bảo thư mục uploads luôn tồn tại khi container chạy
 ENTRYPOINT ["sh", "-c", "mkdir -p /app/uploads && chmod -R 777 /app/uploads && java -jar app.jar"]