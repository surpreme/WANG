package com.okhttp;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

 class IOUtil {
     //注意这里的closeable和closeables
     public static void closeAll(Closeable... closeables) {
         if (closeables==null){
             return;
         }
         for (Closeable closeable:closeables){
             if (closeable!=null){
                 try {
                     closeable.close ();
                 } catch (IOException e) {
                     e.printStackTrace ();
                 }
             }
         }

     }
}
