package SMSLink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
 
/**
    Requires org.json package
*/
import org.json.JSONObject;                 
  
/**

    See Main.java for Usage Examples

*/

public class SMSLinkSMSGateway {
    
    /**

      HTTPS JSON API Endpoint:  https://secure.smslink.ro/sms/gateway/communicate/json.php
      HTTP JSON API Endpoint:   http://www.smslink.ro/sms/gateway/communicate/json.php

    */

    private String endpointURL = "https://secure.smslink.ro/sms/gateway/communicate/json.php";

    private String connection_id;
    private String password;
    
    private String encodingScheme = "UTF-8";
    
    private boolean enableLogging = false;
    
    /**
     * Initialize SMSLink - SMS Gateway
     *
     *  Initializing SMS Gateway will require the parameters $connection_id and $password. connection_id and password can be generated at 
     *   https://www.smslink.ro/sms/gateway/setup.php after authenticated with your account credentials.
     * 
     * @param connection_id SMSLink - SMS Gateway - Connection ID
     * @param password      SMSLink - SMS Gateway - Password
     */
    
    public SMSLinkSMSGateway(String connection_id, String password) {
        this.connection_id = connection_id;
        this.password = password;
    }
    
    /**
     * Enable Log
     */   
    public void enableLogging() {
        this.enableLogging = true;
    }
    
    /**
     * Disable Log
     */
    public void disableLogging() {
        this.enableLogging = false;
    }

    private static java.util.HashMap<String, String> createParams(String action) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("action", action);
        return map;
    }
    
    private static void addParameter(java.util.Map<String, String> map, String key, String value) {
        if(value != null) {
            map.put(key, value);
        }
    }
    
    private static void addParameter(java.util.Map<String, String> map, String key, Integer value) {
        if(value != null) {
            map.put(key, String.valueOf(value));
        }
    }
        
    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    private JSONObject sendRequest(java.util.Map<String, String> map) 
    {
        String requestURL = endpointURL;
        String key = null;
        String val = null;

        try {
            requestURL += "?connection_id=" + URLEncoder.encode(connection_id, encodingScheme);
            requestURL += "&password=" + URLEncoder.encode(password, encodingScheme);
            
            Set<String> keys = map.keySet();
            Iterator<String> i = keys.iterator();
                        
            while(i.hasNext()) {
                key = (String) i.next();
                val = map.get(key);
                if(val != null) {
                    requestURL += "&" + URLEncoder.encode(key, encodingScheme) + "=" + URLEncoder.encode(val, encodingScheme);
                }
            }
            
            logMessage("Request URL: " + requestURL);
            
            URL url = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
             
            String requestResponse = convertStreamToString(urlConnection.getInputStream());
            
            if(requestResponse != null) {
                if(requestResponse.length() > 0) {                    
                    JSONObject json = new JSONObject(requestResponse);
                    return json;
                }
            }
        } catch(ConnectException e) {
            logMessage("Connection Error: " + e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    private boolean validateRequestResponse(JSONObject json) {
        if(json != null) {
            try {
                String ResponseType = json.getString("response_type");
                String ResponseID   = json.getString("response_id");
            
                logMessage("Response Type: " + ResponseType + ", Response ID: " + ResponseID + ", Request Response: " + json.toString());
                
                if (!"MESSAGE".equals(ResponseType))
                {
                  logMessage("Response is a failure message.");
                }                
                else
                {
                  logMessage("Response is a success message.");
                  return true;                  
                }                                               
            } catch(Exception e) {
                e.printStackTrace();
            }
        }        
        return false;
    }
    
    private void logMessage(String logInputMessage) {
        if (this.enableLogging) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat logDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String logOutputMessage = logDateTime.format(cal.getTime()) + " - Log - " + logInputMessage;
            System.out.println(logOutputMessage);
        }
    }
    
    /**
     * Sends SMS
     * 
     * @param to Receiver mobile phone number. Phone numbers should be formatted as a Romanian national mobile phone number (07xyzzzzzz)
     *           or as an International mobile phone number (00 + Country Code + Phone Number, example 0044zzzzzzzzz).
     *
     * @param message Message of the SMS, up to 160 alphanumeric characters, or longer than 160 characters. 
     *
     * @return String on success or null on failure
     */
    public String sendMessage(String to, String message) {
        HashMap<String, String> map = new HashMap<String, String>();        
        
        addParameter(map, "to", to);
        addParameter(map, "message", message);
        
        JSONObject requestResponse = sendRequest(map);
        
        if (validateRequestResponse(requestResponse)) {            
            try {
                return requestResponse.getString("message_id");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    /**
     * Account Balance
     * 
     * @return String on success or null on failure
     */
    public String accountBalance() {
        HashMap<String, String> map = new HashMap<String, String>(); 

        addParameter(map, "mode", "Credit");

        JSONObject requestResponse = sendRequest(map);
 
        if (validateRequestResponse(requestResponse)) {            
            try {
                return requestResponse.getString("response_message");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}