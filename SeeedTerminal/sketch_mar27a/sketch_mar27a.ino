/****************************************************************************
  Code base on "MQTT Exmple for SeeedStudio Wio Terminal".
  Author: Salman Faris
  Source: https://www.hackster.io/Salmanfarisvp/mqtt-on-wio-terminal-4ea8f8
*****************************************************************************/

#include <rpcWiFi.h>
#include"TFT_eSPI.h"
#include <PubSubClient.h>
#include "DHT.h" //Include the DHT library


// Update these with values suitable for your network.
const char* ssid = "TP-Link_7460"; // WiFi Name
const char* password = "13401115";  // WiFi Password


/**********  HOW TO FIND YOUR MOSQUITTO BROKER ADDRESS*******************
  In Windows command prompt, use the command:   ipconfig
  Copy the Ip address of "Wireless LAN adapter Wi-Fi: IPv4 Address"
  Enter the IP in the sever variable below.
*************************************************************************/

const char* server = "broker.hivemq.com";  // MQTT Broker URL

/* TODO
    add the corresponding topics
*/ 
const char* TOPIC_sub = "hej";
const char* TOPIC_pub_connection = "klonk";

bool displayPopup = false;
bool popupPainted = false;


TFT_eSPI tft;
int DHTPIN = A0;
DHT dht (DHTPIN, 11);
int moisturePin = A0;
int humidityPin = A0;
int lightPin = A0;
int temperaturePin = A0;

WiFiClient wioClient;
PubSubClient client(wioClient);
long lastMsg = 0;
char msg[50];
int value = 0;


void setup_wifi() {

  delay(10);

  tft.setTextSize(2);
  tft.setCursor((320 - tft.textWidth("Connecting to Wi-Fi..")) / 2, 120);
  tft.print("Connecting to Wi-Fi..");

  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password); // Connecting WiFi



  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");

  tft.fillScreen(TFT_BLACK);
  tft.setCursor((320 - tft.textWidth("Connected!")) / 2, 120);
  tft.print("Connected!");

  Serial.println("IP address: ");
  Serial.println(WiFi.localIP()); // Display Local IP Address
}


String getPayload(){

}

void callback(char* topic, byte* payload, unsigned int length) {
  tft.fillScreen(TFT_BLACK);
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
// process payload and convert it to a string
  char buff_p[length];
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    buff_p[i] = (char)payload[i];
  }
  Serial.println();
  buff_p[length] = '\0';
  String message = String(buff_p);
// end of conversion
  /***************  Action with topic and messages ***********/
  setColorAndPrintMessage(message);
  //we want to add a notification

  if(strcmp(topic, "BloomBuddy/watering") && message == "notification"){
    displayPopup = true;
  }

}

void showNotification(){
    tft.fillScreen(tft_RED);

  // Set the text color to white
  tft.setTextColor(tft_WHITE);

  // Set the text size and position
  tft.setTextSize(2);
  tft.setCursor(10, 20);

  // Display the message
  tft.println("Watering is needed! :)");

}

void removeNotification(){
  // Set the displayPopup flag to false
  displayPopup = false;
}

void setColorAndPrintMessage(String message) {
  int bgColor;    // declare a backgroundColor
  int textColor = TFT_WHITE;    // initializee the text color to white
  String displayText = "Received message:";

  // Set background color based on input string
  /* TODO
     implement the color changes of the display depending on the command received
     Use: https://wiki.seeedstudio.com/Wio-Terminal-LCD-Basic
  */
  
  // Update TFT display and print input message
  tft.fillScreen(bgColor);    // Fill the screen with the background color
   tft.setTextColor(textColor, bgColor);    // set the text and background color                   
  tft.setTextSize(2);                       // set the size of the text
  tft.setCursor((320 - tft.textWidth(displayText)) / 2, 90);    // Make sure to align the text to the center of the screen
  tft.println(displayText);     // print the text
  tft.setCursor((320 - tft.textWidth(message)) / 2, 120);         
  tft.println(message);

}

void reconnect() {
  // Loop until we're reconnected
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    // Create a random client ID
    String clientId = "WioTerminal";
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");
      // Once connected, publish an announcement...
      client.publish(TOPIC_pub_connection, "hello world");
      Serial.println("Published connection message ");
      // ... and resubscribe
      client.subscribe(TOPIC_sub);
      Serial.print("Subcribed to: ");
      Serial.println(TOPIC_sub);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Wait 5 seconds before retrying
      delay(5000);
    }
  }
}

void setup() {

  tft.begin();
  tft.fillScreen(TFT_BLACK);
  tft.setRotation(3);
  dht.begin();

  Serial.println();
  Serial.begin(115200);
  setup_wifi();
  client.setServer(server, 1883); // Connect the MQTT Server   hive_mqtt_server
  client.setCallback(callback);

    pinMode(WIO_MIC, INPUT);
    pinMode(WIO_KEY_A, INPUT_PULLUP)
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }

    //publishMoistureValues();
    //publishLightValues();
    publishTemperatureValues();
    publishHumidityValues();

    delay(1000);

    if(displayPopup && !popupPainted){
        showNotification();
        popupPainted=true;
    }
    elseif(!displayPopup){
    tft.fillScreen(tft_BLACK)
    }
   if(digitalRead(WIO_KEY_A)==LOW){
    removeNotification();
    popupPainted=false;
    }

/*   Use to continuously execute something in the loop.
     sensor data?! Hint hint  ヾ(o✪‿✪o)ｼ 
  
  long now = millis();
  if (now - lastMsg > 2000) {
    lastMsg = now;
​
  }
*/
}

void publishMicValues(){
  char buffer[16];
  int val = analogRead(WIO_MIC);
  itoa(val, buffer, 10);
  client.publish("hej", buffer);

}

void publishMoistureValues(){
  char buffer[16];
  int val = analogRead(moisturePin);
  itoa(val, buffer, 10);
  client.publish("BloomBuddy/Moisture/raw", buffer);
}

void publishHumidityValues(){
 float h = dht.readHumidity();
 Serial.println(h);
 //if (!isnan(h)) { //Checks whether the readings are valid floating-point numbers.
 char buffer[40];
 itoa(h, buffer, 10);
 client.publish("BloomBuddy/Humidity/raw", buffer);
//  }
}
void publishTemperatureValues(){
float t = dht.readTemperature();
Serial.println(t);
if(!isnan(t)) {   //Checks whether the readings are valid floating-point numbers.
char buffer[40];
itoa(t, buffer, 10);
client.publish("BloomBuddy/Temperature/raw", buffer);
}
}

void publishLightValues(){
  int valLight = analogRead(lightPin);
  char msg[8];
  snprintf(msg, 8, "%d", valLight);
  client.publish("BloomBuddy/Light/raw", msg);
}
