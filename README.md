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

## 📦 API Overview

The API currently supports the following mock resource types:
> ⚠️ **Warning:**  
> Rest Mocker API simulates REST requests and responses. Default data is loaded from the database, while user changes are stored only in memory and are reset after 30 minutes. No actual data is permanently saved, updated, or deleted. All changes are ephemeral and exist only for the duration of the retention period.

### HTTP Response

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
---

### TODO Items

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

> `completed` and `userId` are optional. Defaults: `completed = false`, `userId = 1`

```json
{
  "todo": "Update README",
  "completed": true
}
```
---
### QUOTE Items
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