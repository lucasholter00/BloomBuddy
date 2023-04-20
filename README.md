# BloomBuddy


## Description

BloomBuddy is a plant monitroing product that will be able to help you keep an extra eye on your plants. BloomBuddy will be able to monitor and display temperature, humidity, light and moisture. All this to help you keep the perfect conditions for your plant.

## Usage
Bloombuddy is intended to help you grow plants. 
If you have a Monstera that you want to grow and this Monstera needs a temperature between 18-22 degres celsius, a humidity between 60-80%, it needs at least 6 hours indirect light per day and a soil moisture level of 4. The BloomBuddy will then help you monitor these values so that you can keep the perfect enviorment for your Monstera.

## How to run BloomBuddy with Maven

### Terminal
1. Navigate to the git repository.
2. Make sure you are in the directory containing the pom.xml file.
3. Run `mvn clean package assembly:assembly`
4. Run `java -jar target/BloomBuddy-1.0-SNAPSHOT-jar-with-dependencies.jar`

### IntelliJ
1. If the project files doesn't show up in your explorer, click on the "Project" drop down menu at the very top.
Choose project files, then navigate to pom.xml. Right click the pom file and click add as maven project.
2. Hover over "Run" on the top bar and click edit configurations.
3. Press the + button in the top left and click Maven.
4. In the "Command line" text box, add `clean package assembly:assembly`. Make sure the "Working directory" text box is the path to where the pom.xml is.
5. Press okay in the bottom right. Right click BloomBuddy at the top of the explorer and press Copy path/reference and then absolute path.
6. Go back into run -> edit configurations. Click the + at the top right and press JAR application, then paste the project path and add /BloomBuddy/target/BloomBuddy-1.0-SNAPSHOT-jar-with-dependencies.jar at the end.
7. Scroll down to "Before launch" and press the + button.
8. Click "run another configuration" and choose the maven configuration you just created.
9. Press OK and choose the JAR application you just created in the drop down menu next to the run button.

After this the project will package and run every time you press the run button.

## Support
For any issues contact: 
gushumfe@student.gu.se

## Authors and acknowledgment
- Felix Humleby
- Vilmer Hedin 
- Cornelia Olofsson Larsson 
- Lucas Carlsson Holter 
- Rasmus Nygren
- Erik Nisbet 
