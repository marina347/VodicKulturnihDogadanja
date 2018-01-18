package hr.foi.vodickulturnihdogadanja;

import org.junit.Before;
import org.junit.Test;

import hr.foi.vodickulturnihdogadanja.fragments.SettingsFragment;
import hr.foi.vodickulturnihdogadanja.interactor.SettingsInteractor;
import hr.foi.vodickulturnihdogadanja.interactor.impl.SettingsInteractorImpl;
import hr.foi.vodickulturnihdogadanja.model.SettingsModel;
import hr.foi.vodickulturnihdogadanja.presenter.impl.SettingsPresenterImpl;
import hr.foi.vodickulturnihdogadanja.view.SettingsView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by marbulic on 17.01.18..
 */

public class SettingsMVPTest {
    SettingsInteractor interactor;
    SettingsView view;
    SettingsPresenterImpl settingsPresenter;

    @Before
    public void setUp() throws Exception {
        view = mock(SettingsFragment.class);
        interactor = mock(SettingsInteractorImpl.class);
        settingsPresenter = new SettingsPresenterImpl(interactor, view);
    }

    @Test
    public void testDisplayGetSettings() {
        int userId = 1;
        SettingsModel settingsModel = mock(SettingsModel.class);

        settingsPresenter.tryGetSettings(userId);
        verify(interactor).getSettings(userId);
        settingsPresenter.onSuccess(settingsModel);
        verify(view).onSuccess(settingsModel);
    }

    @Test
    public void testDisplayEditSettings() {
        SettingsModel settingsModel = mock(SettingsModel.class);

        settingsPresenter.tryEditSettings(settingsModel);
        verify(interactor).editSettings(settingsModel);
        settingsPresenter.onSuccess(settingsModel);
        verify(view).onSuccess(settingsModel);

    }
}

