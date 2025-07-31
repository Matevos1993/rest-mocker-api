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
### Example `PaginatedTODO` JSON

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
### Example `CreateTODO` JSON body

> `completed` and `userId` are optional. Defaults: `completed = false`, `userId = 1`

```json
{
  "todo": "Create README",
  "completed": false,
  "userId": 1
}
```
### Example `UpdateTODO` JSON body

> `completed` and `userId` are optional. Defaults: `completed = false`, `userId = 1`

```json
{
  "todo": "Update README",
  "completed": true
}
```