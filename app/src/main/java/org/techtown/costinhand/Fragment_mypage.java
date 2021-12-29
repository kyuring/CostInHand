package org.techtown.costinhand;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_mypage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_mypage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_mypage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_mypage.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_mypage newInstance(String param1, String param2) {
        Fragment_mypage fragment = new Fragment_mypage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //상단 부분
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d("Fragment_home_java_UserStatucCountResponse [response]", String.valueOf(jsonResponse));
                    UISetting(view, jsonResponse);
                } catch (JSONException e ){
                    e.printStackTrace();
                }
            }
        };
        UserStatusCountRequest userStatusCountRequest = new UserStatusCountRequest(MainActivity.userid, responseListener);
        userStatusCountRequest.setShouldCache(false);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(userStatusCountRequest);




        Intent intent = new Intent(getActivity(), EditUserActivity.class);
        // 정보수정
        LinearLayout mypage_editInfo = (LinearLayout) view.findViewById(R.id.mypage_editInfo);
        mypage_editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("editInfo", intent);
            }
        });
        // 비밀번호 변경
        LinearLayout mypage_editPassword = (LinearLayout) view.findViewById(R.id.mypage_editPassword);
        mypage_editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("editPassword", intent);
            }
        });
        // 로그아웃
        LinearLayout mypage_logOut = (LinearLayout) view.findViewById(R.id.mypage_logOut);
        mypage_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 진행
            }
        });
        // 회원 탈퇴
        LinearLayout mypage_remove = (LinearLayout) view.findViewById(R.id.mypage_removeUser);
        mypage_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("removeUser", intent);
            }
        });
    }
    public void UISetting(View view, JSONObject response) throws JSONException {
        TextView userName = view.findViewById(R.id.userName);
        userName.setText(MainActivity.userName);
        TextView menu_count = view.findViewById(R.id.menu_count);
        TextView food_count = view.findViewById(R.id.food_count);
        TextView source_count = view.findViewById(R.id.source_count);
        TextView company_count = view.findViewById(R.id.company_count);

        menu_count.setText(response.getString("menu_count"));
        food_count.setText(response.getString("food_count"));
        source_count.setText(response.getString("source_count"));
        company_count.setText(response.getString("company_count"));
    }
    void ButtonEventSetting(String buttonName, Intent intent) {
        intent.putExtra("buttonName", buttonName);
        startActivity(intent);
        getActivity().overridePendingTransition(0,0);
    }
}