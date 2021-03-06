# Formatting-Weather-Data-in-Scala
SCALA code to extract the desired elements from the RAW weather data files supplied from NATIONAL CLIMATE DATA CENTRE

Project High Level Description:
------------------------------
I have made an attempt to demonstrate functional programming and object oriented programming style in SCALA. So the same algorithm for extracting data from the NCDC raw files in to a structured output has been written in the functional style and object oriented style.

NOTE :- Only one exception applies in functionally written programs. I have used existing classes to READ from and WRITE into files. So a need has aroused to create OBJECTS from these classes to read from and write into files.


Details regarding Functional style scala program:
---------------------------------------------------

The programs to be referred are 
1. FWWeatherData.scala :- This is the main program which drives the execution, which is the entry point for application.
2. Lookup.scala :- The reusability function is written in this file. The reference file paths are hardcoded in thsi program. They have to be changed for the successful execution of the program.

Please refer to the program header section for more details.


Details regarding Object Oriented style scala program:
------------------------------------------------------

The program to be referred is:
1. OBWeather.scala :- This file has a Companion Class and Companion Object. All functions are declared and defined in Companion class.
All calls to these functions are made from Companion Object, which is the entry point for Application.

Please refer to the program header section for more details.


Details Regarding the Reference Files:
-------------------------------------
1. Master_Location.txt - This file is used to get the IATA Station Name and its geocoding location details. Following is the format of the file
STATION NUMBER, IATA STATION NAME, LATTITUDE, LONGITUDE, ELEVATION

2. Weather_Condition.txt - This ref file is used to get the weather condition name based on the weather condition code. The format of the file is :
Weather Condition Code, Weather Condition Name


Assumptions Considered:
-----------------------
1. One file is generated per station. Hence the program is build to handle and process mutiple files in a directory assuming mutiple files will be present on any day to process
2. As the processing of files is in sequential fashion, the data in out put file will also be in the same processing order
