package org.ssutown.manna2.Fragment;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ssutown.manna2.HomeFragment.MemoListAdapter;
import org.ssutown.manna2.HomeFragment.MemoListItem;
import org.ssutown.manna2.MainActivity;
import org.ssutown.manna2.MeetingListview.ListViewAdapter;
import org.ssutown.manna2.R;

public class FragmentHome extends Fragment {

    public static long userID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();
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

        Log.d("userID", "Home user ID: " + userID);

        //메모 어댑터 설정
        final MemoListAdapter adapter;
        adapter = new MemoListAdapter();
        final ListView memo = (ListView)view.findViewById(R.id.memolistview);
        memo.setAdapter(adapter);

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

        return view;
    }



}
