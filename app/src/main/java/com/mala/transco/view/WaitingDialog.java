package com.mala.transco.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.mala.transco.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitingDialog extends Dialog {

    @BindView(R.id.message)
    TextView message;
    public WaitingDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_waiting);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);

        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String message){
        this.message.setText(message);
    }
}
