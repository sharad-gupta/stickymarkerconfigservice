package com.shgupta.stickymarker.apigateway;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import com.shgupta.stickymarker.apigateway.service.UserAccountLoginService;
import com.shgupta.stickymarker.apigateway.service.UserAccountLogoutService;
import com.shgupta.stickymarker.apigateway.service.UserCreateAccountConfirmationService;
import com.shgupta.stickymarker.apigateway.service.UserCreateAccountService;


public class EntryPoint {

	public static void main(String args[]) {
		TextIO textIO = TextIoFactory.getTextIO();

		while (true) {
			int ret = textIO.newIntInputReader()
					.read("What you want to do\n 1 - Account Create\n 2 - Account Confirm\n 3 - Login\n 4 - Logout\n");
			switch (ret) {
			case 1:
				String usernameCr = textIO.newStringInputReader().read("Enter the username");
				String password = textIO.newStringInputReader().withMinLength(8).withInputMasking(true)
						.read("Enter the password");
				String emailAddress = textIO.newStringInputReader().read("Enter valid email address");
				UserCreateAccountService createAccountService = new UserCreateAccountService();
				createAccountService.createAccount(usernameCr, password, emailAddress);
				break;
			case 2:
				String usernameCn = textIO.newStringInputReader().read("Enter the username");
				String confirmationCode = textIO.newStringInputReader().read("Enter the confirmation code received");
				UserCreateAccountConfirmationService createAccountConfirmationService = new UserCreateAccountConfirmationService();
				createAccountConfirmationService.confirmSignUp(confirmationCode, usernameCn);
				break;
			case 3:
				String usernameL = textIO.newStringInputReader().read("Enter the username");
				String passwordL = textIO.newStringInputReader().withInputMasking(true).read("Enter the password");
				UserAccountLoginService accountLoginService = new UserAccountLoginService();
				accountLoginService.login(usernameL, passwordL);
				break;
			case 4:
				String accessToken = textIO.newStringInputReader().read("Enter the access token for user to logout");
				UserAccountLogoutService accountLogoutService = new UserAccountLogoutService();
				accountLogoutService.logout(accessToken);
				break;
			default:
				log("Invalid input provided " + ret);
			}
		}
	}

	public static void log(String msg) {
		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + " Thread "
				+ Thread.currentThread().getName() + " - " + msg);
	}
}
