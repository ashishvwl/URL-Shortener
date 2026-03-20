# URL Shortener

Full-stack URL shortener built with Spring Boot 3, React, PostgreSQL, Redis.

## Tech Stack
- Backend: Java 17, Spring Boot 3, Spring Security (JWT), Spring Data JPA, Redis
- Frontend: React, TypeScript, React Query, Tailwind CSS
- Database: PostgreSQL
- Cache: Redis

## Running locally
docker-compose up -d
./mvnw spring-boot:run
cd url-shortener-ui && npm run dev

## API
POST /api/auth/register  — create account
POST /api/auth/login     — get JWT token
POST /api/urls/shorten   — shorten a URL (auth required)
GET  /:code              — redirect to original URL
GET  /api/urls           — list my URLs (auth required)
