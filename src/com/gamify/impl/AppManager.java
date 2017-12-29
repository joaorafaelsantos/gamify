package com.gamify.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gamify.interf.InterfaceApp;
import com.gamify.model.App;
import com.gamify.model.User;

public class AppManager implements InterfaceApp {

	String userAuth = "joaorsantos"; // To change when add auth (token)

	static List<App> apps = new ArrayList<App>();

	static AppManager am = null;

	public static AppManager getInstance() {
		if(am == null) {
			am = new AppManager();
			App a1 = new App("app1", "joaorsantos", "Gamify UI", "Marketing", "Lorem Ipsum"); // To remove when add MongoDB
			App a2 = new App("app1", "rcosta", "randomp", "Entertainment", "Lorem Ipsum 2"); // To remove when add MongoDB
			apps.add(a1); // To remove when add MongoDB
			apps.add(a2); // To remove when add MongoDB
		}
		return am;
	}

	// Create new app

	@Override
	public void createApp(String appID, String userID, String appName, String type, String description) {
		App a = new App(appID, userID, appName, type, description);
		apps.add(a);
	}

	// Get all apps

	@Override
	public List<App> getApps(String userRequested) {

		boolean permission;

		if (userRequested.equals(userAuth)) {
			permission = true;
		}
		else {
			permission = false;
		}

		if (permission == true) { 
			List<App> filteredApps = new ArrayList<App>();
			for(App app:apps) {
				// List only apps from that user on all apps available
				if (app.getUserID().equals(userRequested)) {
					filteredApps.add(app);
				}
			}
			return filteredApps;
		}
		else if (permission == false) {
			// The user is not authorized to see apps from another user - TO DO: Send error	
		}
		return null;

	}

	@Override
	public App getApp(String userID, String appID) {
		for(App app:apps) {
			if(app.getAppID().equals(appID)) {
				if(app.getUserID().equals(userAuth)) {
					return app;
				}
				else {
					// The user is not authorized to see that app - TO DO: Send error
				}
			}
			else {
				// The app not exists - TO DO: Send error
			}
		}
		return null;
	}

	@Override
	public void changeApp(String appID, String appName, String type, String description) {
		for(App app:apps) {
			if (app.getAppID().equals(appID)) {
				if (app.getUserID().equals(userAuth) ) {
					App newApp = new App(appID, app.getUserID(),appName, type, description);
					int i = apps.indexOf(app);
					apps.set(i, newApp);
				}
			}
		}
	}

	@Override
	public void removeApp(String appID) {
		for(App app:apps) {
			if (app.getAppID().equals(appID)) {
				if (app.getUserID().equals(userAuth)) { 
					apps.remove(app);
				}
				else {
					// The user is not authorized to remove - TO DO: Send error
				}
			}
			else {
				// The app not exists - TO DO: Send error
			}
		}
	}


}

