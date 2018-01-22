package hr.foi.air.fragments;

import android.app.ProgressDialog;
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
import hr.foi.air.R;
import hr.foi.air.interactor.impl.UserInteractorImpl;
import hr.foi.air.model.UserModel;
import hr.foi.air.presenter.UserProfilePresenter;
import hr.foi.air.presenter.impl.UserProfilePresenterImpl;
import hr.foi.air.utils.Base64Coding;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.UserProfileView;

import static android.app.Activity.RESULT_OK;
import static hr.foi.air.fragments.UserProfileFragment.RESULT_LOAD_IMAGE;

/**
 * Klasa koja služi za uređivanje osobnih podataka na profilu korisnika
 * Created by Mateja on 20-Jan-18.
 */

public class UserProfileEditFragment extends Fragment implements UserProfileView {

    @BindView(R.id.img_profile_photo)
    ImageButton outputImage;

    @BindView(R.id.edit_name)
    EditText outputName;

    @BindView(R.id.edit_surname)
    EditText outputSurname;

    @BindView(R.id.edit_username)
    EditText outputUsername;

    @BindView(R.id.edit_password)
    EditText outputPassword;

    int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
    ProgressDialog nDialog;
    UserModel userModel = new UserModel();
    UserProfilePresenter userProfilePresenter;
    Bitmap bitmap;
    String email;
    String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTheme(R.style.ColoredHandleCursorTheme);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserProfilePresenter userProfilePresenter = new UserProfilePresenterImpl(new UserInteractorImpl(), this);
        this.userProfilePresenter = userProfilePresenter;
        spinnerLoad();
        TryGetData();
        EnableEdit();
        TryAddPicture();
    }

    @OnClick(R.id.btn_save_profile_data)
    public void save_click (View view) {
        TryEditData();
        Toast.makeText(getActivity(),R.string.succesfully_editing_data,Toast.LENGTH_SHORT).show();
    }

    /**
     * Metoda za prikaz podataka na profilu korisnika
     * @param userModel
     */
    @Override
    public void onSuccess(UserModel userModel) {
        outputName.setText(userModel.getName());
        outputSurname.setText(userModel.getSurname());
        outputUsername.setText(userModel.getUsername());
        //outputPassword.setText(userModel.getPassword());
        bitmap=Base64Coding.decodeBase64(userModel.getPicture());
        if(bitmap!=null) {
            outputImage.setImageBitmap(bitmap);
            LoggedUserData.getInstance().setImage(Base64Coding.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100));
        }
        email = userModel.getEmail();
        password = userModel.getPassword();
        nDialog.dismiss();
    }

    /**
     * Metoda za dohvacanje podataka korisnika
     */
    private void TryGetData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        userProfilePresenter.tryViewData(userId);
    }

    /**
     * Metoda za promjenu podataka korisnika
     */
    private void TryEditData() {
        userModel.setUserId(userId);
        userModel.setName(outputName.getText().toString());
        userModel.setSurname(outputSurname.getText().toString());
        userModel.setUsername(outputUsername.getText().toString());
        userModel.setEmail(email);

        if (outputPassword.getText().toString() == "") {
            userModel.setPassword(password);
        }
        else {
            userModel.setPassword(outputPassword.getText().toString());
        }

        if (bitmap != null) {
            userModel.setPicture(Base64Coding.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 50));
        }
        else {
            userModel.setPicture("");
        }

        userProfilePresenter.tryEditData(userModel);
    }

    /**
     * Metoda za odabir slike iz galerije
     */
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

    /**
     * Metoda za prikaz odabrane slike na profilu
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * Metoda za omogućavanje unosa na dodir EditView-a
     */
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    };

    private void EnableEdit() {
        outputName.setOnTouchListener(handleTouch);
        outputSurname.setOnTouchListener(handleTouch);
        outputUsername.setOnTouchListener(handleTouch);
        outputPassword.setOnTouchListener(handleTouch);
    }

    /**
     * Metoda za ucitavanje dok se podaci ne prikazu
     */
    private void spinnerLoad(){
        nDialog = new ProgressDialog( getActivity());
        nDialog.setMessage("Učitavam...");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }
}
