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
- API Testing Tool: Postman


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