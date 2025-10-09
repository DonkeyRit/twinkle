package com.github.donkeyrit.twinkle.bll.models;

import com.github.donkeyrit.twinkle.dal.models.User;

/**
 * Holds the currently logged-in user's session info in memory, for the
 * lifetime of the running process only.
 *
 * Previously this was backed by java.util.prefs.Preferences, which persists
 * to OS-level storage (registry / plist / dotfile) that outlives the app and
 * is readable by anything running as the same OS user. The app always starts
 * on the login screen regardless, so nothing relied on that persistence -
 * it only left session data sitting on disk between runs.
 */
public class UserInformation
{
	private static volatile int id;
	private static volatile String login = "";
	private static volatile boolean role;

	public static void setUser(User user)
	{
		id = user.getId();
		login = user.getLogin();
		role = user.isRole();
	}

	public static int getId()
	{
		return id;
	}

	public static String getLogin()
	{
		return login;
	}

	public static boolean isRole()
	{
		return role;
	}
}
