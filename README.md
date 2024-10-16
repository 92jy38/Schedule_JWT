# Schedule_JWT Project
## 소개
프로젝트 기간 : 24.10.07 ~ 24.10.17<br>

## Tools
<img src="https://img.shields.io/badge/intellijidea-207BEA?style=for-the-badge&logo=intellij%20idea&logoColor=white"> JDK : Eclipse Temurin 17.0.12 <br> 
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"><br>
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"><br>
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/><img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br>
<hr/>

# Preview
## Lv. 0 - API 명세서, ERD

| 리소스      | 메서드 | URL                        | 설명                     | 요청 바디                                  | 응답 바디                                    | 주요 상태 코드       |
|-------------|--------|----------------------------|--------------------------|---------------------------------------------|----------------------------------------------|----------------------|
|  **User**   | POST   | `/api/users`               | 유저 생성                | ```json { "username": "string", "email": "string" } ``` | ```json { "id": "number", "username": "string", "email": "string", "createdAt": "string", "updatedAt": "string" } ``` | `201 Created`        |
|             | GET    | `/api/users/{id}`          | 유저 조회                | 없음                                        | ```json { "id": "number", "username": "string", "email": "string", "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | PUT    | `/api/users/{id}`          | 유저 수정                | ```json { "username": "string", "email": "string" } ``` | ```json { "id": "number", "username": "string", "email": "string", "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | DELETE | `/api/users/{id}`          | 유저 삭제                | 없음                                        | 없음                                         | `204 No Content`     |
| **Schedule**| POST   | `/api/schedules`           | 일정 생성                | ```json { "title": "string", "content": "string", "creatorId": "number", "assignedUserIds": ["number"] } ``` | ```json { "id": "number", "title": "string", "content": "string", "creatorUsername": "string", "assignedUsernames": ["string"], "createdAt": "string", "updatedAt": "string" } ``` | `201 Created`        |
|             | GET    | `/api/schedules/{id}`      | 일정 조회                | 없음                                        | ```json { "id": "number", "title": "string", "content": "string", "creatorUsername": "string", "assignedUsernames": ["string"], "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | PUT    | `/api/schedules/{id}`      | 일정 수정                | ```json { "title": "string", "content": "string", "creatorId": "number", "assignedUserIds": ["number"] } ``` | ```json { "id": "number", "title": "string", "content": "string", "creatorUsername": "string", "assignedUsernames": ["string"], "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | DELETE | `/api/schedules/{id}`      | 일정 삭제                | 없음                                        | 없음                                         | `204 No Content`     |
| **Comment** | POST   | `/api/comments`            | 댓글 생성                | ```json { "content": "string", "userId": "number", "scheduleId": "number" } ``` | ```json { "id": "number", "content": "string", "username": "string", "scheduleId": "number", "createdAt": "string", "updatedAt": "string" } ``` | `201 Created`        |
|             | GET    | `/api/comments/{id}`       | 댓글 조회                | 없음                                        | ```json { "id": "number", "content": "string", "username": "string", "scheduleId": "number", "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | PUT    | `/api/comments/{id}`       | 댓글 수정                | ```json { "content": "string", "userId": "number", "scheduleId": "number" } ``` | ```json { "id": "number", "content": "string", "username": "string", "scheduleId": "number", "createdAt": "string", "updatedAt": "string" } ``` | `200 OK`             |
|             | DELETE | `/api/comments/{id}`       | 댓글 삭제                | `userId` (Long)                             | 없음                                         | `204 No Content`     |

![20241016_173119](https://github.com/user-attachments/assets/a70703bf-8a0c-496b-9306-c0d82991348a)

