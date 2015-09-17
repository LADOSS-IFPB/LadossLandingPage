import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet (name = "MailServlet", urlPatterns = "/MailServlet.do")
public class MailServlet extends HttpServlet{
	
	private static final long serialVersionUID = -8586522391364629526L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("\nsetup Mail Server Properties..");
		Properties mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\nget Mail Session..");
		Session getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		MimeMessage generateMailMessage = new MimeMessage(getMailSession);
		try {
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("rerissondaniel@gmail.com"));
			generateMailMessage.setSubject("Novo contato através da Landing Page do Ladoss");
			
			String emailBody = "Novo Contato de " + request.getParameter("nome") + " através da "
								+ "Landing Page do Ladoss<br><br>"
							    + request.getParameter("mensagem").replace("\n","<br>") 
							    + "<br><br> Email para contato: " + request.getParameter("email")
							    + "<br><br>Att,"
							    + "<br><br>Team Ladoss";
			
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session has been created successfully..");
	 
			// Step3
			System.out.println("\n\nGet Session and Send mail");
			Transport transport = getMailSession.getTransport("smtp");
	 
			// Enter your correct gmail UserID and Password
			// if you have 2FA enabled then provide App Specific Password
			transport.connect("smtp.gmail.com", "joaodedeusito@gmail.com", "hyogbrzpxwbfajke");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			response.sendRedirect("contato-sucesso.html");
			System.out.println("E-mail sent successfully.");
		}catch (MessagingException e) {
			System.err.println("Error while sending response.");
			response.sendRedirect("contato-erro.html");
			e.printStackTrace();
		}
	}
}