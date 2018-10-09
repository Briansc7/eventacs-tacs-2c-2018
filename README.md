# Eventacs
### Description
Trabajo Practico TACS - 2° Cuatrimestre 2018 - Tecnologías Avanzadas en la Construcción de Software

### Install
Para levantar la aplicacion ejecutar desde una consola:
$- mvn clean compile exec:java

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
