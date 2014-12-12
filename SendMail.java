
package googlemap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
    
    public void send (){
        
        //Reference:- java mail tutorial from tutorialspoint.com
        
        // Recipient's email ID needs to be mentioned.
        String to = "reciever@example.com";
        
        // Sender's email ID needs to be mentioned
        String from = "sender@example.com";
        
        final String username = "sender";//change accordingly
        final String password = "********";//change accordingly
        
        String host = "localhost";
        String port = "1056";
        
        Properties props = new Properties();
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        
        // Get the Session object.
        Session session = Session.getInstance(props,new javax.mail.Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication() {
                
                return new PasswordAuthentication(username, password);
                
            }
            
        });
        
        try {
            
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));
            
            Calendar cal = Calendar.getInstance();
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
            cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            
            // Set Subject: header field
            message.setSubject("Daily Report of "+dateFormat.format(cal.getTime()));
            
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            
            // Now set the actual message
            messageBodyPart.setText("This is message body");
            
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            
            String filename = "/home/chiran/Desktop/mytxt.txt";
            
            DataSource source = new FileDataSource(filename);
            
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Blocked IP list.txt");
            multipart.addBodyPart(messageBodyPart);
            
            // Send the complete message parts
            message.setContent(multipart);
            
            // Create a multipar message
            multipart = new MimeMultipart();
            
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            
            String jpgname = "/home/chiran/Desktop/screenshot.jpg";
            
            DataSource source1 = new FileDataSource(jpgname);
            
            messageBodyPart.setDataHandler(new DataHandler(source1));
            messageBodyPart.setFileName("Google Map.jpg");
            multipart.addBodyPart(messageBodyPart);
            
            // Send the complete message parts
            message.setContent(multipart);
            
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        
        } catch (MessagingException e) {
            
            throw new RuntimeException(e);
        
        }
        
    }
    
}
