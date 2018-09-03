# eventacs-tacs-2c-2018
Trabajo Practico TACS - 2° Cuatrimestre 2018 - Tecnologías Avanzadas en la Construcción de Software

### Install
Para levantar la app ejecutar desde una consola:
$- mvn clean compile exec:java

### Eventacs API
### Routes
#### Account Services
**Signup**
- Method: POST
- Rol: User
- URI: /signup
- Body Example:
```
  {
    "name": "nameExample",
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
    "password": "encryptedPasswordExample"
  }
```

**Logout**
- Method: POST
- Rol: User
- URI: /login
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
    "": ""
  }
```

#### Events Services
**Get Events**
- Method: GET
- Rol: User
- URI: /events?criteria=A,B,C

**Interested**
- Method: GET
- Rol: Administrator
- URI: /events/:eventId/interested

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

**Create event list**
- Method: POST
- Rol: User
- URI: /event-lists
- Body Example:
```
  {
    "userId": "userIdExample",
    "name": "nameExample"
  }
```

**Modify event list**
- Method: PUT
- Rol: User
- URI: /event-lists/:listId
- Body Example:
```
  {
    "name": "newNameExample"
  }
```

**Delete event list**
- Method: DELETE
- Rol: User
- URI: /event-lists/:listId

**Get shared events between two lists**
- Method: GET
- Rol: Administrator
- URI: /event-lists/shared-events?listId1=1&listId2=2
