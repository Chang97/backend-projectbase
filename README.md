# Backend Project Base

Spring Boot 기반 인증/인가 템플릿입니다. JWT + HttpOnly 쿠키 + Redis(리프레시 토큰/권한 캐시) 조합으로, Vue 프런트와 연동할 때 필요한 CORS·쿠키 전략이 기본 설정되어 있습니다.

## Tech Stack

- Java 21 / Spring Boot 3.x
- Spring Security (stateless JWT)
- Redis: refresh token store & authority/permission cache
- PostgreSQL (예시 스키마)
- Gradle

## Quick Start

```bash
./gradlew clean build
./gradlew bootRun
```

- Base URL: `http://localhost:8080`

## Auth Flow

1. `POST /api/auth/login`
   - Body: `LoginRequest`
   - Response: `LoginResponse` + `Set-Cookie: ACCESS_TOKEN`, `Set-Cookie: REFRESH_TOKEN`
2. `POST /api/auth/refresh`
   - Uses HttpOnly `REFRESH_TOKEN` cookie만으로 새 토큰 번들 재발급
3. `POST /api/auth/logout`
   - 쿠키 무효화 + Redis refresh token revoke
4. `GET /api/auth/me`
   - 세션 확인 및 사용자 스냅샷

쿠키 속성 (`AuthSupport.java`)

| Cookie         | httpOnly | SameSite | Secure (`security.cookies.secure`) | Path               | Max-Age (기본) |
| -------------- | -------- | -------- | ---------------------------------- | ------------------ | -------------- |
| ACCESS_TOKEN   | true     | Lax      | false (dev)                        | `/`                | 3600s          |
| REFRESH_TOKEN  | true     | Lax      | false (dev)                        | `/api/auth/refresh`| 604800s        |

## CORS & Cookies

`application.yml` (`cors.*`)  

| Key               | Value                            |
| ----------------- | -------------------------------- |
| allowed-origins   | `http://localhost:5173`          |
| allowed-methods   | `GET,POST,PUT,PATCH,DELETE,OPTIONS` |
| allowed-headers   | `Authorization, Content-Type`    |
| exposed-headers   | `Authorization`                  |
| allow-credentials | `true`                           |
| max-age           | `3600`                           |

프런트는 `withCredentials: true`, `baseURL=http://localhost:8080`로 axios를 구성합니다.

## Redis Keys

| Prefix                               | Description                                |
| ------------------------------------ | ------------------------------------------ |
| `auth:refresh_token:{userId}:{tokenId}` | Refresh token hash + TTL                   |
| `auth:perm:{userId}`                 | 사용자 권한 목록 캐시 (기본 600s)          |
| `app:permission:{useYn}:{name}`      | 퍼미션 스냅샷 캐시                        |

권한·퍼미션·메뉴 변경 시 `AuthorityCacheEventPort`→`AuthorityCacheEventListener`가 캐시를 무효화합니다.

## 주요 코드 포인트

- `SecurityConfig` : `/api/auth/*`, Swagger, OPTIONS 요청 `permitAll`, `JwtAuthenticationFilter` 삽입
- `JwtAuthenticationFilter` : 쿠키 → Authorization 헤더 순으로 토큰 확인
- `AuthSupport` : 토큰 번들 발급/쿠키 설정/refresh token hash 관리

## License

MIT (필요 시 수정)
