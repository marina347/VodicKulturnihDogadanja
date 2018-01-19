package hr.foi.vodickulturnihdogadanja.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.impl.UserInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.UserModel;
import hr.foi.vodickulturnihdogadanja.presenter.UserProfilePresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.UserProfilePresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.Base64Coding;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.UserProfileView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mateja on 15-Nov-17.
 */

public class UserProfileFragment extends Fragment implements UserProfileView {

    @BindView(R.id.img_profile_photo)
    ImageButton outputImage;

    @BindView(R.id.edit_name)
    EditText outputName;

    @BindView(R.id.edit_surname)
    EditText outputSurname;

    @BindView(R.id.edit_username)
    EditText outputUsername;

    @BindView(R.id.edit_email)
    EditText outputEmail;

    @BindView(R.id.edit_password)
    EditText outputPassword;

    UserProfilePresenter userProfilePresenter;
    Bitmap bitmap;
    public static int RESULT_LOAD_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife.bind(getActivity());
        UserProfilePresenter userProfilePresenter = new UserProfilePresenterImpl(new UserInteractorImpl(), this);
        this.userProfilePresenter = userProfilePresenter;

        TryGetData();
        EnableEdit();
        TryAddPicture();
    }

    @OnClick(R.id.btn_save_profile_data)
    public void save_click (View view) {
        TryEditData();
        Toast.makeText(getActivity(),R.string.succesfully_editing_data,Toast.LENGTH_SHORT).show();
    }

    private void TryGetData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        userProfilePresenter.tryViewData(userId);
    }

    private void TryEditData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();

        UserModel userModel = new UserModel();

        userModel.setUserId(userId);
        userModel.setName(outputName.getText().toString());
        userModel.setSurname(outputSurname.getText().toString());
        userModel.setUsername(outputUsername.getText().toString());
        userModel.setEmail(outputEmail.getText().toString());
        userModel.setPassword(outputPassword.getText().toString());
        userModel.setPicture(Base64Coding.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100));

        userProfilePresenter.tryEditData(userModel);
    }

    public void onSuccess (UserModel userModel) {
        outputName.setText(userModel.getName());
        outputSurname.setText(userModel.getSurname());
        outputUsername.setText(userModel.getUsername());
        outputEmail.setText(userModel.getEmail());
        outputPassword.setText(userModel.getPassword());
        bitmap=Base64Coding.decodeBase64(userModel.getPicture());
        outputImage.setImageBitmap(bitmap);
    }

    //select image from gallery
    private void TryAddPicture() {
        outputImage = (ImageButton) getView().findViewById(R.id.img_profile_photo);
        outputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GaleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //GaleryIntent.setType("image/*");
                startActivityForResult(GaleryIntent, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imageUri = data.getData();
            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                //setting image to imageButton
                outputImage.setImageBitmap(bitmap);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //for edit data task, enable on touch
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    };

    //enable edit on touch editText
    private void EnableEdit() {
        outputName.setOnTouchListener(handleTouch);
        outputSurname.setOnTouchListener(handleTouch);
        outputUsername.setOnTouchListener(handleTouch);
        outputEmail.setOnTouchListener(handleTouch);
        outputPassword.setOnTouchListener(handleTouch);
    }
}
