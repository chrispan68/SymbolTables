
import pygame, sys
from pygame.locals import*
import math
import time

#the text class makes objects that once made, can be easily drawn with pygame by
#just calling one method with one parameter. There are more methods to move, rotate, etc. the text
class text:
    def __init__(self, string, location, angle):
        self.string = string
        self.location = location
        self.angle = angle
        self.modifier = self.getModifier()
        ##below are some variables that are used in drawing the text, the user could change them but there generally isnt a need to###
        self.fontSize = 20
        self.font = "Denmark"
        self.color = (0,0,0)
 
    def print(self):
        print("_TEXT_  location: ", self.location, ";  string: ", self.string)

    #returns a [delta x, delta y] for its coordinates to adjust for its location on the end of a bond
        #to deal with different angles since text isnt drawn on based on the center point of the text
        #but instead drawn on the left point of the text
    def getModifier(self):
        return [-5 + math.cos(self.angle)*10, -4 - math.sin(self.angle)*8]

    def move(self, xChange, yChange):
        self.location = [self.location[0] + xChange, self.location[1]+ yChange]

    def rotate(self, angle):
        self.angle += angle
        self.modifier = self.getModifier()

    def draw(self, surface):
        myfont = pygame.font.SysFont(self.font, self.fontSize)
        label = myfont.render(self.string, 2, self.color)
        surface.blit(label, [self.location[0] + self.modifier[0], self.location[1] + self.modifier[1]])

    def copy(self):
        return text(self.string, self.location, self.angle)

