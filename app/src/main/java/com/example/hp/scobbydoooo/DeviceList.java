package com.example.hp.scobbydoooo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DeviceList extends AppCompatActivity {

    ListView devicesList;
    TextView availableDevices;
    List<String> DeviceNames = new ArrayList<String>();
    List<String> DeviceIPs = new ArrayList<String>();
    setDeviceList setIt;
    ImageView line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        devicesList = findViewById(R.id.list);
        availableDevices = findViewById(R.id.tv_availableDevices);
        line = findViewById(R.id.lastLine);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipelayout);
        line.setImageResource(android.R.color.transparent);
        availableDevices.setText("scanning for devices...");

        new DiscoverTask().execute();
        setIt = new setDeviceList();

        devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(availableDevices.getText().equals("Available Devices")) {
                    Intent perroActivity = new Intent(DeviceList.this,Scobby_Doo_Remote.class);
                    perroActivity.putExtra("ip_add",DeviceIPs.get(i));
                    startActivity(perroActivity);
                    finish();
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                DeviceNames.clear();
                DeviceIPs.clear();
                devicesList.setAdapter(null);
                line.setImageResource(android.R.color.transparent);
                availableDevices.setText("scanning for devices...");
                new DiscoverTask().execute();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },500);
            }
        });
    }

    class setDeviceList extends BaseAdapter {

        @Override
        public int getCount() {
            availableDevices.setText("Available Devices");
            line.setImageResource(R.drawable.list_view_divider);
            if(DeviceNames.size() < 1) {
                availableDevices.setText("No Devices Found");
                line.setImageResource(android.R.color.transparent);
            }
            return DeviceNames.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.list_layout, null);

            TextView name = view.findViewById(R.id.tv_name);
            TextView ip = view.findViewById(R.id.tv_ip);

            name.setText(DeviceNames.get(i));
            ip.setText(DeviceIPs.get(i));

            return view;
        }
    }

    private class DiscoverTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try{
               // DeviceNames=new ArrayList<String>();
               // DeviceIPs=new ArrayList<String>();
                String msg="M-SEARCH * HTTP/1.1\\r\\n";
                InetAddress group=null;

                group=InetAddress.getByName("239.255.255.250");
                MulticastSocket s=null;
                s=new MulticastSocket(1800);
                s.joinGroup(group);
                DatagramPacket hi=new DatagramPacket(msg.getBytes(),msg.length(), group,1800);
                s.send(hi);
                byte[]buf=new byte[1000];
                s.setSoTimeout(1000);
                DatagramPacket recv=new DatagramPacket(buf,buf.length);
                s.receive(recv);
                boolean finished=false;
                while(finished==false){
                    try {
                        s.receive(recv);
                        String received = (new String(recv.getData())).substring(0, recv.getLength());
                        if(received.contains("DeviceName:")){
                            String DeviceName =received.split("DeviceName:")[1].split("\r\n")[0];
                            Log.d("DeviceName",DeviceName);
                            DeviceNames.add(DeviceName);
                            DeviceIPs.add(recv.getAddress().toString().replace("/",""));
                        }
                    }
                    catch (SocketTimeoutException e){
                        finished=true;
                    }
                }
            } catch (Exception e) {
                Log.e("Discovery","error",e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            devicesList.setAdapter(setIt);
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeviceList.this);
        alertDialog.setTitle("Leave Application?");
        alertDialog.setMessage("Are you sure you want to leave the application?");
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_devicelist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Refresh) {
            DeviceNames.clear();
            DeviceIPs.clear();
            devicesList.setAdapter(null);
            line.setImageResource(android.R.color.transparent);
            availableDevices.setText("scanning for devices...");
            new DiscoverTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
