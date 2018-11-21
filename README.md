# Eventacs
### Description
Trabajo Practico TACS - 2° Cuatrimestre 2018 - Tecnologías Avanzadas en la Construcción de Software

### Creacion de certificados
La variable de entorno JAVA_HOME debe estar seteada
En el directorio resources de el servidor de recursos, ejecuta estos 3 comandos:
 -keytool -genkeypair -alias eventacs -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore eventacskeystore.p12 -validity 3650
 -keytool -export -keystore eventacskeystore.p12 -alias eventacs -file eventacsCertificate.cer
 -sudo keytool -import -trustcacerts -file eventacsCertificate.cer -alias eventacs -keystore $(find $JAVA_HOME -name cacerts)
 -contraseña eventacs
 -sudo keytool -import -trustcacerts -file eventacsCertificate.cer -alias eventacs -keystore $(find $JAVA_HOME -name cacerts)
contraseña changeit o changeme
 -archivo hosts agregar la siguiente linea sin comentar
127.0.0.1       eventacs.com    eventacs        localhost
 -copiar los eventacskeystore.p12 y eventacskeystore.p12 generados al resources del servidor de oauth

###Para los certificados del front
 Crear el directorio certificate (en el raiz de la app, del front). Pararse en ese directorio y ejecutar los siguientes comandos
 -openssl genrsa -out server.key 2048
 -openssl rsa -in server.key -out server.key
 -openssl req -sha256 -new -key server.key -out server.csr -subj '/CN=eventacs.com'
 -openssl x509 -req -sha256 -days 365 -in server.csr -signkey server.key -out server.crt
 -cat server.crt server.key > server.pem


### Install
Para levantar la aplicacion ejecutar desde una consola:
$- mvn clean compile exec:java

### Telegram
El bot de telegram implementado es **@TacsBot**. 
Para realizar las pruebas, se tienen los siguientes usuarios:

**Usuario 1:**``username: User1, password: Pw1, IdlistaEventos: Id1``
**Usuario 2:**``username: User2, password: Pw2, IdlistaEventos: Id1``

### Comandos disponibles Telegram

**/ayuda:** muestra los comandos disponibles

**/buscarevento:** busca eventos en eventbrite. Pide de forma separada los siguientes parámetros: keyword, Id de categoría (muestra las categorías disponibles y pregunta si se desea agregar otra categoría), fecha de inicio (pide por separado día, mes y año), y fecha de fin. Retorna una lista de eventos con id y nombre. Se puede utilizar sin haber usado /login.

**/agregarevento:** agrega un evento a una lista de eventos ya existente del usuario. Los parámetros que pide son Id de la lista de eventos, y Id del evento a agregar (primero usar /buscarevento para saber el id). Requiere que el usuario haya verificado su cuenta de telegram usando el comando /login.

**/revisareventos:** muestra los eventos de una lista de eventos del usuario. Solamente pide el id de la lista de eventos a consultar. Requiere que el usuario haya verificado su cuenta de telegram usando el comando /login.

**/login:** verifica a qué usuario de la app pertenece la cuenta de telegram. Sólo es necesario utilizarlo una vez. Pide Usuario y contraseña.
 
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
