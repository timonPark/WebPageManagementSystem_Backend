# Social Login
## 설명
* 소셜 플랫폼 인증을 통해 얻은 accessToken으로 로그인 및 회원가입 진행

## API SPEC

### Method: POST
### URL: {base_url}/user/social/{social_type}
  * base_url: 기본 url
  * social_type:
    * kakao
    * naver
    * google


### Request Body
```
{
    "accesstoken" : {social accessToken}
}
```

### Resepose Body
* Status Code: 200
```
{
    "status": "success",
    "data":{
        "accesstoken": {accessToken}
    },
    "message": null
}
```

* Status Code: 401 - 인증 실패
```
{
    "status": "error",
    "data": null,
    "message": "소셜 {social_type} 인증에 실패하였습니다. accessToken: {social_accessToken}
}
```

* Status Code: 403 - 휴면 계정
```
{
    "status": "error",
    "data": null,
    "message": "해당 계정은 휴면 계정입니다"
}
```

* Status Code: 403 - 계정 삭제
```
{
    "status": "error",
    "data": null,
    "message": "해당 계정은 삭제 되었습니다"
}
```
* Status Code: 403 - 중복 회원 가입
```
{
    "status": "error",
    "data": null,
    "message": "이미 가입되어 있는 계정입니다. 소셜로그인여부: Y, 소셜타입: {social_type}"
}
```