# Deployment API


## Prerequisites
* Java 8
* Maven 3


## How does it work?
This deployment API is served as an executable JAR (Java ARchive).

It should be executed in command line to deploy a build on a specific environment, like this:
```shell
java -jar deploy-api-0.0.1.jar {env} {build} [{number}]
```

This API should be called by the continuous integration (CI) server during specific *deploy* jobs (there should be one job per environment).


The program expects the following arguments:
* `{env}` *(required)*: Target environment on which the build is deployed.
* `{build}` *(required)*: Existing build name.
* `{number}` *(optional)*: Existing build number. If not provided, the latest build is deployed.


## Environments properties
The program relies on a configuration file named `application.yml`.

### Embedded test configuration
A default file is provided within the application resources (see `src/main/resources`) in order to proceed tests. 

**This should never contain operational password!**

### Production configuration
In production, this embedded file can be overridden. To do so, simply put an `application.yml` file next to the JAR archive.

This file will be loaded from the execution classpath, so be careful to run your JAR from current directory:
```shell
# WRONG:
~~java -jar folder/deploy-api.jar env build~~

# CORRECT:
cd folder
java -jar deploy-api.jar env build 
```


## Test the program locally
To execute the deploy API locally in order to test it, simply execute the following command on your project home directory:
```shell
mvn spring-boot:run
```
Note that this *local run* is provided with with default arguments set in the `pom.xml` (see `build` section).

You can override them in command line:
```shell
mvn spring-boot:run -Drun.arguments="arg1,arg2"
```


## TODO
- Complete deploy execution implementation.
- Rename `application.yml` to `deploy-api-conf.yml`.