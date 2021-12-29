package org.techtown.costinhand;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_home.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_home newInstance(String param1, String param2) {
        Fragment_home fragment = new Fragment_home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //리소스들을 초기화 해주는 단계
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //레이아웃을 inflate 하는 메소드입니다. view 객체를 얻을 수 있어 view와 관련된 객체들을 초기화
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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


        //Button Setting!!
        LinearLayout button_restaurant_menu = (LinearLayout) view.findViewById(R.id.button_restaurant_menu);
        button_restaurant_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("menu", true);
            }
        });
        LinearLayout buttonIngredient = (LinearLayout) view.findViewById(R.id.buttonIngredient);
        buttonIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("ingredient", true);
            }
        });
        LinearLayout buttonCompany = (LinearLayout) view.findViewById(R.id.buttonCompany);
        buttonCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("company", true);
            }
        });
        LinearLayout buttonSource = (LinearLayout) view.findViewById(R.id.buttonSource);
        buttonSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("source", true);
            }
        });
        LinearLayout buttonReceipt = (LinearLayout) view.findViewById(R.id.buttonReceipt);
        buttonReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("receipt", false);
            }
        });
        LinearLayout buttonMonthlyHistory = (LinearLayout) view.findViewById(R.id.buttonMonthlyHistory);
        buttonMonthlyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonEventSetting("monthlyHistory", false);
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
    void ButtonEventSetting(String buttonName, boolean ItemMenuUse) {
        Intent intent;
        if(ItemMenuUse) {
            intent = new Intent(getActivity(), ItemMenuActivity.class);
            intent.putExtra("buttonName", buttonName);
            startActivity(intent);
            getActivity().overridePendingTransition(0,0);
        } else {
            if(buttonName.equals("receipt")) {
                intent = new Intent(getActivity(), ReceiptActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            } else if(buttonName.equals("monthlyHistory")) {
                intent = new Intent(getActivity(), MonthlyHistoryActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        }
    }
}