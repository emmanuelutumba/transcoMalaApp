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

public class SuccessDialog extends Dialog {
    @BindView(R.id.error_message)
    TextView txtMessage;

    @BindView(R.id.btn_close)
    TextView btn_close;

    private SuccessDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_success);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        bindEvents();
    }

    public static SuccessDialog context(Context context) {
        SuccessDialog successDialog = new SuccessDialog(context);
        return successDialog;
    }

    public SuccessDialog setMessage(String message) {
        txtMessage.setText(message);
        return this;
    }

    private void bindEvents() {
        btn_close.setOnClickListener(view -> {
            this.hide();
        });
    }
}
