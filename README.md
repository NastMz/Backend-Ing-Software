- [PUERTOS](#puertos)
- [URIs](#uris)
- [TOKEN](#token)
    * [Para generar un token](#para-generar-un-token)
    * [Para hacer peticiones](#para-hacer-peticiones)
- [GATEWAY](#gateway)
    * [Archivo `aplication.yml`](#archivo--aplicationyml-)
    * [Agregar servicios al gateway](#agregar-servicios-al-gateway)
- [IMAGENES ZAPATOS](#imagenes-zapatos)
    * [Para subir una imagen](#para-subir-una-imagen)
    * [Para mostrar una imagen](#para-mostrar-una-imagen)
- [RESTABLECER CONTRASEÑA](#restablecer-contraseña)
  * [Para validar email](#para-validar-email)
  * [Para validar respuesta](#para-validar-respuesta)
  * [Para restablecer contraseña](#para-restablecer-contraseña)

# PUERTOS

----

| Servicio           | Puerto |
|--------------------|--------|
| `config-service`   | `8070` |
| `gateway-service`  | `8080` |
| `eureka-service`   | `8761` |

# URIs

----

| URI                   | Metodo  |
|-----------------------|---------|
| `/api/*/list`         | GET     |
| `/api/*/detail/{id}`  | GET     |
| `/api/*/save`         | POST    |   
| `/api/*/delete/{id}`  | DELETE  |  
| `/api/*/update/{id}`  | PUT     |

> `*` -> *Palabra antes de -service*

# TOKEN

----
> `/oauth/validate?token=*token*` *Verificar si un token ya expiro*

> `/oauth/client_credential/accesstoken` *Obtener un token*

> `/oauth/getPayload?token=*token*` *Obtener informacion de un token*

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

# GATEWAY

----

Este servicio hace el redireccionamiento a los demas servicios, esta configurado para recibir las request por el
puerto **8080** y redireccionarlas a su respectivo servicio

### Archivo `aplication.yml`

En este archivo que esta en la ruta `gateway-service\src\main\resources` se agregan los diversos servicios para que se
redireccionen.

La sintaxis es la siguiente:

```yaml
internal.lb.uri: http://localhost
spring:
    cloud:
        gateway:
            routes:
                -   id: user-service
                uri: ${internal.lb.uri}:8000
                predicates:
                    - Path=/api/user/**
                filters:
                    - AuthFilter
```

La variable `internal.lb.uri` es la direccion global por donde llegan las peticiones.

Ademas el atributo `routes` se divide asi:

- `Id` Es el nombre del servicio

- `uri` es la direccion por la que ese servicio recibe las peticiones que es el mismo global, pero con el puerto
  asignado al mismo

- `Path` Es la ruta que se asigno para acceder al los metodos del controlador

- `filters` Es la propiedad que hace que las peticiones a ese servicio deban llevar el token en la cabecera, si no esta
  entonces se pueden hacer peticiones sin token

## Agregar servicios al gateway

Para agregar un nuevo servicio, se duplica la seccion `id` y se pone debajo de la anterior con los nuevos parametros:

```yaml
internal.lb.uri: http://localhost
spring:
    cloud:
        gateway:
            routes:
                -   id: user-service
                    uri: ${internal.lb.uri}:9000
                    predicates:
                        - Path=/api/user/**
                    filters:
                        - AuthFilter
                # Nuevo servicio agregado
                -   id: role-service
                    uri: ${internal.lb.uri}:8001
                    predicates:
                        - Path=/api/role/**
                    filters:
                        - AuthFilter
```

# IMAGENES ZAPATOS

----

Ejemplos de como usar el servicio, estan hechos en bootstrap y javascript, además estan enviando directamente la
peticion al servicio (cuando se implemente, ahi que mandarlo al gateway con el token). Las peticiones tienen que llevar
la misma estructura de los ejemplos ya que si no fallan.

## Para subir una imagen

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />

    <title>Test Subir Imagenes</title>
</head>
<body>
<div class="container-sm">
    <form method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="shoeCode" class="form-label">Codigo</label>
            <input
                    type="text"
                    class="form-control"
                    id="shoeCode"
                    name="shoeCode"
                    value="ZP1"
            />
        </div>
        <div class="mb-3">
            <label for="shoeName" class="form-label">Nombre</label>
            <input
                    type="text"
                    class="form-control"
                    id="shoeName"
                    name="shoeName"
                    value="asdasd"
            />
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Precio</label>
            <input
                    type="number"
                    class="form-control"
                    id="price"
                    name="price"
                    value="2000"
            />
        </div>
        <div class="mb-3">
            <label for="stock" class="form-label">Stock</label>
            <input
                    type="number"
                    class="form-control"
                    id="stock"
                    name="stock"
                    value="15"
            />
        </div>
        <div class="mb-3">
            <label for="categoryCode" class="form-label">Categoria</label>
            <input
                    type="number"
                    class="form-control"
                    id="categoryCode"
                    name="categoryCode"
                    value="3"
            />
        </div>
        <div class="mb-3">
            <label for="supplierNit" class="form-label">Proveedor</label>
            <input
                    type="number"
                    class="form-control"
                    id="supplierNit"
                    name="supplierNit"
                    value="1784939034"
            />
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Descripcion</label>
            <textarea
                    class="form-control"
                    id="description"
                    rows="3"
                    name="description"
                    value="adasda"
            ></textarea>
        </div>
        <div class="mb-3">
            <label for="image" class="form-label">Imagen</label>
            <input class="form-control" type="file" id="image" name="image"/>
        </div>
        <button type="button" class="btn btn-primary" id="btnAgregar">
            Enviar
        </button>
    </form>
</div>

<script src="script.js"></script>

<script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"
></script>
</body>
</html>

```

```javascript
window.onload = iniciar;

function iniciar() {
    var btnAgregar = document.getElementById("btnAgregar");
    btnAgregar.addEventListener("click", clickBtnAgregar);

}

function clickBtnAgregar() {
    // Objeto con los atributos de la entidad Zapatos
    let shoe = {
        shoeCode: document.getElementById("shoeCode").value,
        shoeName: document.getElementById("shoeName").value,
        price: document.getElementById("price").value,
        stock: document.getElementById("stock").value,
        description: document.getElementById("description").value,
        categoryCode: document.getElementById("categoryCode").value,
        supplierNit: document.getElementById("supplierNit").value,
    };

    //se obtiene la imagen que se subio
    var input = document.querySelector('input[type="file"]');

    // El objeto se debe convertir a JSON ya que la peticion va a estar en otro formato
    // que va a contener a este JSON
    const json = JSON.stringify(shoe);
    const blob = new Blob([json], {
        type: "application/json",
    });

    // Se crea el cuerpo de la peticion de tipo multipart/form-data
    // y se añaden el JSON y la imagen 
    // (los nombres de los campos deben ser "shoesRequest" y "image" obligatoriamente)
    let formData = new FormData();
    formData.append("shoesRequest", blob);
    formData.append("image", input.files[0]);

    // se hace la peticion
    // la url debe apuntar al puerto del gateway
    // y en la cabecera de la peticion se debe agregar el token
    async function cargarJson() {
        let json = await cargarUrl("http://localhost:8003/api/shoes/save");
    }

    async function cargarUrl(url) {
        let response = await fetch(url, {
            method: "POST",
            body: formData,
        });
        return response.json();
    }

    cargarJson();
}

```

## Para mostrar una imagen

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <!-- Bootstrap CSS -->
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous"
    />

    <title>Test Cargar Imagenes</title>
</head>
<body>
<div id="products-cards-container">
    <div class="products-cards"></div>
</div>

<script src="load.js"></script>

<script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
        crossorigin="anonymous"
></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"
></script>
</body>
</html>


```

```javascript
window.onload = iniciar;

async function iniciar() {
    const container = document.getElementById("products-cards-container");

    // listado de zapatos
    let shoes = await cargarJson();

    // se hace la peticion
    // la url debe apuntar al puerto del gateway
    // no necesita token
  
    async function cargarJson() {
        let json = cargarUrl("http://localhost:8003/api/shoes/list");
        console.log(json);
        return json;
    }

    async function cargarUrl(url) {
        let response = await fetch(url, {
            method: "GET",
            headers: {
                "Content-type": "application/json",
            },
        });
        return response.json();
    }
    
    function returnCards(shoes) {
       // con <img src="data:image/jpeg;base64,${shoes.imageBytes}"/> 
      // se convierten los bytes del JSON a imagen
        return (
            '<div class="products-cards">' +
            shoes.map((shoes) => ` <div>
                                    <div class="product-header">
                                      <img src="data:image/jpeg;base64,${shoes.imageBytes}" width="250px"/>
                                    </div>
                                    <div class="product-content">
                                      <h4>${shoes.shoeName}</h4>
                                      <p>${shoes.price}</p>
                                    </div> 
                                  </div>`
            )
                .join("") + "</div>"
        );
    }

    container.innerHTML = returnCards(shoes);
}


```

# RESTABLECER CONTRASEÑA

----
> `/api/user/check/email` *Verificar si esta registrado el email*

> `/api/user/check/answer` *Verificar si la respuesta coincide con la del email registrado*

> `/api/user/restore` *Restablece la contraseña*


## Para validar email

```javascript
var details = {
    'email': 'test@gmail.com',
};

var formBody = [];
for (var property in details) {
    var encodedKey = encodeURIComponent(property);
    var encodedValue = encodeURIComponent(details[property]);
    formBody.push(encodedKey + "=" + encodedValue);
}
formBody = formBody.join("&");

fetch('http://localhost:8080/api/user/check/email', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    },
    body: formBody
});

```

## Para validar respuesta

```javascript
var details = {
    'email': 'test@gmail.com',
    'secureAnswer': 'respuesta',
};

var formBody = [];
for (var property in details) {
    var encodedKey = encodeURIComponent(property);
    var encodedValue = encodeURIComponent(details[property]);
    formBody.push(encodedKey + "=" + encodedValue);
}
formBody = formBody.join("&");

fetch('http://localhost:8080/api/user/check/answer', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    },
    body: formBody
});

```

## Para restablecer contraseña

```javascript

let userRestore = {
  'email': 'test@gmail.com',
  'secureAnswer': 'respuesta',
  'newPassword': 'password',
}

async function cargarUrl(url) {
  let response = await fetch(url, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(userRestore)
  });
  return response.json();
}

async function cargarJson() {
  let json = await cargarUrl("http://localhost:8080/api/user/restore");

  console.log(json);
}

cargarJson();

```