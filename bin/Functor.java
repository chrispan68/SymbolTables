/**
   General function class, a "Functor" mapping the operate() function to the
   syntaxical representation of the function. Creating a class representing a
   function simplifies and liberates my code through features such as polymorphism.
   
   I borrow the term "Functor" from Category Theory because Java already has a
   class named "Function," so "Functor" is the next best name I can think of, even
   if it is not precisely the "Functor" of Category Theory.
 */
public abstract class Functor {
    public int operate;
}
