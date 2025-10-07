FROM eclipse-temurin:21-alpine AS base
WORKDIR /app
COPY . ./

# Test stage
FROM base AS test
RUN --mount=type=cache,id=gradle,target=/root/.gradle \
    ./gradlew --build-cache --no-daemon check

# Build stage
FROM base AS build
RUN --mount=type=cache,id=gradle,target=/root/.gradle \
    ./gradlew --build-cache --no-daemon -x check build

# Final image
FROM eclipse-temurin:21-alpine
WORKDIR /app

RUN apk --no-cache add curl && \
    adduser -D user

COPY --from=build /app/build/libs/app-1.0.0.jar ./app.jar

USER user
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
