package com.example.hp.scobbydoooo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Scobby_Doo_Remote extends AppCompatActivity
{
    public boolean conn = false, pressed = false;
    private ImageView cross,paw;
    private ImageButton dright,dleft,dfront,dback,tright,tleft,tfront,tback,neutral,lleft,lright,sit,stand;
    private SeekBar speedVal;
    public int delay = 35;
    String ip;
    BufferedReader reader;
    GameView videoScreen;
    RobotCom robot = new RobotCom();

    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scobby__doo__remote);
        conn = false;

        dright = findViewById(R.id.ib_rright);
        dleft = findViewById(R.id.ib_rleft);
        dfront = findViewById(R.id.ib_dfront);
        dback = findViewById(R.id.ib_dback);
        neutral = findViewById(R.id.ib_neutral);
        tright = findViewById(R.id.ib_tright);
        tleft = findViewById(R.id.ib_tleft);
        tfront = findViewById(R.id.ib_tfront);
        tback = findViewById(R.id.ib_tback);
        lleft = findViewById(R.id.ib_dleft);
        lright = findViewById(R.id.ib_dright);
        sit = findViewById(R.id.ib_sit);
        stand = findViewById(R.id.ib_stand);
        speedVal = findViewById(R.id.sb_speedBar);
        videoScreen = findViewById(R.id.gv_video);
        cross = findViewById(R.id.tilt_image);
        paw = findViewById(R.id.paw_image);

        ip = getIntent().getStringExtra("ip_add");
        delay = 35;
        robot.openTcp(ip);
        conn = true;
        new VideoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        sit.setEnabled(false);
        sit.setAlpha(0.1f);
        stand.setEnabled(false);
        stand.setAlpha(0.1f);
        dright.setEnabled(false);
        dright.setAlpha(0.1f);
        dleft.setEnabled(false);
        dleft.setAlpha(0.1f);
        dfront.setEnabled(false);
        dfront.setAlpha(0.1f);
        dback.setEnabled(false);
        dback.setAlpha(0.1f);
        tright.setEnabled(false);
        tright.setAlpha(0.1f);
        tleft.setEnabled(false);
        tleft.setAlpha(0.1f);
        tfront.setEnabled(false);
        tfront.setAlpha(0.1f);
        tback.setEnabled(false);
        tback.setAlpha(0.1f);
        lleft.setEnabled(false);
        lleft.setAlpha(0.1f);
        lright.setEnabled(false);
        lright.setAlpha(0.1f);
        paw.setAlpha(0.1f);
        cross.setAlpha(0.1f);
        speedVal.setAlpha(0.1f);

        if (isFirstTime()) {
            Intent intent = new Intent(Scobby_Doo_Remote.this,FirstTime.class);
            startActivity(intent);
        }


        speedVal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                delay = (50 - speedVal.getProgress());
                robot.mTcpClient.delay = delay;
            }
        });

//        dfront.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"FRONT");
//            }
//        });
//
//        dback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"BACK");
//            }
//        });
//
//        dleft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"ROTLEFT");
//            }
//        });
//
//        dright.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"ROTRIGHT");
//            }
//        });
//
//        tfront.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"TILTFRONT");
//            }
//        });
//
//        tback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"TILTBACK");
//            }
//        });
//
//        tleft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"TILTLEFT");
//            }
//        });
//
//        tright.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"TILTRIGHT");
//            }
//        });
//
//        lleft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"LATLEFT");
//            }
//        });
//
//        lright.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"LATRIGHT");
//            }
//        });
//
//        sit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"SIT");
//            }
//        });
//
//        stand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"STAND");
//            }
//        });
//
//        neutral.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                robot.send_general_LUCI_string(280,"NEUTRAL");
//            }
//        });
//


        dfront.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();
                if (action == MotionEvent.ACTION_DOWN)
                {
                    dfront.setImageResource(R.drawable.direction_down_48);
                    pressed = true;
                    new send_action().execute("front.txt");
                    return true;
                }

                else if (action == MotionEvent.ACTION_UP)
                {
                    pressed = false;
                    dfront.setImageResource(R.drawable.direction_up_48);
                    return true;
                }

                return false;
            }
        });

        dback.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    dback.setImageResource(R.drawable.direction_down_48);
                    pressed = true;
                    new send_action().execute("back.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    dback.setImageResource(R.drawable.direction_up_48);
                    return true;
                }
                return false;
            }
        });

        dright.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    dright.setImageResource(R.drawable.r_right_down_48);
                    pressed = true;
                    new send_action().execute("t_right.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    dright.setImageResource(R.drawable.r_right_up_48);
                    return true;
                }

                return false;
            }
        });

        dleft.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    dleft.setImageResource(R.drawable.r_left_down_48);
                    pressed = true;
                    new send_action().execute("t_left.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    dleft.setImageResource(R.drawable.r_left_up_48);
                    return true;
                }

                return false;
            }
        });

        tfront.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    tfront.setImageResource(R.drawable.tilt_down_48);
                    pressed = true;
                    new send_action().execute("tilt_front.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    tfront.setImageResource(R.drawable.tilt_up_48);
                    return true;
                }

                return false;
            }
        });

        tback.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    tback.setImageResource(R.drawable.tilt_down_48);
                    pressed = true;
                    new send_action().execute("tilt_back.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    tback.setImageResource(R.drawable.tilt_up_48);
                    return true;
                }

                return false;
            }
        });

        tright.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    tright.setImageResource(R.drawable.tilt_down_48);
                    pressed = true;
                    new send_action().execute("tilt_right.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    tright.setImageResource(R.drawable.tilt_up_48);
                    return true;
                }
                return false;
            }
        });

        tleft.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    tleft.setImageResource(R.drawable.tilt_down_48);
                    pressed = true;
                    new send_action().execute("tilt_left.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    tleft.setImageResource(R.drawable.tilt_up_48);
                    return true;
                }
                return false;
            }
        });

        lleft.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    lleft.setImageResource(R.drawable.direction_down_48);
                    pressed = true;
                    new send_action().execute("l_left.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    lleft.setImageResource(R.drawable.direction_up_48);
                    return true;
                }

                return false;
            }
        });

        lright.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    lright.setImageResource(R.drawable.direction_down_48);
                    pressed = true;
                    new send_action().execute("l_right.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    lright.setImageResource(R.drawable.direction_up_48);
                    return true;
                }

                return false;
            }
        });

        neutral.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    neutral.setImageResource(R.drawable.neutral_down_48);
                    pressed = true;
                    new send_action().execute("stance.txt");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    neutral.setImageResource(R.drawable.neutral_up_48);

                    sit.setEnabled(true);
                    sit.setAlpha(1f);
                    stand.setEnabled(false);
                    stand.setAlpha(0.1f);
                    dright.setEnabled(true);
                    dright.setAlpha(1f);
                    dleft.setEnabled(true);
                    dleft.setAlpha(1f);
                    dfront.setEnabled(true);
                    dfront.setAlpha(1f);
                    dback.setEnabled(true);
                    dback.setAlpha(1f);
                    tright.setEnabled(true);
                    tright.setAlpha(1f);
                    tleft.setEnabled(true);
                    tleft.setAlpha(1f);
                    tfront.setEnabled(true);
                    tfront.setAlpha(1f);
                    tback.setEnabled(true);
                    tback.setAlpha(1f);
                    lleft.setEnabled(true);
                    lleft.setAlpha(1f);
                    lright.setEnabled(true);
                    lright.setAlpha(1f);
                    paw.setAlpha(1f);
                    cross.setAlpha(1f);
                    speedVal.setAlpha(1f);

                    return true;
                }
                return false;
            }

        });

        stand.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    stand.setImageResource(R.drawable.stand_down_48);
                    pressed = true;
                    new send_action().execute("stand.txt");

                    sit.setEnabled(true);
                    sit.setAlpha(1f);
                    neutral.setEnabled(true);
                    neutral.setAlpha(1f);
                    dright.setEnabled(true);
                    dright.setAlpha(1f);
                    dleft.setEnabled(true);
                    dleft.setAlpha(1f);
                    dfront.setEnabled(true);
                    dfront.setAlpha(1f);
                    dback.setEnabled(true);
                    dback.setAlpha(1f);
                    tright.setEnabled(true);
                    tright.setAlpha(1f);
                    tleft.setEnabled(true);
                    tleft.setAlpha(1f);
                    tfront.setEnabled(true);
                    tfront.setAlpha(1f);
                    tback.setEnabled(true);
                    tback.setAlpha(1f);
                    lleft.setEnabled(true);
                    lleft.setAlpha(1f);
                    lright.setEnabled(true);
                    lright.setAlpha(1f);
                    paw.setAlpha(1f);
                    cross.setAlpha(1f);

                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    stand.setImageResource(R.drawable.stand_up_48);
                    stand.setEnabled(false);
                    stand.setAlpha(0.1f);
                    return true;
                }
                return false;
            }

        });

        sit.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View view, MotionEvent motion) {
                int action = motion.getAction();

                if(action == MotionEvent.ACTION_DOWN) {
                    sit.setImageResource(R.drawable.sit_down_48);
                    pressed = true;
                    new send_action().execute("sit.txt");

                    stand.setEnabled(true);
                    stand.setAlpha(1f);
                    neutral.setEnabled(false);
                    neutral.setAlpha(0.1f);
                    dright.setEnabled(false);
                    dright.setAlpha(0.1f);
                    dleft.setEnabled(false);
                    dleft.setAlpha(0.1f);
                    dfront.setEnabled(false);
                    dfront.setAlpha(0.1f);
                    dback.setEnabled(false);
                    dback.setAlpha(0.1f);
                    tright.setEnabled(false);
                    tright.setAlpha(0.1f);
                    tleft.setEnabled(false);
                    tleft.setAlpha(0.1f);
                    tfront.setEnabled(false);
                    tfront.setAlpha(0.1f);
                    tback.setEnabled(false);
                    tback.setAlpha(0.1f);
                    lleft.setEnabled(false);
                    lleft.setAlpha(0.1f);
                    lright.setEnabled(false);
                    lright.setAlpha(0.1f);
                    paw.setAlpha(0.1f);
                    cross.setAlpha(0.1f);

                    return true;
                }
                else if (action == MotionEvent.ACTION_UP) {
                    pressed = false;
                    sit.setImageResource(R.drawable.sit_up_48);
                    sit.setEnabled(false);
                    sit.setAlpha(0.1f);
                    return true;
                }
                return false;
            }

        });
    }

    public class send_action extends AsyncTask <String, Void, String >
    {
        @Override
        protected String doInBackground(String... params) {
            while(pressed) {
                try {
                    reader = new BufferedReader(new InputStreamReader(getAssets().open(params[0])));
                    String line;
                    byte[] data;
                    int j;

                    while (true) {
                        line = reader.readLine();

                        if (line == null)
                            break;

                        String[] row = line.split(",");
                        data = new byte[row.length];
                        for (j = 0; j < row.length; j++) {
                            data[j] = s2b(row[j]);
                        }

                        sendMessageBytes(data);
                        System.out.println(line);
                    }
                    reader.close();
                } catch (Exception e) {
                    Log.e("TCP", "S: Error", e);
                }
            }
            return "executed";
        }
    }

    public void sendMessageBytes (byte[] data){
            try {
                robot.mTcpClient.sendMessage(data);
            } catch (Exception e) {
                Log.e("SOCKET", "exception", e);
            }
        }

    public static byte s2b(String a)
    {
        char[] buffer = a.toCharArray();
        byte ans = 0;
        for(int i=0; i < buffer.length ; i++)
        {
            ans = (byte) (ans*10 + ((int)buffer[i] - 48));
        }
        return ans;
    }

    private class VideoTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (conn) {
                try {
                    String msg = "REQUEST_VIDEO_LINK";
                    msg = msg.concat("\nFOR_IP:");
                    msg = msg.concat(ip);

                    InetAddress group = null;

                    group = InetAddress.getByName("239.255.255.250");
                    MulticastSocket s = null;
                    s = new MulticastSocket(1700);
                    s.joinGroup(group);
                    DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                            group, 1700);
                    s.send(hi);
                    byte[] buf = new byte[1000];
                    DatagramPacket recv = new DatagramPacket(buf, buf.length);
                    s.setSoTimeout(1000);
                    String received_link = "\n";
                    try {
                        s.receive(recv);
                        s.receive(recv);
                        received_link = (new String(recv.getData())).substring(0, recv.getLength());

                        Log.d("VIDEOLINK:", received_link);
                    } catch (SocketTimeoutException e) {
                        received_link = "\n";
                    }
                    String[] separated = received_link.split("\n");
                    if (separated.length > 2) {

                        Log.d("multicast ip:", separated[2].split(":")[1]);
                        Log.d("multicast port:", separated[3].split(":")[1]);

                        group = InetAddress.getByName(separated[2].split(":")[1]);
                        int port = Integer.parseInt(separated[3].split(":")[1]);


                        TcpClient vid = new TcpClient(new TcpClient.OnMessageReceived() {
                            @Override
                            //here the messageReceived method is implemented
                            public void messageReceived(String message) {
                                //this method calls the onProgressUpdate
                                publishProgress();
                            }
                        });

                        vid.SERVER_IP = group.toString().replace("/", "");
                        vid.SERVER_PORT = port;
                        vid.run();
                        List<Byte> video_data_temp = new ArrayList<Byte>();
                        Bitmap image;
                        while (conn) {
                            byte[] vdata = vid.readVideo();
                            String vdata_s = new String(vdata, "US-ASCII");

                            int sindex = vdata_s.indexOf("START");
                            int eindex = vdata_s.indexOf("END");
                            if (eindex != -1) {
                                for (int i = 0; i < eindex; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                                byte[] recvideo = toByteArray(video_data_temp);
                                try {
                                    image = BitmapFactory.decodeByteArray(recvideo, 0, recvideo.length);
                                    if (image != null) {
                                        Canvas c = null;
                                        videoScreen.bmp = image;

                                        c = videoScreen.getHolder().lockCanvas();

                                        synchronized (videoScreen.getHolder()) {
                                            videoScreen.onDraw(c);
                                        }
                                        videoScreen.getHolder().unlockCanvasAndPost(c);
                                        vid.socket.getInputStream().reset();
                                    }
                                } catch (Exception e) {
                                }

                            }

                            if (sindex != -1) {
                                video_data_temp.clear();
                                for (int i = sindex + 5; i < vdata.length; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                            }

                            if (sindex == -1 && eindex == -1 && vdata.length > 5) {
                                for (int i = 0; i < vdata.length; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                            }
                        }
                        vid.stopClient();
                    } else {
                        //StandAlone TCP IMAGE STREAM
                        robot.request_video_connection();
                        Thread.sleep(1000);
                        TcpClient vid = new TcpClient(new TcpClient.OnMessageReceived() {
                            @Override
                            //here the messageReceived method is implemented
                            public void messageReceived(String message) {
                                //this method calls the onProgressUpdate
                                publishProgress();
                            }
                        });

                        vid.SERVER_IP = ip;
                        vid.SERVER_PORT = 10000;
                        vid.run();
                        List<Byte> video_data_temp = new ArrayList<Byte>();
                        Bitmap image;
                        while (conn) {
                            byte[] vdata = vid.readVideo();
                            String vdata_s = new String(vdata, "US-ASCII");

                            int sindex = vdata_s.indexOf("START");
                            int eindex = vdata_s.indexOf("END");
                            if (eindex != -1) {
                                for (int i = 0; i < eindex; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                                byte[] recvideo = toByteArray(video_data_temp);
                                try {
                                    image = BitmapFactory.decodeByteArray(recvideo, 0, recvideo.length);
                                    if (image != null) {
                                        Canvas c = null;
                                        videoScreen.bmp = image;

                                        c = videoScreen.getHolder().lockCanvas();

                                        synchronized (videoScreen.getHolder()) {
                                            videoScreen.onDraw(c);
                                        }
                                        videoScreen.getHolder().unlockCanvasAndPost(c);
                                        vid.socket.getInputStream().reset();
                                    }
                                } catch (Exception e) {
                                }

                            }

                            if (sindex != -1) {
                                video_data_temp.clear();
                                for (int i = sindex + 5; i < vdata.length; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                            }

                            if (sindex == -1 && eindex == -1 && vdata.length > 5) {
                                for (int i = 0; i < vdata.length; i++) {
                                    video_data_temp.add(vdata[i]);
                                }
                            }

                        }
                        vid.stopClient();


                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public static byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    @Override
    public void onBackPressed() {
        robot.mTcpClient.stopClient();
        Intent DList = new Intent(Scobby_Doo_Remote.this, DeviceList.class);
        startActivity(DList);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Disconnect) {
            robot.mTcpClient.stopClient();
            Intent DList = new Intent(Scobby_Doo_Remote.this, DeviceList.class);
            startActivity(DList);
            finish();
            return true;
        }
        if(item.getItemId() == R.id.deviceTracking){

            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.root.tensorflow_test");
            if (launchIntent != null) {
                launchIntent.putExtra("ip_add",ip);
                startActivity(launchIntent);//null pointer check in case package name was not found
            }
            else {
                Toast.makeText(Scobby_Doo_Remote.this, "Activity Not Found", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }

    @Override
    public void onStop(){
        super.onStop();
        if(conn == true) {
            conn = false;
            robot.mTcpClient.stopClient();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        if(conn == false) {
            robot.openTcp(ip);
            robot.mTcpClient.delay = delay;
            conn = true;
            new VideoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
