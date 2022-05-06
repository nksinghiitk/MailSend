
import java.io.*;
import java.util.Scanner;
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

   static String subj="", filenme="", frmu="", head="",pmess="";

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
       	    	message.setContent("Dear "+to+"<br><br>"+pmess+"<br><br>Your marks are given below : <br><br>"+headbody+"<tr>"+ messbody+"</tr></table>","text/html");

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

		System.out.println("***********************************");
		System.out.println("*                                 *");
		System.out.println("*                                 *");
		System.out.println("*                                 *");
		System.out.println("*  Welcome to Mail Send Program   *");
		System.out.println("*                                 *");
		System.out.println("*                                 *");
		System.out.println("*                                 *");
		System.out.println("***********************************");
		System.out.println("Please provide the some parameters:");
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the instructor email");
		String insemail = in.nextLine();
		System.out.println("Enter the subject of email");
		String esubj = in.nextLine();
		System.out.println("Enter the marks file name");
		String mflenme = in.nextLine();
		System.out.println("Enter the Message which you want to give to the student");
		String premessage = in.nextLine();

		if((insemail.isEmpty())||(esubj.isEmpty())||(mflenme.isEmpty())){
			System.out.println("Please enter all the values");
			System.exit(0);
		}else{
			subj =esubj;
			filenme=mflenme;
			frmu=insemail;
			pmess=premessage;

			// write these values in properties
			PropertiesCache cache = PropertiesCache.getInstance();
			cache.setProperty("subject", subj);
			cache.setProperty("filename", filenme);
			cache.setProperty("instructormail", frmu);
			cache.setProperty("pmailmess", premessage);
			try{
				cache.flush();
			}
			catch(Exception ex){
				System.out.println("The error is "+ex.toString());
			}
		
		}
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
			if(count == 0){
				head=st;
			}else{
				SendMail(st,subj,head,frmu);
			}
			count++;
    		}
		System.out.println(count-1+" mail sent. ");
		
	}
	catch(FileNotFoundException fnfe) {
                       fnfe.printStackTrace();
        } catch(IOException ioe) {
                       ioe.printStackTrace();
        }
    }
}
