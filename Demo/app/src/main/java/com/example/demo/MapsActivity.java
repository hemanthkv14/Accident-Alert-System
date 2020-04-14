package com.example.demo;
import androidx.fragment.app.FragmentActivity;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private HeatmapTileProvider mProvider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap mMap) {
        LatLng hyd = new LatLng(17.361719, 78.475166);
        mMap.addMarker(new MarkerOptions().position(hyd).title("Hyderabad"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hyd));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hyd, 12.0f));
        Resources rs=getResources();
        String[] s=rs.getStringArray(R.array.plc);
        Set<String> d=new HashSet<String>(Arrays.asList(s));
        ArrayList<LatLng> src = new ArrayList<LatLng>();
        ArrayList<LatLng> dst = new ArrayList<LatLng>();
        for(String t:d) {
            String[] x = t.split(" ");
            String[] y=x[0].split(",");
            src.add(new LatLng(Double.parseDouble(y[0]), Double.parseDouble(y[1])));
            y=x[1].split(",");
            dst.add(new LatLng(Double.parseDouble(y[0]), Double.parseDouble(y[1])));
        }
        mProvider = new HeatmapTileProvider.Builder().data(src).build();
        mProvider.setRadius(50);
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        mProvider = new HeatmapTileProvider.Builder().data(dst).build();
        mProvider.setRadius(50);
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }
}
