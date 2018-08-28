package com.creativityapps.gmailbackground;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.bt_send_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityPermissionsDispatcher.sendTextEmailWithPermissionCheck(MainActivity.this);
            }
        });
        findViewById(R.id.bt_send_html).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityPermissionsDispatcher.sendHtmlEmailWithPermissionCheck(MainActivity.this);
            }
        });
        findViewById(R.id.bt_send_attachment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityPermissionsDispatcher.sendAttachedEmailWithPermissionCheck(MainActivity.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void sendTextEmail() {
        sendEmail(BackgroundMail.TYPE_PLAIN, null);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void sendHtmlEmail() {
        sendEmail(BackgroundMail.TYPE_HTML, null);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void sendAttachedEmail() {
        // create a `Test.txt` and locate it on device sdcard and run the example
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/Test.txt";

        sendEmail(BackgroundMail.TYPE_PLAIN, fileName);
    }

    private void sendEmail(String type, String fileName) {
        BackgroundMail.Builder builder = BackgroundMail.newBuilder(this)
                .withUsername("username@gmail.com")
                .withPassword("password12345")
                .withSenderName("Your sender name")
                .withMailTo("to-email@gmail.com")
                .withMailCc("cc-email@gmail.com")
                .withMailBcc("bcc-email@gmail.com")
                .withSubject("This is the subject")
                .withType(type)
                .withUseDefaultSession(false)
                .withSendingMessage(R.string.sending_email)
                .withOnSuccessCallback(new BackgroundMail.OnSendingCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, R.string.msg_email_sent_successfully, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Exception e) {
                        Toast.makeText(MainActivity.this, R.string.msg_error_sending_email, Toast.LENGTH_SHORT).show();
                    }
                });

        if (BackgroundMail.TYPE_HTML.equalsIgnoreCase(type)) {
            builder.withBody("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1>This is test html title</h1>\n" +
                    "\n" +
                    "<p>This is test html paragraph.</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>\n");
        } else {
            builder.withBody("This is test plain body");
        }
        if (fileName != null) {
            builder.withAttachments(fileName);
        }

        builder.send();
    }
}
