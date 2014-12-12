
package googlemap;

import java.io.*;

public class IPCalculator {

    private long IPlong;
    private String gps;
    
    public String convert (String str){   //http://www.dnsqueries.com/en/ip_v4_converter.php
        
        String [] s = str.split("\\.");
        int i1 = Integer.parseInt(s[0]);
        int i2 = Integer.parseInt(s[1]);
        int i3 = Integer.parseInt(s[2]);
        int i4 = Integer.parseInt(s[3]);
        
        this.IPlong = 1l * i4 + 256l * i3 + 65536l * i2 + 16777216l * i1;   //converting IP address to integer

        return myMethod(this.IPlong);
        
    }
    
    private String myMethod(long myIP){       //Getting location ID from IP

	String fileName = "GeoLiteCity-Blocks.csv";                 // file 
        
	try { 
            
	    FileReader fileRd = new FileReader(fileName); 
	    BufferedReader bufferRd = new BufferedReader(fileRd);
	    String line = null; 
            
	    while( (line = bufferRd.readLine()) != null) { 
                
		String [] s = line.split(",");
                
                String [] s1 = s[0].split("\"");
                long ip1 = Long.parseLong(s1[1], 10);
                
                String [] s2 = s[1].split("\"");
                long ip2 = Long.parseLong(s2[1], 10);
                
                if (ip2 > myIP && ip1 < myIP){
                    
                    String [] s3 = s[2].split("\"");
                    int ip3 = Integer.parseInt(s3[1]);    // System.out.println(ip3);
                    return showCity(ip3);
                    
                }
                
	    }
            
	    fileRd.close();
	    bufferRd.close();

	} catch (FileNotFoundException x) { 
            
	    System.out.println("Make sure " + fileName + " is also here!");
	    System.exit(-1);
            
	} catch (IOException x) { 
            
	    System.out.println(x);
	    System.exit(-1);
            
	} 
        
        return "";

    }
    
    private String showCity(int num){     //Getting longitude and latitude of location ID
        
        String fileName = "GeoLiteCity-Location.csv";                 // file 
        
	try { 
            
	    FileReader fileRd = new FileReader(fileName); 
	    BufferedReader bufferRd = new BufferedReader(fileRd);
	    String line = null; 
            
	    while( (line = bufferRd.readLine()) != null) { 
                
                String [] s = line.split(",");
                
                if (num == Integer.parseInt(s[0])){
                
           //         System.out.print(line+"\t");
                    gps = ("&markers=" + s[5] + "," + s[6]);
                    return gps;
                
                }
                
	    }
            
	    fileRd.close();
	    bufferRd.close();

	} catch (FileNotFoundException x) { 
            
	    System.out.println("Make sure " + fileName + " is also here!");
	    System.exit(-1);
            
	} catch (IOException x) { 
            
	    System.out.println(x);
	    System.exit(-1);
            
	} 
        
        return "";

    }
    
}
