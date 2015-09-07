package com.jp.plugin.whatsapp;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import java.util.Iterator;
import android.net.Uri;

public class StartApp extends CordovaPlugin {

	//Plugins created by JP on 7 Sep 2015

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	        if (action.equals("start")) {
	            this.start(args, callbackContext);
	        }
			else {
				callbackContext.error("Invalid Selection");
			}

			return true;
    }

	public void start(JSONArray args, CallbackContext callback) {

			String com_name = null;
			String activity = null;
			Intent LaunchIntent;

			try {
				if (args.get(0) instanceof JSONArray) {
					com_name = args.getJSONArray(0).getString(0);
					activity = args.getJSONArray(0).getString(1);
				}
				else {
					com_name = args.getString(0);
				}

				/**
				 * call activity
				 */
				if(activity != null) {
					if(com_name.equals("action")) {
						// sample: android.intent.action.VIEW
						if(activity.indexOf(".") > 0) {
							LaunchIntent = new Intent(activity);
						}
						else {
							LaunchIntent = new Intent("android.intent.action." + activity);
						}
					}
					else {
						LaunchIntent = new Intent();
						LaunchIntent.setComponent(new ComponentName(com_name, activity));
					}
				}
				else {
					LaunchIntent = this.cordova.getActivity().getPackageManager().getLaunchIntentForPackage(com_name);
				}

				/**
				 * put arguments
				 */
				if(args.length() > 1) {
					JSONArray params = args.getJSONArray(1);
					JSONObject key_value;
					String key;
					String value;

					for(int i = 0; i < params.length(); i++) {
						if (params.get(i) instanceof JSONObject) {
							Iterator<String> iter = params.getJSONObject(i).keys();

							 while (iter.hasNext()) {
								key = iter.next();
								try {
									value = params.getJSONObject(i).getString(key);

									LaunchIntent.putExtra(key, value);
								} catch (JSONException e) {
									callback.error("json params: " + e.toString());
								}
							}
						}
						else {
							LaunchIntent.setData(Uri.parse(params.getString(i)));
						}
					}
				}

				this.cordova.getActivity().startActivity(LaunchIntent);
				callback.success();

			} catch (JSONException e) {
				callback.error("json: " + e.toString());
			} catch (Exception e) {
				callback.error("intent: " + e.toString());
	            final String appPackageName = cordova.getActivity().getPackageName();
	            try {
	                cordova.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + com_name)));
	            }
	            catch (android.content.ActivityNotFoundException anfe) {
	                cordova.getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + com_name)));
	            }
	        }
    }

}
