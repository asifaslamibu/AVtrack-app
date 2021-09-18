package com.abs.avtrack.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.abs.avtrack.HttpHandler;
import com.abs.avtrack.MapsActivity;
import com.abs.avtrack.R;
import com.abs.avtrack.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private List<Adapter> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    public static int getid;
    public static String veh_name;
    public static String veh_speed;
    public static String devicetime;
    public static String attributes;
    public static String taskname;
    public static String taskdetail;

    public static String user_s;
    public static String employee_id;
    public static String pass;
    public static String date;
    public static String end_location;
    public static String lat;
    public static String lng;
    public static String assign_by;
    private ListView lv;
    public ProgressDialog progressBar;
    ArrayList<HashMap<String, String>> contactList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        contactList = new ArrayList<>();
        lv = (ListView) root.findViewById(R.id.list);
        new fetch_login1().execute();
        return root;
    }
    private class fetch_login1 extends AsyncTask<Void, Void, Void> {


        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(getContext());
              progressBar.setMessage("Please wait...");
             progressBar.setCancelable(false);
           progressBar.show();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url1 =  "http://151.106.59.94:8012/avconnect/api/position.php?user=1&accesskey=12345";
            String jsonStr = sh.makeServiceCall(url1);
            System.out.println(url1);
            String access$100 =TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Response from url: ");
            sb2.append(jsonStr);
            Log.e(access$100, sb2.toString());
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONObject(jsonStr).getJSONArray("data");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject jb = (JSONObject) ((JSONObject) contacts.get(i)).get("task");
                        getid = Integer.parseInt(jb.getString(SessionManager.KEY_ID));
                        veh_name = jb.getString("name");
                        veh_speed = jb.getString("speed");
                        devicetime = jb.getString("devicetime");
                        attributes = jb.getString("attributes");
                        lat = jb.getString("latitude");
                        lng = jb.getString("longitude");
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", String.valueOf(getid));
                        contact.put("vname", veh_name);
                        contact.put("speed", veh_speed+"km/h");
                        contact.put("devicetime", devicetime);
                        contact.put("lat", lat);
                        contact.put("lng", lng);
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    String TAG = null;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Json parsing error: ");
                    sb3.append(e.getMessage());
                    Log.e(TAG, sb3.toString());
                    Context applicationContext = getContext();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Json parsing error: ");
                    sb.append(e.getMessage());
//                    Toast.makeText(applicationContext, sb.toString(), Toast.LENGTH_LONG).show();

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                //  Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
            }


            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(progressBar.isShowing())
                progressBar.dismiss();
            final ListAdapter adapter = new SimpleAdapter(
                    getContext(), contactList,
                    R.layout.list_design2, new String[]{"vname", "speed",
                    "devicetime"}, new int[]{R.id.name,
                    R.id.speed, R.id.time});

            lv.setAdapter(adapter);
            System.out.println("samad");
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    for (int a=0;a<contactList.size();a++)
                    {
                        HashMap<String, String> hashmap= contactList.get(i);
                        String id = hashmap.get("id");
                        String veh_name = hashmap.get("vname");
                        String speed = hashmap.get("speed");
                        String devicetime = hashmap.get("devicetime");
                        String lat = hashmap.get("lat");
                        String lng = hashmap.get("lng");
                        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MapsActivity.class);
                        intent.putExtra("vname",veh_name);
                        intent.putExtra("speed",speed);
                        intent.putExtra("devicetime",devicetime);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);
                        startActivity(intent);

                    }
                }

            });
        }
    }
}