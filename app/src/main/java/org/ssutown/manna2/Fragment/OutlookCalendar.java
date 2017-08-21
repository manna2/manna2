package org.ssutown.manna2.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;

import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.R;

/**
 * Created by Jiyeon on 2017-08-18.
 */

public class OutlookCalendar extends AppCompatActivity {

    final static String CLIENT_ID = "8124e310-520e-4c43-b3d6-d553c7ec72fd";
    final static String SCOPES [] = {"https://graph.microsoft.com/User.Read"};
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me";


    private static final String TAG = MainActivity.class.getSimpleName();
    Button olbutton;

    private PublicClientApplication sampleApp = OutlookLogin.getSampleApp();
    private AuthenticationResult authResult = OutlookLogin.getAuthResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlook_calendar);

        olbutton = (Button)findViewById(R.id.outlookcalendar);

        olbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowResult();
            }
        });

        sampleApp = null;
        if (sampleApp == null) {
            sampleApp = new PublicClientApplication(
                    this.getApplicationContext(),
                    CLIENT_ID);
        }

    }
    public Activity getActivity() {
        return this;
    }

    public void ShowResult(){
        TextView resultview = (TextView)findViewById(R.id.resultcalendar1);
        resultview.setText(authResult.getUser().getDisplayableId() + '\n'+
                authResult.getUser().getIdentityProvider()+'\n'+
                authResult.getUser().getName()+'\n'+
                authResult.getUser().getUserIdentifier());
    }



}
