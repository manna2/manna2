package org.ssutown.manna2.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.HomeFragment.ChangeProfile;
import org.ssutown.manna2.HomeFragment.MemoListAdapter;
import org.ssutown.manna2.HomeFragment.MemoListItem;
import org.ssutown.manna2.HomeFragment.profile;
import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.MeetingFragment.Users;
import org.ssutown.manna2.MeetingRoom.User;
import org.ssutown.manna2.R;

public class FragmentHome extends Fragment {

    TextView textView;

    public static long userID;
    private static final int PROFILE_CHANGED = 100;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
    TextView nickname;
    ImageView profileimage;
    public static FragmentHome newInstance(String text){
        FragmentHome fragmentHome=new FragmentHome();
        Bundle bundle=new Bundle();
        bundle.putString("text",text);
        fragmentHome.setArguments(bundle);
        return fragmentHome;
    }


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        userID = ((MainActivity)getActivity()).getUserID();
        ImageButton changeProfile= (ImageButton)view.findViewById(R.id.chagne);

        Log.d("userID", "Home user ID: " + userID);

        //메모 어댑터 설정
        final MemoListAdapter adapter;
        adapter = new MemoListAdapter();
        final ListView memo = (ListView)view.findViewById(R.id.memolistview);
        memo.setAdapter(adapter);

        nickname = (TextView)view.findViewById(R.id.nickname);
        profileimage = (ImageView)view.findViewById(R.id.profile);

        //메모추가
        final ImageButton addmemo =(ImageButton) view.findViewById(R.id.memoplus);
        addmemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editmemo = (EditText)getView().findViewById(R.id.memoedit);
                String key = databaseReference.child(String.valueOf(userID)).push().getKey();

                MemoListItem memo = new MemoListItem(editmemo.getText().toString(), key);

                databaseReference.child("user_Info").child(String.valueOf(userID)).child("memo").child(key).setValue(memo);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editmemo.getWindowToken(), 0);
                editmemo.setText("");

            }
        });

//        adapter.addItem("sdfsdfsf","aaaa");

        //메모불러오기
        databaseReference.child("user_Info").child(String.valueOf(userID)).child("memo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    adapter.addItem(ds.getValue(MemoListItem.class).getMemo(), ds.getValue(MemoListItem.class).getUniquekey());
                    Log.i("gsa",ds.getValue(MemoListItem.class).getMemo());
                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //profile설정
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getActivity(), ChangeProfile.class);
                change.putExtra("userid", String.valueOf(userID));
                getActivity().startActivityForResult(change,100);
//                startActivityForResult(change,100);
            }
        });

        databaseReference.child("user_Info").child(String.valueOf(userID)).child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    nickname.setText(dataSnapshot.getValue(profile.class).getNickname());
                    profileimage.setImageDrawable(setProfileIamge(dataSnapshot.getValue(profile.class).getAnimal()));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    public Drawable setProfileIamge(String animal) {
        Drawable drawable;
        switch (animal) {
            case "bear":
                drawable = getActivity().getResources().getDrawable(R.drawable.bear);
                break;
            case "cat":
                drawable = getResources().getDrawable(R.drawable.cat);
                break;
            case "cheetha":
                drawable = getResources().getDrawable(R.drawable.cheetah);
                break;
            case "cow":
                drawable = getResources().getDrawable(R.drawable.cow);
                break;
            case "fox":
                drawable = getResources().getDrawable(R.drawable.fox);
                break;
            case "hedgehog":
                drawable = getResources().getDrawable(R.drawable.hedgehog);
                break;
            case "tiger":
                drawable = getResources().getDrawable(R.drawable.tiger);
                break;
            case "wolf":
                drawable = getResources().getDrawable(R.drawable.wolf);
                break;
            case "pig":
                drawable = getResources().getDrawable(R.drawable.meeting3);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.bear);
        }
        return drawable;
    }

    public void test(){
        databaseReference.setValue("hello");
    }

}
