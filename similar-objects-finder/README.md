Similarities Finder
===================

#### Aaron and Phil's ST project 

A program using symbol tables to help find matches in a set of data from a user's attribute preferences. The data set will consist of a set of items which have a few types of attributes and then each specific item will have a value of each type of attribute (for example the attribute type is size, and the value will be small medium or large). Hash maps will be used for attribute types which have a set number of options and trees will be used when they have a range of options.

![whiteboard-1](https://github.com/AlgoPHS/SymbolTables/raw/similar-objects-finder/similar-objects-finder/similar-objects-finder.png)

Compiling
-------------------

The program is structured within the simfinder package, so compile from the
root directory with `javac simfinder/Driver.java`.

Usage
-------------------

Run the program as `java simfinder.Driver data/places.txt`. It takes one command line argument, the name of a data file to read in. The current driver will then prompt the user to choose a value for each option, then calculate and display the best options.

Input Format
-------------------

The input shall be a tab separated list of items, one per line, with the first
line being a header.

The first column shall be a unique identifier for items, such as a name. All
headers and values will be processed as strings.

