# Deployment API.


## Prerequisites
* Java 8
* Maven 3


## How does it work?
This deployment API is served as an executable JAR (Java ARchive).

It should be executed in command line to deploy a build on a specific environment, like this:
```
java -jar deploy-api-0.0.1.jar {env} {build} [{number}]
```

In continuous integration (CI), this API should be called by Jenkins during a specific deploy job (there should be one job per environment).


For now, the program expects the following arguments:
* `{env}` *(required)*: Target environment on which the build is deployed.
* `{build}` *(required)*: Existing build name.
* `{number}` *(optional)*: Existing build number. If not provided, the latest build is deployed.


## Test the program locally
To execute the deploy API locally in order to test it, simply execute the following command on your project home directory:
```
mvn spring-boot:run
```
Note that this *local run* is provided with with default arguments set in the `pom.xml` (see `build` section).

You can override them in command line:
```
mvn spring-boot:run -Drun.arguments="arg1,arg2"
```