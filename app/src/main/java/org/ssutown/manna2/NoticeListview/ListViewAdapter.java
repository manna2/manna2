package org.ssutown.manna2.NoticeListview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ssutown.manna2.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    String animal;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notice_listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.usericon2) ;
        TextView userName = (TextView) convertView.findViewById(R.id.noticeusername) ;
        TextView noticecontents = (TextView) convertView.findViewById(R.id.noticecontents) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

//        int image = R.drawable.bear;

        Log.d("listviewAdapter", "getView: " + animal);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageResource(setProfileIamge(animal));
        iconImageView.getLayoutParams().height = 130;
        iconImageView.getLayoutParams().width = 130;
        userName.setText(listViewItem.getUsername());
        noticecontents.setText(listViewItem.getContents());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String icon, String Username, String contents, String noticeID) {
        ListViewItem item = new ListViewItem();
        Log.d("adapter", "addItem: ");

        animal = icon;

        item.setContents(contents);
        item.setNoticeID(noticeID);
        item.setUserIcon(icon);
        item.setUsername(Username);

        listViewItemList.add(item);
    }

    public void clear(){
        listViewItemList.clear();
    }


    public int setProfileIamge(String animal) {
        int drawable;
        switch (animal) {
            case "bear":
                drawable = R.drawable.bear;
                break;
            case "cat":
                drawable = R.drawable.cat;
                break;
            case "cheetha":
                drawable = R.drawable.cheetah;
                break;
            case "cow":
                drawable = R.drawable.cow;
                break;
            case "fox":
                drawable = R.drawable.fox;
                break;
            case "hedgehog":
                drawable = R.drawable.hedgehog;
                break;
            case "tiger":
                drawable = R.drawable.tiger;
                break;
            case "wolf":
                drawable = R.drawable.wolf;
                break;
            case "pig":
                drawable = R.drawable.meeting3;
                break;
            default:
                drawable = R.drawable.bear;
        }
        return drawable;
    }

}

