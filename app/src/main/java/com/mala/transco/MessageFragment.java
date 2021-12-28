package com.mala.transco;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mala.transco.view.ErrorDialog;
import com.mala.transco.view.SuccessDialog;
import com.mala.transco.view.WaitingDialog;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageFragment extends Fragment {

    @BindView(R.id.faq)
    TextView faqTxt;

    @BindView(R.id.sendBtn)
    TextView sendBtn;

    @BindView(R.id.open_cam)
    TextView openCam;

    @BindView(R.id.num_bus)
    EditText num_bus;

    @BindView(R.id.ligne)
    EditText ligne;

    @BindView(R.id.date)
    EditText date;

    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.media)
    ImageView media;

    private NavController navController;
    private MessageViewModel messageViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.messageViewModel = new ViewModelProvider(requireActivity()).get(MessageViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);

        String faqData = getArguments().getString("faqData");
        Faq faq = new Gson().fromJson(faqData, new TypeToken<Faq>() {
        }.getType());
        faqTxt.setText(faq.getDescription());


        File file = new File(requireActivity().getFilesDir(), "transco.png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Uri uriCarPhoto = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", file);

        ActivityResultLauncher resultContract = registerForActivityResult(
                new ActivityResultContracts.TakePicture(), result -> {
                    if (result) {
                        try {
                            Bitmap bitmapCaptureImage = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uriCarPhoto);
                            media.setImageBitmap(bitmapCaptureImage);
                        } catch (Exception e) {

                        }
                    }
                });

        openCam.setOnClickListener(view1 -> {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                resultContract.launch(uriCarPhoto);
            }

        });

        sendBtn.setOnClickListener(view1 -> {

            if (!num_bus.getText().toString().equals("") &&
                    !ligne.getText().toString().equals("") &&
                    !content.getText().toString().equals("")) {

                Message message = new Message();
                message.setObject(faq.getDescription());
                message.setFaq(faq);
                message.setNumBus(num_bus.getText().toString());
                message.setLigne(ligne.getText().toString());
                message.setDate(date.getText().toString());
                message.setContent(content.getText().toString());

                WaitingDialog waitingDialog = new WaitingDialog(requireContext());
                waitingDialog.show();
                this.messageViewModel.send(message).observe(requireActivity(), messageHttpResponse -> {
                    waitingDialog.dismiss();
                    if (messageHttpResponse.getCode().equals("200")) {

                        num_bus.setText("");
                        ligne.setText("");
                        date.setText("");
                        content.setText("");

                        SuccessDialog.context(requireContext()).setMessage(messageHttpResponse.getMessage()).show();
                    } else {
                        ErrorDialog.context(requireContext()).setMessage(messageHttpResponse.getMessage()).show();
                    }
                });
            } else
                ErrorDialog.context(requireContext()).setMessage("Veuillez renseigner les champs requises svp").show();


        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);
    }
}