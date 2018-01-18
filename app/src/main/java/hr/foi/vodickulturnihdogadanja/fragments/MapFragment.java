package hr.foi.vodickulturnihdogadanja.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import hr.foi.vodickulturnihdogadanja.R;

/**
 * Created by LEGION Y520 on 10.1.2018..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment supportMapFragment;
    GoogleMap map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, null);
        supportMapFragment = new SupportMapFragment();
        getFragmentManager().beginTransaction().add(R.id.frame, supportMapFragment).commit();
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        supportMapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }
}
