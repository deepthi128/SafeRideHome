/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nwmissouri.universitypolice.saferidehome.service;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.nwmissouri.universitypolice.saferidehome.conf.ConfigurationProperties;
import edu.nwmissouri.universitypolice.saferidehome.dao.SafeRideHomeDao;
import edu.nwmissouri.universitypolice.saferidehome.exceptions.RideAlreadyModifiedException;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Ride;
import edu.nwmissouri.universitypolice.saferidehome.pojos.User;
import edu.nwmissouri.universitypolice.saferidehome.pojos.Vehicle;


public class SafeRideHomeService {

	public User validateUser(String userId, String password) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.validateUser(userId, password);
	}

	public User validateUser(String userId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.validateUser(userId);
	}

	public User updateUserStatus(String userId, String status) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.updateUserStatus(userId, status);
	}

	public boolean addVehicle(Vehicle vehicle) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.addVehicle(vehicle);
	}

	public boolean addUser(User user) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		if (user.getPassword() == null) {
			user.setPassword("default");
		}
		user.setPassword(user.getPassword());
		if (dao.addUser(user)) {
			sendConfirmationMail(user);
		}
		return true;
	}

	public boolean updateUser(User user) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		user.setPassword(user.getPassword());
		return dao.updateUser(user);
	}

	public String addRideRequest(Ride ride) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.addRideRequest(ride);
	}

	public ArrayList<User> getUsers() {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getUsers();
	}

	public ArrayList<Vehicle> getVehicles() {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getVehicles();
	}

	public boolean updateVehicleStatus(String vehicleId, String status) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.updateVehicleStatus(vehicleId, status);
	}

	@SuppressWarnings("unused")
	private String encrypt(String string) {
		char[] stringChars = string.toCharArray();
		for (int i = 0; i < stringChars.length; i++) {
			stringChars[i] = (char) (stringChars[i] + 121);
		}
		return new String(stringChars);
	}

	@SuppressWarnings("unused")
	private String decrypt(String string) {
		char[] stringChars = string.toCharArray();
		for (int i = 0; i < stringChars.length; i++) {
			stringChars[i] = (char) (stringChars[i] - 121);
		}
		return new String(stringChars);
	}

	private boolean sendConfirmationMail(User user) {
		String msg = "<html>	 <body>	 <center>		<img src='/images/safeRideLogo.jpg' width='80' height='80' border='0' >		</center>	Welcome "
				+ user.getName()
				+ ", <br/> <br/>	Your registration with Safe Ride Home is almost complete. Please click on the following link to verify your account. <br/> <br/>	<center><a href='http://"
				+ ConfigurationProperties.host
				+ ":"
				+ ConfigurationProperties.port
				+ "/SafeRideHome/safeRideHomeController?requestFrom=verifyUser&key=asdjkhasw392NCWEUHF834HEUIH348R32892U3EMQX21SDALKJ34k2j34h234n2jk3n42uh323ui2uh32g42y3u4y2h342h4239reh9324rfwhe239478h&userId="
				+ user.getUserId()
				+ "'> Click here to confirm your account</a> </center>	<br/>	</br>	Have a Safe Ride Home!!!	<br/> <br/>	- Team SafeRideHome.	<br/> <br/> <br/> <br/>	Follow us on Facebook at https://www.facebook.com/safe.rides.9 for any changes in Safe Ride timings and updates.	</br></br>	This is an automatic mail. Please do not respond to this message. For further queries, contact us at (660) 562-1245. 		</body>	</html>	";
		return sendEmail(user.getEmailId(), "SafeRideHome Email Verification",
				msg);
	}

	@SuppressWarnings("static-access")
	public boolean sendEmail(String toAddress, String subject, String msg) {
		Session session;
		Properties props;
		try {
			props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtps.auth", "true");
			props.put("mail.debug", "true");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.enable", "true");

			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"nwmsusaferidehome@gmail.com",
									"universitypolice");
						}
					});
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toAddress));
			message.setSubject(subject);
			message.setContent(msg, "text/html; charset=utf-8");
			Transport t = session.getTransport("smtps");
			t.send(message);
		} catch (MessagingException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		return true;
	}

	public ArrayList<Ride> getRidesListForDriver(String driverId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getRidesListForDriver(driverId);
	}

	public ArrayList<Ride> addRideAndGetRidesListForDriver(Ride ride) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.addRideAndGetRidesListForDriver(ride);
	}

	public ArrayList<Ride> getRidesRequests() {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getRidesRequests();
	}

	public String getStudentPhoneNumberAndName(String student919) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getStudentPhoneNumberAndName(student919);
	}

	public boolean changeRideStatus(Ride ride)
			throws RideAlreadyModifiedException {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.changeRideStatus(ride);
	}

	public ArrayList<String> getAvailableVehicles() {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getAvailableVehicles();
	}

	public String getRideId(String userId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getRideId(userId);
	}

	public double[] getRideStatus(String rideId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getRideStatus(rideId);
	}

	public String[] getRideStatusForDispatcher(String student919,
			String phoneNumber) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.getRideStatusForDispatcher(student919, phoneNumber);
	}

	public int vaidateUserId(String userId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		return dao.vaidateUserId(userId);
	}

	public String sendResetPasswordEmail(String userId, String phoneNumber,
			String emailId) {
		SafeRideHomeDao dao = new SafeRideHomeDao();
		String msg = "<html> <body> <center> <img src='/images/safeRideLogo.jpg' width='80' height='80' border='0' >		</center>	Welcome back,  <br/> <br/>	To reset your password, click on the link below <br/> <br/>	<center><a href='http://"
				+ ConfigurationProperties.host
				+ ":"
				+ ConfigurationProperties.port
				+ "/SafeRideHome/safeRideHomeController?requestFrom=resetPassword&key=asdjkhasw392NCWEUHF834HEUIH348R32892U3EMQX21SDALKJ34k2j34h234n2jk3n42uh323ui2uh32g42y3u4y2h342h4239reh9324rfwhe239478h&userId="
				+ userId
				+ "'> Click here to reset your password</a> </center>	<br/>	</br>	Have a Safe Ride Home!!!	<br/> <br/>	- Team SafeRideHome.	<br/> <br/> <br/> <br/>	Follow us on Facebook at https://www.facebook.com/safe.rides.9 for any changes in Safe Ride timings and updates.	</br></br>	This is an automatic mail. Please do not respond to this message. For further queries, contact us at (660) 562-1245. 		</body>	</html>	";
		if (dao.validateUserDetailsForPasswordReset(userId, phoneNumber,
				emailId)) {
			if (sendEmail(emailId, "Safe Ride Home - Password Reset", msg)) {
				return "MAILSENT";
			} else {
				return "MAILEXCEPTION";
			}
		}
		return "INVALIDDETAILS";
	}
}
