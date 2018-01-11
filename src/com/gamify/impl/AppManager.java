package com.gamify.impl;

import java.util.ArrayList;
import java.util.List;

import com.gamify.data.AppData;
import com.gamify.interf.InterfaceApp;
import com.gamify.model.App;

public class AppManager implements InterfaceApp {

	String userAuth = "joaorsantos"; // To change when add auth (token)

	static List<App> apps = new ArrayList<App>();

	static AppManager am = null;

	public static AppManager getInstance() {
		if(am == null) {
			am = new AppManager();
		}
		return am;
	}

	// Create new app

	@Override
	public void createApp(String appID, String userID, String appName, String type, String description) {
		App app = new App(appID, userID, appName, type, description);
		AppData appData = AppData.getInstance();				
		appData.insertData(app);
	}

	// Get all apps

	@Override
	public List<App> getApps(String userRequested) {
		if (userRequested.equals(userAuth)) {
			AppData appData = AppData.getInstance();				
			return appData.getData(userRequested);
		}
		else {
			// The user is not authorized to see apps from another user - TO DO: Send error	
		}

		return null;
	}

	// Get specific app

	@Override
	public List<App> getApp(String userRequested, String appID) {
		if (userRequested.equals(userAuth)) {
			AppData appData = AppData.getInstance();				
			return appData.getSpecificData(userRequested, appID);
		}
		else {
			// The user is not authorized to see apps from another user - TO DO: Send error	
		}

		return null;
	}

	// Change app

	@Override
	public void changeApp(String appID, String appName, String type, String description) {

		boolean exists = false;
		AppData appData = AppData.getInstance();		
		apps = appData.getAllData();

		for(App app:apps) {
			// Check if app exists
			if (app.getAppID().equals(appID)) {
				exists = true;
				// Check if the user have permission to change the app
				if (app.getUserID().equals(userAuth) ) {
					appData.changeData(appID, appName, type, description);
				}
				else {
					// The user is not authorized to change the apps from another user - TO DO: Send error	
				}
			}
		}
		
		if (exists == false) {
			// There are no app with that ID - TO DO: Send error
		}
	}

	// Remove app

	@Override
	public void removeApp(String appID) {
		
		boolean exists = false;
		AppData appData = AppData.getInstance();		
		apps = appData.getAllData();

		for(App app:apps) {
			// Check if app exists
			if (app.getAppID().equals(appID)) {
				exists = true;
				// Check if the user have permission to change the app
				if (app.getUserID().equals(userAuth) ) {
					appData.removeData(appID);
				}
				else {
					// The user is not authorized to change the apps from another user - TO DO: Send error	
				}
			}
		}
		
		if (exists == false) {
			// There are no app with that ID - TO DO: Send error
		}


}
}

