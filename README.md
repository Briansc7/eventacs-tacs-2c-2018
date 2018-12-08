# Eventacs
### Description
Trabajo Practico TACS - 2° Cuatrimestre 2018 - Tecnologías Avanzadas en la Construcción de Software

### Pasos con docker
Modificar el archivo hosts con el siguiente comando:
``sudo nano /etc/hosts``

Agregar al archivo hosts las siguientes tres líneas:

``172.10.0.8	backend``

``172.10.0.9 	oauth-server``

``172.10.0.10 	frontend``

(esto solo es necesario hacerlo una vez)

Hacer cd al directorio raíz de evectacs-tacs-2c-2018 y ejecutar el siguiente comando para levantar la app:
``sudo docker-compose up``

Una vez terminado se puede probar el bot de telegram mandandole mensajes a @TacsBot (ver sección de telegram de este documento).

También se puede probar el front accediendo desde un navegador a http://frontend:3000/ 

Al finalizar las pruebas, ejecutar:
``sudo docker-compose down``

Si al probar una nueva versión de la app los cambios no se reflejan al levantar al app con docker, puede deberse a que docker esté usando imágenes viejas.

Por lo tanto para probar una nueva versión de la app, puede ser necesario correr primero el siguiente comando:
``sudo docker rmi $(sudo docker images)``


### Pasos viejos sin docker
### Creacion de certificados
La variable de entorno JAVA_HOME debe estar seteada
En el directorio resources del servidor de recursos borrar los archivos eventacskeystore.p12 y eventacsCertificate.cer, luego ejecutar los siguientes 3 comandos:

 ``keytool -genkeypair -alias eventacs -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore eventacskeystore.p12 -validity 3650``
 (contraseña eventacs, nombre y organización eventacs.com, los demás datos no es necesario completarlos)
 
 ``keytool -export -keystore eventacskeystore.p12 -alias eventacs -file eventacsCertificate.cer``
 (contraseña eventacs)
 
 ``sudo keytool -import -trustcacerts -file eventacsCertificate.cer -alias eventacs -keystore $(find $JAVA_HOME -name cacerts)``
(contraseña changeit o changeme)

Si el tercer comando ya había sido ejecutado anteriormente, es necesario primero ejecutar el siguiente comando:
``sudo keytool -delete -alias eventacs -keystore $(find $JAVA_HOME -name cacerts)``
(contraseña changeit o changeme)

 -archivo hosts (ubicado en linux en /etc/hosts) agregar la siguiente linea sin comentar
``127.0.0.1       eventacs.com    eventacs        localhost``

 -copiar los eventacskeystore.p12 y eventacskeystore.p12 generados a la carpeta resources del servidor de oauth

###Para los certificados del front
 Crear el directorio certificate (en el raiz de la app, del front). Pararse en ese directorio y ejecutar los siguientes comandos
 
 ``openssl genrsa -out server.key 2048``
 
 ``openssl rsa -in server.key -out server.key``
 
 ``openssl req -sha256 -new -key server.key -out server.csr -subj '/CN=eventacs.com'``
 
 ``openssl x509 -req -sha256 -days 365 -in server.csr -signkey server.key -out server.crt``
 
 ``cat server.crt server.key > server.pem``


### Mysql
Instalar Mysql y crear el usuario pds. Para ello loguearse en Mysql como root y ejecutar los siguientes comandos:

``CREATE USER 'pds'@'localhost' IDENTIFIED BY 'clave';``

``GRANT ALL PRIVILEGES ON * . * TO 'pds'@'localhost';``

### Install
Luego de instalar los certificados y crear el usuario de Mysql, realizar los siguientes pasos:
Primero levantar el servidor de Mongo y el de Redis.
Luego ubicarse en la carpeta oauth-authorization-server y ejecutar desde una consola el siguiente comando para levantar el servidor de autenticación:

$- ``mvn clean compile exec:java``

Finalmente ubicarse en la carpeta raíz (eventacs-tacs-2c-2018) y ejecutar desde una consola el siguiente comando para levantar el servidor de recursos (levanta al bot de telegram):

$- ``mvn clean compile exec:java``

### Telegram
El bot de telegram implementado es **@TacsBot**. 
Para realizar las pruebas, se tienen los siguientes usuarios:

**Usuario 1:**``username: User1, password: Pw1, IdlistaEventos: 2``

**Usuario 2:**``username: User2, password: Pw2, IdlistaEventos: no tiene``

### Comandos disponibles Telegram

**/ayuda:** muestra los comandos disponibles

**/buscarevento:** busca eventos en eventbrite. Pide de forma separada los siguientes parámetros: keyword, Id de categoría (muestra las categorías disponibles y pregunta si se desea agregar otra categoría), fecha de inicio (pide por separado día, mes y año), y fecha de fin. Retorna una lista de eventos con listId y nombre. Se puede utilizar sin haber usado /login.

**/agregarevento:** agrega un evento a una lista de eventos ya existente del usuario. Los parámetros que pide son Id de la lista de eventos, y Id del evento a agregar (primero usar /buscarevento para saber el listId). Requiere que el usuario haya verificado su cuenta de telegram usando el comando /login.

**/revisareventos:** muestra los eventos de una lista de eventos del usuario. Solamente pide el listId de la lista de eventos a consultar. Requiere que el usuario haya verificado su cuenta de telegram usando el comando /login.

**/login:** verifica a qué usuario de la app pertenece la cuenta de telegram. Sólo es necesario utilizarlo una vez. Pide Usuario y contraseña.

**/cancelar:** cancela el uso de un comando para volver al menú inicial. Por ejemplo si precionó por error /buscarevento, se puede salir sin tener que pasar por todo el proceso de pedido de parámetros.
 
### Backend API
### Routes
**Puerto:** 9000 **Basepath:** "/eventacs" 
Example:
`localhost:9000/eventacs`

Para los métodos POST, se debe agregar el header Content-Type con el valor application/json.
El body debe ser escrito en formato json como se ve en los ejemplos a continuación.

#### Account Services
**Signup**
- Method: POST
- Rol: User
- URI: /signup
- Body Example:
```
  {
    "name": "nameExample",
    "lastName": "lastNameExample",
    "password": "encryptedPasswordExample"
  }
```

**Login**
- Method: POST
- Rol: User
- URI: /login
- Body Example:
```
  {
    "name": "nameExample",
    "encryptedPassword": "encryptedPasswordExample"
  }
```

**Logout**
- Method: POST
- Rol: User
- URI: /logout
- Body Example:
```
  {
    "sessionCookieId": "sessionCookieIdExample"
  }
```

#### Users Management Services
**Get User**
- Method: GET
- Rol: Administrator
- URI: /users/:userId

**Get Users**
- Method: GET
- Rol: Administrator
- URI: /users

**Create Alarm**
- Method: POST
- Rol: User
- URI: /users/:userId/alarms
- Body Example:
```
  {
    "keyword": "keywordExample",
    "categories": ["105"],
    "startDate": "2018-12-12",
    "endDate": "2018-12-24"
  }
```
`Parameters:
  keyword Optional[String], 
  endDate Optional[LocalDate], 
  startDate Optional[LocalDate]
  categories Optional[List[String]]`

#### Events Services
**Get Events**
- Method: GET
- Rol: User
- URI: /events?keyword={someCriteria}

`Parameters:
  keyword Optional[String], 
  endDate Optional[LocalDate], 
  startDate Optional[LocalDate]
  categories Optional[List[String]]`

**Get Watchers**
- Method: GET
- Rol: Administrator
- URI: /events/:eventId/watchers

**Count Events**
- Method: GET
- Rol: Administrator
- URI: /events/count?timelapse={someTimelapse}

`Timelapses = TODAY, FEW_DAYS, WEEK, MONTH, ALL`

#### Events Lists Services
**Add event to event list**
- Method: PUT
- Rol: User
- URI: /event-lists/:listId/:eventId
- Body: userId
```
  {
    "userId": "User1"
  }
```


**Create event list**
- Method: POST
- Rol: User
- URI: /event-lists
- Body Example:
```
  {
    "userId": "User1",
    "listName": "listNameExample"
  }
```

**Modify event list**
- Method: PUT
- Rol: User
- URI: /event-lists/:listId
- Body Example:
```
  {
    "listName": "listNameExample"
  }
```

**Delete event list**
- Method: DELETE
- Rol: User
- URI: /event-lists/:listId

**Get shared events between two lists**
- Method: GET
- Rol: Administrator
- URI: /event-lists/shared-events?listId=1&anotherListId=2



Mongo

En mongo guardamos 2 colecciones con el siguiente formato:

**EventLists

{
	"_id" : ObjectId("5bfb193b3f751cb4ca497a44"),
	"listId" : "1",
	"listName" : "Lista figo",
	"userId" : "Figo",
	"events" : {
		"name" : "alto event",
		"start" : "Sun Nov 25 18:50:54 ART 2018",
		"description" : "un re evento",
		"end" : "Sun Nov 25 18:50:54 ART 2018",
		"id" : "98765",
		"category" : "900",
		"logoUrl" : "http"
	          }
}

**Alarms

{
	"_id" : ObjectId("5bfb193b3f751cb4ca497a44"),
	"alarmId" : "1",
	"userId" : "Figo",
	"search" : {
		"keyword" : "buenos",
		"start" : "Sun Nov 25 18:50:54 ART 2018",
		"categories" : [ ],
		"end" : "Sun Nov 25 18:50:54 ART 2018",
	}
}

Tenemos dos métodos de idGenerator que cada vez que se crea una entrada, nos devuelve un id único, el alarmId y el listId para manejarlo desde el modelo.
