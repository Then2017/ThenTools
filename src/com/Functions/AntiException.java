package com.Functions;

public class AntiException extends Exception
{
	
   public AntiException()
   {
   }
   public AntiException(String s)
   {
       super(s);
   }
   public AntiException(String s, Throwable throwable)
   {
       super(s);
       AntiCrack.Fake(this, throwable);
   }
   
   
//   try
//   {
//   }
//   catch(IoException e)
//   {
//        throw new AntiException(ioexception.toString(), ioexception);
//   }
}
