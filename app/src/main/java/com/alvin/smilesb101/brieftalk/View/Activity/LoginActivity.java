package com.alvin.smilesb101.brieftalk.View.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.Bean.QQUserInfoBean;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.Utils.Helper;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ThemeBaseActivity implements LoaderCallbacks<Cursor>,OnClickListener{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private ActivityLoginBinding binding;
    private ProgressDialog dialog;

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static Context rootContent;

    private IUiListener qqLoginListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            //设置Openid 和 token
            Log.i(TAG, "onComplete: 授权成功");
            initOpenidAndToken((JSONObject) o);
            _User userBean = new _User();
            BmobQuery<_User> query = new BmobQuery();
            query.addWhereEqualTo("openid",mTencent.getOpenId());
            query.findObjects(rootContent, findListener);
        }

        @Override
        public void onError(UiError uiError) {
            Log.i(TAG, "onError: 错误"+uiError.errorMessage);
            ToastUtils.show(rootContent,"授权失败。");
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "onError: 取消登录");
            ToastUtils.show(rootContent,"授权失败。");
        }
    };

    private FindListener<_User> findListener = new FindListener<_User>() {
        @Override
        public void onSuccess(List<_User> list) {
            if(list.isEmpty()){
                getUserInfo();
            }
            else{
                goToMain(list.get(0));
            }
        }

        @Override
        public void onError(int i, String s) {
            ToastUtils.show(rootContent,"服务器被黑洞吃掉了额。");
        }
    };

    private Tencent mTencent;
    private final static String mAppId = "222222";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        //初始化后端云
        Bmob.initialize(this,"93493eb990bcb33993957a94029816dc");
        rootContent = binding.getRoot().getContext();
        mTencent = Tencent.createInstance(mAppId, this.getApplicationContext());
        // Set up the login form.
        mEmailView = binding.email;
        populateAutoComplete();

        mPasswordView = binding.password;

        Button mEmailSignInButton = binding.emailSignInButton;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        binding.setMActivity(this);
        ImageView qqLogin = findViewById(R.id.qqLogin);
        qqLogin.setOnClickListener(this);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userId = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userId)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Log.i(TAG, "attemptLogin: 等露露露露");
            _User user = new _User();
            user.setPassword(password);
            Log.i(TAG, "attemptLogin: 密码"+password);
            user.userId = Helper.md5(userId);
            Log.i(TAG, "attemptLogin: "+user.userId);
            checkIsExit(user);
        }
    }

    private void checkIsExit(final _User user){
        showProgress(true);
        BmobQuery<_User> query = new BmobQuery();
        query.addWhereEqualTo("userId",user.userId);
        query.findObjects(rootContent, new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                if(list.isEmpty())
                {
                    user.setUsername("BriefTalker"+System.currentTimeMillis());
                    storeBeanToBmob(user);
                }
                else
                {
                    //登录
                    user.setUsername(list.get(0).getUsername());
                    user.login(rootContent, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            showProgress(false);
                            goToMain(user);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showProgress(false);
                            Log.i(TAG, "onFailure: "+s);
                            ToastUtils.show(rootContent,s);
                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {
                showProgress(false);
                Log.i(TAG, "onError: "+s);
                ToastUtils.show(rootContent,"服务器被黑洞吃掉了额。");
            }
        });
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if(show) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("登录中");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }
        else {
            if(dialog!=null)
            {
                dialog.dismiss();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.qqLogin:
                Log.i(TAG, "onClick: qq登录");
                if(mTencent==null)
                {
                    mTencent = Tencent.createInstance(mAppId,this.getApplicationContext());
                }
                if(mTencent!=null)
                {
                    mTencent.login(this, "all",qqLoginListener);
                }
                break;
        }
    }

    public void qqLogout()
    {
        mTencent.logout(this);
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");

            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserInfo()
    {
        final UserInfo userInfo = new UserInfo(this,mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                ToastUtils.show(rootContent,"授权成功！");

                Log.i(TAG, "onComplete: 授权成功");
                QQUserInfoBean infoBean = new Gson().fromJson(o.toString(),QQUserInfoBean.class);
                _User bean = new _User();
                Log.i(TAG, "onComplete: "+infoBean.nickname);
                bean.openid = mTencent.getOpenId();
                bean.userId = Helper.md5(System.currentTimeMillis()+mTencent.getOpenId());
                bean.setUsername(infoBean.nickname);
                bean.setPassword("null");
                bean.userHeader = infoBean.figureurl_qq_1;

                storeBeanToBmob(bean);
            }

            @Override
            public void onError(UiError uiError) {
                ToastUtils.show(rootContent,"拉取用户信息失败。");
            }

            @Override
            public void onCancel() {
                ToastUtils.show(rootContent,"拉取用户信息失败。");
            }
        });
    }

    private void storeBeanToBmob(final _User bean){
        showProgress(true);
        if(bean.userHeader==null||bean.userHeader.trim()==""){
            bean.userHeader = "http://on792ofrp.bkt.clouddn.com/18-5-6/54152573.jpg";
        }
        bean.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //注册成功==>主界面
                showProgress(false);
                goToMain(bean);
            }

            @Override
            public void onFailure(int i, String s) {
                showProgress(false);
                Log.i(TAG, "onFailure: storeBeanToBmob "+s);
                ToastUtils.show(rootContent,"服务器被黑洞吃掉了额。");
            }
        });
    }

    private void goToMain(_User bean){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,MainActivity.class);
        intent.putExtra("userInfo",bean);
        ToastUtils.show(this,"登录成功！");
        LoginActivity.this.startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,qqLoginListener);
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUserName;
        private final String mPassword;

        UserLoginTask(String userName, String password) {
            mUserName = userName;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

