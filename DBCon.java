package googlemap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;

public class DBCon {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String query;
    
    private final String pw = "mysql";
    private int count = 0;
    
    private File file = new File("/home/chiran/Desktop/mytxt.txt");
    private FileWriter writer;
    private BufferedWriter bwriter;
    
    IPCalculator ip = new IPCalculator();
    
    private String myStr = "";
    
    public DBCon(){
        try {
            
            writer = new FileWriter(file.getAbsolutePath());
            bwriter = new BufferedWriter(writer);
            bwriter.write("IPAddress\t\tDate\t\tTime"+"\n");
            bwriter.close();
            
        } catch (Exception ex) {
            
            System.out.println(ex);
            
        }
        
        while (true) {
        
            try{

                Class.forName("com.mysql.jdbc.Driver");

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root",pw);
                st = con.createStatement();

                query = "SHOW DATABASES LIKE 'googlemap'";
                rs = st.executeQuery(query);

                while (rs.next()){

                    count++;

                }

                if (count == 1){

                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/googlemap","root",pw);
                    st = con.createStatement();
                    break;

                }else{

                    query = "CREATE DATABASE IF NOT EXISTS googlemap";     //Creating DB if doesn't exist      
                    st.executeUpdate(query);

                    query = "USE googlemap";
                    st.executeUpdate(query);

                    //Creating table if doesn't exist

                    query = "CREATE TABLE IF NOT EXISTS reportIP(\n" +
                            "    IPAddress VARCHAR (15) PRIMARY KEY,\n" +
                            "    BlockedDate DATE NOT NULL,\n" +
                            "    BlockedTime TIME NOT NULL\n" +
                            ")";

                    st.executeUpdate(query);

                    query = "SET global max_connections = 100000";            
                    st.executeUpdate(query);
                    
                    break;

                }

            }catch(Exception ex){   //waiting until apache web server and mysql server starts

                //System.out.println(ex);

            }
            
        }
        
    }
    
    public String getIP(){
        
        try{
            
            query = "SELECT * FROM reportIP "
                    + "WHERE BlockedDate < NOW()"
                    //+ " AND BlockedTime < NOW()"
                    + "ORDER BY BlockedTime";
            rs = st.executeQuery(query);
            
            while (rs.next()){
            
               String IPAddress = rs.getString("IPAddress");
               String blkD = rs.getString("BlockedDate");
               String blkT = rs.getString("BlockedTime");
                
               query = "DELETE FROM reportIP WHERE IPAddress = ?";
               
               PreparedStatement preparedStmt = con.prepareStatement(query);
               
               preparedStmt.setString (1, IPAddress);
               
               preparedStmt.executeUpdate();
               
             //  System.out.println(IPAddress + " " + blkD + " " + blkT);
               
               myStr = myStr.concat(ip.convert(IPAddress));
               
               writer = new FileWriter(file.getAbsolutePath(),true);
               bwriter = new BufferedWriter(writer);
               bwriter.write(IPAddress + "\t\t" + blkD + "\t" + blkT+"\n");
               bwriter.close();
            
            }
            
            return myStr;
            
            
        }catch(Exception ex){
            
            System.out.println(ex);
            
        }
        
        return null;
        
    }
    
}
