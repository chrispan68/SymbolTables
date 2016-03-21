Description
-------------------
This program draws organic molecules based on the user input of an IUPAC name. This program has dictionaries that relate the strings to objects that represent the image of that part of the compound, which can be drawn on a pygame surface (ex. "bromo" to a text object with the string Br, "eth" to a line). These dictionaries are implemented in python with hash tables, which are an efficient implementation of symbol tables because they have a constant search time.

Execution
-------------------
To execute the program, run the iupacNaming file and enter the name of the compound as asked. 

Input
-------------------
The compound must have a carbon backbone. The side chains that can be used are: methyl, ethyl.... dodecyl, iodo, chloro, floro, bromo, amine, hydroxy, and alcohols (using 'ol'). Carbon backbones can have 2-12 carbons and can be linear or cyclic. The location of sidechains as suffixes can be identified be either the 3-butanol or but-3-ol form. For other sidechains normal IUPAC rules apply for identifying their location. If a compound that doesn't fit these rules is entered, the program will just print 'That is not a valid name' and allow you to enter more names. 

Output
-------------------
Example 1:
![Alt text](https://raw.githubusercontent.com/AlgoPHS/SymbolTables/master/IUPAC-naming/example1.png)

Example 2:

![Alt text](https://raw.githubusercontent.com/AlgoPHS/SymbolTables/master/IUPAC-naming/example2.png)
