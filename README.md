[![N|Solid](https://www.nisum.com/hs-fs/hubfs/Full%20logo%20version%20color.png?width=199&height=1234&name=Full%20logo%20version%20color.png)](https://www.nisum.com/es/inicio)

# Willians Vasquez
## Ejercicio técnico Java




## Diagrama de Solucion:
```sh
               +---------------------+
               |     API Gateway     |
               +----------+----------+
                          |
     +--------------------+--------------------+
     |                                         |
+--------+                               +--------+
|  JWT   |                               |Swagger |
+--------+                               +--------+
     |                                         |
     |                HTTP Requests             |   API Documentation
     |                                         |
+-----------+                         +--------------+
|  Backend  |                         |  Swagger UI  |
+-----------+                         +--------------+
     |                                         |
     |               REST Responses             |   API Documentation
     |                                         |
+-----------+                         +--------------+
|  Database |                         |  Swagger UI  |
+-----------+                         +--------------+
```
-En este diagrama, el API Gateway es el punto de entrada para las solicitudes HTTP que llegan al sistema. El API Gateway utiliza el mecanismo de autenticación JWT para verificar la identidad del usuario que realiza la solicitud. Si la autenticación es exitosa, el API Gateway redirige la solicitud al componente de backend que contiene la lógica de negocio y el controlador REST. El backend se comunica con la base de datos para leer o escribir datos según la solicitud.

El componente de backend también utiliza Swagger UI para exponer la documentación de la API REST, que describe los endpoints y los parámetros de entrada y salida de cada endpoint. Los desarrolladores pueden utilizar Swagger UI para explorar la API, probarla y generar código de cliente en una variedad de lenguajes de programación.

En resumen, este diagrama muestra cómo los componentes principales de una solución basada en REST con JWT y Swagger interactúan entre sí para proporcionar una forma segura y documentada de interactuar con un sistema.
## Documentacion de Swagger 
[http://localhost:8080/swagger-ui/index.html]
En Swagger encontrara detalles de como funciona cada Endpoint

## Pruebas con Postman 
Para probar los endpoints de una API utilizando Postman, sigue estos pasos:
 PARTE 1: Inicializar con Usuario Temporal 
1. Abre Postman y crea una nueva solicitud HTTP haciendo clic en el botón "New" en la esquina superior izquierda.
2. Seleccione el método HTTP  POST para la solicitud y ingrese la URL del endpoint localhost:6060/api/v1/login.
3. Configure  el cuerpo de la solicitud  en la pestaña "Body y coloque estructura similar a 
 {
    "name": "User",
    "email": "user@user.org",
    "password": "34erdfcvB"
}.
4. Haga clic en el botón "Send" para enviar la solicitud.
5. Verifique la respuesta recibida del servidor en la pestaña "Response". Si la respuesta contiene un cuerpo, asegúrate de que los datos sean correctos y estén formateados correctamente.
6. copie el contenido de la pripiedad "token" que sera utilisado para crear propiamente los usuarios con toda la estructura requerida y que la solicitud que acabamos de crear genera temporalmente el un token valido por aproximadamente 2 minutos.

PARTE 2 crear Usuarios
1. Crea una nueva solicitud HTTP haciendo clic en el botón "New" en la esquina superior izquierda.
2. Seleccione el método HTTP  POST para la solicitud y ingrese la URL del endpoint localhost:6060/api/v1/user.
3. Configure  el cuerpo de la solicitud  en la pestaña "Body y coloque estructura similar a 
 {
"name": "nombre",
"email": "email@email.org",
"password": "password",
"phones": [
{
"number": "123455567",
"citycode": "1",
"contrycode": "57"
}
]
}.
4. dirijase a la opscion "Autorizacion" seleccione el tipo "Bearer token" y pegen el Token generado en el paso anterior.
5.  Verifique la respuesta recibida del servidor en la pestaña "Response". Si la respuesta contiene un cuerpo, asegúrate de que los datos sean correctos y estén formateados correctamente.
6.  repita el paso la cantidad de usuarios que requiera crea.

PARTE 3 Visualiza todos  los usuarios Creado
1. Crea una nueva solicitud HTTP haciendo clic en el botón "New" en la esquina superior izquierda.
2. Seleccione el método HTTP  GET para la solicitud y ingrese la URL del endpoint localhost:6060/api/v1/user.
4. dirijase a la opscion "Autorizacion" seleccione el tipo "Bearer token" y pegen el Token generado en el paso anterior.
5.  Verifique la respuesta recibida del servidor en la pestaña "Response". Si la respuesta contiene un cuerpo, asegúrate de que los datos sean correctos y estén formateados correctamente.

PARTE 4 Actualiza los Usuarios 
1. Crea una nueva solicitud HTTP haciendo clic en el botón "New" en la esquina superior izquierda.
2. Seleccione el método HTTP  PUT para la solicitud y ingrese la URL del endpoint colocando el email del usuario que requiere modificar,  localhost:6060/api/v1/user/email@email.org 
3.  Configure  el cuerpo de la solicitud  en la pestaña "Body y coloque estructura con los datos que requiere modificar  
 {
"name": "nombre",
"email": "email@email.org",
"password": "password",
"phones": [
{
"number": "123455567",
"citycode": "1",
"contrycode": "57"
}
]
}.
4. dirijase a la opscion "Autorizacion" seleccione el tipo "Bearer token" y pegen el Token de un usuario.
5.  Verifique la respuesta recibida del servidor en la pestaña "Response". Si la respuesta contiene un cuerpo, asegúrate de que los datos sean correctos y estén formateados correctamente.

PARTE 5 Actualiza los Token en caso de que este expirado  
1. Crea una nueva solicitud HTTP haciendo clic en el botón "New" en la esquina superior izquierda.
2. Seleccione el método HTTP  PUT para la solicitud y ingrese la URL del endpoint localhost:6060/api/v1/login.
3. Configure  el cuerpo de la solicitud  en la pestaña "Body y coloque estructura similar a 
 {
    "name": "User",
    "email": "user@user.org",
    "password": "34erdfcvB"
}.
4. dirijase a la opscion "Autorizacion" seleccione el tipo "Bearer token" y pegen el Token de un usuario.
5.  Verifique la respuesta recibida del servidor en la pestaña "Response". Si la respuesta contiene un cuerpo, asegúrate de que los datos sean correctos y estén formateados correctamente.

Saludos,



