/*
--------------------------------------------------------------------------------
-------------------------------------------------------------------------------
NAME                  DATE           WHO             VERSION
FWWeatherData.scala   02/07/2016     VinayKumar      Intial Draft

Description:-
The following program reads the weather data from a FIXEDWIDTH file and emits the data
in the below given format:
IATA STATAION NAME|LATITUDE,LONGITUDE,ELEVATION|DATETIME|WEATHERCONDITION|TEMPERATURE|PRESSURE|HUMIDITY
Notes: IATA Station name will be in 3 letters, Temperature in Celcius and Pressure in HPA and Humidity in Percentage
       For information regarding the file format please refer to NATIONAL CLIMATIC DATA CENTRE 


Input Parameters:-
    1. PARAMETER 1 : Input Directory whic contains files to be processed
    2. PARAMETER 2 : OUTPUT file name along with the path
    3. PARAMETER 3 : MASTER_LOCATION reference file with path
    4. PARAMETER 4 : WEATHER_CONDITION.txt reference file with path


Functions:-
This program uses to functions defined in standalone OBJECT named LOOKUP
   Function 1: conditionlkp :- This function takes weather condition code STRING and weather condition reference file as input                             parameters and emits the type of weather condition
                               Example:- RAINY, SUNNY etc... PLease refer file Weather_Condition.txt file
   Function 2: stationlkp   :- This function takes STATION NUMBER and Master Loaction reference file as input parameters and emits its                      IATA and GEOCODE Informtion. Please refer file Master_Location.txt file for more information

Run Command : scala FWWeatherData /home/cloudera/Desktop/CBAPROJECT/DATASETS /home/cloudera/Desktop/CBAPROJECT/OUTPUT/WEATHER_FORMATTED.txt /home/cloudera/Desktop/CBAPROJECT/REFFILES/Master_Location.txt /home/cloudera/Desktop/CBAPROJECT/REFFILES/Weather_Conditions.txt
----------------------------------------------------------------------------
---------------------------------------------------------------------------
*/  



//Importing all required libraries
import java.io._
import Lookup._

object FWWeatherData {
def main(args: Array[String]) {
// Checking wheather required parameters are passed
 if (args.length < 1) {
      System.err.println("Invalid number of parameters, Required 4: 1. I/P DIR 2. OUTPUT file 3 and 4 for Reference Files")
      System.exit(1)
    }
try
{
  val files = new java.io.File(args(0)).listFiles.filter(_.getName.endsWith(".txt"))
  val writer = new FileWriter(args(1), true)

  for ( file <- files) {

  val lines = scala.io.Source.fromFile(file).getLines()


    //FOR LOOP for extracting and formating each line form the above String Iterator
    for (line <- lines)  {

    // Getting Station Number from weather file
    val station = line.substring(4,10)

    //Extracting and Formating DATE into desired format
    val date = Array(line.substring(15,19),line.substring(19,21),line.substring(21,23)).mkString("-")


    //Extracting and Formating TIME into desired format
    val time = Array(line.substring(23,25),line.substring(25,27),"00").mkString(":")


    //Concatinating DATE and TIME
    val datetime = date + "T" + time +"Z"


    //Extracting ,Formating and Validating Temparature
    val temp = line.substring(88,92).toDouble
    val temperature = line.substring(87,88) + (if (temp >= 99) 0 else temp)/10

    //Extracting and Validating Pressure
    val pre = line.substring(99,104).toInt
    val pressure = if (pre == 99999) 0 else pre

    //Extracting and Validating Humidity
    val hum = line.substring(61,63).toInt
    val humidity = if (hum >= 99) 0 else hum

    //LOOKUP for weather Conditions from CONDITIONLKP function
    val condition = conditionlkp(line.substring(63,64), args(3))

    // Extracing Station Name and GECODE information using STATIONLKP function
    val stat = stationlkp(station, args(2))
    val geocode = Array(stat(1),stat(2),stat(3)).mkString(",")

    //Printing all data elements to a FILE 
    writer.write(Array(stat(0),geocode,datetime,condition,temperature,pressure,humidity).mkString("|"))
    writer.append("\n")
      }
   }
 writer.close()
} catch {
  case e: Exception => println("exception caught: " + e)
}
}
}