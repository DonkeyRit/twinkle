package com.github.donkeyrit.twinkle.bll.models;

import com.github.donkeyrit.twinkle.dal.models.User;
import java.util.prefs.Preferences;

public class UserInformation 
{
	private static final String PREF_ROLE = "role";
	private static final String PREF_LOGIN = "login";
	private static final String PREF_PASS = "password";
	private static final String PREF_ID = "id";


	private static Preferences preferences;

	static {
		preferences = Preferences.userNodeForPackage(UserInformation.class);
	}

	public static void setUser(User user)
	{
		preferences.putInt(PREF_ID, user.getId());
		preferences.put(PREF_LOGIN, user.getLogin());
		preferences.put(PREF_PASS, user.getPassword());
		preferences.putBoolean(PREF_ROLE, user.isRole());
	}

	public static int getId()
	{
		return preferences.getInt(PREF_ID, 0);
	}

	public static String getLogin()
	{
		return preferences.get(PREF_LOGIN, "");
	}

	public static String getPassword()
	{
		return preferences.get(PREF_PASS, "");
	}

	public static void setPassword(String password)
	{
		preferences.put(PREF_PASS, password);
	}

	public static boolean isRole()
	{
		return preferences.getBoolean(PREF_ROLE, false);
	}	
}
