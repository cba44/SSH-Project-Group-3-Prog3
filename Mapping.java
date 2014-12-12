package googlemap;

import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Mapping {
    
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private BufferedImage image;
    private URL myURL;
    
    public void show(String gps) {
        
        try {
            
            myURL = new URL("http://maps.google.com/maps/api/staticmap?"
                    + "size=5000x3000&zoom=1" + gps + "&"
                    + "sensor=false&format=jpg");
            image = ImageIO.read(myURL);

            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);
            frame.add(panel);
            frame.pack();
            frame.setLocation(-2000, -2000);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            BufferedImage img = new BufferedImage(frame.getWidth(),frame.getHeight(),BufferedImage.TYPE_INT_RGB);
            
            frame.paint(img.getGraphics()); //https://groups.google.com/forum/#!msg/comp.lang.java.gui/A4pqvWcO2tk/-ObDd_9ULmYJ
            
            ImageIO.write(img,"jpg",new File("/home/chiran/Desktop/screenshot.jpg"));
            
            //Sending the mail about summary of dail y IP addresses and google map using mail
            SendMail sendMail = new SendMail();
            sendMail.send();
            
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));    //close after printing image
            
            System.out.println("kk");
            
        } catch (MalformedURLException e) {
            
            e.printStackTrace();
                   
        } catch (Exception e) {
            
            e.printStackTrace();
                  
        }
        
     }
    
}
