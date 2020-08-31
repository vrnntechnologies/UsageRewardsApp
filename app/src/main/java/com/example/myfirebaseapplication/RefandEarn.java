package com.example.myfirebaseapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ServerValue;

public class RefandEarn extends AppCompatActivity {
    Button refearn;
    private Uri mInvitationUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refand_earn);
        refearn = findViewById(R.id.refearn);
        refearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Create Dynomic Link
//GitHub testing

                public void createLink(){
                    // [START ddl_referral_create_link]
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                    String link = "https://mygame.example.com/?invitedby=" + uid;
                    FirebaseDynamicLinks.getInstance().createDynamicLink()
                            .setLink(Uri.parse(link))
                            .setDomainUriPrefix("https://example.page.link")
                            .setAndroidParameters(
                                    new DynamicLink.AndroidParameters.Builder("com.example.android")
                                            .setMinimumVersion(125)
                                            .build())
                            .setIosParameters(
                                    new DynamicLink.IosParameters.Builder("com.example.ios")
                                            .setAppStoreId("123456789")
                                            .setMinimumVersion("1.0.1")
                                            .build())
                            .buildShortDynamicLink()
                            .addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                                @Override
                                public void onSuccess(ShortDynamicLink shortDynamicLink) {
                                    mInvitationUrl = shortDynamicLink.getShortLink();
                                    // ...
                                }
                            });
                    // [END ddl_referral_create_link]
                }

            }

//Send Link
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // ...

                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                // Get deep link from result (may be null if no link is found)
                                Uri deepLink = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();
                                }
                                //
                                // If the user isn't signed in and the pending Dynamic Link is
                                // an invitation, sign in the user anonymously, and record the
                                // referrer's UID.
                                //
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user == null
                                        && deepLink != null
                                        && deepLink.getBooleanQueryParameter("invitedby", false)) {
                                    String referrerUid = deepLink.getQueryParameter("invitedby");
                                    createAnonymousAccountWithReferrerInfo(referrerUid);
                                }
                            }
                        });
            }



        });
    }
}


