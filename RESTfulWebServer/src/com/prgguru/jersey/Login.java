package com.prgguru.jersey;

import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ejb.UserEJB;

//Path: http://localhost/<appln-folder-name>/login
@ApplicationScoped
@ManagedBean
@Path("/login")
@Stateless
public class Login {

	@Inject
	UserEJB userEJB;

	// HTTP Get Method
	@GET
	// Path: http://localhost/<appln-folder-name>/login/dologin
	@Path("/dologin")
	// Produces JSON as response
	@Produces(MediaType.APPLICATION_JSON)
	// Query parameters are parameters:
	// http://localhost/<appln-folder-name>/login/dologin?username=abc&password=xyz
	public String doLogin(@QueryParam("username") String uname,
			@QueryParam("password") String pwd) {
		String response = "";
		if (checkCredentials(uname, pwd)) {
			response = Utitlity.constructJSON("login", true);
		} else {
			response = Utitlity.constructJSON("login", false,
					"Incorrect Email or Password");
		}
		return response;
	}

	/**
	 * Method to check whether the entered credential is valid
	 * 
	 * @param uname
	 * @param pwd
	 * @return
	 */
	private boolean checkCredentials(String uname, String pwd) {
		System.out.println("Inside checkCredentials");
		boolean result = false;
		if (Utitlity.isNotNull(uname) && Utitlity.isNotNull(pwd)) {
			try {
				result = userEJB.checkLogin(uname, pwd);
				// result = DBConnection.checkLogin(uname, pwd);
				// System.out.println("Inside checkCredentials try "+result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// System.out.println("Inside checkCredentials catch");
				result = false;
			}
		} else {
			// System.out.println("Inside checkCredentials else");
			result = false;
		}

		return result;
	}

}
