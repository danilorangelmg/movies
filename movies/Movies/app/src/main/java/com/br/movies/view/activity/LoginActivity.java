package com.br.movies.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.br.movies.R;
import com.br.movies.bo.contract.GenericResponse;
import com.br.movies.bo.exception.ValidationException;
import com.br.movies.bo.service.UserService;
import com.br.movies.bo.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.br.movies.bo.contract.SetUpActionBar;

/**
 * Created by danilorangel on 03/04/17.
 */

public class LoginActivity extends AppCompatActivity implements SetUpActionBar {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.txtEmail)
    AutoCompleteTextView txtEmail;

    @Bind(R.id.txtUserName)
    AutoCompleteTextView txtUserName;

    @Bind(R.id.txtName)
    AutoCompleteTextView txtName;

    @Bind(R.id.txtPassword)
    EditText txtPassword;

    @Bind(R.id.txtConfirmPassword)
    EditText txtConfirmPassword;

    @Bind(R.id.btnSignIn)
    CardView btnSignIn;

    @Bind(R.id.contName)
    View containerName;

    @Bind(R.id.contEmail)
    View containerEmail;

    @Bind(R.id.contConfPassword)
    View containerConfirmPassword;

    private boolean isNewUser = false;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupActionBar();
        Util.checkPermission(this, new String[]{Manifest.permission.INTERNET});

        setupActionBar();
    }

    @OnClick(R.id.btnSignIn)
    public void registerUser() {

        try {
            validateField();
        } catch (ValidationException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        String user = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();

        if (isNewUser) {
            String name = txtName.getText().toString();
            String email = txtEmail.getText().toString();

            UserService.getInstance().createUser(this, user, password, email, name, new GenericResponse() {
                @Override
                public void onSuccess() {
                    loadOffer();
                }

                @Override
                public void onError(VolleyError volleyError) {

                }
            });
        } else {
            UserService.getInstance().doLogin(this, user, password, new GenericResponse() {
                @Override
                public void onSuccess() {
                    loadOffer();
                }

                @Override
                public void onError(VolleyError volleyError) {
                    if (volleyError != null && volleyError.getMessage() != null && volleyError.getMessage().contains("username or password is invalid")) {
                        //direciona cadastro
                        isNewUser = true;
                        containerConfirmPassword.setVisibility(View.VISIBLE);
                        containerEmail.setVisibility(View.VISIBLE);
                        containerName.setVisibility(View.VISIBLE);
                        isNewUser = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuário ou senha invalidos", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void loadHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadOffer() {
        Intent intent = new Intent(LoginActivity.this, ExplainActivity.class);
        startActivity(intent);
        finish();
    }

    private void validateField() throws ValidationException {

        if (!validade(txtUserName)) {
            throw new ValidationException("Informe o nome de usuário!");
        }

        if (!validade(txtPassword)) {
            throw new ValidationException("Informe a Senha!");
        }

        if (isNewUser) {
            if (!validade(txtConfirmPassword)) {
                throw new ValidationException("Confirme sua senha!");
            }

            if (!validade(txtEmail)) {
                throw new ValidationException("Informe o E-mail!");
            }

            if (!validade(txtName)) {
                throw new ValidationException("Informe seu nome!");
            }

            if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                throw new ValidationException("As senhas não sãs as mesmas");
            }

        }
    }

    public boolean validade(AutoCompleteTextView view) {
        if (view.getText().toString() == null || view.getText().toString().trim().equals("")) {
            return false;
        }

        return true;
    }

    public boolean validade(EditText view) {
        if (view.getText().toString() == null || view.getText().toString().trim().equals("")) {
            return false;
        }

        return true;
    }



    private void setupActionBar() {
        setupActionBarConfig(LoginActivity.this, getSupportActionBar(), toolbar, drawer, toggle);
        setupActionBar(LoginActivity.this, getSupportActionBar(), false);
    }

    @Override
    public void setupActionBar(AppCompatActivity activity, ActionBar actionBar, boolean enableHomeButton) {
        Util.setupActionBar(activity, actionBar, enableHomeButton);
    }

    @Override
    public void setupActionBarConfig(AppCompatActivity activity, ActionBar actionBar, Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle toggle) {
        Util.setupActionBarConfig(activity, actionBar, toolbar, drawer, toggle);
    }

    @Override
    public void toggleMenu(DrawerLayout drawer) {
        Util.toggleMenu(drawer);
    }
}
