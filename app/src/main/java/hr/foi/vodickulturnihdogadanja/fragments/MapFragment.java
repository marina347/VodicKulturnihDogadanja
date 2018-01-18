package hr.foi.vodickulturnihdogadanja.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import hr.foi.vodickulturnihdogadanja.R;

/**
 * Created by LEGION Y520 on 10.1.2018..
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment supportMapFragment;
    GoogleMap map;
    String location;
    List<Address> addressList;
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
        location = getArguments().getString("location");
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            addressList=geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address=addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng).title(location));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }
}
