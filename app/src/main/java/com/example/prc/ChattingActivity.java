package com.example.prc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prc.Notifications.Client;
import com.example.prc.Notifications.Data;
import com.example.prc.Notifications.MyResponse;
import com.example.prc.Notifications.Sender;
import com.example.prc.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    ImageView profile_image;
    TextView username,userStatusooTxt;
    FirebaseUser fuser;
    DatabaseReference reference , rootRef;
    ImageButton btn_send,btn_attach;
    EditText text_send;
    private static final int CHOOSE_IMAGE = 1;
    private String checker="",myUrl="";
    private StorageTask uploadTask;
    private Uri fileUri;
    MessageAdapter messageAdapter;
    private List<Messages> mchat = new ArrayList<>();

    private String messageReceiverID, messageSenderId;
    RecyclerView recyclerView;
    private ProgressDialog loadingBar;
    private String savedCurrentTime, saveCurrentDate;
    private String receiverUserId = "";
    private FirebaseAuth mAuth;
    Intent intent;

    APIService apiService;

    Boolean notify = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);

        mAuth = FirebaseAuth.getInstance();
        initToolbar();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fuser = FirebaseAuth.getInstance().getCurrentUser();
        messageSenderId = mAuth.getCurrentUser().getUid();
        messageReceiverID = getIntent().getExtras().get("visit_user_id").toString();
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        btn_attach = findViewById(R.id.btn_attach);
        text_send = findViewById(R.id.text_send);
        userStatusooTxt = findViewById(R.id.name_statusoo);

        rootRef = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        loadingBar = new ProgressDialog(this);


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        savedCurrentTime = currentTime.format(calendar.getTime());

        intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        final String userid = intent.getStringExtra("visit_user_id");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals(""))
                {
                    sendMessage(fuser.getUid(),userid,msg);
                }
                else
                {
                    Toast.makeText(ChattingActivity.this,"You can't send empty message",Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        btn_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CharSequence options[] = new CharSequence[]
                        {
                                "Images",
                                "PDF File",
                                "MS Word File"
                        };

                AlertDialog.Builder builder = new AlertDialog.Builder(ChattingActivity.this);
                builder.setTitle("Select File");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (i == 0)
                        {
                            checker = "image";

                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Select Image"), 438);
                        }
                        if (i == 1)
                        {
                            checker = "pdf";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/pdf");
                            startActivityForResult(intent.createChooser(intent, "Select pdf File"), 438);
                        }
                        if (i == 2)
                        {
                            checker = "docx";
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("application/msword");
                            startActivityForResult(intent.createChooser(intent, "Select word File"), 438);


                        }
                    }
                });
                builder.show();
            }
        });



        receiverUserId = getIntent().getExtras().get("visit_user_id").toString();

        reference = FirebaseDatabase.getInstance().getReference("User").child(receiverUserId);

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final Contacts contacts = dataSnapshot.getValue(Contacts.class);
                username.setText(contacts.getName());
                Picasso.get().load(contacts.getImage()).transform(new CircleTransform()).placeholder(R.drawable.profile_image).into(profile_image);

                if (dataSnapshot.child("userState").hasChild("state")) {
                    String state = dataSnapshot.child("userState").child("state").getValue().toString();
                    String date = dataSnapshot.child("userState").child("date").getValue().toString();
                    String time = dataSnapshot.child("userState").child("time").getValue().toString();
                    if (state.equals("online")){
                        userStatusooTxt.setText("Online");
                        userStatusooTxt.setTextColor(Color.GREEN);
                    }else if (state.equals("offline")){
                        userStatusooTxt.setTextColor(Color.RED);
                        userStatusooTxt.setText("Offline:"+time);

                    }
                }else {
                    userStatusooTxt.setText("Offline");
                }

                rootRef.child("Messages").child(messageSenderId).child(messageReceiverID)
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s)
                            {
                                Messages messages = dataSnapshot.getValue(Messages.class);
                                if (messages.getTo().equals(messageSenderId) && messages.getFrom().equals(userid) ||
                                        messages.getTo().equals(userid) && messages.getFrom().equals(messageSenderId))
                                {
                                    mchat.add(messages);
                                }

                                messageAdapter = new MessageAdapter(ChattingActivity.this , mchat , contacts.getImage());
                                recyclerView.setAdapter(messageAdapter);
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s)
                            {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot)
                            {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s)
                            {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError)
                            {

                            }
                        });


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        updateUserStatus("online");
    }

    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }


        @Override
        public String key() {
            return "circle";
        }
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

    private void sendMessage(String sender, final String receiver, final String message)
    {

        String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
        String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

        DatabaseReference userMessageKeyRef = rootRef.child("Messages").child(messageSenderId)
                .child(messageSenderRef).child(messageReceiverID).push();
        String messagePushId = userMessageKeyRef.getKey();
        Map messageTextBody = new HashMap();
        messageTextBody.put("message",message);
        messageTextBody.put("type","text");
        messageTextBody.put("from",sender);
        messageTextBody.put("to",receiver);
        messageTextBody.put("messageId",messagePushId);
        messageTextBody.put("time",savedCurrentTime);
        messageTextBody.put("date",saveCurrentDate);

        Map messageBodyDetails = new HashMap();
        messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
        messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageTextBody);

        rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(ChattingActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(ChattingActivity.this,"Error",Toast.LENGTH_SHORT).show();

                }
                text_send.setText("");
            }
        });



        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("User").child(sender);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Contacts contacts = dataSnapshot.getValue(Contacts.class);
                if (notify){
                    sendNotification(receiver,contacts.getName(),message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendNotification(final String receiver, final String name, final String message)
    {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Token token = ds.getValue(Token.class);
                    Data data = new Data(fuser.getUid(),R.drawable.ic_stat_name, name+": "+message,"New Message",
                            receiver);
                    Sender sender = new Sender(data,token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200 ){
                                        if (response.body().success == 1){
                                            Toast.makeText(ChattingActivity.this,"Send",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void readMessages(final String myid, final String userid, final String imageurl)
    {
        mchat = new ArrayList<>();

        reference =  FirebaseDatabase.getInstance().getReference("Messages");

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Messages messages = snapshot.getValue(Messages.class);

                    if (messages.getTo().equals(myid) && messages.getFrom().equals(userid) ||
                            messages.getTo().equals(userid) && messages.getFrom().equals(myid))
                    {
                        mchat.add(messages);
                    }
                    messageAdapter = new MessageAdapter(ChattingActivity.this , mchat , imageurl);
                    recyclerView.setAdapter(messageAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 438 && requestCode == RESULT_OK || data != null &&data.getData()!= null){

            loadingBar.setTitle("Sending Image");
            loadingBar.setMessage("Please wait, We are working for you \nWe Are Sending your Image\nThank you");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            fileUri = data.getData();
            System.out.println("File URI : "+fileUri);
            if (!checker.equals("image")){

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Document Files");
                final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
                final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

                DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                        .child(messageSenderId).child(messageReceiverID).push();

                final String messagePushId = userMessageKeyRef.getKey();


                final StorageReference filePath = storageReference.child(messagePushId + "."+checker);

                filePath.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            Map messageImageBody = new HashMap();
                            messageImageBody.put("message",task.getResult().getDownloadUrl().toString());
                            messageImageBody.put("name",fileUri.getLastPathSegment());
                            messageImageBody.put("type",checker);
                            messageImageBody.put("from",messageSenderId);
                            messageImageBody.put("to",messageReceiverID);
                            messageImageBody.put("messageId",messagePushId);
                            messageImageBody.put("time",savedCurrentTime);
                            messageImageBody.put("date",saveCurrentDate);

                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                            messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                            rootRef.updateChildren(messageBodyDetails);
                            loadingBar.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(ChattingActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double p = (100.0*taskSnapshot.getBytesTransferred());
                        loadingBar.setMessage((int)p+"% \nUploading ...");
                    }
                });


            }else if (checker.equals("image")) {

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
                final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
                final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

                DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                        .child(messageSenderId).child(messageReceiverID).push();

                final String messagePushId = userMessageKeyRef.getKey();


                final StorageReference filePath = storageReference.child(messagePushId + "."+"jpg");
                uploadTask = filePath.putFile(fileUri);
                System.out.println("image uploaded : ");
                filePath.putFile(fileUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            Map messageImageBody = new HashMap();
                            messageImageBody.put("message",task.getResult().getDownloadUrl().toString());
                            messageImageBody.put("name",fileUri.getLastPathSegment());
                            messageImageBody.put("type",checker);
                            messageImageBody.put("from",messageSenderId);
                            messageImageBody.put("to",messageReceiverID);
                            messageImageBody.put("messageId",messagePushId);
                            messageImageBody.put("time",savedCurrentTime);
                            messageImageBody.put("date",saveCurrentDate);

                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                            messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                            rootRef.updateChildren(messageBodyDetails);
                            loadingBar.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
                        Toast.makeText(ChattingActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double p = (100.0*taskSnapshot.getBytesTransferred());
                        loadingBar.setMessage((int)p+"% \nUploading ...");
                    }
                });

            }else {
                loadingBar.dismiss();
                Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();

            }




        }


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


}