# Deployment API

## Prerequisites
* Java 8
* Maven 3


## How does it work?
This deployment API is served as an executable JAR (Java ARchive).

It should be executed in command line to deploy a build on a specific environment, like this:
```
java -jar deploy-api-0.0.1.jar {env} {build} [{number}]
```

This API should be called by the continuous integration (CI) server during specific *deploy* jobs (there should be one job per environment).


The program expects the following arguments:
* `{env}` *(required)*: Target environment on which the build is deployed.
* `{build}` *(required)*: Existing build name.
* `{number}` *(optional)*: Existing build number. If not provided, the latest build is deployed.


## Environments properties
The program relies on a configuration file named `application.yml`. This file contains environments and repository manager API properties.  
It is a [YAML](http://yaml.org/) document.

### Test (embedded) configuration
A default file is provided within the application resources (see `src/main/resources`) in order to run development tests.  
**This file should never be commited with actual operational password!**

### Production (external) configuration
In production, the default embedded file can be overridden. To do so, simply put an `application.yml` file next to the JAR archive:
```
/production/folder
    deploy-api-0.0.1.jar
    application.yml
```

It allows operational teams to provide the sensitive passwords in a secure way.  
Note that they are other ways to provided theses properties, 
read documentation about [spring-boot's external configuration] (http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
for more information.

### Warning: execution classpath
This file will be loaded from the *execution* classpath, so be careful from where you run your JAR.

Suppose you have the following tree structure:
```
/folder
    deploy-api.jar
    application.yml
```

The following command line **won't** load the configuration file (KO):
```bash
# Execution classpath is '/'
java -jar /folder/deploy-api.jar env build
```

The following command line **will** load the configuration file (OK):
```bash
# Execution classpath is '/folder/'
cd /folder
java -jar deploy-api.jar env build
```


## Run the program without packaging
To execute the deploy API without packaging the JAR archive (e.g. from IDE), simply execute the following command on your project home directory:
```
mvn spring-boot:run
```
Note that this *on-the-fly* run is provided with with default arguments set in the `pom.xml` (see `build` section).  
You can override them in command line:
```
mvn spring-boot:run -Drun.arguments="arg1,arg2"
```


## TODO
- Complete deploy execution implementation.
- Rename `application.yml` to `deploy-api-conf.yml`.