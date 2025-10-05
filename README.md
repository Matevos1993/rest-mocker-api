# Rest Mocker API

**Rest Mocker API** is an open-source service designed to simulate RESTful APIs for frontend development, integration testing, and learning purposes. It provides easily configurable endpoints and realistic mock data, helping developers prototype or test client applications without relying on live backends.

This project is built with **[Quarkus](https://quarkus.io/)** – the Supersonic Subatomic Java Framework – to provide fast startup times and developer-friendly features.

---

## 🚀 Features

- ✅ Mock common REST endpoints (GET, POST, PUT, DELETE)
- 📄 Simulate different response payloads and status codes
- ⏱️ Add artificial latency for realistic network simulation
- 🔐 Optional request validation or authentication simulation
- 📦 Easily extendable with your own endpoints
- 🌱 Quarkus Dev Mode with hot reload
- 🧪 Great for frontend development, testing, or educational use

---

# 📦 API Overview

The API currently supports the following mock resource types:
> ⚠️ **Warning:**  
> Rest Mocker API simulates REST requests and responses. Default data is loaded from the database, while user changes are stored only in memory and are reset after 30 minutes. No actual data is permanently saved, updated, or deleted. All changes are ephemeral and exist only for the duration of the retention period.

## HTTP Response

| Endpoint                   | Method | Description                      |
|----------------------------|--------|----------------------------------|
| `/http/{status}`           | GET    | Mock HTTP Response               |
| `/http/{status}/{message}` | GET    | Create your custom HTTP Response |

### Example `Response body`

```json
{
  "status": 200,
  "message": "ok"
}
```
### Example `Custom response body`
> The status code must be between 100 and 599
```json
{
  "status": 100,
  "message": "Hello World!"
}
```
### Code example 
**URL:** `GET https://your-api-url/http/200`

```javascript
fetch('https://your-api-url/http/200')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));
```

**URL:** `GET https://your-api-url/http/{status}/{message}`

```javascript
fetch('https://your-api-url/http/200/hello_world')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));
```
---

## TODO Items

| Endpoint                                              | Method | Description                                                 |
|-------------------------------------------------------|--------|-------------------------------------------------------------|
| `/todos`                                              | GET    | Fetch list of mocked TODO items                             |
| `/todos?offset=10&limit=20&sort=createdAt&order=desc` | GET    | Fetch list of mocked TODO items with pagination and sorting |
| `/todos/{id}`                                         | GET    | Fetch a single TODO item by ID                              |
| `/todos`                                              | POST   | Add a new mock TODO item                                    |
| `/todos/{id}`                                         | PUT    | Update a mock TODO item                                     |
| `/todos?id={id}`                                      | DELETE | Delete a mock TODO item                                     |

### Example `TODO` JSON

```json
{
  "id": 1,
  "todo": "Create README",
  "completed": true,
  "userId": 1,
  "createdAt": "2025-30-07T12:00:00Z",
  "updatedAt": null
}
```
### Example `Paginated TODO` JSON
> `queryparams` are optional. By default `limit == 100`,`page == 1`,`sort == id`,`order == asc`

```json
[
  {
    "todos": [
      {
        "id": 1,
        "todo": "Create README",
        "completed": true,
        "userId": 1,
        "createdAt": "2025-30-07T12:00:00Z",
        "updatedAt": null
      },
      {
        "id": 2,
        "todo": "Implement API endpoints",
        "completed": false,
        "userId": 1,
        "createdAt": "2025-30-07T12:05:00Z",
        "updatedAt": null
      }
    ],
    "totalCount": 22,
    "limit": 10,
    "sort": "id",
    "order": "asc",
    "totalPages": 1,
    "currentPage": 1
  }
]
```
### Example `Create TODO` JSON body

> `completed` and `userId` are optional. Defaults: `completed = false`, `userId = 1`

```json
{
  "todo": "Create README",
  "completed": false,
  "userId": 1
}
```
### Example `Update TODO` JSON body

> `completed` is optional. Default: `completed = false`

```json
{
  "todo": "Update README",
  "completed": true
}
```
### Code example
**URL:** `GET https://your-api-url/todos`

```javascript
fetch('https://your-api-url/todos')
    .then(res => res.json())
    .then(data => console.log(data));
```

**URL:** `GET https://your-api-url/todos?offset=10&limit=20&sort=createdAt&order=desc`

```javascript
fetch('https://your-api-url/todos?offset=10&limit=20&sort=createdAt&order=desc')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `GET https://your-api-url/todos/{id}`

```javascript
fetch('https://your-api-url/todos/{id}')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `POST https://your-api-url/todos`

```javascript
fetch('https://your-api-url/todos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ 
        todo: 'Create README', 
        completed: false 
    })
})
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `PUT https://your-api-url/todos/{todoId}`

```javascript
fetch('https://your-api-url/todos/{todoId}', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ 
        todo: 'Create README', 
        completed: true 
    })
})
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `DELETE https://your-api-url/todos?id=id1,id2,id3...`

```javascript
fetch('https://your-api-url/todos?id=1,2,3', {method: 'DELETE'})
    .then(res => res.json())
    .then(data => console.log(data));
```
---
## QUOTE Items
| Endpoint                                       | Method | Description                          |
|------------------------------------------------|--------|--------------------------------------|
| `/quotes?limit=10&page=1&search=author:albert` | GET    | Fetch list of mocked QUOTE items     |
| `/quotes/random`                               | GET    | Fetch a single random QUOTE item     |
| `/quotes/random/list?limit=5`                  | GET    | Fetch an random array of QUOTE items |
| `/quotes/{id}`                                 | GET    | Fetch a single QUOTE item by ID      |

### Example `QUOTE` JSON

```json
  {
      "id": 1,
      "content": "The only way to do great work is to love what you do.",
      "author": "Steve Jobs"
  }
```

### Example `Paginated QUOTE` JSON
> `queryparams` are optional. By default `limit == 100`,`page == 1`

```json
[
  {
    "quotes": [
      {
        "id": 1,
        "content": "The only way to do great work is to love what you do.",
        "author": "Steve Jobs"
      },
      {
        "id": 2,
        "content": "Success is not the key to happiness. Happiness is the key to success.",
        "author": "Albert Schweitzer"
      },
      {
        "id": 3,
        "content": "In the middle of difficulty lies opportunity.",
        "author": "Albert Einstein"
      }
    ],
    "totalCount": 100,
    "limit": 3,
    "totalPages": 34,
    "currentPage": 1,
    "search": null,
    "searchBy": null
  }
]
```
### Example `Random QUOTE Array` JSON
> `queryparam` is optional. By default `limit == 10`

```json
[
  {
    "id": 68,
    "content": "If you want something you’ve never had, you must be willing to do something you’ve never done.",
    "author": "Thomas Jefferson"
  },
  {
    "id": 86,
    "content": "Life is what happens when you’re busy making other plans.",
    "author": "John Lennon"
  },
  {
    "id": 34,
    "content": "The harder you work for something, the greater you’ll feel when you achieve it.",
    "author": "Anonymous"
  }
]
```
### Code example
**URL:** `GET https://your-api-url/quotes?limit={limit}&page={page}&search={search}`
> You can search by author or content. Use `author:value` or `content:value` in your search query.

```javascript
fetch('https://your-api-url/quotes?limit=10&page=1&search=author:albert')
    .then(res => res.json())
    .then(data => console.log(data));
```

**URL:** `GET https://your-api-url/quotes/random`

```javascript
fetch('https://your-api-url/quotes/random')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `GET https://your-api-url/quotes/random/list?limit={limit}`

```javascript
fetch('https://your-api-url/quotes/random/list?limit=5')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `GET https://your-api-url/quotes/{id}`

```javascript
fetch('https://your-api-url/quotes/5')
    .then(res => res.json())
    .then(data => console.log(data));
```
---
## USER Resources
> User endpoints allow you to generate up to 50 users and make requests. 
> The generated users are not saved in the database; 
> they exist only in memory and are reset every day. 
> You can access them using a unique ID, 
> which is generated by our system and stored in cookies.

| Endpoint                                                | Method | Description              |
|---------------------------------------------------------|--------|--------------------------|
| `/users?count={count}`                                  | GET    | Generate up to 50 users  |
| `/users/{id}`                                           | GET    | Fetch user by id         |
| `/users/{id}`                                           | PUT    | Update user              |
| `/users/{id}/todos`                                     | POST   | Create a new Todo        |
| `/users/{id}/todos/{todoId}`                            | PUT    | Update todo              |
| `/users/{id}/todos?todoId={todoId1,todoId2,todoId3...}` | DELETE | Delete one or more todos |

### Example `USER Array` JSON

```json
[
  {
    "id": 1,
    "firstName": "Steve",
    "lastName": "King",
    "gender": "male",
    "dateOfBirth": "2006-01-14",
    "age": 19,
    "email": "steve.king@example.com",
    "username": "steve.king",
    "password": "steve540",
    "role": "Moderator",
    "todos": [
      {
        "id": 14,
        "todo": "Attend workshop",
        "completed": false,
        "userId": 1,
        "createdAt": "2024-09-10T06:00:00.000+00:00",
        "updatedAt": null
      },
      {
        "id": 65,
        "todo": "Watch tutorial",
        "completed": true,
        "userId": 1,
        "createdAt": "2025-06-05T19:00:00.000+00:00",
        "updatedAt": "2025-06-06T20:00:00.000+00:00"
      }
    ]
  }
]
```

### Example `User Update` JSON body
> You may provide only one or multiple fields to update.
```json
{
  "firstName": "string",
  "lastName": "string",
  "gender": "string",
  "dateOfBirth": "date",
  "email": "string",
  "password": "string",
  "role": "string"
}
```
### Example `Create TODO` JSON body

> `completed` is optional. Default: `completed = false`

```json
{
  "todo": "Create README",
  "completed": false
}
```
### Example `Update TODO` JSON body

```json
{
  "todo": "Update README",
  "completed": true
}
```
### Code example
**URL:** `GET https://your-api-url/users?count={count}`

```javascript
fetch('https://your-api-url/users?count=10')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `GET https://your-api-url/users/{userId}`

```javascript
fetch('https://your-api-url/users/1')
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `PUT https://your-api-url/users/{userId}`

```javascript
fetch('https://your-api-url/users/1', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ 
        firstName: 'John', 
        lastName: 'Doe',
        gender: 'male',
        dateOfBirth: '2006-01-14',
        email: 'steve.king@example.com',
        password: 'steve540',
        role: 'Moderator'
    })
})
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `POST https://your-api-url/users/{userId}/todos`

```javascript
fetch('https://your-api-url/users/1/todos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        todo: 'Create and update todo', 
        completed: false
    })
})
    .then(res => res.json())
    .then(data => console.log(data));
```
**URL:** `PUT https://your-api-url/users/{userId}/todos/{todoId}`

```javascript
fetch('https://your-api-url/users/1/todos/10', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({completed: true})
})
    .then(res => res.json())
    .then(data => console.log(data));
```

**URL:** `DELETE https://your-api-url/users/{userId}/todos?todoId={id1},{id2}`

```javascript
fetch('https://your-api-url/users/1/todos?todoId=14,65', {method: 'DELETE'})
    .then(res => res.json())
    .then(data => console.log(data));
```
---
## Authorization

After generating users, you can authorize them.

To authorize:
- Use the `userId` from your cookies and send it as a `CookieParam`.
- Send the `username` and `password` in the request body.

On successful authorization, you will receive a **Bearer token**.  
Use this token in the `Authorization` header to make authenticated requests:

| Endpoint                                                | Method | Description                   |
|---------------------------------------------------------|--------|-------------------------------|
| `/auth/login`                                           | POST   | send an authorization request |


### Example `Token` JSON

```json
{
  "token": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJwcmVzdG9uLnBhcmtlckBleGFtcGxlLmNvbSIsInVzZXJuYW1lIjoicHJlc3Rvbi5wYXJrZXIiLCJleHBpcmVzSW4iOjE3NTk3MDE1OTkwMDB9.tp3mqyr4GuX16MSyN5c1JjmbCvakkPmHCy5PIJHBv14"
}

```

### Token Structure
> The token is a JWT.
> It expires every day at 23:59:59.
```json
{
  "id": "integer",
  "username": "string",
  "email": "string",
  "expiresIn": "date"
}
```
**URL:** `POST https://your-api-url/auth/login`

```javascript
fetch('https://your-api-url/auth/login', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Cookie': 'userId=1' // Replace with your actual userId from cookies
    },
    body: JSON.stringify({
        username: 'preston.parker',
        password: 'yourPasswordHere'
    })
})
    .then(res => res.json())
    .then(data => console.log('Token:', data))
    .catch(err => console.error('Error:', err));
```