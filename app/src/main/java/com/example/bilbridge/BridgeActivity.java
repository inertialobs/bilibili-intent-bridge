package com.example.bilbridge;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public final class BridgeActivity extends Activity {
    private static final String PILIPLUS_PACKAGE = "com.example.piliplus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri incoming = getIntent() == null ? null : getIntent().getData();
        if (incoming == null) {
            openPiliPlusHome();
        } else {
            forward(incoming);
        }
        finish();
    }

    private void forward(Uri incoming) {
        String scheme = incoming.getScheme();
        Uri target = incoming;
        if ("tv.danmaku.bili".equalsIgnoreCase(scheme)
                || "bilibili-hd".equalsIgnoreCase(scheme)) {
            target = incoming.buildUpon().scheme("bilibili").build();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, target);
        intent.setPackage(PILIPLUS_PACKAGE);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            showMissingClientMessage();
        }
    }

    private void openPiliPlusHome() {
        Intent launch = getPackageManager().getLaunchIntentForPackage(PILIPLUS_PACKAGE);
        if (launch == null) {
            showMissingClientMessage();
            return;
        }

        try {
            startActivity(launch);
        } catch (ActivityNotFoundException exception) {
            showMissingClientMessage();
        }
    }

    private void showMissingClientMessage() {
        Toast.makeText(this, "无法打开 PiliPlus，请确认已安装", Toast.LENGTH_LONG).show();
    }
}
