
import java.io.*;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/** Used for send individual marks to all the individual user .
 * @author Nagendra Kumar Singh<nksinghiitk@gmail.com>
 * @author https://sites.google.com/site/nksinghiitk
 * @version 1.0
 * @since 2022
*/

public class SendMarksMail{


 /**
   * This method is used to load properties file for reading.
   * @param filename  This is file name of properties file
   * @exception FileNotFoundException On file input error.
   * @see FileNotFoundException 
   * @exception IOException On input error.
   * @see IOException
   * @return Properties prop
   */
   public static Properties readPropertiesFile(String fileName) throws IOException {
   	FileInputStream fis = null;
   	Properties prop = null;
   	try {
       		fis = new FileInputStream(fileName);
       		prop = new Properties();
       		prop.load(fis);
   	} catch(FileNotFoundException fnfe) {
      		fnfe.printStackTrace();
   	} catch(IOException ioe) {
       		ioe.printStackTrace();
   	} finally {
       		fis.close();
   	}
   	return prop;
   }
 /**
   * This method is used to send mail.
   * @param line  This contains the email and marks
   * @param subj  This is the subject of mail
   * @param head  This is the legend of marks header
   * @param from  This is the sender email/instructor email
   * @exception MessagingException On messaging error.
   * @see MessagingException
   * @exception IOException On input error.
   * @see IOException
   * @return nothing.
   */
   public static void SendMail(String line,String subj,String head,String from) {
	try{
		Properties prop = readPropertiesFile("configMail.properties");
		//get the line convert into array
		String strArray[] = line.split(",");
		//get the value from array		
		// Recipient's email ID needs to be mentioned.
	        String to = strArray[0];
		String messbody="";
		for (int i = 0; i < strArray.length; i++) {
        	    messbody=messbody+"<td>"+strArray[i] + "</td>";
        	}
	        // Sender's email ID needs to be mentioned
        	String user = prop.getProperty("fromadd");
	        String passwd = prop.getProperty("passwd");
        	// Assuming you are sending email from through gmails smtp
	        String host = prop.getProperty("smtpadd");
        	String prt = prop.getProperty("smtpprt");
        
		String headArray[] = head.split(",");
		String headbody="";
		headbody="<table border=1><tr>";
        	for (int i = 0; i < headArray.length; i++) {
	            headbody=headbody+"<td>"+headArray[i] + "</td>";
        	}
		headbody=headbody+"</tr>";

        	// Get system properties
	        Properties properties = System.getProperties();

        	// Setup mail server
	        properties.put("mail.smtp.host", host);
        	properties.put("mail.smtp.port", prt);
	        properties.put("mail.smtp.ssl.enable", "false");
        	properties.put("mail.smtp.auth", "true");

	        // Get the Session object.// and pass username and password
        	Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            		protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(user, passwd);
            	}

        	});

	    	// Used to debug SMTP issues
            	//session.setDebug(true);
            	session.setDebug(false);

            	// Create a default MimeMessage object.
            	MimeMessage message = new MimeMessage(session);

	    	// Set From: header field of the header.
            	message.setFrom(new InternetAddress(from));

            	// Set To: header field of the header.
            	message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            	// Set Subject: header field
            	message.setSubject(subj);

            	// Now set the actual string message
       	    	message.setContent("Your marks are given below : <br><br>"+headbody+"<tr>"+ messbody+"</tr></table>","text/html");

            	// Send message
            	Transport.send(message);
            	System.out.println("Sent message successfully to "+to);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	catch(IOException ex){
	    ex.printStackTrace();
	}

    }
 /**
   * This is the main method which makes use of SendMail method.
   * @param args Unused.
   * @return Nothing.
   * @exception IOException On input error.
   * @see IOException
   * @exception FileNotFoundException On file input error.
   * @see FileNotFoundException 
   */
   public static void main(String[] args){
	try{
		Properties cprop = readPropertiesFile("courseconfig.properties");
		String subj = cprop.getProperty("subject");
	        String head = cprop.getProperty("headerm");
	        String filenme = cprop.getProperty("filename");
	        String frmu = cprop.getProperty("instructormail");

		File fileName=new File(filenme);
		// Creating an object of BufferedReader class
		BufferedReader br=null;
        	br = new BufferedReader(new FileReader(fileName));		
        	// Declaring a string variable
        	String st;
		int count=0;
        	// Condition holds true till
        	// there is character in a string
        	while ((st = br.readLine()) != null){
			count++;
			SendMail(st,subj,head,frmu);
    		}
		System.out.println(count+" mail sent. ");
	}
	catch(FileNotFoundException fnfe) {
                       fnfe.printStackTrace();
        } catch(IOException ioe) {
                       ioe.printStackTrace();
        }
    }
}
