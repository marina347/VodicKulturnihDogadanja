package hr.foi.air.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

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

/**
 * Klasa za prikazivanje profila korisnika
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

    UserProfilePresenter userProfilePresenter;
    Bitmap bitmap;
    public static int RESULT_LOAD_IMAGE = 1;
    ProgressDialog nDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
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
        outputImage.setEnabled(false);
    }

    /**
     * Metoda za otvaranje fragmenta za uređivanje profila
     * @param view
     */
    @OnClick(R.id.btn_edit_profile_data)
    public void open_click (View view) {
        UserProfileEditFragment userProfileEditFragment = new UserProfileEditFragment();
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,userProfileEditFragment, "edit_profile_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Metoda za dohvacanje podataka
     */
    private void TryGetData() {
        int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
        userProfilePresenter.tryViewData(userId);
    }

    /**
     * Metoda za prikaz podataka na profilu korisnika
     * @param userModel
     */
    public void onSuccess (UserModel userModel) {
        outputName.setText(userModel.getName());
        outputSurname.setText(userModel.getSurname());
        outputUsername.setText(userModel.getUsername());
        outputEmail.setText(userModel.getEmail());
        bitmap= Base64Coding.decodeBase64(userModel.getPicture());
        outputImage.setImageBitmap(bitmap);
        nDialog.dismiss();
    }

    /**
     * Metoda za ucitavanje dok se podaci ne prikazu
     */
    private void spinnerLoad(){
        nDialog = new ProgressDialog( getActivity());
        nDialog.setMessage("Ucitavam...");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();
    }
}
