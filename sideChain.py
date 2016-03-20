import pygame, sys
from pygame.locals import*
import math
import time

#the sideChain class allows for a collection of shapes with one main shape with side chains, all acting as a side chain to another compound, can be used recursively
class sideChain:
    def __init__(self, shape, sideChains, locations):
        self.shape = shape
        self.locations = locations
        self.sideChains = sideChains

    def print(self):
        print('_SIDECHAIN_  shape: ', self.shape, "; self.sideChains: ", sideChains)

    def draw(self, surface):
        self.shape.draw(surface)
        for i in range(len(self.sideChains)):
            self.sideChains[i].draw(surface)

    def addCarbon(self):
        self.shape.numC += 1
        for i in range(len(self.locations)):
            locations[i] += 1
        
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
