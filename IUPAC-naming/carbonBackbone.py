import pygame, sys
from pygame.locals import*
import math
import time
from line import line

#makes a collection a collection of lines to make up the representation of a
#carbon backbone in IUPAC naming
class carbonBackbone:
    #parameters: the first point on the the carbon backbone, the number of carbons (ex. butane = 4 carbons, hexane = 6), and a boolean that is true of cyclic compounds, false otherwise
    def __init__(self, startPos, numC, cycloBool):
        self.angle = 0
        self.numC = numC
        self.startPos = startPos
        self.cycloBool = cycloBool
        self.bondLength = 20 #20 is a magic number that should prob be changed
        self.lines = self.getLines()

    def print(self):
        print("_CBB_  startPos: ", self.startPos, ";  cycloBool: ", self.cycloBool, ";  numC: ", self.numC, ";  lines: ")
        for i in range(len(self.lines)):
            self.lines[i].print()

    def copy(self):
        return carbonBackbone(self.startPos.copy(), self.numC, self.cycloBool)

    #calculates where the lines should be
    def getLines(self):
        self.lines = []
        sx = self.startPos[0]
        sy = self.startPos[1]
 #       upAngle = 0 #this angle could be a bit bigger or smaller, but I think a 60 degree angle looks good
        downAngle = -math.pi/3
        if not self.cycloBool:
            for i in range(self.numC - 1): #a point is a carbon, so you have the number of carbons - 1 lines
                if i == 0:
                    self.lines.append( line([sx, sy], self.angle, self.bondLength))
                elif i%2 == 0:
                    self.lines.append( line(self.lines[i-1].endPos, self.angle, self.bondLength))
                else:
                    self.lines.append( line(self.lines[i-1].endPos, self.angle  + downAngle, self.bondLength))
            return self.lines
        angleDiff = 2*math.pi/self.numC #exterior angle math
        for i in range(self.numC):
            self.lines.append( line([sx, sy], math.pi/3 + self.angle - angleDiff*i, self.bondLength)) #math.pi/3 makes it start with a certain side
            sx = self.lines[i].endPos[0]
            sy = self.lines[i].endPos[1]
        return self.lines

    #parameters: carbon number, the __th side chain (if there is already one side chain on that carbon it would be 2, if there arent any yet it would be 1, etc.)
    #returns the angle for the side chain on that carbon
    def getSideChainAngles(self, cNum, scNum, numLocations):
        #determines adjustement based on scNum
        if self.cycloBool:
            if scNum == 1:
                adjustment = -(math.pi*2 - math.pi/self.numC)/3
            else:
                adjustment = (math.pi*2 - math.pi/self.numC)/3
        else:
            if scNum == 1:
                adjustment = 0
            elif cNum > 1 and cNum < self.numC:
                adjustment = math.pi
            else:
                if scNum == 2:
                    adjustment = math.pi/2
                else:
                    adjustment = math.pi
        #returns the angle
        if cNum > 1 and cNum < self.numC: #check that not one of end carbons
            if (self.lines[cNum-1].getCartesianAngle(True) < 2*math.pi         and       self.lines[cNum-2].getCartesianAngle(False) < math.pi    and    self.lines[cNum-1].getCartesianAngle(True) > math.pi): 
                return ( self.lines[cNum-1].getCartesianAngle(True) + self.lines[cNum-2].getCartesianAngle(False) )/2   + adjustment
            return ( self.lines[cNum-1].getCartesianAngle(True) + self.lines[cNum-2].getCartesianAngle(False) )/2 + math.pi  + adjustment
        if self.cycloBool:
            if (self.lines[0].getCartesianAngle(True) < 2*math.pi         and       self.lines[-1].getCartesianAngle(False)  < math.pi    and    self.lines[0].getCartesianAngle(True) > math.pi):
                return ( self.lines[0].getCartesianAngle(True) + self.lines[-1].getCartesianAngle(False) )/2 + adjustment
            return ( self.lines[0].getCartesianAngle(True) + self.lines[-1].getCartesianAngle(False) )/2 + adjustment
        if cNum == 1:
            return math.pi - self.lines[cNum - 1].getCartesianAngle(True) + adjustment
        if cNum == self.numC:
            print(adjustment*180/math.pi, "  a")
            return math.pi - self.lines[cNum - 2].getCartesianAngle(False) + adjustment
        #the cNum has to be between 0 and numC+1  (exculsice between)

    def getAngles(self, locations):
        numLocs = []
        angles = []
        for i in range(self.numC):
            numLocs.append(0)
            angles.append([])
        for i in range(len(locations)):
            numLocs[locations[i]-1] += 1
        for k in range(len(angles)):
            if self.cycloBool:
                for i in range(numLocs[k]):
                    ##decide adjustement based on # of sidechains on that carbon
                    if numLocs[k] == 1:
                        adjustment = 0
                    else:
                        adjustment = (math.pi*2 - math.pi/self.numC)/6 * (2*i-1)
                    if k == 0:#first carbon
                        if (self.lines[-1].getCartesianAngle(False) < 2*math.pi         and       self.lines[0].getCartesianAngle(True)  < math.pi    and    self.lines[-1].getCartesianAngle(False) > math.pi):
                            angles[k].append(( self.lines[0].getCartesianAngle(True) + self.lines[-1].getCartesianAngle(False) )/2 + adjustment)
                        else:
                            angles[k].append( (self.lines[0].getCartesianAngle(True) + self.lines[-1].getCartesianAngle(False) )/2 + adjustment + math.pi)
                    else: #mid carbon
                        if (self.lines[k-1].getCartesianAngle(False) < 2*math.pi         and       self.lines[k].getCartesianAngle(True) < math.pi    and    self.lines[k-1].getCartesianAngle(False) > math.pi): 
                            angles[k].append(( self.lines[k].getCartesianAngle(True) + self.lines[k-1].getCartesianAngle(False) )/2   + adjustment)
                        else:
                            angles[k].append( ( self.lines[k].getCartesianAngle(True) + self.lines[k-1].getCartesianAngle(False) )/2 + math.pi  + adjustment)
            else:
                for i in range(numLocs[k]):
                    if numLocs[k] == 1:
                        adjustment = 0
                    elif numLocs[k] == 2: 
                        if (k == 0 or k == self.numC -1): #first/last carbon
                            adjustment = math.pi/3*(i-1)
                        else:
                            adjustment = math.pi*(i)
                    else:
                        adjustment = math.pi/2*(i-1)
                    if k > 0 and k < self.numC-1: #mid carbon
                        angles[k].append( ( self.lines[k].getCartesianAngle(True) + self.lines[k-1].getCartesianAngle(False) )/2   + adjustment)
                    elif k == 0:#first carbon
                        angles[k].append( math.pi - self.lines[k].getCartesianAngle(True) + adjustment)
                    #end carbon
                    else:
                        angles[k].append( 2*math.pi - self.lines[k -1].getCartesianAngle(True) + adjustment)                        
        return angles
        

    #returns the angle that a double or triple bond on that carbon would have (not the chemical bond angle)
    def getBondAngle(self, cNum):
        return self.lines[cNum-1].angle

    #returns the [x, y] of the point of the carbon given as a parameter
    def getPos(self, cNum):
        if cNum == self.numC: #example: if you want the last carbon, its the endPos of the last line, the same as the startPos of a line that doesn't exist
            return self.lines[cNum-2].endPos
        return self.lines[cNum-1].startPos
    
    def draw(self, surface):
        self.getLines()
        for i in range(len(self.lines)):
            self.lines[i].draw(surface)

    def move(self, xChange, yChange):
        self.startPos = [self.startPos[0] + xChange, self.startPos[1] + yChange]
        self.lines = self.getLines()

    def rotate(self, angle):
        self.angle += angle
        self.lines = self.getLines()
##        if (len(self.lines) >0):
##            self.lines[0].rotate(angle)
##            newEnd = self.lines[0].endPos
##            for i in range(1, len(self.lines)):
##                self.lines[i].rotate( angle)
##                self.lines[i].move( -self.lines[i].startPos[0]+newEnd[0], newEnd[1] - self.lines[i].startPos[1] )        #moves the line so that its new start position equals the the previous line's new end position
##                newEnd = self.lines[i].endPos
