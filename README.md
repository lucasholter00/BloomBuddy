BloomBuddy

## Description
BloomBuddy serves as your own plant guardian, providing diverse monitoring solutions for your green companions. It offers real time tracking and display of crucial environmental factors, including temperature, humidity, light levels and soil moisture. With BloomBuddy, maintaining optimal growing conditions for your plants becomes easier and more precise, contributing to their health and vitality.

## Software and hardware architecture
![Component structure diagram](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/uploads/b7beed9cabeb0ae21395a0c9a4dfd3d8/Projekt_diagram_-_Page_1.png)
![Class diagram](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/uploads/f19efa6c8c237b04e288f7d02df5e5b3/bloombuddy.png)

## Video demo
This link will lead to a oneDrive video:
[Video Demo](https://gunet-my.sharepoint.com/:v:/g/personal/guscornol_student_gu_se/Ef7wUHXtSWlGtFdcnH3O_KcBorAMB6A0i6HYVr6Ug_5KYA?e=xh6pG6)

## How to run BloomBuddy with Maven
#### Terminal:
1. Navigate to the git repository.
2. Make sure you are in the directory containing the pom.xml file.
3. Run `mvn clean package assembly:assembly`
4. Run `java -jar target/BloomBuddy-1.0-SNAPSHOT-jar-with-dependencies.jar`

#### IntelliJ:
1. If the project files doesn't show up in your explorer, click on the "Project" drop down menu at the very top. Choose project files, then navigate to pom.xml. Right click the pom file and click add as maven project.
2. Hover over "Run" on the top bar and click edit configurations.
3. Press the + button in the top left and click Maven.
4. In the "Command line" text box, add clean package assembly:assembly. Make sure the "Working directory" text box is the path to where the pom.xml is.
5. Press okay in the bottom right. Right click BloomBuddy at the top of the explorer and press Copy path/reference and then absolute path.
6. Go back into run -> edit configurations. Click the + at the top right and press JAR application, then paste the project path and add /BloomBuddy/target/BloomBuddy-1.0-SNAPSHOT-jar-with-dependencies.jar at the end.
7. Scroll down to "Before launch" and press the + button.
8. Click "run another configuration" and choose the maven configuration you just created.
9. Press OK and choose the JAR application you just created in the drop down menu next to the run button.

After this the project will package and run every time you press the run button.


## Wio seeed terminal setup
1. Download the Arduino IDE.
2. Set up the [wio seeed terminal.](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/)
3. Download the following libraries:
- "Seeed Arduino rpcWiFi"
- "TFT_eSPI"
- "PubSubClient"
- "DHT sensor library"
4. Open sketch_mar27a.ino, and then click upload sketch with the wio seeed terminal selected.

## Usage
BloomBuddy is designed to assist you in cultivating plants and crops by providing crucial environmental monitoring. For instance, if you are nurturing a Monstera, which requires a temperature range of 18-22 degrees Celsius, a humidity level between 60-80%, a soil moisture level of 100, and a good amount of light everyday, BloomBuddy is your go to aid. It tracks these conditions, enabling you to maintain an ideal environment for your Monstrea’s growth.

## Support
For any issues contact:
- gushumfe@student.gu.se
- gushedivi@student.gu.se
- gusnygra@student.gu.se
- guscornol@student.gu.se
- guscarlslu@student.gu.se

## Authors and acknowledgment
- Felix Humleby
- Vilmer Hedin
- Cornelia Olofsson Larsson
- Lucas Carlsson Holter
- Rasmus Nygren

## Main contributions per team member
- Felix Humleby: Threshold tracking on wio seeed terminal, initialize temperature sensor, PMR, sprint deliveries, home page tip generation, use case diagram.
- Vilmer Hedin: Context diagram, Initialize light intensity sensor, threshold logic, The ability to change and save thresholds through the application, plantEditingScene, Display set thresholds in LineCharts, User manual.
- Cornelia Olofsson Larsson: CI implementation, Database design, Implement MyPlants scene, Extend HomeScene scene, Implement water tracking(partly out of scope), Functionallity around historical data(backend), Arrange weekly meetings, video DEMO 
- Lucas Carlsson Holter: Class diagram, Component diagram, Connectivity between application and wio seed terminal, Monitor moisture, Contributions to readme, Account creation functionallity(backend), Merge request and issue template, Plant profile functionallity(backend), Adding observers to existing classes, Current user object, CI implementation.
- Rasmus Nygren: Figma mockup of UI, basic scene structure for all scenes, whole scenes with controllers (accountCreation, loginScene, plantAddingScene), sceneSwitch structure, LineChartDataType enum class.

## Project status
Our plant monitoring app holds potential for the future, more specifically within the area of fully automated systems. Our system offers a solution to the increasing demand in sustainable and efficient farming/gardening practices. By constantly providing vital parameters of the plant and the option to set thresholds, we encourage further development of our application and integration to reach the potential of a fully automated greenhouse or other system.

## Get started for developers
- [Project Structure](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/Project-structure)
- [Use cases](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/Use-cases)
- [Use case diagram](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/Use-case-diagrams)
- [Database design](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/Database-design)
- [Requirements](https://git.chalmers.se/courses/dit113/2023/group-18/bloombuddy/-/wikis/Requirements,-team-contract-and-team-communication)

## User manual

### 1. Log in
#### 1.1 Create account
1. Press sign up now.
2. Enter your preferred username and password.
3. Press create account.

#### 1.2 Log in
1. Fill in the username and password text fields with the correct details.
2. Press log in.

### 2. Home
#### 2.1 Season tips
1. To receive suggestions on suitable crops to plant in the current season, press the “Generate new tip” button.
2. Each crop displayed in the window comes with information about its nutrient content.

#### 2.2 Set as active
1. To select a plant for monitoring and viewing its statistics, click on the “Activate” button located below the plant's name.

### 3. My plants
#### 3.1 Add a new plant
1. Press the big green “+” button in the bottom right corner.
2. Enter your plant's name in the upper most text field.
3. Enter your plant's optimal environmental conditions.
4. Press “Save Settings” and wait for the confirmation of your profile successfully being created.

#### 3.2 Edit existing plants
1. Press the “Edit” button on the plant you want to edit.
2. Enter your plant's new optimal environmental conditions.
3. Press “Save Settings” and wait for the confirmation of your settings being successfully saved.

#### 3.3 Watering
1. Press the “Water” button below the plant's name to notify the system that you have watered your plant.

#### 3.4 Set as active
1. To select a plant for monitoring and viewing its statistics, click on the “Activate” button located above the “Edit” button.

### 4. Statistics
#### 4.1 Line charts
1. The first chart “Temperature” contains three data series. The red data series represents the temperature, providing a trend of measurements over time. The two black data series represent threshold values – these are fixed limits that help you understand when the sensor data crosses a significant point.
2. The second chart “Light Level” contains three data series. The red data series represents the light intensity, providing a trend of measurements over time. The two black data series represent threshold values – these are fixed limits that help you understand when the sensor data crosses a significant point.
3. The third chart “Moisture” contains three data series. The red data series represents the temperature, providing a trend of measurements over time. The two black data series represent threshold values – these are fixed limits that help you understand when the sensor data crosses a significant point.
4. The fourth chart “Humidity” contains three data series. The red data series represents the air humidity, providing a trend of measurements over time. The two black data series represent threshold values – these are fixed limits that help you understand when the sensor data crosses a significant point.

#### 5. Log-out
1. To log out of your account, locate and press the “Log-out” button. This button can be found in the bottom left corner of almost every screen within the application.

