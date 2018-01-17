package hr.foi.vodickulturnihdogadanja.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.vodickulturnihdogadanja.R;
import hr.foi.vodickulturnihdogadanja.interactor.impl.SettingsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;
import hr.foi.vodickulturnihdogadanja.presenter.SettingsPresenter;
import hr.foi.vodickulturnihdogadanja.presenter.impl.SettingsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.utils.LoggedUserData;
import hr.foi.vodickulturnihdogadanja.view.SettingsView;

/**
 * Created by Mateja on 12-Jan-18.
 */

public class SettingsFragment extends Fragment implements SettingsView {

    @BindView(R.id.switch_notification)
    Switch switchNotification;
    @BindView(R.id.language_hr)
    RadioButton languageHr;
    @BindView(R.id.language_eng)
    RadioButton languageEng;

    int userId = LoggedUserData.getInstance().getTokenModel().getUserId();
    int notification;
    int language;
    private Unbinder unbinder;

    SettingsPresenter settingsPresenter;
    SettingsModel settingsModel = new SettingsModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SettingsPresenter settingsPresenter = new SettingsPresenterImpl(new SettingsInteractorImpl(), this);
        this.settingsPresenter = settingsPresenter;
        settingsPresenter.tryGetSettings(userId);
    }

    @Override
    public void onSuccess(SettingsModel settings) {
        int state = settings.getPushUpNotification();
        int stateLanguage = settings.getLanguageId();

        if (state == 1) {switchNotification.setChecked(true);}
        else if (state == 0) {switchNotification.setChecked(false);}

        if (stateLanguage == 1) {languageEng.setChecked(true);}
        else if (stateLanguage == 2) {languageHr.setChecked(true);}
    }

    @Override
    public void onFailed(String text) {
        showToastOnUI(text);
    }

    private void showToastOnUI(final String msg) {
        final Context ctx = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.save_settings)
    public void save() {
        TryEditSettings();
        Toast.makeText(getActivity(),"Postavke su promjenjene",Toast.LENGTH_SHORT).show();
    }

    @OnCheckedChanged(R.id.switch_notification)
    void onGenderSelected(CompoundButton button, boolean isChecked){
        if (isChecked) {
            notification = 1;
            //Toast.makeText(getActivity(),"Switch is on",Toast.LENGTH_SHORT).show();
        }
        else {
            notification = 0;
            //Toast.makeText(getActivity(),"Switch is off",Toast.LENGTH_SHORT).show();
        }
    }

    @OnCheckedChanged({R.id.language_eng, R.id.language_hr})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            switch (button.getId()) {
                case R.id.language_eng:
                    language = 1;
                    break;
                case R.id.language_hr:
                    language = 2;
                    break;
            }
        }
    }

    private void TryEditSettings() {
        settingsModel.setUserId(userId);
        settingsModel.setPushUpNotification(notification);
        settingsModel.setLanguageId(language);

        settingsPresenter.tryEditSettings(settingsModel);
    }
}