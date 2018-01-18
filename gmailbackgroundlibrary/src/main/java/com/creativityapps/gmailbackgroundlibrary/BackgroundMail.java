package com.creativityapps.gmailbackgroundlibrary;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.util.GmailSender;
import com.creativityapps.gmailbackgroundlibrary.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BackgroundMail {
    private final String TAG = BackgroundMail.class.getSimpleName();

    private String username;
    private String password;
    private int port;
    private String protocol;
    private String senderName;
    private String mailTo;
    private String mailCc;
    private String mailBcc;
    private String subject;
    private String body;
    private String type;
    private boolean useDefaultSession;
    private String sendingMessage;
    private String sendingMessageSuccess;
    private String sendingMessageError;
    private boolean processVisibility = true;
    private ArrayList<String> attachments = new ArrayList<>();
    private Context mContext;
    private OnSuccessCallback onSuccessCallback;
    private OnFailCallback onFailCallback;

    public final static String TYPE_PLAIN = "text/plain";
    public final static String TYPE_HTML = "text/html";

    public interface OnSuccessCallback {
        void onSuccess();
    }

    public interface OnFailCallback {
        void onFail();
    }

    public BackgroundMail(Fragment fragment) {
        this(fragment.getActivity().getApplicationContext());
    }

    public BackgroundMail(Context context) {
        this.mContext = context;
        this.sendingMessage = context.getString(R.string.msg_sending_email);
        this.sendingMessageSuccess = context.getString(R.string.msg_email_sent_successfully);
        this.sendingMessageError = context.getString(R.string.msg_error_sending_email);
    }

    private BackgroundMail(Builder builder) {
        mContext = builder.context;
        attachments = builder.attachments;
        username = builder.username;
        password = builder.password;
        port = builder.port;
        protocol = builder.protocol;
        senderName = builder.senderName;
        mailTo = builder.mailTo;
        mailCc = builder.mailCc;
        mailBcc = builder.mailBcc;
        subject = builder.subject;
        body = builder.body;
        type = builder.type;
        useDefaultSession = builder.useDefaultSession;
        setSendingMessage(builder.sendingMessage);
        setSendingMessageSuccess(builder.sendingMessageSuccess);
        setSendingMessageError(builder.sendingMessageError);
        processVisibility = builder.processVisibility;
        setOnSuccessCallback(builder.onSuccessCallback);
        setOnFailCallback(builder.onFailCallback);
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    public static Builder newBuilder(Fragment fragment) {
        return new Builder(fragment.getActivity().getApplicationContext());
    }

    public void setGmailUserName(@NonNull String string) {
        this.username = string;
    }

    public void setGmailUserName(@StringRes int strRes) {
        this.username = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getGmailUserName() {
        return username;
    }

    public void setGmailPassword(@NonNull String string) {
        this.password = string;
    }

    public void setGmailPassword(@StringRes int strRes) {
        this.password = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getGmailPassword() {
        return password;
    }

    public void setType(@NonNull String string) {
        this.type = string;
    }

    public void setType(@StringRes int strRes) {
        this.type = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setUseDefaultSession(boolean useDefaultSession) {
        this.useDefaultSession = useDefaultSession;
    }

    public boolean isUseDefaultSession() {
        return useDefaultSession;
    }

    public void showVisibleProgress(boolean state) {
        this.processVisibility = state;
    }

    public boolean isProgressVisible() {
        return processVisibility;
    }

    public void setMailTo(@NonNull String string) {
        this.mailTo = string;
    }

    public void setMailTo(@StringRes int strRes) {
        this.mailTo = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getMailTo() {
        return mailTo;
    }

    public void setMailCc(@NonNull String string) {
        this.mailCc = string;
    }

    public void setMailCc(@StringRes int strRes) {
        this.mailCc = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getMailCc() {
        return mailCc;
    }

    public void setMailBcc(@NonNull String string) {
        this.mailBcc = string;
    }

    public void setMailBcc(@StringRes int strRes) {
        this.mailBcc = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getMailBcc() {
        return mailBcc;
    }

    public void setSubject(@NonNull String string) {
        this.subject = string;
    }

    public void setSubject(@StringRes int strRes) {
        this.subject = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setBody(@NonNull String string) {
        this.body = string;
    }

    public void setBody(@StringRes int strRes) {
        this.body = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getBody() {
        return body;
    }

    public void setSendingMessage(@NonNull String string) {
        this.sendingMessage = string;
    }

    public void setSendingMessage(@StringRes int strRes) {
        this.sendingMessage = mContext.getResources().getString(strRes);
    }

    @NonNull
    public String getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessageSuccess(@Nullable String string) {
        this.sendingMessageSuccess = string;
    }

    public void setSendingMessageSuccess(@StringRes int strRes) {
        this.sendingMessageSuccess = mContext.getResources().getString(strRes);
    }

    @Nullable
    public String getSendingMessageSuccess() {
        return sendingMessageSuccess;
    }

    public void setSendingMessageError(@Nullable String string) {
        this.sendingMessageError = string;
    }

    public void setSendingMessageError(@StringRes int strRes) {
        this.sendingMessageError = mContext.getResources().getString(strRes);
    }

    @Nullable
    public String getSeningMessageError() {
        return sendingMessageError;
    }

    public void addAttachment(@NonNull String attachment) {
        this.attachments.add(attachment);
    }

    public void addAttachment(@StringRes int strRes) {
        this.attachments.add(mContext.getResources().getString(strRes));
    }

    public void addAttachments(@NonNull List<String> attachments) {
        this.attachments.addAll(attachments);
    }

    public void addAttachments(String... attachments) {
        this.attachments.addAll(Arrays.asList(attachments));
    }

    @NonNull
    public List<String> getAttachments() {
        return attachments;
    }

    public void setOnSuccessCallback(OnSuccessCallback onSuccessCallback) {
        this.onSuccessCallback = onSuccessCallback;
    }

    public void setOnFailCallback(OnFailCallback onFailCallback) {
        this.onFailCallback = onFailCallback;
    }

    public void send() {
        if (TextUtils.isEmpty(username)) {
            throw new IllegalArgumentException("You didn't set a Gmail username");
        }
        if (TextUtils.isEmpty(password)) {
            throw new IllegalArgumentException("You didn't set a Gmail password");
        }
        if (TextUtils.isEmpty(mailTo) && TextUtils.isEmpty(mailCc) && TextUtils.isEmpty(mailBcc)) {
            throw new IllegalArgumentException("You didn't set any recipient addresses");
        }
        if (!Utils.isNetworkAvailable(mContext)) {
            Log.d(TAG, "you need internet connection to send the email");
        }

        new SendEmailTask().execute();
    }

    public class SendEmailTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (processVisibility) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage(sendingMessage);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            try {
                GmailSender sender = new GmailSender(username, password, port, protocol, useDefaultSession);
                if (!attachments.isEmpty()) {
                    for (int i = 0; i < attachments.size(); i++) {
                        if (!attachments.get(i).isEmpty()) {
                            sender.addAttachment(attachments.get(i));
                        }
                    }
                }
                sender.sendMail(subject, body, username, senderName, mailTo, mailCc, mailBcc, type);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (result) {
                if (processVisibility && !TextUtils.isEmpty(sendingMessageSuccess)) {
                    Toast.makeText(mContext, sendingMessageSuccess, Toast.LENGTH_SHORT).show();
                }
                if (onSuccessCallback != null) {
                    onSuccessCallback.onSuccess();
                }
            } else {
                if (processVisibility && !TextUtils.isEmpty(sendingMessageError)) {
                    Toast.makeText(mContext, sendingMessageError, Toast.LENGTH_SHORT).show();
                }
                if (onFailCallback != null) {
                    onFailCallback.onFail();
                }
            }
        }
    }

    public static final class Builder {
        private Context context;

        private String username;
        private String password;
        private int port = GmailSender.DEFAULT_PORT;
        private String protocol = GmailSender.DEFAULT_PROTOCOL;

        private boolean useDefaultSession = true;
        private String sendingMessage;
        private String sendingMessageSuccess;
        private String sendingMessageError;
        private boolean processVisibility = true;

        private OnSuccessCallback onSuccessCallback;
        private OnFailCallback onFailCallback;

        private String senderName;
        private String mailTo;
        private String mailCc;
        private String mailBcc;
        private String subject = "";
        private String body = "";
        private String type = BackgroundMail.TYPE_PLAIN;
        private ArrayList<String> attachments = new ArrayList<>();


        private Builder(Context context) {
            this.context = context;
            this.sendingMessage = context.getString(R.string.msg_sending_email);
            this.sendingMessageSuccess = context.getString(R.string.msg_email_sent_successfully);
            this.sendingMessageError = context.getString(R.string.msg_error_sending_email);
        }

        // sending settings

        public Builder username(@NonNull String username) {
            this.username = username;
            return this;
        }

        public Builder username(@StringRes int usernameRes) {
            this.username = context.getResources().getString(usernameRes);
            return this;
        }

        public Builder password(@NonNull String password) {
            this.password = password;
            return this;
        }

        public Builder password(@StringRes int passwordRes) {
            this.password = context.getResources().getString(passwordRes);
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        //set email mime type
        public Builder type(@NonNull String type) {
            this.type = type;
            return this;
        }

        public Builder type(@StringRes int typeRes) {
            this.type = context.getResources().getString(typeRes);
            return this;
        }

        public Builder useDefaultSession(boolean useDefaultSession) {
            this.useDefaultSession = useDefaultSession;
            return this;
        }


        public Builder sendingMessage(String message) {
            this.sendingMessage = message;
            return this;
        }

        public Builder successMessage(String message) {
            this.sendingMessageSuccess = message;
            return this;
        }

        public Builder errorMessage(String message) {
            this.sendingMessageError = message;
            return this;
        }

        public Builder sendingMessage(int stringId) {
            this.sendingMessage = context.getString(stringId);
            return this;
        }

        public Builder successMessage(int stringId) {
            this.sendingMessageSuccess = context.getString(stringId);
            return this;
        }

        public Builder errorMessage(int stringId) {
            this.sendingMessageError = context.getString(stringId);
            return this;
        }

        public Builder processVisibility(boolean visible) {
            this.processVisibility = visible;
            return this;
        }

        public Builder onSuccessCallback(OnSuccessCallback onSuccessCallback) {
            this.onSuccessCallback = onSuccessCallback;
            return this;
        }

        public Builder onFailCallback(OnFailCallback onFailCallback) {
            this.onFailCallback = onFailCallback;
            return this;
        }

        // email values

        public Builder withSenderName(@NonNull String senderName) {
            this.senderName = senderName;
            return this;
        }

        public Builder withMailTo(@NonNull String mailTo) {
            this.mailTo = mailTo;
            return this;
        }

        public Builder withMailTo(@StringRes int mailToRes) {
            this.mailTo = context.getResources().getString(mailToRes);
            return this;
        }

        public Builder withMailCc(@NonNull String mailCc) {
            this.mailCc = mailCc;
            return this;
        }

        public Builder withMailCc(@StringRes int mailCcRes) {
            this.mailCc = context.getResources().getString(mailCcRes);
            return this;
        }

        public Builder withMailBcc(@NonNull String mailBcc) {
            this.mailBcc = mailBcc;
            return this;
        }

        public Builder withMailBcc(@StringRes int mailBccRes) {
            this.mailBcc = context.getResources().getString(mailBccRes);
            return this;
        }

        public Builder withSubject(@NonNull String subject) {
            this.subject = subject;
            return this;
        }

        public Builder withSubject(@StringRes int subjectRes) {
            this.subject = context.getResources().getString(subjectRes);
            return this;
        }

        public Builder withBody(@NonNull String body) {
            this.body = body;
            return this;
        }

        public Builder withBody(@StringRes int bodyRes) {
            this.body = context.getResources().getString(bodyRes);
            return this;
        }

        public Builder withAttachments(@NonNull ArrayList<String> attachments) {
            this.attachments.addAll(attachments);
            return this;
        }

        public Builder withAttachments(String... attachments) {
            this.attachments.addAll(Arrays.asList(attachments));
            return this;
        }

        public Builder withAttachments(@ArrayRes int attachmentsRes) {
            this.attachments.addAll(Arrays.asList(context.getResources().getStringArray(attachmentsRes)));
            return this;
        }

        public BackgroundMail build() {
            return new BackgroundMail(this);
        }

        public BackgroundMail send() {
            BackgroundMail backgroundMail = build();
            backgroundMail.send();
            return backgroundMail;
        }
    }
}
