# WebPageManagementSystem

## Project Spec
### Backend
- java 17.0.10
- Spring Boot 3.2.3
- dgs 6.0.3

### Frontend

### Dev Tools
- ERD: CloudERD
- IDE: IntelliJ
- Database Tool: Datagrip
- VM: Docker
- DB: PostgreSQL
- API Testing Tool: Postman

## API 문서
[소셜 로그인](https://github.com/timonPark/WebPageManagementSystem_Backend/tree/master/wiki/user/README.md)
[로그인]

## graphql Example
1. 브라우저에 http://localhost:8080/graphiql를 넣으세요

2. 아래와 같이 쿼리를 날려보세요
```
query showQuery($titleFilter: String){
  shows(titleFilter: $titleFilter) {
        title
        releaseYear
    }
}

{
  "titleFilter": "is"
}
```

## 로그 파일 관련
[로컬 로그 파일 가이드](https://locrian-gerbil-117.notion.site/Spring-boot-e5ec541ba3c44cd583a28924cae31cc3)
