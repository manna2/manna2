package org.ssutown.manna2.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.MsalException;
import com.microsoft.identity.client.MsalServiceException;
import com.microsoft.identity.client.MsalUiRequiredException;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jiyeon on 2017-08-16.
 */
public class OutlookLogin extends AppCompatActivity {
    
    final static String CLIENT_ID = "8124e310-520e-4c43-b3d6-d553c7ec72fd";
    final static String SCOPES [] = {"https://graph.microsoft.com/Calendars.Read"};
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me/events";

    /* UI & Debugging Variables */
    private static final String TAG = MainActivity.class.getSimpleName();
    Button callGraphButton;
    Button signOutButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference outlookdb = database.getReference();
    long userID = FragmentHome.userID;

    /* Azure AD Variables */
    private static PublicClientApplication sampleApp;
    private static AuthenticationResult authResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlook_login);

        callGraphButton = (Button) findViewById(R.id.callGraph);
        signOutButton = (Button) findViewById(R.id.clearCache);

        callGraphButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCallGraphClicked();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSignOutClicked();
            }
        });

  /* Configure your sample app and save state for this activity */
        sampleApp = null;
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    this.getApplicationContext(),
                    CLIENT_ID);
        }

  /* Attempt to get a user and acquireTokenSilent
   * If this fails we do an interactive request
   */
        List<User> users = null;

        try {
            users = sampleApp.getUsers();

            if (users != null && users.size() == 1) {
          /* We have 1 user */

                sampleApp.acquireTokenSilentAsync(SCOPES, users.get(0), getAuthSilentCallback());
            } else {
          /* We have no user */

          /* Let's do an interactive request */
                sampleApp.acquireToken(this, SCOPES, getAuthInteractiveCallback());
            }
        } catch (MsalClientException e) {
            Log.d(TAG, "MSAL Exception Generated while getting users: " + e.toString());

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "User at this position does not exist: " + e.toString());
        }

    }

//
// App callbacks for MSAL
// ======================
// getActivity() - returns activity so we can acquireToken within a callback
// getAuthSilentCallback() - callback defined to handle acquireTokenSilent() case
// getAuthInteractiveCallback() - callback defined to handle acquireToken() case
//

    public Activity getActivity() {
        return this;
    }

    /* Callback method for acquireTokenSilent calls
     * Looks if tokens are in the cache (refreshes if necessary and if we don't forceRefresh)
     * else errors that we need to do an interactive request.
     */
    private AuthenticationCallback getAuthSilentCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
            /* Successfully got a token, call Graph now */
                Log.d(TAG, "Successfully authenticated");

            /* Store the authResult */
                authResult = authenticationResult;

            /* call graph */
                callGraphAPI();

            /* update the UI to post call Graph state */
                updateSuccessUI();

            }

            @Override
            public void onError(MsalException exception) {
            /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());

                if (exception instanceof MsalClientException) {
                /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                /* Exception when communicating with the STS, likely config issue */
                } else if (exception instanceof MsalUiRequiredException) {
                /* Tokens expired or no session, retry with interactive */
                }
            }

            @Override
            public void onCancel() {
            /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }


    /* Callback used for interactive request.  If succeeds we use the access
         * token to call the Microsoft Graph. Does not check cache
         */
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
            /* Successfully got a token, call graph now */
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());

            /* Store the auth result */
                authResult = authenticationResult;

            /* call Graph */
                callGraphAPI();

            /* update the UI to post call Graph state */
                updateSuccessUI();

            }

            @Override
            public void onError(MsalException exception) {
            /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());

                if (exception instanceof MsalClientException) {
                /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
            /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    /* Set the UI for successful token acquisition data */
    private void updateSuccessUI() {
        callGraphButton.setVisibility(View.INVISIBLE);
        signOutButton.setVisibility(View.VISIBLE);
        findViewById(R.id.welcome).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.welcome)).setText("Welcome, " +
                authResult.getUser().getName());
        findViewById(R.id.graphData).setVisibility(View.VISIBLE);
    }

    /* Use MSAL to acquireToken for the end-user
     * Callback will call Graph api w/ access token & update UI
     */
    private void onCallGraphClicked() {
        sampleApp.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());
    }

    /* Handles the redirect from the System Browser */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sampleApp.handleInteractiveRequestRedirect(requestCode, resultCode, data);
    }

    /* Use Volley to make an HTTP request to the /me endpoint from MS Graph using an access token */
    private void callGraphAPI() {
        Log.d(TAG, "Starting volley request to graph");

    /* Make sure we have a token to send to graph */
        if (authResult.getAccessToken() == null) {return;}

        RequestQueue queue = Volley.newRequestQueue(this);
        JSONObject parameters = new JSONObject();

        try {
            parameters.put("key", "value");
        } catch (Exception e) {
            Log.d(TAG, "Failed to put parameters: " + e.toString());
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, MSGRAPH_URL,
                parameters,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            /* Successfully called graph, process data and send to UI */
                Log.d(TAG, "Response: " + response.toString());

                try { // json 파싱
                    ParseEvent(response);
                }
                catch (Exception e)
                { e.printStackTrace(); }

                updateGraphUI(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + authResult.getAccessToken());
                return headers;
            }
        };

        Log.d(TAG, "Adding HTTP GET to Queue, Request: " + request.toString());

        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    /* Sets the Graph response */
    private void updateGraphUI(JSONObject graphResponse) {
        TextView graphText = (TextView) findViewById(R.id.graphData);
        graphText.setText(graphResponse.toString());
    }

    /* Clears a user's tokens from the cache.
 * Logically similar to "sign out" but only signs out of this app.
 */
    private void onSignOutClicked() {

    /* Attempt to get a user and remove their cookies from cache */
        List<User> users = null;

        try {
            users = sampleApp.getUsers();

            if (users == null) {
            /* We have no users */

            } else if (users.size() == 1) {
            /* We have 1 user */
            /* Remove from token cache */
                sampleApp.remove(users.get(0));
                updateSignedOutUI();

            }
            else {
            /* We have multiple users */
                for (int i = 0; i < users.size(); i++) {
                    sampleApp.remove(users.get(i));
                }
            }

            Toast.makeText(getBaseContext(), "Signed Out!", Toast.LENGTH_SHORT)
                    .show();

        } catch (MsalClientException e) {
            Log.d(TAG, "MSAL Exception Generated while getting users: " + e.toString());

        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "User at this position does not exist: " + e.toString());
        }
    }

    /* Set the UI for signed-out user */
    private void updateSignedOutUI() {
        callGraphButton.setVisibility(View.VISIBLE);
        signOutButton.setVisibility(View.INVISIBLE);
        findViewById(R.id.welcome).setVisibility(View.INVISIBLE);
        findViewById(R.id.graphData).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.graphData)).setText("No Data");
    }

    public void ParseEvent(JSONObject event) throws Exception{
        JSONArray events = event.getJSONArray("value");

        Log.i("eventssize", String.valueOf(events.length()));
        for(int i=0;i<events.length();i++){
            String eventname = events.getJSONObject(i).getString("subject");
            String start = events.getJSONObject(i).getJSONObject("start").getString("dateTime");
            String end = events.getJSONObject(i).getJSONObject("end").getString("dateTime");

            Log.i("valuscontent", eventname + start + end);

            if (!start.substring(0,10).equals(end.substring(0,10))){
                start = start.substring(0,10);
                end = end.substring(0,10);
            }

            saveEventtoFirebase(eventname,start,end);
        }



    }
    public void saveEventtoFirebase(String event, String start, String end){
        long uniquekey = MakeRandom();
        String eventname = event;
        String eventstart = start;
        String eventend = end;

        if(start.contains("T")){
            String tempstart[] = eventstart.split("T");
            String tempstart1 = tempstart[0];
            String tempstart2 = tempstart[1];

            String startday1[] = tempstart1.split("-");
            String starttime[] = tempstart2.split(":");

            String startyear = startday1[0];
            String startmonth = startday1[1];
            String startday = startday1[2];

            String starthour = starttime[0];
            String startminute = starttime[1];

            String tempend[] = eventend.split("T");
            String tempend1 = tempend[0];
            String tempend2 = tempend[1];


            String endday1[] = tempend1.split("-");
            String endtime[] = tempend2.split(":");

            String endyear = endday1[0];
            String endmonth = endday1[1];
            String endday = endday1[2];

            String endhour = endtime[0];
            String endminute = endtime[1];

            CalendarItem list = new CalendarItem(eventname,eventstart,eventend,uniquekey, startyear, startmonth,
                    startday,starthour,startminute, endyear, endmonth, endday, endhour, endminute);

            outlookdb.child("user_Info").child(String.valueOf(userID)).child("calendar").push().setValue(list);
            list.tostring();

        }
        else{
            String startday1[] = start.split("-");

            String startyear = startday1[0];
            String startmonth = startday1[1];
            String startday = startday1[2];

            String endday1[] = end.split("-");

            String endyear = endday1[0];
            String endmonth = endday1[1];
            String endday = endday1[2];

            CalendarItem list = new CalendarItem(eventname,eventstart,eventend,uniquekey, startyear, startmonth,
                    startday,"x","x", endyear, endmonth, endday, "x", "x");

            outlookdb.child("user_Info").child(String.valueOf(userID)).child("calendar").push().setValue(list);
            list.tostring();
        }

    }

    public long MakeRandom(){
        Random random = new Random();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String today = df.format(date);
        String ran = String.valueOf(random.nextInt()%9000+10000);

        String id = today+ran;
        long noticeid = Long.valueOf(id);
        return noticeid;
    }
}