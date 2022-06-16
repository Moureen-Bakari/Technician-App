package com.george.optica_technicianapplication.UI.Fragments;

import static com.george.optica_technicianapplication.Constant.Constants.MyOrders;
import static com.george.optica_technicianapplication.Constant.Constants.Users;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.george.optica_technicianapplication.Adapters.MyOrdersAdapter;
import com.george.optica_technicianapplication.Constant.Constants;
import com.george.optica_technicianapplication.Models.User;
import com.george.optica_technicianapplication.Models.WorkTimer;
import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.UI.Activities.NotificationsActivity;
import com.george.optica_technicianapplication.UI.Activities.StartOrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference databaseReference;
    private MyOrdersAdapter adapter;
    private ArrayList<WorkTimer> listView;
    private int totalOrdersCount;
    private TextView numberOfOrdersTxt;
    private TextView technicianName;
    private RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Initialize the views
        setHasOptionsMenu(true);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        Button button = view.findViewById(R.id.startOrder);
        technicianName = view.findViewById(R.id.technicianName);
        numberOfOrdersTxt = view.findViewById(R.id.numberOfOrders);
        myOrdersRecyclerView = view.findViewById(R.id.myOrdersRecyclerView);

        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
        assert uid != null;
        String userId = uid.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference(Users).child(userId).child(Constants.ORDERNO_ELAPSEDTIME);

        /*Recycler View*/
        listView = new ArrayList<>();
        myOrdersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        myOrdersRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyOrdersAdapter(getActivity(), listView);
        myOrdersRecyclerView.setAdapter(adapter);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);

        /*Database Event Listeners*/
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    WorkTimer workTimer = dataSnapshot.getValue(WorkTimer.class);
                    listView.add(workTimer);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*Setting Optica Technicians Name*/
        DatabaseReference usersDatabaseReference = FirebaseDatabase.getInstance().getReference(Users);
        usersDatabaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.UserLoginInfo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userProfile = snapshot.getValue(User.class);

                        if (userProfile != null) {
                            String username = userProfile.getUsername();
                            technicianName.setText(MessageFormat.format("Hello {0}", username));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        /*Orders Count for Technician*/
        DatabaseReference techniciansTotalOrdersCount = FirebaseDatabase.getInstance().getReference(Users);
        techniciansTotalOrdersCount.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(MyOrders)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if the technician already has orders
                        if (snapshot.exists()) {
                            totalOrdersCount = (int) snapshot.getChildrenCount();
                            numberOfOrdersTxt.setText(Integer.toString(totalOrdersCount));
                        } else {
                            numberOfOrdersTxt.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        button.setOnClickListener(this);
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

    /*Button On Click Listener*/
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.startOrder) {
            Intent intent = new Intent(getActivity(), StartOrderActivity.class);
            startActivity(intent);
        }
    }
}







































































