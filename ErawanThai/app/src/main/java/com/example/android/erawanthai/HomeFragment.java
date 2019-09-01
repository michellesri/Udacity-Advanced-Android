package com.example.android.erawanthai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        yelpApiRequest(view);
        googlePlacesApiRequest(view);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng erawan = new LatLng(37.235223, -121.874177);
        mMap.addMarker(new MarkerOptions().position(erawan).title("Marker for Erawan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(erawan));
        mMap.setMinZoomPreference(14);
    }

    public void yelpApiRequest(View view) {
        String url = "https://api.yelp.com/v3/businesses/4uR0PeEUK_4yb1mF32ZNSQ";
        final TextView yelpDataTv = view.findViewById(R.id.yelp_data);
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        yelpDataTv.setText("Response is: "+ response.substring(0,500));
                        try {
                            renderYelpData(response, yelpDataTv);
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), R.string.yelp_data_error, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                yelpDataTv.setText(error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer _JuO43shWpaT0aXGpg3V-92ndB3aU2ZcXVCwENWS_lCpKbvZ1-IRnYcqITBv9nAlCU_RmLI0oJmvO7DykBo0aaHUKgcK0Gh3bgL4yLIOrIQtpaF55GY8sUy8PrsGXXYx");
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void renderYelpData(String response, TextView tv) throws JSONException {
        JSONObject base = new JSONObject(response);
        Double rating = (Double) base.get("rating");
        Integer reviewCount = (Integer) base.get("review_count");
        String yelpString = getString(R.string.yelp_rating) + rating.toString() + "\n"  + getString(R.string.reviews) + reviewCount.toString();
        tv.setText(yelpString);


    }

    public void googlePlacesApiRequest(View view) {
        final TextView textView = view.findViewById(R.id.gogole_places_data);
        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        String url ="https://maps.googleapis.com/maps/api/place/details/json?key=" + getString(R.string.google_api_key) + "&placeid=ChIJ07FnJmkxjoARKRpM0Au6Yi4";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            renderGooglePlacesData(response, textView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), R.string.google_places_data_error, Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void renderGooglePlacesData(String response, TextView tv) throws JSONException {
        JSONObject base = new JSONObject(response);
        JSONArray jsonHours = base.getJSONObject("result").getJSONObject("opening_hours").getJSONArray("weekday_text");
        StringBuilder hours = new StringBuilder();

        for (int i = 0; i < jsonHours.length(); i++) {
            hours.append(jsonHours.getString(i)).append("\n");
        }

        tv.setText(hours);

    }



}

// yelp api key: _JuO43shWpaT0aXGpg3V-92ndB3aU2ZcXVCwENWS_lCpKbvZ1-IRnYcqITBv9nAlCU_RmLI0oJmvO7DykBo0aaHUKgcK0Gh3bgL4yLIOrIQtpaF55GY8sUy8PrsGXXYx
// business id: "4uR0PeEUK_4yb1mF32ZNSQ"