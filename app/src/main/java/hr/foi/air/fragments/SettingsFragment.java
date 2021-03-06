package hr.foi.air.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.R;
import hr.foi.air.activity.NavigationActivity;
import hr.foi.air.interactor.impl.SettingsInteractorImpl;
import hr.foi.air.model.SettingsModel;
import hr.foi.air.presenter.SettingsPresenter;
import hr.foi.air.presenter.impl.SettingsPresenterImpl;
import hr.foi.air.utils.LocalHelper;
import hr.foi.air.utils.LoggedUserData;
import hr.foi.air.view.SettingsView;

/**
 * Klasa za prikaz postavki
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

    /**
     * Metoda za prikaz postavki na ekranu
     * @param settings
     */
    @Override
    public void onSuccess(SettingsModel settings) {
        int state = settings.getPushUpNotification();
        int stateLanguage = settings.getLanguageId();

        if (state == 1) {switchNotification.setChecked(true);}
        else if (state == 0) {switchNotification.setChecked(false);}

        if (stateLanguage == 1) {
            languageEng.setChecked(true);
        }
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

    /**
     * Metoda za promjenu postavki i vraćanje na listu događaja
     * @param textView
     */
    @OnClick(R.id.save_settings)
    public void save(TextView textView) {
        TryEditSettings();
        refresh();
        //Toast.makeText(getActivity(),"Postavke su promjenjene",Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                clearActivityStack();
                clearFragmentStack();
            }
        }, 1000); //1sec
    }

    /**
     * Metoda za ukljucivanje/iskjucivanje obavijesti
     * @param button
     * @param isChecked
     */
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

    /**
     * Metoda za odabir jezika aplikacije
     * @param button
     * @param checked
     */
    @OnCheckedChanged({R.id.language_eng, R.id.language_hr})
    public void onRadioButtonCheckChanged(CompoundButton button, boolean checked) {
        if(checked) {
            switch (button.getId()) {
                case R.id.language_eng:
                    language = 1;
                    updateViews("en");
                    break;
                case R.id.language_hr:
                    language = 2;
                    updateViews("hr");
                    break;
            }
        }
    }

    /**
     * Metoda za spremanje postavki
     */
    private void TryEditSettings() {
        settingsModel.setUserId(userId);
        settingsModel.setPushUpNotification(notification);
        settingsModel.setLanguageId(language);

        settingsPresenter.tryEditSettings(settingsModel);
    }

    private void updateViews(String lang) {
        Context context = LocalHelper.setLocale(getActivity(), lang);
        Resources resources = context.getResources();
    }

    /**
     * Metoda za osvježenje trenutnog fragmenta (kako bi se jezik prikazao nakon odabira)
     */
    private void refresh() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof SettingsFragment) {
            FragmentTransaction fragTransaction =  (getActivity()).getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();}
    }

    /**
     * Metode za ciscenje stoga i povratak na popis dogadaja
     */
    private void clearFragmentStack() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    private void clearActivityStack() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
