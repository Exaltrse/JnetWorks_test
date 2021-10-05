# Page Number Reducer
#### _Test task for JNetworks_

## Task description
Application reduces unsorted list of page numbers to compact form compatible for using in print dialog.
Using Java 8, Maven, JUnit, JAX-RS API.

## Application server requirements
For running this application you must have installed JDK 8 and Wildfly server at least 20.0.1.Final version.

## Deploying application
1. Download the project`s file into your computer.
2. Open downloaded project in your prefered IDE.
3. Run "install" phase of Maven lifecycle.
4. Take the archive with package of application JnetWorksTestTask-1.0.001.war from {$Project`s directory$}\JnetWorks_test\JnetWorksTestTask\target
5. Run Wildfly in the standalone mode (_by starting {$Wildfly_directory$}\bin\standalone.sh_).
6. Deploy JnetWorksTestTask-1.0.001.war at the Wildfly application server:
    1. If Wildfly configured for auto-deployment - simply put file in {$Wildfly_directory$}\standalone\deployments
    2. Or you can use graphical UI of Wildfly at _http://localhost:8080/_.
