package com.Functions;

public class AntiCrack
{
   private AntiCrack()
   {
   }
   public static Throwable Fake(Throwable throwable, Throwable throwable1)
   {
       try
       {
           throwable.getClass().getMethod("initCause", new Class[] {
               java.lang.Throwable.class
           }).invoke(throwable, new Object[] {
               throwable1
           });
       }
       catch(Exception exception) { }
       return throwable;
   }
   
   
   
}
