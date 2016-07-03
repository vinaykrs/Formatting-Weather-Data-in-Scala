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
