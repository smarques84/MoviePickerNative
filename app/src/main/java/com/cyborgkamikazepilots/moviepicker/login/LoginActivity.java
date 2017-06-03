package com.cyborgkamikazepilots.moviepicker.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cyborgkamikazepilots.moviepicker.MainActivity;
import com.cyborgkamikazepilots.moviepicker.R;

import org.w3c.dom.Text;

/**
 * Created by smarques on 03/06/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter mPresenter;
    private ProgressBar mLoadingBar;
    private TextView mUserEmailView;
    private TextView mUserPasswordView;
    private Button mLoginButton;
    private MaterialDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoadingBar = (ProgressBar) findViewById(R.id.progress_bar_login);
        mUserEmailView = (TextView) findViewById(R.id.txt_login_email);
        mUserPasswordView = (TextView) findViewById(R.id.txt_login_password);
        mLoginButton = (Button) findViewById(R.id.btn_login_email);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserEmailView.getText().toString();
                String password = mUserPasswordView.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.login_details_empty), Toast.LENGTH_SHORT).show();
                } else {
                    mPresenter.authenticateUser(userName, password);
                }
            }
        });

        mPresenter = new LoginPresenter(this);

        mProgressDialog = new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog_wait_title)
                .content(R.string.progress_dialog_wait_message)
                .progress(true, 0)
                .progressIndeterminateStyle(false).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void showMainScreen() {
        mProgressDialog.hide();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showProgressBar() {
        //mLoadingBar.setVisibility(View.VISIBLE);
        mProgressDialog.show();
    }

    @Override
    public void showOnLoginFail() {
        mProgressDialog.hide();
        Toast.makeText(LoginActivity.this, getString(R.string.login_details_invalid), Toast.LENGTH_SHORT).show();
    }
}
