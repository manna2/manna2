package org.ssutown.manna2.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.gson.JsonObject;
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
 * Created by Jiyeon on 2017-08-18.
 */

public class OutlookCalendar extends AppCompatActivity {

    final static String CLIENT_ID = "8124e310-520e-4c43-b3d6-d553c7ec72fd";
    final static String SCOPES [] = {"https://graph.microsoft.com/Calendars.Read"};
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me/events?$select=subject,start,end";

    private static PublicClientApplication sampleApp;
    private static AuthenticationResult authResult;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference outlookdb = database.getReference();
    long userID = FragmentHome.userID;

    private static final String TAG = MainActivity.class.getSimpleName();
    Button olbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlook_calendar);

        Button olbutton = (Button) findViewById(R.id.outlookcalendar);
        olbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCallGraphClicked();
            }
        });

        sampleApp = null;
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    this.getApplicationContext(),
                    CLIENT_ID);
        }

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

    public Activity getActivity() {
        return this;
    }

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
//                updateSuccessUI();

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
//                updateSuccessUI();


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

    private void onCallGraphClicked() {
        sampleApp.acquireToken(getActivity(), SCOPES, getAuthInteractiveCallback());
    }
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
//                Log.d(TAG, "Response: " + response.toString());

                try { // json 파싱
                    ParseEvent(response);
                }
                catch (Exception e)
                { e.printStackTrace(); }

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


        //여기서 작업하고 나머지

        onPause();


    }


    @Override
    public void onPause() {
        super.onPause();

        // Remove the activity when its off the screen
        finish();
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
