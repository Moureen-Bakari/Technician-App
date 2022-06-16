package com.george.optica_technicianapplication.UI.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.george.optica_technicianapplication.Constant.Constants;
import com.george.optica_technicianapplication.Models.WorkTimer;
import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.databinding.ActivityStartOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class StartOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityStartOrderBinding binding;

    private String date2;
    private String Date1;

    private boolean running;
    private long stopOffSet;
    private int i = 0;


    /*Firebase Database*/
    private DatabaseReference databaseReference;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
        date2 = date.format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" HH:mm:ss");
        Date1 = simpleDateFormat.format(calendar.getTime());
        binding.chronometer.setFormat("Time: %s");

        /*Firebase User*/
        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
        assert uid != null;
        String userId = uid.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child(Constants.ORDERNO_ELAPSEDTIME);

        binding.scanButton.setOnClickListener(this);
        binding.stop.setOnClickListener(this);
    }

    /*Back Button Functionality*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /*Menu Items Clicked*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /*On Options menu Clicked*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    /*OnClick Listener Navigation*/
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stop:
                stopChronometer(StartOrderActivity.this);
                break;

            case R.id.scanButton:
                startChronometer();
                binding.stop.setVisibility(View.VISIBLE);
                scanCode();
        }
    }

    /*Start Scanning the code when scan btn is clicked*/
    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("scanning code");
        integrator.initiateScan();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int results, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, results, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getBaseContext(), "Scanning Cancelled", Toast.LENGTH_SHORT).show();
                binding.stop.setVisibility(View.GONE);
            } else {
                binding.orderNumberTextTxt.setText("#" + result.getContents());
                binding.date.setText(date2);
                binding.startTime.setText(Date1);
            }
        } else {
            super.onActivityResult(requestCode, results, data);
        }
    }

    public void startChronometer() {
        if (!running) {
            binding.chronometer.setBase(SystemClock.elapsedRealtime() - stopOffSet);
            binding.chronometer.start();
            running = true;
        }

    }


    public void stopChronometer(StartOrderActivity view) {
        if (running) {
            binding.stop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
                            binding.stopTime.setText(dateFormat.format(new Date()));
                            //get All the values
                            String date = binding.date.getText().toString();
                            String OrderNo = binding.orderNumberTextTxt.getText().toString();
                            String startTimer = binding.startTime.getText().toString();
                            String commence = binding.stopTime.getText().toString();

                            if (OrderNo.equals("") || startTimer.equals("") || commence.equals("")) {
                                Toast.makeText(StartOrderActivity.this, "One  of the values is missing , kindly rescan", Toast.LENGTH_LONG).show();
                            } else {
                                WorkTimer workTimer = new WorkTimer(date, OrderNo, startTimer, commence);
                                databaseReference.push().setValue(workTimer);
                                Toast.makeText(StartOrderActivity.this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
                                Toast.makeText(StartOrderActivity.this, "Kindly Scan another order", Toast.LENGTH_LONG).show();
                                binding.orderNumberTextTxt.getText().clear();
                                binding.orderNumberTextTxt.clearFocus();
                                binding.date.setText("");
                                binding.startTime.setText("");
//                                    binding.orderView.setText("");
                                binding.stopTime.setText("");
                                binding.stop.setVisibility(View.GONE);
                            }
                        }
                    }, 0);
                }

            });

        }
    }

}
