# GmailBackground
A small library to send an email in background without user interaction

[![](https://jitpack.io/v/e16din/GmailBackground.svg)](https://jitpack.io/#e16din/GmailBackground)

## Why this fork?
Added port and protocol properties to the GmailBackground chain.

```java
.port(587)
.protocol("smpts")
```

## Usage
```java
BackgroundMail.newBuilder(this)
                .username("username@gmail.com")
                .password("password12345")
                .port(587)
                .protocol("smtps")
                .withSenderName("Your sender name")
                .withMailTo("to-email@gmail.com")
                .withMailCc("cc-email@gmail.com")
                .withMailBcc("bcc-email@gmail.com")
                .withSubject("this is the subject")
                .withBody("this is the body")
                .withAttachments(fileName)
                .useDefaultSession(false)
                .processVisibility(true)
                .onSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .onFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
```
If you have the feature for user to change sender `username` and `password`. You should ignore use default session. See more detail [here](http://docs.oracle.com/javaee/6/api/javax/mail/Session.html#getDefaultInstance)
```java
.useDefaultSession(false)
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
    compile 'com.github.e16din:GmailBackground:{latest-version}'
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
Based on https://github.com/kristijandraca/BackgroundMailLibrary (code cleanup, tweaks, and jitpack support)

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
