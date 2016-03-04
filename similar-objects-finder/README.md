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

See the sample files in the `data` folder for examples.

Implementation
--------------------

Much of the code deals with parsing input from a fairly human-readable format,
and getting it into the data structures needed for efficient calculations.

The `InputObject` class creates an array of `FieldValue` objects based on
arrays of headers and values. `FieldValue` objects store a header-value pair,
and support equality and hashcode based on their contents, so support being
used as map keys where multiple objects with the same values will be the same
key. The `Parser` class provides a static method to read from a `File` object
and return an `ArrayList` of `InputObject`s.

The `Calculator` constructor runs in linear time to place the data into a
structure that allows for efficient calculations, namely a mapping of
`FieldValue` combinations to `Set`s of `String` object IDs. It also provides
access to a list of blank `FieldValue` objects suitable for submitting to the
`calculate` method, and a method for retrieving the options available for a
given header key.
