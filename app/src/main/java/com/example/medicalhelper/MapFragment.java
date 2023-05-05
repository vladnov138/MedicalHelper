package com.example.medicalhelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medicalhelper.databinding.FragmentHomeBinding;
import com.example.medicalhelper.databinding.FragmentMapBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;

public class MapFragment extends Fragment {

    private final Point CIRCLE_CENTER = new Point(59.956, 30.323);
    private final Point CAMERA_CENTER = new Point(55.751574, 37.573856);

    private MapView mapview;
    private MapObjectCollection mapObjects;

    private FragmentMapBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MapKitFactory.setApiKey("0cd599a1-8d6b-4423-a833-4d39ac7785f2");
        MapKitFactory.initialize(((MainActivity) requireActivity()));
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapview = (MapView) root.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(CIRCLE_CENTER, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        return root;
    }


    @Override
    public void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}