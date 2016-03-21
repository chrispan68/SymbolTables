#Daphne KH
#python 3.4
#IUPAC Naming
#
#This is the main file that should be run. In here is the iupacNaming class, which
#has a method to get  a string as input from the user, the method stringToComp
#which parses the string into a compound, a constructor (the constructor has the
#symbol tables), and a bunch of helper methods

from Compound import Compound
from line import line
from carbonBackbone import carbonBackbone
from text import text
from sideChain import sideChain
import pygame, sys
import time
from pygame.locals import*

class iupacNaming:
    def __init__(self):
        loc = [0,0] #initial location for side chains
        bol = False #cycloBool
        self.suffixes = {'ol': text('OH', loc, 0), 'amine': text('NH2',loc, 0), 'ene': "double bond", 'en': "double bond", 'yne': "triple bond", 'yn': "triple bond", 'yl': "nothing - yl", 'ane': "alkane", 'an': "alkane" }
        self.numberPrefixes = {'di': 2, 'mono': 1, 'tri': 3,'quat': 4}
 #       self.numberPrefixes = {'di': 2, 'mono': 1, 'tri': 3,'quat': 4, 'penta': 5, 'hexa': 6, 'septa': 7, 'octa': 8, 'nona': 9, 'deca': 10, 'hendeca': 11, 'icosa': 20}
        self.alkaneRoots = {'meth': line(loc, 0, 0), 'eth': carbonBackbone( loc, 2, bol) , 'prop': carbonBackbone( loc, 3, bol) , 'but': carbonBackbone( loc, 4, bol) , 'pent': carbonBackbone( loc, 5, bol) , 'hex': carbonBackbone( loc, 6, bol) , 'hept': carbonBackbone( loc, 7, bol) , 'oct': carbonBackbone( loc, 8, bol) , 'non': carbonBackbone( loc, 9, bol) , 'dec': carbonBackbone( loc, 10, bol) , 'undec': carbonBackbone( loc, 11, bol) , 'dodec': carbonBackbone( loc, 12, bol) }
        self.sideChainRoots = {'iodo': text('I', loc, 0), 'chloro': text('Cl', loc, 0),'floro': text('F', loc, 0), 'bromo': text('Br', loc, 0), "hydroxy": text('OH',loc, 0)  } #sec-butyl, isoStuff, tertStuff 
        self.hyphenatedPrefixes = {'sec': "thing 1", 'tert': "thing 2"}
    
    def getName(self):
        return input('What is the name of the compound? ')
   
    #parses a string that is the name of the molecu4-floro-cycloheptane into a Compound type
    def stringToComp(self, original):
        sideChains = []
        nameArr = original.split('-')
        for i in range (len(nameArr)):#sec-butyl to secbutyl or similar    and get rid of the di/tri/etc. stuff
            nameArr[i] = self.cropNumPrefix(nameArr[i])[1]
            if nameArr[i] in self.hyphenatedPrefixes:
                nameArr[i] = "(" + nameArr[i] + ")" + nameArr[i+1]
                del nameArr[i+1]
        if nameArr[-1] in self.suffixes: #the @@@@-5-ol   case
            name = nameArr.pop()
            location = nameArr.pop()
            sideChains.append([self.getShape(name), location])
        splitMain = self.cropSuffix(nameArr.pop())
        secondSplit = self.cropPrefix(splitMain[0])
        if secondSplit[0] != '' and  nameArr[-1][0].isdigit():  #the 5-methyl@@@@  case
            location = nameArr.pop()
            secondSplit[0] = self.cropYL(secondSplit[0])
            sideChains.append([ self.getShape(secondSplit[0]), location])
            splitMain[0] = secondSplit[1]
            if splitMain[1] != 'ane' and splitMain[1] != 'an':
                sideChains.append([  self.getShape(splitMain[1]), 0])
        elif  splitMain[1] != 'ane'    and   len(nameArr) > 0    and   nameArr[-1][0].isdigit():   #the 5-@@@@-ol   case
            if len(nameArr[-1]) > 1: #meaning mutliple sidechains --> need to also remove the di/tri/etc. prefix/suffix thing
                splitMain[0] = self.cropNumPrefix(splitMain[0])[1]
            location = nameArr.pop()
            sideChains.append( self.getShape([splitMain[1], location]))
        if (splitMain[0][-1] == 'a'): #hexa --> hex
                splitMain[0]= splitMain[0][:-1]
        cycloBool = False
        if len(splitMain[0]) >5 and splitMain[0][:5] == 'cyclo': #if cyclo, gets rid of the cyclo bit and sets cyclo to be true
            cycloBool = True
            splitMain[0] = splitMain[0][5:]
        mainCompound = None
        if splitMain[0] in self.alkaneRoots:
            mainCompound = Compound( self.alkaneRoots[splitMain[0]], cycloBool)##here is the main compound
        else:
            if splitMain[0] in self.sideChainRoots:
                mainCompound = Compound(self.sideChainRoots[splitMain[0]], cycloBool)
            else:
                raise Exception('I do not recognize ", splitMain[0]')
        for i in range(0, len(nameArr), 2):
            sideChains.append( [self.getShape(nameArr[1]), nameArr[0]])
            del nameArr[0] #deletes the chain and its location, which was just added to the sideChains
            del nameArr[0]
        for i in range(len(sideChains)): #parse the location into an int array
            j = 0
            locs = []
            while j<len(sideChains[i][1]):
                if sideChains[i][1][j] == ',':
                    locs.append(int(sideChains[i][1][:j] ))
                    sideChains[i][1] = sideChains[i][1][j+1:]
                    j = 0
                j += 1
            locs.append(int(sideChains[i][1]))
            sideChains[i][1] = locs
        mainCompound.addSideChains(sideChains)
        return mainCompound
        
    #######below is a bunch of helper methods for the parser##########
    def cropPrefix(self, string):
        for i in range(1, len(string)-1):
            if string[i:i+2] == 'yl':
                return [string[:i+2], string[i+2:]]
            if string[:i+1] in self.sideChainRoots:
                return [ string[:i+1], string[i+1:]]
        return ['', string]
    
    def cropNumPrefix(self, string):#end a boolean, true means to start at endind, false means to start and the beginning
        for i in range(2, len(string)):
            if string[:i] in self.numberPrefixes: #if the i and later is a number prefix
                return [string[:i], string[i:]]
        return ['', string]
    
    def cropNumSuffix(self, string):
        for i in range(2, len(string)):
            if string[-i:] in self.numberPrefixes: 
                return [string[:-i], string[-i:]] 
        return [string, '']


    def cropSuffix(self, string):
        for i in range(2, len(string)):
            if string[-i:] in self.suffixes: #if the i and later is a suffix
                return [string[:-i], string[-i:]] #return the string up to i
        return [string, '']

    def cropYL(self, string):
        if len(string) >= 2:
            if string[-2:] == 'yl':
                return string[:-2]
        return string
            
    #returns the shape that corresponds to the string
    def getShape(self, name):
        if name in self.sideChainRoots:
            return self.sideChainRoots[name]
        if name in self.alkaneRoots:
            return self.alkaneRoots[name]
        if name in self.suffixes:
            return self.suffixes[name]
        if name in self.hyphenatedPrefixes:
            return self.hyphenatedPrefixes[name]
        return None 

while True:
    thing = iupacNaming()
    original = thing.getName()
    try:
        compound = thing.stringToComp(original)
        compound.configure()
        pygame.init()
        screen = pygame.display.set_mode((400, 300))
        background = pygame.Surface(screen.get_size())
        background.fill((255,255,255))
        screen.blit(background, (0, 0))
        pygame.display.set_caption('IUPAC Naming')
        compound.draw(screen)
        pygame.display.update()
        for event in pygame.event.get():
            if event.type == QUIT:
                pygame.quit()
                sys.exit()
    except:
        print("That is not a valid name.")

