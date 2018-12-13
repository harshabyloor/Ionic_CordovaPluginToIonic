package de.appplant.cordova.plugin.printer.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import android.webkit.MimeTypeMap;

import com.efi.datapasstrial.MainActivity;


public class Upload extends Activity {
  String tempDir = android.os.Environment.getExternalStorageDirectory().getPath()+ "/PrintMeTemp";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

   Intent i=new Intent(this, MainActivity.class);
   //i.addFlags(Intent);
   i.putExtra("Data","SomeData");
   startActivity(i);

    Intent intent = getIntent();
    String action = intent.getAction();
    String type = intent.getType();
    final MimeTypeMap mime = MimeTypeMap.getSingleton();
    String mimeExtension=mime.getExtensionFromMimeType(type);
    System.out.println("myExtension: "+mimeExtension);
   if (Intent.ACTION_SEND.equals(action) && type != null) {
      if (type.startsWith("image/")) {
        handleSendImage(intent, mimeExtension); // Handle multiple images being sent
      }
      else if (type.startsWith("application/")) {
        handleSendApplication(intent, mimeExtension);
      }
      else if(type.startsWith("text/")) handleSendText(intent, mimeExtension);
    }
    finish();
  }

  void handleSendText(Intent intent, String mimeExtension) {
    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    if(imageUri!=null)
    {
      deleteDir(new File(tempDir));
      //if (imageUri != null) {
        File dir = new File(tempDir);
        try{
          if(dir.mkdir()) {
            saveFile(imageUri, mimeExtension);
          } else {
            System.out.println("Directory is not created");
          }
        }catch(Exception e){
          e.printStackTrace();
        }
    }
    else{
    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
    if (sharedText != null) {
      // Update UI to reflect text being shared
    }
  }
  }

  void handleSendImage(Intent intent, String mimeExtension) {
    Uri intentUri = intent.getData();
    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    deleteDir(new File(tempDir));
    if (imageUri != null) {
      File dir = new File(tempDir);
      try{
        if(dir.mkdir()) {
          saveFile(imageUri, mimeExtension);
        } else {
          System.out.println("Directory is not created");
        }
      }catch(Exception e){
        e.printStackTrace();
      }
      // Update UI to reflect image being shared
    }
  }

  void handleSendApplication(Intent intent, String mimeExtension) {

    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
    deleteDir(new File(tempDir));
    if (imageUri != null) {
      File dir = new File(tempDir);
      try{
        if(dir.mkdir()) {
          saveFile(imageUri, mimeExtension);
        } else {
          System.out.println("Directory is not created");
        }
      }catch(Exception e){
        e.printStackTrace();
      }
      // Update UI to reflect image being shared
    }
  }

  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i=0; i<children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    return dir.delete();
  }
  public String returnFileName (Uri uri, String mimeExtension){
    String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
    String path = "";
    if(mimeExtension!=null && !mimeExtension.equals("")) mimeExtension="."+mimeExtension;
    ContentResolver cr = getApplicationContext().getContentResolver();
    Cursor metaCursor = cr.query(uri, projection, null, null, null);
    if (metaCursor != null) {
      try {
        if (metaCursor.moveToFirst()) {
          path = metaCursor.getString(0);
        }
      } finally {
        metaCursor.close();
      }
    }
    System.out.println("path in plugin: "+path);

    if(path==null ||  path.equals(""))
        {
          System.out.println("File Name is null or empty");
            String lastPath=uri.getLastPathSegment();
            System.out.println("lastPath: "+lastPath);
            if(lastPath==null ||  lastPath.equals(""))
            {
              System.out.println("File Name is null or empty even in URL hence namimg Untitled");
                path="Untitled"+mimeExtension;
            }
            else
            {
              System.out.println("Url has file name hence pinging file name from url");
                path=lastPath;
            }



        }
            int index=path.lastIndexOf(".");
            String pathExt="";
            if(index!=-1 && index!=0)
                pathExt=path.substring(index,path.length());
        if(pathExt!=null && !pathExt.equals(""))
        {
          System.out.println("File Name is proper: "+path);
        }
        else{
          System.out.println("File has no Ext Hence appending Ext: ");
            path=path+mimeExtension;
        }


    if(path.length()>30){
      String first = path.substring(0, 13);
      String last = path.substring(path.length()-15, path.length());
      path = first+"..."+last;
    }
    return path;
  }

  void saveFile(Uri sourceURI, String mimeExtension)
  {
    String destinationFilename = tempDir + File.separator + returnFileName(sourceURI, mimeExtension);
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    File file;
    try {
      bis = new BufferedInputStream(getContentResolver().openInputStream(sourceURI));
      bos = new BufferedOutputStream(new FileOutputStream(destinationFilename, false));
      byte[] buf = new byte[1024];
     int bytesRead=0;
	      while(( bytesRead = bis.read(buf)) != -1) {
	    	  bos.write(buf, 0, bytesRead); 
	    	}
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bis != null) bis.close();
        if (bos != null) bos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
