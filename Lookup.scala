/*
--------------------------------------------------------------------------------
-------------------------------------------------------------------------------
NAME                DATE           WHO             VERSION
Lookup.scala        02/07/2016     VinayKumar      Intial Draft

Description:-
This program has following functions:
Functions:-
   Function 1: conditionlkp :- This function takes Weather Condition code STRING and Reference file name as input parameters and emits                          the type of weather condition
                               Example:- RAINY, SUNNY etc... PLease refer file Weather_Condition.txt file
   Function 2: stationlkp   :- This function takes STATION NUMBER and Reference File as input parameters and emits its IATA and                                GEOCODE Informtion. Please refer file Master_Location.txt file for more information

----------------------------------------------------------------------------
---------------------------------------------------------------------------
*/

object Lookup {
def conditionlkp(x: String, filename1: String) : String = {

val conlkp = scala.io.Source.fromFile(filename1).getLines().map(_.split(",")).map( arr => arr(0) -> arr(1)).toMap
conlkp.apply(x)
    } 

def stationlkp(y: String, filename2: String) :Array[String] = {

val statlkp = scala.io.Source.fromFile(filename2).getLines().map(_.split(",")).map(arr => arr.head -> arr.tail).toMap
statlkp.apply(y)
    }
}