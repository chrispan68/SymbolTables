#Daphne KH
#python 3.4
#IUPAC Naming
#
#compound, which represents one organic compound that holds all the sidechains
#and can be drawn onto the carbon backbone

from line import line
from carbonBackbone import carbonBackbone
from text import text
from sideChain import sideChain
import math

#everything is based on the backbone, if it is moved and configure is called, the sidechains will move with it
#but if the sidechains are moved and configure is called, the sidechains will be moved back to fit the backbone
#note: the configure method MUST be called before the draw method
class Compound:
    #parameters: a carbonBackone, and a boolean that's true if the backbone should be changed to be made cyclic
    def __init__(self, backbone, cycloBool):
        self.backbone = backbone
        backbone.cycloBool = cycloBool
        self.backbone.getLines()
        self.sideChains = []   #2D array of sideChain, location(s)
        self.configuredSideChains = []
        self.bonds = []

    def addSideChains(self, sideChains):
        self.sideChains = sideChains

    #calculates where all the lines and text should be and moves them
    def configure(self):
        self.backbone.move(100, 100) #(50,50) is a given start position and will be changed later
        self.backbone.rotate(math.pi/6)
        self.configuredSideChains = []
        numLocations = []
        for i in range(len(self.sideChains)):
            for j in range(len(self.sideChains[i][1])):
                numLocations.append(self.sideChains[i][1][j])
        scAngles = self.backbone.getAngles(numLocations)
        bondLength = 20 #####magic number###########
        locs = []
        for i in range(len(self.sideChains)):
            for j in range(len(self.sideChains[i][1])):
                locs.append(self.sideChains[i][1][j])
                pos =  self.backbone.getPos(self.sideChains[i][1][j])               
                angle = scAngles[    self.sideChains[i][1][j] -1   ].pop()
                self.l = line(pos, angle, 9)
                sChain = self.sideChains[i][0].copy()
                if isinstance(sChain, carbonBackbone):
                    sChain.numC += 1
                elif isinstance(sChain, sideChain) and isinstance(sChain.shape, carbonBackbone):
                    sChain.addCarbon()
                else:
                    self.configuredSideChains.append( line(pos, angle, bondLength))
                    pos = self.configuredSideChains[-1].endPos
                sChain.rotate( angle )
                sChain.move(pos[0], pos[1])
                self.configuredSideChains.append(sChain)

    def numTimesIn(self, element, array):
        counter = 0
        for i in range(len(array)):
            if array[i] == element:
                counter += 1
        return counter

    #draws the compound screen parameter, the configure method should be called once beforehand
    def draw(self, screen):
        for i in range(len(self.configuredSideChains)):
            self.configuredSideChains[i].draw(screen)
        self.backbone.draw(screen)

    def print(self):
        for i in range(len(self.sideChains)):
            self.sideChains[i][0].print()
        self.backbone.print()
        
##        for i in range(len(bonds)):
##            if (bonds[i] > 1):
##                #get the location of that spot based off of line array in CBB
##                #add a line of the double bond to shapes
##            if (bonds[i] > 2:
##                #get the location of that spot based off of line array in CBB
##                #add a line for the triple bond to shapes
##        #check to see that everything fits on the screen
##        #if not everything fits
##            #change scale factor, calculate, loop through shapes and adjust scale factor
##        #loop through shapes and move to center the molecule
##

