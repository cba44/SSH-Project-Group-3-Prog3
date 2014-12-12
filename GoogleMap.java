package googlemap;

public class GoogleMap {

    public static void main(String[] args) {
        
        DBCon dbc = new DBCon();
        
        String gps = dbc.getIP();
        
        if (!gps.equals("")){
        
            Mapping mymap = new Mapping();
            mymap.show(gps);
        
        }
            
    }
    
}
