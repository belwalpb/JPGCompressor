package com.jpgresizer.JPGResizer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class App 
{
	
	public static double percent=1;
	public static String rootdir="";
	public static ArrayList<String> jpglist=new ArrayList<String>();
    public static void main( String[] args )
    {
    	
    	try {
       String choice="";
       Scanner sc=new Scanner(System.in);
       boolean validchoice=false;
       while(!validchoice)
       {
    	   System.out.println("Choose the Compression Type:");
           System.out.println("Enter 1 For loseless compression");
           System.out.println("Enter 2 For lossy compression");
           choice=sc.nextLine();
           if(choice.equals("1")||choice.equals("2"))
           { validchoice=true; }         
       }
       
       if(choice.equals("2"))
       {  boolean validquality=false;
   
   	double quality=0.0;
   	while(!validquality)
   	{
   		System.out.println("Enter Quality Percentage Between 0% and 100%:");
   		quality=Double.parseDouble(sc.nextLine());
   		if(quality<=100 && quality >0)
   		{	validquality=true;    		}
   	}
   	
   	quality=quality/100.0;
   	App.percent=quality;
       }
          
       
       boolean validdir=false;
   	String rootdir="";
   	while(!validdir)
   	 {
   		System.out.println("Please Enter the Complete Folder Path:");
           rootdir=sc.next();
           
           File rootfolder=new File(rootdir);
           
           if(rootfolder.exists() && rootfolder.isDirectory())
           {   App.rootdir=rootdir; validdir=true;           }  
           else {  System.out.println("Enter Valid Directory"); }
   	 }
    	System.out.println("Fetching Files");
       fetchallists(App.rootdir);
       System.out.println("Found "+App.jpglist.size()+" JPG Files");
       System.out.println("Compression Process Started:::::::::::::::::::::::::::::::::");
     int sizeoffiles=App.jpglist.size();
     int i=1;
     
       for(String filename: App.jpglist)
      {
    	  System.out.println("Converting "+i+" / "+sizeoffiles+" Filename: "+filename);
    	  File inputFile = new File(filename);
    	  BufferedImage inputImage = ImageIO.read(inputFile);
    	  int scaledWidth = (int) (inputImage.getWidth() * App.percent);
          int scaledHeight = (int) (inputImage.getHeight() * App.percent);
    	  BufferedImage outputImage = new BufferedImage(scaledWidth,
                  scaledHeight, inputImage.getType());
    	  Graphics2D g2d = outputImage.createGraphics();
          g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
          g2d.dispose();
          String formatName = filename.substring(filename
                  .lastIndexOf(".") + 1);
          ImageIO.write(outputImage, formatName, new File(filename)); 	  
    	  i++;
      }
       
       System.out.println("Compression Process Successfully Finished");
       sc.close();
    }
    catch(Exception e)
    {
    	System.out.println(e);
    }
    	
    }
    
   public static void fetchallists(String filepath)
   {
	   File file=new File(filepath);
	   if((file.exists()) && (file.isFile()) && (!file.isHidden())  && (filepath.endsWith(".jpg") || filepath.endsWith(".JPG")) )
	   {
		   App.jpglist.add(filepath);
	   }
	   
	   else
	   {
		   
		   if((file.exists()) && (file.isDirectory()) && (!file.isHidden()))
		   {
			   
			   for(File tempfile: file.listFiles())
			   {
				   
				   fetchallists(tempfile.getAbsolutePath());
			   }			   			   
		   }	   
		   
	   }
	   
   }
    
    
}
