# User API Spec

## Register User
Endpoint: POST api/users

Request Body:
```json
{
  "username": "surya",
  "password": "password",
  "name": "I Nyoman Surya Pradipta"
}
```

Response Body (Success):
```json
{
  "data": "OK"
}
```

Response Body (Failed):
```json
{
  "errors": "Username must not blank"
  
}
```


## Login User
Endpoint: POST api/auth/login

Request Body:
```json
{
  "username": "surya",
  "password": "password"
}
```

Response Body (Success):
```json
{
  "data": {
    "token": "TOKEN",
    "expiredAt": 123123 ///milliseconds
  }
}
```

Response Body (Failed, 401):
```json
{
  "data": "KO",
  "errors": "Username must not blank"
  
}
```

## Get User
Endpoint: GET api/users/current

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "username": "surya",
  "name": "I Nyoman Surya Pradipta"
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized"
  
}
```



## Update User
notes:
PUT itu ditimpa, semua data dikirim ulang.
PATCH bisa update salah satu saja.

Endpoint: PATCH api/users/current

Request Header:
- X-API-TOKEN: Token (Mandatory)

Request Body:
```json
{
  "name": "I Nyoman Surya Pradipta", // put if only want to update name
  "password": "password" // put if only want to update password
}
```

Response Body (Success):
```json
{
  "data": {
    "username": "surya",
    "name": "I Nyoman Surya Pradipta"
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized"
  
}
```


## Logout User
Endpoint: DELETE api/auth/logout

Request Header:
- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": "OK",
}
```
