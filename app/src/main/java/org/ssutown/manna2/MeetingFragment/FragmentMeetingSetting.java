package org.ssutown.manna2.MeetingFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.ssutown.manna2.Fragment.FragmentHome;
import org.ssutown.manna2.MeetingListview.ListViewAdapter;
import org.ssutown.manna2.MeetingRoom.User;
import org.ssutown.manna2.NoticeListview.ListViewItem;
import org.ssutown.manna2.R;
import org.ssutown.manna2.MeetingRoom.meeting_Info;

import java.util.ArrayList;

public class FragmentMeetingSetting extends Fragment {
    TextView textView;
    public static meeting_Info meeting_info;


    public static FragmentMeetingSetting newInstance(String text){
        FragmentMeetingSetting fragmentMeetingSetting=new FragmentMeetingSetting();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentMeetingSetting.setArguments(bundle);
        return fragmentMeetingSetting;
    }
    @Nullable @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_meeting_setting,container,false);

        meeting_info = MeetingMainActivity.info;
        Button setmeeting = (Button)view.findViewById(R.id.setmeeting);
        Button kakaoInvite = (Button)view.findViewById(R.id.invite);
        final Button exit = (Button)view.findViewById(R.id.exit);

        ArrayList<User> users= MeetingMainActivity.users;

        final org.ssutown.manna2.NoticeListview.ListViewAdapter adapter = new org.ssutown.manna2.NoticeListview.ListViewAdapter();
        final ListView listview = (ListView)view.findViewById(R.id.memberlistview);
        listview.setAdapter(adapter);

        setmeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Editmeeting.class);
                intent.putExtra("info",meeting_info);
                startActivity(intent);

            }
        });

        final Context context = this.getActivity();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("미팅탈퇴");
                alert.setMessage("미팅방을 탈퇴하시겠습니까?");

                alert.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getActivity(),"탈퇴되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
            }
        });



        kakaoInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDefaultFeedTemplate();
            }
        });

        for(int i = 0; i<users.size(); i++){
            adapter.addItem(users.get(i).getAnimal(),users.get(i).getNickname(),users.get(i).getUserID(),"");
        }

        return view;
    }

    private void sendDefaultFeedTemplate() {

        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("일정 공유 MANNA",
                        "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                        LinkObject.newBuilder().setWebUrl("https://dev.kakao.com")
                                .setMobileWebUrl("https://dev.kakao.com").build())
                        .setDescrption("MANNA로 초대되었습니다")
                        .build())
                .addButton(new ButtonObject("MANNA으로 이동하기", LinkObject.newBuilder()
                        .setWebUrl("'https://dev.kakao.com")
                        .setMobileWebUrl("'https://dev.kakao.com")
                        .setAndroidExecutionParams("meeting_info="+meeting_info.getMeeting_id()+":"+meeting_info.getMeeting_name())
                        .build()))
                .build();

        KakaoLinkService.getInstance().sendDefault(getActivity(), params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());

            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                Toast.makeText(getActivity(),"초대에 성공하였습니다",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void out(){
    }




}
