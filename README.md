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
 
>  `/oauth/getPayload?token=*token*` *Obtener informacion de un token*

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

# PUERTOS DE LOS SERVICIOS

Para asignarle un puerto especifico a los servicios, se debe hacer en el archivo `aplication.properties` que se encuentra en la ruta `\src\main\resources` de cada servicio.

Los parametros son:

```properties
 server.port=8000
 spring.application.name=user-service
```

En este archivo tambien se encuentran los parametros de la base de datos.

```properties
spring.datasource.url=jdbc:mysql://localhost/zhopy?useSSL=false
spring.datasource.dbname=zhopy
spring.datasource.username=______
spring.datasource.password=_____
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

# GATEWAY

Este servicio hace el redireccionamiento a los demas servicios, esta configurado para recibir las request por el puerto **8080** y redireccionarlas a su respectivo servicio

### Archivo `aplication.yml`

En este archivo que esta en la ruta `gateway-service\src\main\resources` se agregan los diversos servicios para que se redireccionen.

La sintaxis es la siguiente:

```yaml
internal.lb.uri: http://localhost
spring:
 cloud:
  gateway:
   routes:
      - id: user-service
       uri: ${internal.lb.uri}:8000
       predicates:
        - Path=/api/user/**
       filters:
        - AuthFilter
```


La variable `internal.lb.uri` es la direccion global por donde llegan las peticiones.

Ademas el atributo `routes` se divide asi:

- `Id` Es el nombre del servicio

- `uri` es la direccion por la que ese servicio recibe las peticiones que es el mismo global, pero con el puerto asignado al mismo

- `Path` Es la ruta que se asigno para acceder al los metodos del controlador

- `filters` Es la propiedad que hace que las peticiones a ese servicio deban llevar el token en la cabecera, si no esta entonces se ueden haacer peticiones sin token

## Agregar servicios al gateway

Para agregar un nuevo servicio, se duplica la seccion `id` y se pone debajo de la anterior con los nuevos parametros:

```yaml
internal.lb.uri: http://localhost
spring:
  cloud:
    gateway:
      routes:
          - id: user-service
            uri: ${internal.lb.uri}:9000
            predicates:
              - Path=/api/user/**
            filters:
              - AuthFilter
          - id: role-service 
            uri: ${internal.lb.uri}:8001
            predicates:
              - Path=/api/role/**
            filters:
              - AuthFilter
```

