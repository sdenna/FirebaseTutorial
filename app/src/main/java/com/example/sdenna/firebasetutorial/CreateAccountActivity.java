package com.example.sdenna.firebasetutorial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean authSuccessful = true;

    EditText nameEditText;
    EditText emailEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = (EditText)findViewById(R.id.editTextName);
        emailEditText = (EditText) findViewById(R.id.editTextEmail);
        userNameEditText = (EditText)findViewById(R.id.editTextUserName);
        passwordEditText = (EditText)findViewById(R.id.editTextPassword);
        errorMessageTextView = (TextView)findViewById(R.id.errorMessageTextView);
    }

    public void onClickCreateAccount(View v) {
        // erases previous error messages
        errorMessageTextView.setText("");
        boolean valid = true;

        // Need to include the code here her call the methods to check if any fields are invalid

        if (!verifyName())
            valid = false;

        if (!verifyEmail())
            valid = false;

        if (!verifyUserName())
            valid = false;

        if (!verifyStrongPassword())
            valid = false;

        // if inputs are valid, then call the intent to load the DisplayDataActivity
        if (valid) {
            String name = nameEditText.getText().toString();
            String email = emailEditText.getText().toString();


            // Need to strip ending space if it exists
            String lastChar = email.substring(email.length()-1,email.length());
            if (lastChar.equals(" ")) {
                email = email.substring(0, email.length() - 1);
            }


            String password = passwordEditText.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Denna-success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                authSuccessful = true;

                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Denna-fail", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                authSuccessful = false;
                                //updateUI(null);
                            }

                            // ...
                        }
                    });

            // Need to find a way to have the auth NOT go to the new intent if it fails by firebase (ex - dup email)
            // This intent shouldn't be happening when it fails!
            // add some more log statements to see where it is going - or try debugger

             if (authSuccessful) {
                 Intent intent = new Intent(this, DisplayDataActivity.class);
                 intent.putExtra("userName", name);
                 intent.putExtra("userEmail", email);
                 startActivity(intent);
             }

        }
    }

    public void onClickCancel(View v) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public boolean verifyName() {
        if (nameEditText.getText().toString().length() == 0) {
            errorMessageTextView.append("Please enter a name\n");
            return false;
        }
        return true;
    }

    public boolean verifyEmail() {
        if (emailEditText.getText().toString().length() == 0) {
            errorMessageTextView.append("Please enter an email\n");
            return false;
        }
        return true;
    }


    public boolean verifyUserName() {
        if (userNameEditText.getText().toString().length() == 0) {
            errorMessageTextView.append("Please enter a user name\n");
            return false;
        }
        return true;
    }

    public boolean verifyStrongPassword() {
        boolean valid = true;
        if (passwordEditText.getText().toString().length() == 0) {
            errorMessageTextView.append("Please enter a password\n");
            return false;
        }

        String s = passwordEditText.getText().toString();
        String errorMsg = "";

    	System.out.println(s);
		if (!checkLength(s)) {
			errorMsg += "\nPassword must be 8 char or more in length";
			valid = false;
		}

		if (!checkLowerCase(s)) {
            errorMsg += "\nPassword must contain a lowercase letter";
			valid = false;
		}

		if (!checkUpperCase(s)) {
            errorMsg += "\nPassword must contain an uppercase letter";
			valid = false;
		}

		if (!checkNumber(s)) {
            errorMsg += "\nPassword must contain a number";
			valid = false;
		}
		if (!checkSpecialCharacter(s)) {
            errorMsg += "\nPassword must contain a special character ";
			valid = false;
		}

        if (!valid){
            System.out.println("\nInvalid password + \n" + errorMsg);
            errorMessageTextView.append(errorMsg);
        }

		return valid;
	}


    // Helper methods to check validity of the password to see if it is Strong enough

    private static boolean checkLength(String s) {
        return (s.length() >= 8);
    }

    private static boolean checkLowerCase(String s) {
        String str =s.replaceAll("[*a-z]", "");
        return !(s.equals(str));
    }

    private static boolean checkUpperCase(String s) {
        String str =s.replaceAll("[*A-Z]", "");
        return !(s.equals(str));
    }

    private static boolean checkNumber(String s) {
        boolean containsDigit = false;
        for (char c : s.toCharArray()) {
            if (containsDigit = Character.isDigit(c)) {
                break;
            }
        }
        return containsDigit;
    }

    private static boolean checkSpecialCharacter(String s) {
        boolean containsCharacter = false;
        for (char c : s.toCharArray()) {
            if  (!Character.isDigit(c) && !Character.isLetter(c)) {
                containsCharacter = true;
                break;
            }
        }
        return containsCharacter;

    }


}
