package com.example.firebase_6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.gson.JsonIOException;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class GoogleActivity extends AppCompatActivity {

    ImageView ivg;
    TextView tvg1;
    TextView tvg2;
    TextView tvg3;
    Button butg;

    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    LoginButton login_button;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    AccessTokenTracker accessTokenTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_google);

        ivg=(ImageView)findViewById(R.id.ivg);
        tvg1=(TextView)findViewById(R.id.tvg1);
        tvg2=(TextView)findViewById(R.id.tvg2);
        tvg3=(TextView)findViewById(R.id.tvg3);
        butg=(Button)findViewById(R.id.butg);
        login_button=(LoginButton)findViewById(R.id.login_button);

        mFirebaseAuth=FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            tvg1.setText(personName);
            tvg2.setText(personId);
            tvg1.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(ivg);
        }

        butg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.butg:
                        signOut();
                        break;
                }
            }
        });

        callbackManager=CallbackManager.Factory.create();
        //login_button.setReadPermissions(Arrays.asList("email","public_profile"));
        login_button.setReadPermissions("email","public_profile");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(GoogleActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                handleFacebookToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(GoogleActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(GoogleActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=mFirebaseAuth.getCurrentUser();
                if(user!=null){
                    updateUI(user);
                }
                else{
                    updateUI(null);
                }
            }
        };

        accessTokenTracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    mFirebaseAuth.signOut();
                }
            }
        };

    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(GoogleActivity.this, "Sign out successfully from google", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

    }
   // AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        /*@Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken==null){
                tvg1.setText("");
                tvg2.setText("");
                tvg3.setText("");
                ivg.setImageResource(0);
                Toast.makeText(GoogleActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
           // else
                //loaduserProfile(currentAccessToken);

        }*/
    //};

   /* private void loaduserProfile(AccessToken newAccessToken){
        GraphRequest request=GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name=object.getString("first_name");
                    String last_name=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");

                    String image_url="https://graph.facebook.com/"+id+"/picture?type=normal";

                    tvg1.setText(first_name);
                    tvg2.setText(last_name);
                    tvg3.setText(email);
                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(GoogleActivity.this).load(image_url).into(ivg);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }


            }
        });
        Bundle parameters=new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }*/
    private void handleFacebookToken(AccessToken token){
        Toast.makeText(this, "handleFacebookToken", Toast.LENGTH_SHORT).show();

        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(GoogleActivity.this, "Sign in with credential is successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Toast.makeText(GoogleActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private void updateUI(FirebaseUser user){
        if(user!=null){
           /* tvg1.setText(user.getDisplayName());
            if(user.getPhotoUrl()!=null){
                String photoUrl=user.getPhotoUrl().toString();
                photoUrl=photoUrl+"?type=large";
                Picasso.get().load(photoUrl).into(ivg);
            }*/
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                tvg1.setText(name);
            };
        }
        else {
            tvg1.setText("");
           ivg.setImageResource(0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
