# Deployment API.


## Prerequisites
* Java 8
* Maven 3


## REST API
| Path                               | Description |
|:-----------------------------------|:------------|
| `/build-info/{build}/{number}`     | TODO |
| `/artifact-info/{build}/{number}`  | TODO |
| `/deploy/{env}/{build}`            | TODO |
| `/deploy/{env}/{build}/{number}`   | TODO |


## Run the application
Execute the following command on your project home directory:
```
mvn spring-boot:run
```

And access the following URL:
```
http://localhost:8080
```