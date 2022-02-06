# PUERTOS

| Servicio                | Puerto  |
|-------------------------|---------|
| `user-service port`     | `8000`  |
| `role-service port`     | `8001`  |
| `auth-jwt-service port` | `8090`  |
 | `gateway-service port`  | `8080`  |

# URIs

| URI                   | Metodo  |
|-----------------------|---------|
| `/api/*/list`         | GET     |
| `/api/*/detail/{id}`  | GET     |
| `/api/*/save`         | POST    |   
| `/api/*/delete/{id}`  | DELETE  |  
| `/api/*/update/{id}`  | PUT     |

> `*` *Palabra antes de -service*

# TOKEN

> `/oauth/validate?token=*token*` *Verificar si un token ya expiro*

> `/oauth/client_credential/accesstoken` *Obtener un token*
 
> > `/oauth/getPayload?token=*token*` *Obtener informacion de un token*

## Para generar un token

```javascript
var details = {
    'email': 'test@gmail.com',
    'password': '123456',
    'grant_type': 'client_credentials'
};

var formBody = [];
for (var property in details) {
    var encodedKey = encodeURIComponent(property);
    var encodedValue = encodeURIComponent(details[property]);
    formBody.push(encodedKey + "=" + encodedValue);
}
formBody = formBody.join("&");

fetch('http://localhost:8080/oauth/client_credential/accesstoken', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    },
    body: formBody
});

```

## Para hacer peticiones

```javascript
async function cargarUrl(url) {
    let response = await fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization:
                "Bearer eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDQxNjAxMTMsImlhdCI6MTY0NDE1NjUxMywiaXNzIjoid3d3Lnpob3B5LmNvbSIsInN1YiI6IntcInVzZXJOYW1lXCI6XCJURVNUIFRFU1QyXCIsXCJlbWFpbFwiOlwidGVzdEBnbWFpbC5jb21cIixcInJvbGVDb2RlXCI6MX0ifQ.02lwvnJVDW_lbZ5PSwcW-i18iIGhFRrpbZ8Hj7xoKMiCMPoNWOBi--LxeQsKrxss",
        },
    });
    return response.json();
}

async function cargarJson() {
    let json = await cargarUrl("http://localhost:8080/api/user/list");

    console.log(json);
}

cargarJson();

```