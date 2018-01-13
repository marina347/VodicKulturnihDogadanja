package hr.foi.vodickulturnihdogadanja.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import hr.foi.vodickulturnihdogadanja.R;

/**
 * Created by Mateja on 12-Jan-18.
 */

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwitchNotification();
    }

    public void SwitchNotification() {
        Switch switchNotification = (Switch) getActivity().findViewById(R.id.switch_notification);
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //TODO If the switch button is on
                    Toast.makeText(getActivity(),"Switch is on",Toast.LENGTH_SHORT).show();
                }
                else {
                    // TODO If the switch button is off
                    Toast.makeText(getActivity(),"Switch is on",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
