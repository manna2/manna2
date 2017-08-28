package org.ssutown.manna2.MaterialCalendar_Person;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import org.ssutown.manna2.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by YNH on 2017. 8. 19..
 */

public class AddAppointment_Person extends Activity {

    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

    private com.google.api.services.calendar.Calendar mService = null;
    GoogleAccountCredential mCredential;


    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;


    String accountName;

    String name;
    String start;
    String end;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_material_add);

        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        Button btnAdd = (Button)findViewById(R.id.EDIT);
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (EasyPermissions.hasPermissions(
                        getApplicationContext(), android.Manifest.permission.GET_ACCOUNTS)) {

                    SharedPreferences selectedAccountName = getSharedPreferences("selectedAccountName", Context.MODE_PRIVATE);
                    accountName = selectedAccountName.getString("accountName_a","");

                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
//                        accountName 에 계정이 들어간 것을 확인 할 수 있었음
                        Toast toast = Toast.makeText(getApplicationContext(),accountName,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,0);
                        toast.show();
                    } else {
                        // Start a dialog from which the user can choose an account
                        startActivityForResult(
                                mCredential.newChooseAccountIntent(),
                                REQUEST_ACCOUNT_PICKER);
                    }
                } else {
                    // Request the GET_ACCOUNTS permission via a user dialog
//                    EasyPermissions.requestPermissions(
//                            this,
//                            "This app needs to access your Google account (via Contacts).",
//                            REQUEST_PERMISSION_GET_ACCOUNTS,
//                            android.Manifest.permission.GET_ACCOUNTS);
                    Log.v("googleLog","requestPermissions");

                }

                Toast toast = Toast.makeText(getApplicationContext(),accountName,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();

                Log.v("googleLog","setSelectedAccountName");

                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

                final EditText edit_name = (EditText)findViewById(R.id.appoint_name);

                final DatePicker startDatePicker = (DatePicker)findViewById(R.id.startDay);
                final DatePicker endDatePicker = (DatePicker)findViewById(R.id.endDay);
                final TimePicker startTimePicker = (TimePicker)findViewById(R.id.startTime);
                final TimePicker endTimePicker = (TimePicker)findViewById(R.id.endTime);

                String startDate = dateformat.format
                        (new java.sql.Date(startDatePicker.getYear()-1900, startDatePicker.getMonth(), startDatePicker.getDayOfMonth()));
                String endDate = dateformat.format
                        (new java.sql.Date(endDatePicker.getYear()-1900, endDatePicker.getMonth(), endDatePicker.getDayOfMonth()));
                String startTime = timeformat.format
                        (new java.sql.Time(startTimePicker.getHour(), startTimePicker.getMinute(),0));
                String endTime = timeformat.format
                        (new java.sql.Time(endTimePicker.getHour(), endTimePicker.getMinute(),0));

                name = edit_name.getText().toString();

                start = new StringBuilder()
                        .append(startDate)
                        .append('T')
                        .append(startTime)
                        .append("+09:00")
                        .toString();
                end = new StringBuilder()
                        .append(endDate)
                        .append('T')
                        .append(endTime)
                        .append("+09:00")
                        .toString();

                new MakeRequestTask(mCredential).execute();

                Log.v("googleLog","End of MakeRequestTask...Please");

                finish();
            }
        });

    }

    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

        private com.google.api.services.calendar.Calendar mService = null;
        private Exception mLastError = null;


        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();

            Log.v("googleLog","MakeRequestTask");

        }

        /**
         * Background task to call Google Calendar API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {

                Log.v("googleLog","doInBackground...Please");

                insertEvent(mService,name,start,end);
                return getDataFromApi();
            } catch (Exception e) {

                Log.v("googleLog","doInBackground_exception");

                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         * @return List of Strings describing returned events.
         * @throws IOException
         */

        public void insertEvent(com.google.api.services.calendar.Calendar mService,String name_a,String start_a,String end_a) throws IOException {

            Log.v("googleLog","insertEvent()");


            try {
                com.google.api.services.calendar.model.Calendar calendar =
                        mService.calendars().get("primary").execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

//            Event event = new Event()
//                    .setSummary(name_a);
//
//            DateTime startDateTime = new DateTime(start_a);
//            EventDateTime start = new EventDateTime()
//                    .setDateTime(startDateTime)
//                    .setTimeZone("Asia/Seoul");
//            event.setStart(start);
//
//            DateTime endDateTime = new DateTime(end_a);
//            EventDateTime end = new EventDateTime()
//                    .setDateTime(endDateTime)
//                    .setTimeZone("Asia/Seoul");
//            event.setEnd(end);

            Event event = new Event()
                    .setSummary("qwer")
                    .setLocation("800 Howard St., San Francisco, CA 94103")
                    .setDescription("A chance to hear more about Google's developer products.");

            DateTime startDateTime = new DateTime("2017-08-22T09:00:00-07:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2017-08-22T17:00:00-07:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setEnd(end);


            String calendarId = "primary";
            try {
                mService.events().insert(calendarId, event).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private List<String> getDataFromApi() throws IOException {

            // List the next 15 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(20)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                    end = event.getEnd().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s ~ %s)", event.getSummary(), start,end));
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<String> output) {

        }

        @Override
        protected void onCancelled() {

            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            AddAppointment_Person.REQUEST_AUTHORIZATION);
                }
            }

        }

        void showGooglePlayServicesAvailabilityErrorDialog(
                final int connectionStatusCode) {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            Dialog dialog = apiAvailability.getErrorDialog(
                    AddAppointment_Person.this,
                    connectionStatusCode,
                    REQUEST_GOOGLE_PLAY_SERVICES);
            dialog.show();
        }

    }
}