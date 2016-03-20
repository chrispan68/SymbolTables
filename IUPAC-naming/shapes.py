#Daphne KH
#python 3.4
#IUPAC Naming
#
#shapes, includes the class text, line, carbonBackBone, and sideChain
#all of these classes have the methods: copy, rotate, move, and draw,
#which allows them to all be drawn and moved easily in a loop in the Compound class
#these objects store location, angle, and some other values so they can be easily drawn
#in pygame by calling the draw method, that only has one parameter: the surface

import pygame, sys
from pygame.locals import*
import math
import time

#the text class makes objects that once made, can be easily drawn with pygame by
#just calling one method with one parameter. There are more methods to move, rotate, etc. the text
class text:
    def __init__(self, string, location):
        self.string = string
        self.location = location
        ##below are some variables that are used in drawing the text, the user could change them but there generally isnt a need to###
        self.fontSize = 20
        self.font = "Denmark"
        self.color = (0,0,0)
        self.angle = 0 #the angle doesnt really matter for text, it just allows text to be used easily with other shapes (carbonBackBone, line, ...)

    def print(self):
        print("_TEXT_  location: ", self.location, ";  string: ", self.string)

    def move(self, xChange, yChange):
        self.location = [self.location[0], self.location[1]]

    def rotate(self, angle):
        self.angle += angle

    def draw(self, surface):
        myfont = pygame.font.SysFont(self.font, self.fontSize)
        label = myfont.render(self.string, 2, self.color)
        screen.blit(label, self.location)

    def copy(self):
        return text(self.string, self.fontSize, self.font, self.location, self.color)

#the line class makes objects that once made, can be easily drawn with pygame by
#just calling one method with one parameter. There are more methods to move, rotate, etc. the text
class line:
    #parameters: array with the initial x and y coordinates of the first point on the line, the angle of the line
    #if the start position was the origin on the unit circle, and the length of the line
    def __init__(self, startPos, angle, length):
        self.startPos = startPos
        self.length = length
        self.angle = angle
        self.endPos = self.getEndPos()
        self.width = 2 #2 is magic number for the line width, it could be changed
        self.color = (0,0,0) #the color is set to black, but it doesnt really matter

    #returns the angle of the line assuming that the startPos represents the origin of a cartesian plane
    #parameter startWithStart - boolean, if true uses startPos as the origin, if false, uses endPos as the origin
    def getCartesianAngle(self, startWithStart):
        if self.length == 0: #prevents divide by 0 error in case of a point
            return 0
        if startWithStart:
            startPos = self.startPos
            endPos = self.endPos
        else:
            endPos = self.startPos
            startPos = self.endPos
        opp = startPos[1] - endPos[1]#opposite in the sense sin equals opposite over hypotenuse, calculated starting-ending so the signs will correlate to cartesian plane
        if (startPos[0] <= endPos[0]): #startingX < endingX, meaning quadrants I or IV
            if (startPos[1] >= endPos[1]):#startingY > endingY, meaning quadrant I  (remember computer graph)
                return math.asin(opp/self.length)
            #quandrant IV
            return math.pi*2 + math.asin(opp/self.length)
        # quadrants II or III
        #if (startPos[1] > endPos[1]): #startingY>endingY, meaning quandrant II
        return math.pi - math.asin(opp/self.length)
 #           else: #quandrant III

     #returns the last point on the line
    def getEndPos(self):
        return [self.startPos[0] + math.cos(self.angle)*self.length, self.startPos[1] - math.sin(self.angle)*self.length]
    
    def print(self):
        print("_LINE_ startPos: ", self.startPos, ";  endPos: ", self.endPos, ";  angle: ", self.angle*180/math.pi, ";  length: ", self.length)

    #moves the line by the two paramters: delta x and delta y
    def move(self, xChange, yChange):
        self.endPos = [self.endPos[0] + xChange, self.endPos[1] + yChange]
        self.startPos = [self.startPos[0] + xChange, self.startPos[1] + yChange]

    #rotates the line, keeping the start_pos still and moving end_pos, and returns the new location of end_pos
    def rotate(self, angle):
        print(angle)
        self.angle = angle + self.angle
        self.endPos = self.getEndPos()
    
    def draw(self, surface):
        pygame.draw.aaline(surface, self.color, self.startPos, self.endPos, self.width)

    def copy(self):
        return line(self.startPos.copy(), self.angle, self.length)


#makes a collection a collection of lines to make up the representation of a
#carbon backbone in IUPAC naming
class carbonBackbone:
    #parameters: the first point on the the carbon backbone, the number of carbons (ex. butane = 4 carbons, hexane = 6), and a boolean that is true of cyclic compounds, false otherwise
    def __init__(self, startPos, numC, cycloBool):
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
        sy = self.startPos[0]
        sx = self.startPos[1]
        upAngle = math.pi/6 #this angle could be a bit bigger or smaller, but I think a 60 degree angle looks good
        downAngle = -math.pi/6
        if not self.cycloBool:
            for i in range(self.numC - 1): #a point is a carbon, so you have the number of carbons - 1 lines
                if i == 0:
                    self.lines.append( line([sx, sy], downAngle, self.bondLength))
                elif i%2 == 0:
                    self.lines.append( line(self.lines[i-1].endPos, downAngle, self.bondLength))
                else:
                    self.lines.append( line(self.lines[i-1].endPos, upAngle, self.bondLength))
            self.lines[0].color = (200, 0,0)
            return self.lines
        firstAngle = math.pi/3 #this can be changed, doesnt really matter, just decides which side of the polygon to start with
        angleDiff = 2*math.pi/self.numC #exterior angle math
        for i in range(self.numC):
            self.lines.append( line([sx, sy], firstAngle - angleDiff*i, self.bondLength))
            sx = self.lines[i].endPos[0]
            sy = self.lines[i].endPos[1]
        lines[0].color = (200,0, 0)
        return self.lines

    #returns the angle for the side chain on that carbon
    def getSideChainAngle(self, cNum):
        if cNum > 1 and cNum < self.numC: #check that not one of end carbons
            return ( self.lines[cNum-1].getCartesianAngle(True) + self.lines[cNum-2].getCartesianAngle(False) )/2
        if cycloBool:
            return ( self.lines[0].getCartesianAngle(True) + self.lines[-1].getCartesianAngle(False) )/2
        return self.lines[cNum - 1].getCartesianAngle(True) + math.pi/2

    #returns the angle that a double or triple bond on that carbon would have (not the chemical bond angle)
    def getBondAngle(self, cNum):
        return self.lines[cNum-1].angle

    #returns the [x, y] of the point of the carbon given as a parameter
    def getPos(self, cNum):
        return self.lines[cNum-1].startPos
    
    def draw(self, surface):
        for i in range(len(self.lines)):
            self.lines[i].draw(surface)

    def move(self, xChange, yChange):
        self.startPos = [self.startPos[0] + xChange, self.startPos[1] + yChange]
        for i in range(len(self.lines)):
            self.lines[i].move(xChange, yChange)
        self.lines = self.getLines()

    def rotate(self, angle):
        if (len(self.lines) >0):
            self.lines[0].rotate(angle)
            newEnd = self.lines[0].endPos
            for i in range(1, len(self.lines)):
                print('foo')
                self.lines[i].rotate( angle)
                self.lines[i].move( -self.lines[i].startPos[0]+newEnd[0], newEnd[1] - self.lines[i].startPos[1] )        #moves the line so that its new start position equals the the previous line's new end position
                newEnd = self.lines[i].endPos
        self.lines = self.getLines()

#the sideChain class allows for a collection of shapes with one main shape with side chains, all acting as a side chain to another compound, can be used recursively
class sideChain:
    def __init__(self, shape, sideChains):
        self.shape = shape
        self.sideChains = sideChains

    def print(self):
        print('_SIDECHAIN_  shape: ', self.shape, "; self.sideChains: ", sideChains)

    def draw(self, surface):
        self.shape.draw(surface)
        for i in range(len(self.sideChains)):
            self.sideChains[i].draw(surface)
        
    def move(self, xChange, yChange):
        self.shape.move(xChange, yChange)
        for i in range(len(self.sideChains)):
            self.sideChains[i].move(xChange, yChange)

    def rotate(self, angle):
        self.shape.rotate(angle)
        for i in range(len(self.sideChains)):
            self.sideChains[i].rotate(angle)

    def copy(self):
        return sideChain(self.shape.copy(), self.sideChains.copy())

##
#shapes tester
carbonBackbone = carbonBackbone(  [50,50],  5, False)
#carbonBackbone.rotate(math.pi/2) ##MUST DEBUG THIS!!!
line = line([50,50], 0, 20)
while True:
    pygame.init()
    screen = pygame.display.set_mode((700, 700))
    background = pygame.Surface(screen.get_size())
    background.fill([255,255,255])
    screen.blit(background, (0, 0))
    pygame.display.set_caption('line')
    carbonBackbone.draw(screen)
    pygame.display.update()
#    pygame.display.update()
    for i in range(3):
        time.sleep(1)
        carbonBackbone.rotate(math.pi/6)
        carbonBackbone.draw(screen)
        pygame.display.update()
    for event in pygame.event.get():
        if event.type == QUIT:
            pygame.quit()
            sys.exit()

    




    
