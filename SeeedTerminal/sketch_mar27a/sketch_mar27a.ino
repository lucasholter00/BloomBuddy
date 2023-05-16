/****************************************************************************
  Code base on "MQTT Exmple for SeeedStudio Wio Terminal".
  Author: Salman Faris
  Source: https://www.hackster.io/Salmanfarisvp/mqtt-on-wio-terminal-4ea8f8
*****************************************************************************/

#include <rpcWiFi.h>
#include"TFT_eSPI.h"
#include <PubSubClient.h>
#include "DHT.h" //Include the DHT library
#include <cstring>


// Update these with values suitable for your network.
const char* ssid = "VILMERSLAPTOP"; // WiFi Name
const char* password = "BloomBuddy123";  // WiFi Password
char getThresholdColorTemperature[6] = "green";
char getThresholdColorHumidity[6] = "green";
char getThresholdColorLight[6] = "green";
char getThresholdColorMoisture[6] = "green";

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


TFT_eSPI tft;
int DHTPIN = A0;
DHT dht (DHTPIN, 11);
int moisturePin = A1;
int humidityPin = A0;
int lightPin = A2;
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
  Serial.println("got a message!");
  tft.fillScreen(TFT_BLACK);
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
// process payload and convert it to a string
  char buff_p[length + 1];
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
    buff_p[i] = (char)payload[i];
  }
  Serial.println();
  buff_p[length] = '\0';
  char* message =  buff_p;
  if(std::strcmp(topic,"BloomBuddy/Threshold/Color/Temperature") == 0){
    std::strcpy(getThresholdColorTemperature, message);
    ThresholdIndication();
  }
  if(std::strcmp(topic, "BloomBuddy/Threshold/Color/Humidity") == 0){
    std::strcpy(getThresholdColorHumidity, message);
    ThresholdIndication();
   }
  if(std::strcmp(topic, "BloomBuddy/Threshold/Color/Light") == 0){
    std::strcpy(getThresholdColorLight, message);
    ThresholdIndication();
  }
  if(std::strcmp(topic, "BloomBuddy/Threshold/Color/Moisture") == 0){
    std::strcpy(getThresholdColorMoisture, message);
    ThresholdIndication();
  }
  
// end of conversion
  /***************  Action with topic and messages ***********/
  //setColorAndPrintMessage(message); // not necessary

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
      client.subscribe(TOPIC_sub); // not necessary
      subToThresholdValues();
      
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
  subToThresholdValues();
  client.setServer(server, 1883); // Connect the MQTT Server   hive_mqtt_server
  client.setCallback(callback);
  subToThresholdValues();
  ThresholdIndication();

    pinMode(WIO_MIC, INPUT);
}

void loop() {

  if (!client.connected()) {
    reconnect();
  }
    client.loop();
    publishMoistureValues();
    publishLightValues();
    publishTemperatureValues();
    publishHumidityValues();
    delay(1000);

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
    client.subscribe("BloomBuddy/Threshold/Color/Moisture",0);
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

void ThresholdIndication(){
  // Calculate the height of each screen quadrant
  int quadrantHeight = tft.height() / 4;

  // Clear the display
  //tft.fillScreen(TFT_BLACK);
  // Draw text and fill color for the first quadrant
  tft.setCursor(10, 7);
  tft.setTextColor(TFT_WHITE);
  tft.setTextSize(2);
  tft.setTextWrap(true);
  if(std::strcmp(getThresholdColorTemperature, "green") == 0){
  tft.fillRoundRect(0, 0, tft.width(), quadrantHeight, 5, TFT_GREEN);
  }else{
  tft.fillRoundRect(0, 0, tft.width(), quadrantHeight, 5, TFT_RED);
  }
  tft.println("TEMP");

  // Draw text and fill color for the second quadrant
  tft.setCursor(10, quadrantHeight + 7);
  tft.setTextColor(TFT_WHITE);
  tft.setTextSize(2);
  tft.setTextWrap(true);
  if(std::strcmp(getThresholdColorHumidity, "green") == 0){
    tft.fillRoundRect(0, quadrantHeight, tft.width(), quadrantHeight, 5, TFT_GREEN);
  }else{
    tft.fillRoundRect(0, quadrantHeight, tft.width(), quadrantHeight, 5, TFT_RED);
  }
  tft.println("HUMIDITY");

  // Draw text and fill color for the third quadrant
  tft.setCursor(10, 2 * quadrantHeight + 7);
  tft.setTextColor(TFT_WHITE);
  tft.setTextSize(2);
  tft.setTextWrap(true);
  if(std::strcmp(getThresholdColorMoisture, "green") == 0){
    tft.fillRoundRect(0, 2 * quadrantHeight, tft.width(), quadrantHeight, 5, TFT_GREEN);
  }else{
    tft.fillRoundRect(0, 2 * quadrantHeight, tft.width(), quadrantHeight, 5, TFT_RED);
  }
  tft.println("MOISTURE");

  // Draw text and fill color for the fourth quadrant
  tft.setCursor(10, 3 * quadrantHeight + 7);
  tft.setTextColor(TFT_WHITE);
  tft.setTextSize(2);
  tft.setTextWrap(true);
  if(std::strcmp(getThresholdColorLight, "green") == 0){
    tft.fillRoundRect(0, 3 * quadrantHeight, tft.width(), quadrantHeight, 5, TFT_GREEN);
  }else{
    tft.fillRoundRect(0, 3 * quadrantHeight, tft.width(), quadrantHeight, 5, TFT_RED);
  }
  tft.println("LIGHT");
}
void subToThresholdValues(){
  client.subscribe("BloomBuddy/Threshold/Color/Temperature",0);
  client.subscribe("BloomBuddy/Threshold/Color/Humidity",0);
  client.subscribe("BloomBuddy/Threshold/Color/Light",0);
  client.subscribe("BloomBuddy/Threshold/Color/Moisture",0);
  Serial.print("Subcribed to: ");
  Serial.println("BloomBuddy/Threshold/Color/Temperature");
  Serial.println("BloomBuddy/Threshold/Color/Humidity");
  Serial.println("BloomBuddy/Threshold/Color/Light");
  Serial.println("BloomBuddy/Threshold/Color/Moisture");
}

