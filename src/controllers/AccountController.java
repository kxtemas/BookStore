package controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import beans.User;
import business.UserBusinessService;

@ManagedBean
@ViewScoped
public class AccountController 
{
	@Inject
	UserBusinessService ubs;
	
	/**
	 * Call authenticate in the UserBusinessService
	 * @param user
	 * @return
	 */
	public String authenticate(User user) 
	{
		// if user is authenticated go home else display error
		if (ubs.authenticate(user)) 
		{
			FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("user", user);
			return "LoginSuccess.xhtml";
		} else {
			return "LoginFailed.xhtml";
		}
	}

	/**
	 * Calls register in the UserBusinessService
	 * @param user
	 * @return
	 */
	public String register(User user) 
	{
		// if user is found go home else display error
		if (ubs.register(user)) {
			return "LoginSuccess.xhtml";
		} else {
			return "FailPage.xhtml";
		}
	}
}