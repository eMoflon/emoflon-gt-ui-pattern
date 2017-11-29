package org.moflon.gt.mosl.pattern.language.exceptions;

public abstract class PatternLanguageException extends Exception
{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   public PatternLanguageException(String message){
      super(message);
   }
   
   public PatternLanguageException(String message, Throwable e){
      super(message, e);
   }
   
   public PatternLanguageException(Throwable e){
      super(e);
   }

}
