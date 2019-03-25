# bowling-scorer
A spring boot application for bowling score calculation with basic UI

Pre-requisites:
Java (min version 8) must be installed and included on the path.

Steps to run:

There are multiple ways to run this application.
As this application uses maven you can first build the application by running following command in the root directory:
* **mvn clean package**

After that to run the application you can use command:
* **java -jar target/bowling-scorer-0.0.1-SNAPSHOT.jar**

Or it can be run directky using maven spring boot plugin command in the root directory:
* **mvn spring-boot:run**

You can also directly run the main method from class **BowlingApplication.java** from inside an IDE.

Usage:
After running the application using one of the methods above please open **http://localhost:8080/scorer/home**.
You can use the UI to create and score a bowling game.

The only special thing worth noting is that you need to input -1 for a foul.