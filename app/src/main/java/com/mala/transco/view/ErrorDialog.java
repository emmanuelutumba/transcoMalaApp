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

public class ErrorDialog extends Dialog {
    @BindView(R.id.error_message)
    TextView txtMessage;

    @BindView(R.id.btn_close)
    TextView btn_close;

    public ErrorDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_error);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        bindEvents();
    }

    public static ErrorDialog context(Context context) {
        ErrorDialog errorDialog = new ErrorDialog(context);
        return errorDialog;
    }


    public ErrorDialog setMessage(String message) {
        txtMessage.setText(message);
        return this;
    }


    private void bindEvents() {
        btn_close.setOnClickListener(view -> {
            this.dismiss();
        });
    }


}
