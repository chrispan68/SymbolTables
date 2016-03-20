#Daphne KH
#python 3.4
#IUPAC Naming
import pygame, sys
from pygame.locals import*
import math

#the line class makes objects that once made, can be easily drawn with pygame by
#just calling one method with one parameter. There are more methods to move, rotate, etc. the text
class line:
    #parameters: array with the initial x and y coordinates of the first point on the line, the angle of the line
    #if the start position was the origin on the unit circle, and the length of the line
    def __init__(self, startPos, angle, length):
        while angle > math.pi*2:
            angle -= math.pi*2   
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
        self.angle = angle + self.angle
        self.endPos = self.getEndPos()
    
    def draw(self, surface):
        pygame.draw.line(surface, self.color, self.startPos, self.endPos, self.width)

    def copy(self):
        return line(self.startPos.copy(), self.angle, self.length)
