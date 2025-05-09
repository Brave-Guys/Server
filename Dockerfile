### 1단계: 빌드
FROM gradle:8.3-jdk17 AS builder
WORKDIR /build
COPY --chown=gradle:gradle . .
RUN ./gradlew build -x test --no-daemon --gradle-user-home /tmp/.gradle

### 2단계: 실행
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/build/libs/strengthhub-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
