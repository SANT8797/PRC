package com.example.prc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class scan_palm  extends AppCompatActivity {
    private ImageView photo11,photo21,photo31,photo41,photo51,photo61,photo71;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    private FirebaseAuth mAuth;
    private String savedCurrentTime, saveCurrentDate;
    private String messageReceiverID, messageSenderId,currentPhotoPath;
    FirebaseUser fuser;
    DatabaseReference  rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_palm);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMMM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        savedCurrentTime = currentTime.format(calendar.getTime());
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        messageSenderId = mAuth.getCurrentUser().getUid();
        messageReceiverID = "uX8AMqcRf8SMdK3h6tGBuTn40ar2";

        rootRef = FirebaseDatabase.getInstance().getReference();
        photo11 = findViewById(R.id.img11);
        photo21 = findViewById(R.id.img21);
        photo31 = findViewById(R.id.img31);
        photo41 = findViewById(R.id.img41);
        photo51 = findViewById(R.id.img51);
        photo61 = findViewById(R.id.img61);
        photo71 = findViewById(R.id.img71);

        btn1=findViewById(R.id.btn11);
        btn2=findViewById(R.id.btn21);
        btn3=findViewById(R.id.btn31);
        btn4=findViewById(R.id.btn41);
        btn5=findViewById(R.id.btn51);
        btn6=findViewById(R.id.btn61);
        btn7=findViewById(R.id.btn71);
        btn8=findViewById(R.id.submitbtnscan);
        btn9=findViewById(R.id.backbtnscan);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  fileName = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photo1003";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photo27";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photo3";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photo49";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photoSE52";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  fileName = "photoT20258";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                    File imageFile = File.createTempFile(fileName,".jpg",storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();
                    Uri imageUri = FileProvider.getUriForFile(scan_palm.this,"com.example.prc.fileprovider",imageFile);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,6);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.submitbtnscan));
                {
                    Intent intent=new Intent(scan_palm.this, UpiPaymentActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {

            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo11.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });


        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo21.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo31.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo41.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

        if (requestCode == 4 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo51.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

        if (requestCode == 5 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo61.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

        if (requestCode == 6 && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            photo71.setImageBitmap(bitmap);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            final ProgressDialog progressdialog=new ProgressDialog(this);
            progressdialog.setTitle("Uploading.....");
            progressdialog.show();

            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image Files");
            final String messageSenderRef = "Messages/" + messageSenderId + "/" + messageReceiverID;
            final String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef = rootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverID).push();

            final String messagePushId = userMessageKeyRef.getKey();


            final StorageReference imageRef = storageReference.child(messagePushId + "."+"jpg");

            byte[] b = stream.toByteArray();
            imageRef.putBytes(b)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressdialog.dismiss();
                                    //imageView.setImageBitmap(null);
                                    Log.e("upload : ","Success");

                                    Uri downloadUri = uri;
                                    Map messageImageBody = new HashMap();
                                    messageImageBody.put("message",downloadUri.toString());
                                    messageImageBody.put("name",uri.getLastPathSegment());
                                    messageImageBody.put("type","image");
                                    messageImageBody.put("from",messageSenderId);
                                    messageImageBody.put("to",messageReceiverID);
                                    messageImageBody.put("messageId",messagePushId);
                                    messageImageBody.put("time",savedCurrentTime);
                                    messageImageBody.put("date",saveCurrentDate);

                                    Map messageBodyDetails = new HashMap();
                                    messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageImageBody);
                                    messageBodyDetails.put(messageReceiverRef+"/"+messagePushId,messageImageBody);

                                    rootRef.updateChildren(messageBodyDetails);
                                }
                            });

                            Toast.makeText(getApplicationContext(),"Successfully uploaded"
                                    ,Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressdialog.dismiss();
                            Toast.makeText(scan_palm.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                    @SuppressWarnings("VisibleForTests")double progress
                            = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                    progressdialog.setMessage(((int) progress) + "% uploading....");

                }
            });

        }

    }
}