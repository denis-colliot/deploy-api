# Deployment API.


## Prerequisites
* Java 8
* Maven 3


## REST API
| Path                               | Method | Description |
|:-----------------------------------|:-------|:------------|
| `/build-info/{build}/{number}`     | GET    | Retrieves the information on a specific build.             |
| `/artifact-info/{build}/{number}`  | GET    | Retrieves the information on a specific build artifact.    |
| `/deploy/{env}/{build}`            | PUT    | Deploys the latest build version on the given environment. |
| `/deploy/{env}/{build}/{number}`   | PUT    | Deploys the specific build number on the given environment |


## Run the application
Execute the following command on your project home directory:
```
mvn spring-boot:run
```

Access the web interface with by opening the following URL in your browser:
```
http://localhost:8080
```

Or use REST API methods with following example command lines:
```
curl -X GET localhost:8080/rest/artifact-info/observatory-build-front/154
curl -X PUT localhost:8080/rest/deploy/integration/observatory-build-front/154
```