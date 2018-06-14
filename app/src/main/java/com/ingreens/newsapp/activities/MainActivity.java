package com.ingreens.newsapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ingreens.newsapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String EMAIL = "email";
    private LoginButton login_button;
    private TextView tvUserName,tv_user_name;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    private String fbId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initparameters();
        initViews();

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //Log.d(TAG, “User logged out successfully”);
                    System.out.println("&&&&&&&&&&&&&&&&&&&&");
                    System.out.println("user logout sucessfully");
                    System.out.println("&&&&&&&&&&&&&&&&&&&&");
                    //rlProfileArea.setVisibility(View.GONE);
                }
            }
        };
    }
private void initparameters(){
    accessToken=AccessToken.getCurrentAccessToken();

    callbackManager=CallbackManager.Factory.create();
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initViews(){

    login_button=findViewById(R.id.login_button);
    tvUserName=findViewById(R.id.tvUserName);
    tv_user_name=findViewById(R.id.tv_user_name);
    //login_button.setReadPermissions(Arrays.asList(EMAIL));
    //login_button.setReadPermissions(Arrays.asList(new String[]{"email", "user_birthday", "user_hometown"}));
    login_button.setReadPermissions(Arrays.asList(new String[]{"email", "user_friends", "public_profile"}));
        //Arrays.asList("user_friends", "email", "public_profile"));

    if (accessToken!=null){
        getProfileData();
        //getFriendsList();
    }else {
            tvUserName.setVisibility(View.GONE);
            tv_user_name.setVisibility(View.GONE);
    }

    login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken token=loginResult.getAccessToken();
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("token=="+token);
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTT");
            getProfileData();
            //getFriendsList();
        }

        @Override
        public void onCancel() {
            System.out.println("#################333");
            System.out.println("user cancel login itself");
            System.out.println("#################333");

        }

        @Override
        public void onError(FacebookException error) {
            System.out.println("#################333");
            System.out.println("login problem from register callback");
            System.out.println("#################333");
        }
    });

}

    public void getProfileData() {
        try {
            accessToken = AccessToken.getCurrentAccessToken();
            //rlProfileArea.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.VISIBLE);
            GraphRequest graphRequest=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if (accessToken!=null){
                        if (object!=null){
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
                            System.out.println("JSONObject==="+object);
                            System.out.println("JSONObject to String==="+object.toString());
                            System.out.println("GraphResponse==="+response);
                            System.out.println("GraphResponse toString==="+response.toString());
                            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@22");
                            try {
                                fbId=response.getJSONObject().getString("id");
                                String namee=response.getJSONObject().getString("name");
                                //String email=response.getJSONObject().getString("email");
                                String firstName = response.getJSONObject().getString("first_name");
                                //String middleName = response.getJSONObject().getString("middle_name");
                                String lastName = response.getJSONObject().getString("last_name");
                                //String gender = response.getJSONObject().getString("gender");
                                //String birthday = response.getJSONObject().getString("birthday");
                                System.out.println("###### From response.getJSONObject() ######");
                                System.out.println("id=="+fbId);
                                System.out.println("full name=="+namee);
                                //System.out.println("email=="+object.getString("email"));
                                System.out.println("first Name=="+firstName);
                                //System.out.println("middle Name=="+middleName);
                                System.out.println("last Name=="+lastName);
                                System.out.println("gender=="+response.getJSONObject().getString("gender"));
                                System.out.println("birthday=="+response.getJSONObject().getString("birthday"));
                                System.out.println("hometown=="+response.getJSONObject().getString("hometown"));
                                System.out.println("friends=="+response.getJSONObject().getString("friends"));
                                System.out.println("###### From response.getJSONObject() ######");

                                String profileImageUrl = ImageRequest.getProfilePictureUri(object.optString("id"), 200, 200).toString();
                                System.out.println("&&&&&&& from ImageRequest &&&&&&&");
                                System.out.println("profile image uri==="+profileImageUrl.toString());
                                System.out.println("&&&&&&& from ImageRequest &&&&&&&");

                                tv_user_name.setText(namee);

                                /*if (object.has("friendlist")) {
                                    JSONObject friend = object.getJSONObject("friendlist");
                                    JSONArray data = friend.getJSONArray("data");
                                    for (int i=0;i<data.length();i++){
                                        System.out.println("IDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDID");
                                        Log.i("idddd",data.getJSONObject(i).getString("id"));
                                        System.out.println("IDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDIDID");
                                    }
                                }*/

                                /*JSONArray jsonArrayFriends = object.getJSONObject("friendlist").getJSONArray("data");
                                JSONObject friendlistObject = jsonArrayFriends.getJSONObject(0);
                                String friendListID = friendlistObject.getString("id");
                                myNewGraphReq(fbId);*/

                                getFriendsList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            Bundle bundle=new Bundle();
            //bundle.putString("fields","id,name,link,birthday,gender,email");
            bundle.putString("fields", "id,email,name,first_name,middle_name,last_name,gender,birthday,hometown,friends");
            //bundle.putString("fields", "friendlist, members");
            //bundle.putString("fields", "id,email,first_name,last_name,friends");
            //bundle.putString(“fields”, “id,name,link,birthday,gender,email”);
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*private void myNewGraphReq(String friendlistId) {
        final String graphPath = "/"+friendlistId+"/members/";
        AccessToken token = AccessToken.getCurrentAccessToken();
        GraphRequest request = new GraphRequest(token, graphPath, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("myNewGraphReq(String friendlistId) response ===="+graphResponse.toString());
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                JSONObject object = graphResponse.getJSONObject();
                try {
                    JSONArray arrayOfUsersInFriendList= object.getJSONArray("data");
                *//* Do something with the user list
                 ex: get first user in list, "name" *//*
                    JSONObject user = arrayOfUsersInFriendList.getJSONObject(0);
                    String usersName = user.getString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle param = new Bundle();
        param.putString("fields", "name");
        request.setParameters(param);
        request.executeAsync();
    }*/



    private void getFriendsList() {
        final List<String> friendslist = new ArrayList<String>();
       // final String graphPath = "/"+friendlistId+"/members/";
        final String graphPath = "/" + fbId + "/friends";
        new GraphRequest(accessToken, "/me/friends",
                null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                /* handle the result */

                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("Graph response ===="+response.toString());
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");
                System.out.println("&&&&&&&&&&&&&&&&&&&&&77");

                try {
                    JSONObject responseObject = response.getJSONObject();
                    JSONArray dataArray = responseObject.getJSONArray("data");
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    System.out.println("dataArray===="+dataArray);
                    System.out.println("dataArray length===="+dataArray.length());
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String fbId = dataObject.getString("id");
                        String fbName = dataObject.getString("name");

                        Log.e("FbId", fbId);
                        Log.e("FbName", fbName);
                        friendslist.add(fbId);
                    }
                    Log.e("fbfriendList", friendslist.toString());
                    List<String> list = friendslist;
                    String friends = "";
                    if (list != null && list.size() > 0) {
                        friends = list.toString();
                        if (friends.contains("[")) {
                            friends = (friends.substring(1, friends.length() -1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    //hideLoadingProgress();
                }
            }
        }).executeAsync();
        //return friendslist;

    }

}
