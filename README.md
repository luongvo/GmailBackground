# GmailBackground
A small library to send an email in background without user interaction

[![](https://jitpack.io/v/luongvo/GmailBackground.svg)](https://jitpack.io/#luongvo/GmailBackground)

## Why this fork?
I used to use the original lib https://github.com/yesidlazaro/GmailBackground for my apps and found some issues listed [here](https://github.com/yesidlazaro/GmailBackground/issues). Also, made some PRs to contribute but it seems the author is not fast enough on the merging these PRs. So I create this fork repo and make it available via [jitpack](https://jitpack.io) for everyone need this.

Features and bugs fixings included:
- Fix `OnSuccessCallback` and `OnFailCallback` not being called when `.withProcessVisibility(false)` [#12](https://github.com/yesidlazaro/GmailBackground/issues/12) [#28](https://github.com/yesidlazaro/GmailBackground/issues/28)
- Add `sender name` beside `sender email` [#26](https://github.com/yesidlazaro/GmailBackground/issues/26)
- Attachment file name fixing [#7](https://github.com/yesidlazaro/GmailBackground/issues/7)
- Add feature to ignore use `default session` from `java mail` [#21](https://github.com/yesidlazaro/GmailBackground/issues/21). More detail [here](http://docs.oracle.com/javaee/6/api/javax/mail/Session.html#getDefaultInstance).
- Make the lib options be more flexible, add `cc` and `bcc` addresses options
- Some refactoring

## Usage
```java
BackgroundMail.newBuilder(this)
        .withUsername("username@gmail.com")
        .withPassword("password12345")
        .withSenderName("Your sender name")
        .withMailTo("to-email@gmail.com")
        .withMailCc("cc-email@gmail.com")
        .withMailBcc("bcc-email@gmail.com")
        .withType(BackgroundMail.TYPE_PLAIN)
        .withSubject("this is the subject")
        .withBody("this is the body")
        .withAttachments(Environment.getExternalStorageDirectory().getPath() + "/test.txt")
        .withSendingMessage(R.string.sending_email)
        .withOnSuccessCallback(new BackgroundMail.OnSendingCallback() {
            @Override
            public void onSuccess() {
                // do some magic
            }
            
            @Override
            public void onFail(Exception e) {
                // do some magic
            }
        })
        .send();
```
- Set `withSendingMessage` to custom message on sending progress dialog. If not, the sending dialog will not be showed.

- If you have the feature for user to change sender `username` and `password`. You should ignore use default session. See more detail [here](http://docs.oracle.com/javaee/6/api/javax/mail/Session.html#getDefaultInstance).
```java
.withUseDefaultSession(false)
```
**Installation**

```groovy
repositories {
    // ...
    maven { url "https://jitpack.io" }
}
```
```groovy
dependencies {
    compile 'com.github.luongvo:GmailBackground:{latest-version}'
}
```
Find the `{latest-version}` in the badge at the top of this readme file.

**Permissions**
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```
**attachments**

for attachments you need set READ_EXTERNAL_STORAGE permission in your manifiest
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```

**Proguard**
```
-keep class org.apache.** { *; }
-dontwarn org.apache.**

-keep class com.sun.mail.** { *; }
-dontwarn com.sun.mail.**

-keep class java.beans.** { *; }
-dontwarn java.beans.**
```

## License
Copyright 2017 Luong Vo

Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
