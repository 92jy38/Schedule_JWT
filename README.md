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

## Mermaid <br>
![20241016_173119](https://github.com/user-attachments/assets/a70703bf-8a0c-496b-9306-c0d82991348a) <br> <hr>

## lv1 일정 <br>

### Schedule CRUD TEST <br>

- POST <br>
  ![20241016_232526_1](https://github.com/user-attachments/assets/62f876ff-1901-4f25-88ba-007ec1492edd)<br>
- GET <br>
  ![20241016_232526_2](https://github.com/user-attachments/assets/f144482e-f11d-41fc-8e6e-2232559a990e)<br>
- PUT <br>
  ![20241016_232526_3](https://github.com/user-attachments/assets/96afcd7c-b5ec-44ff-8937-8859b036cd9c)<br>
- DELETE <br>
  ![20241016_232526_4](https://github.com/user-attachments/assets/adbd8d34-8e52-4c85-94cb-92c508c35f93)<br> <hr>


## lv2 댓글 <br>
### Comment CRUD TEST <br>

- POST <br>
![20241016_235459_1](https://github.com/user-attachments/assets/962b3b19-da40-449a-aa80-976c351490e7)<br>
- GET <br>
![20241016_235459_2](https://github.com/user-attachments/assets/862f36c2-2dc6-41c8-89ce-a2df812f1f0c)<br>
- PUT <br>
![20241016_235459_3](https://github.com/user-attachments/assets/e00f811c-fe9c-4060-875e-0d3bdb861e53)<br>
- DELETE <br>
![20241016_235459_4](https://github.com/user-attachments/assets/1b58c31e-638f-4bbb-9ec5-e17b2b4e8258)<br> <hr>


## lv3 페이징 <br>
### Paging TEST <br>

![20241017_000650_2](https://github.com/user-attachments/assets/2be5dfb2-14f1-439e-9723-d87c1f207176)<br> <hr>


## lv4 유저 <br>
### User CRUD TEST

- POST <br>
![20241016_224653_1](https://github.com/user-attachments/assets/d4b083bc-c318-4058-b376-0345971f68fe)<br>
- GET <br>
![20241016_224653_2](https://github.com/user-attachments/assets/769a2c65-4b20-4687-9844-eed95dbd72ff)<br>
- PUT <br>
![20241016_224653_3](https://github.com/user-attachments/assets/b21b66ca-6908-40b3-9fd7-72e4421c178b)<br>
- DELETE <br>
![20241016_224653_4](https://github.com/user-attachments/assets/0778d0f3-f49c-4c35-9f0c-c3adf6c23059)<br> <hr>


## lv5 예외 처리 <br>
### Exception TEST 

- Wrong Schedule Id <br>
![20241017_011420_1](https://github.com/user-attachments/assets/5ea21ff3-ff5f-4357-b206-d1fe639982ba)<br>
- Wrong User Id <br>
![20241017_011420_2](https://github.com/user-attachments/assets/b9f016d6-3403-4c56-abd2-28056c2ac19f)<br>
- Wrong Comment Id <br>
![20241017_011420_3](https://github.com/user-attachments/assets/33e3d932-2c72-4dcb-9a35-a435dfd79290)<br>
- Used User Id <br>
![20241017_011420_4](https://github.com/user-attachments/assets/ba656a41-fea2-4613-9872-94e2815f7edc)<br>
- No Permission <br>
![20241017_011420_5](https://github.com/user-attachments/assets/70a0505f-7536-4ae6-82dc-f8a5642bb987)<br> <hr>

