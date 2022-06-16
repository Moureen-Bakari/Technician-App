package com.george.optica_technicianapplication.UI.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.george.optica_technicianapplication.Constant.Constants;
import com.george.optica_technicianapplication.Adapters.MyOrdersAdapter;
import com.george.optica_technicianapplication.Models.WorkTimer;
import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.UI.Activities.NotificationsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class OrdersFragment extends Fragment {

    private RecyclerView processedOrdersRecyclerViews;
    private DatabaseReference databaseReference;
    private MyOrdersAdapter adapter ;
    private ArrayList<WorkTimer>listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        processedOrdersRecyclerViews = view.findViewById(R.id.processedOrdersRecyclerViews);

        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
        assert uid != null;
        String userId = uid.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child(Constants.ORDERNO_ELAPSEDTIME);
        processedOrdersRecyclerViews.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setReverseLayout(true);
        processedOrdersRecyclerViews.setLayoutManager(layoutManager);

        listView = new ArrayList<>();
        adapter = new MyOrdersAdapter(getActivity(),listView);
        processedOrdersRecyclerViews.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    WorkTimer workTimer =  dataSnapshot.getValue(WorkTimer.class);
                    listView.add(workTimer);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    /*Menu Items Clicked*/
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
    }

    /*On Options menu Clicked*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.notification) {
            Intent intent = new Intent(getActivity(), NotificationsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}