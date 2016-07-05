/*
--------------------------------------------------------------------------------
-------------------------------------------------------------------------------
NAME                DATE           WHO             VERSION
OBWeather.scala     02/07/2016     VinayKumar      Intial Draft

Description:-
The following program reads the weather data from a FIXEDWIDTH file and emits the data
in the below given format:
IATA STATAION NAME|LATITUDE,LONGITUDE,ELEVATION|DATETIME|WEATHERCONDITION|TEMPERATURE|PRESSURE|HUMIDITY

It has two components
       1. Companion Class   OBWeather - This has all members defined to process the suplied Weather Data file
       2. Companion Object  OBWeather - This calls all the functions from the COMPANION CLASS to process the 
                                        suplied Weather Data file

Input Parameters:
      PARAMETER 1: Directory containing files which are to be processed
      PARAMETER 2: OUTPUT file name along with the PATH
      PARAMETER 3: MASTER LOCATIONS file along with the path
      PARAMETER 4: WEATHER CONDITIONS file along with the path
 
Run Command: scala OBWeather /home/cloudera/Desktop/CBAPROJECT/DATASETS/ /home/cloudera/Desktop/CBAPROJECT/OUTPUT/WEATHER_FORMATTED.txt /home/cloudera/Desktop/CBAPROJECT/REFFILES/Master_Location.txt /home/cloudera/Desktop/CBAPROJECT/REFFILES/Weather_Conditions.txt
Notes: IATA Station name will be in 3 letters, Temperature in Celcius and Pressure in HPA and Humidity in Percentage
       For information regarding the file format please refer to NATIONAL CLIMATIC DATA CENTRE
----------------------------------------------------------------------------
---------------------------------------------------------------------------
*/

// Including required Libraries 
import java.io._

/* Following Companion Class needs following parameters:

Input Parameters:
      PARAMETER 1: FILE along with the path to be processed
      PARAMETER 2: OUTPUT file name along with the PATH
      PARAMETER 3: MASTER LOCATIONS file along with the path
      PARAMETER 4: WEATHER CONDITIONS file along with the path

It has following functions:

     1. getStationGeocode() - This function will return the IATA station name along with its geocodes
     2. getDate()           - This function will extract the DATE from each record of the file
     3. getTime()          - This function will extract TIME from each record of the file
     4. getTemparature()    - This function will extract TEMPARATURE from each record of the file
     5. getPressure()       - This function will extract PRESSURE from each record of the file
     6. getHumidity()       - This function will extract HUMIDITY from each record of the file
     7. getWethCon()        - This function will get the Weather Condition for each record of the file
*/


class OBWeather(input: File, output: String, masterref: String, wethcon: String) {

val lines = scala.io.Source.fromFile(input).getLines()

val writer = new FileWriter(output, true)

def getStationGeocode(arrline: String, sindx: Int, eindx: Int): Array[String] ={
val statlkp = scala.io.Source.fromFile(masterref).getLines().map(_.split(",")).map(arr => arr.head -> arr.tail).toMap
statlkp.apply(arrline.substring(sindx,eindx))
    }


def getDate(arrline: String, s1indx: Int, e1indx: Int, s2indx: Int, e2indx: Int): String = {
Array(arrline.substring(s1indx, e1indx),arrline.substring(e1indx,s2indx),arrline.substring(s2indx,e2indx)).mkString("-")
    }

def getTime(arrline: String, s1indx: Int, e1indx: Int, s2indx: Int): String = {
Array(arrline.substring(s1indx, e1indx),arrline.substring(e1indx,s2indx),"00").mkString(":")
    }


def getTemparature(arrline: String, sindx: Int, eindx: Int, s2indx: Int): String = {
val temp = arrline.substring(eindx,s2indx).toDouble
arrline.substring(sindx,eindx) + (if (temp >= 99) 0 else temp)/10
    }


def getPressure(arrline: String, sindx: Int, eindx: Int): Int = {
val pre = arrline.substring(sindx,eindx).toInt
if (pre == 99999) 0 else pre
   }

def getHumidity(arrline: String, sindx: Int, eindx: Int): Int = {
val hum = arrline.substring(sindx, eindx).toInt
if (hum >= 99) 0 else hum
   }

def getWethCondition(arrline: String, sindx: Int, eindx: Int): String  = {
val conlkp = scala.io.Source.fromFile(wethcon).getLines().map(_.split(",")).map( arr => arr(0) -> arr(1)).toMap
conlkp.apply(arrline.substring(sindx,eindx))
   }
}



/* Following companion Object call required functions defined in above class file to process the 
Weather Data file*/

object OBWeather {

def main(args: Array[String]) {

// Checking wheather required parameters are passed
 if (args.length < 1) {
      System.err.println("Invalid number of arguments : 4 arguments required")
      System.exit(1)
    }
//Gets the list of files from the Directory
val files = new java.io.File(args(0)).listFiles.filter(_.getName.endsWith(".txt"))

// Loops through each file from Array of files
   for ( file <- files) {

   // Creating Object of OBWeather class
   val obj = new OBWeather(file, args(1), args(2), args(3))

   //Looping through each record of the file
   for (line <- obj.lines){

   val stat = obj.getStationGeocode(line, 4, 10)
   val geocode = Array(stat(1),stat(2),stat(3)).mkString(",")

   val datetime = obj.getDate(line, 15,19,21,23) + "T" + obj.getTime(line,23,25,27) + "Z"

   val temparature = obj.getTemparature(line,87,88,92)

   val pressure = obj.getPressure(line,99,104)

   val humidity = obj.getHumidity(line,61,63)

   val condition = obj.getWethCondition(line,63,64)

   //Writing to file
   obj.writer.write(Array(stat(0),geocode,datetime,condition,temparature,pressure,humidity).mkString("|"))
   obj.writer.append("\n")
     }
obj.writer.close()
  }
 }
}