# Contact API

Contact API is a simple API, where a user can get a quick overview over all contacts resources
like person, skills...
Implemented with API-First Approach

## Technological stack

- OpenJDK 14
- Gradle 6.5
- Spring Boot 2.3.0
- Spring MVC
- Spring Data JPA
- Spring Security
- OpenAPI Generator
- Swagger
- Lombok
- MapStruct 1.3.1
- Spock 1.3 for testing
- H2 as embedded Database
- Docker
- Docker Compose
- Keycloak as an authorization server

## Installation
Add "authorizationserver" entry in your hosts file
```bash
echo "127.0.0.1       authorizationserver" >> /etc/hosts
```
Start the Contact API and their dependencies
```bash
docker-compose up
```

## Usage

### Swagger URL
http://localhost:8081

### Authentication
Get an temporary token from the authentication server and store it in an environment variable TOKEN

```bash
 export TOKEN=`curl -ss --data "grant_type=password&client_id=contacts-api-client&username=user1&password=user1" http://authorizationserver:8080/auth/realms/ContactsApiRealm/protocol/openid-connect/token | jq -r .access_token`
```

Use the TOKEN when requesting the API in swagger UI or using command lines
```bash
curl -X GET "http://localhost:8081/contacts?limit=20&page=0" -H "accept: application/hal+json" -H "Authorization: Bearer $TOKEN"
```

## To be done
- HAL implementation (Hypertext Application Language)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
