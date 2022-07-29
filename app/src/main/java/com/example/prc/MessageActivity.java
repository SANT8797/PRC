package com.example.prc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.prc.Notifications.Client;
import com.example.prc.Notifications.Token;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class MessageActivity extends AppCompatActivity
{

    private DatabaseReference  contactsRef,usersRef,reference;
    private FirebaseAuth mAuth;
    private String currentUserId;
    private String userName ="", profileImage ="",userStatusoo ="";
    private String calledBy ="";
    private DatabaseReference userRef;

    RecyclerView myContactsList;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initToolbar();

        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactsRef = FirebaseDatabase.getInstance().getReference().child("User");
        usersRef = FirebaseDatabase.getInstance().getReference().child("User");
        userRef = FirebaseDatabase.getInstance().getReference().child("User");
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        myContactsList=findViewById(R.id.contact_list);
        myContactsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void initToolbar() {

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar_chatting);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        validateUser();
        updateUserStatus("online");
        fire();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserStatus("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateUserStatus("offline");
    }

    private void fire()
    {
        FirebaseRecyclerOptions<Contacts> options
                = new FirebaseRecyclerOptions.Builder<Contacts>()
                .setQuery(contactsRef, Contacts.class)
                .build();

        FirebaseRecyclerAdapter<Contacts, ContactsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Contacts, ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, final int i, @NonNull final Contacts contacts)
            {
                final String listUserId = getRef(i).getKey();
                final boolean bool;
                usersRef.child(listUserId).addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            userName = dataSnapshot.child("name").getValue().toString();
                            holder.userNameTxt.setText(userName);
                            profileImage = (String)  dataSnapshot.child("image").getValue().toString();
                            Picasso.get().load(profileImage).into(holder.profileImageView);
                            if (dataSnapshot.child("userState").hasChild("state")) {
                                String state = dataSnapshot.child("userState").child("state").getValue().toString();
                                String date = dataSnapshot.child("userState").child("date").getValue().toString();
                                String time = dataSnapshot.child("userState").child("time").getValue().toString();
                                if (state.equals("online")){
                                    holder.userStatusooTxt.setText("Online");
                                    holder.userStatusooTxt.setTextColor(Color.GREEN);
                                }else if (state.equals("offline")){
                                    holder.userStatusooTxt.setTextColor(Color.RED);
                                    holder.userStatusooTxt.setText("Offline:");
                                    holder.userSeenTxt.setText(date +" "+time);
                                }
                            }else {
                                holder.userStatusooTxt.setText("Offline");
                            }

                        }
                        holder.profileImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent callingIntent = new Intent(MessageActivity.this,ChattingActivity.class);
                                callingIntent.putExtra("visit_user_id",listUserId);
                                startActivity(callingIntent);

                            }
                        });
                        holder.userNameTxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent callingIntent = new Intent(MessageActivity.this,ChattingActivity.class);
                                callingIntent.putExtra("visit_user_id",listUserId);
                                startActivity(callingIntent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });



            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_design, parent,false );
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };
        myContactsList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder
    {
        TextView userNameTxt,userStatusooTxt,userSeenTxt;
        ImageView profileImageView;



        public ContactsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userNameTxt = itemView.findViewById(R.id.name_contact);
            userSeenTxt = itemView.findViewById(R.id.name_seen);
            profileImageView = itemView.findViewById(R.id.image_contact);
            userStatusooTxt = itemView.findViewById(R.id.name_statusoo);

        }
    }
    private void validateUser()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(currentUserId).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
                {
                    Intent settingIntent = new Intent(MessageActivity.this,ProfileActivity1.class);
                    startActivity(settingIntent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }


    private void updateUserStatus(String state)
    {
        reference = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid());
        String saveCurrentTime,saveCurrentDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate =currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime =currentTime.format(calendar.getTime());

        HashMap<String,Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time",saveCurrentTime);
        onlineStateMap.put("date",saveCurrentDate);
        onlineStateMap.put("state",state);

        reference.child("userState").updateChildren(onlineStateMap);
    }

}