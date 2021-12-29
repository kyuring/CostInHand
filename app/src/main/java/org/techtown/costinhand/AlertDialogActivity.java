package org.techtown.costinhand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AlertDialogActivity extends Dialog {
    private Context mContext;
    private String detailText = "";
    private String titleText ="";
    private int buttonCount = 2;
    public AlertDialogActivity(@NonNull Context context, String detailText, String titleText, int buttonCount) {
        super(context);
        this.mContext = context;
        this.detailText = detailText;
        this.titleText = titleText;
        this.buttonCount = buttonCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        TextView alert_dialog_detail_text = (TextView) findViewById(R.id.alert_dialog_detail_text);
        alert_dialog_detail_text.setText(detailText);

        TextView alert_title_text = (TextView) findViewById(R.id.alert_title_text);
        alert_title_text.setText(titleText);

        LinearLayout oneButton = (LinearLayout) findViewById(R.id.oneButton);
        LinearLayout twoButton = (LinearLayout) findViewById(R.id.twoButton);
        if(buttonCount == 1){
            twoButton.setVisibility(View.GONE);
            oneButton.setVisibility(View.VISIBLE);
            Button btnOK = (Button) findViewById(R.id.btnOK);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }else if(buttonCount == 2) {
            oneButton.setVisibility(View.GONE);
            twoButton.setVisibility(View.VISIBLE);
            Button btnCancle = (Button) findViewById(R.id.btnCancle);
            btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            Button btnSave = (Button) findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }





}