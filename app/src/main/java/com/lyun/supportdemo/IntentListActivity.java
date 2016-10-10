package com.lyun.supportdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class IntentListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView intent_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_list);

        initView();
        initData();
    }

    @Override
    public void initView() {
        intent_list = (ListView) findViewById(R.id.intent_list);
    }

    @Override
    public void initData() {
        List<String> intents = new ArrayList<>();

        intents.add("Intent Cell Phone");
        intents.add("Open The WebPage");
        intents.add("Send Email");
        intents.add("Map Location");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, intents);
        intent_list.setAdapter(adapter);

        intent_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> view, View view1, int i, long l) {
        switch (i) {
            case 0:
                // 打电话
                Uri number = Uri.parse("tel: 55501290");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                break;
            case 1:
                // 查看网页
                Uri webpage = Uri.parse("https://www.baidu.com");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
                break;
            case 2:
                // 发送带附件的电子邮件
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // The intent does not have a URIm so declare the "text/plain" MIME type
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jon@example.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
                PackageManager manager = getPackageManager();
                List<ResolveInfo> activities = manager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean intentSafe = activities.size() > 0;
                if (intentSafe)
                    startActivity(emailIntent);
                else
                    Toast.makeText(this, "don't have the match app", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Uri location = Uri.parse("geo:38.899533,120.313");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                /***
                 * 检查是否存在可以响应的app
                 */
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> infos = packageManager.queryIntentActivities(mapIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = infos.size() > 0;
                if (isIntentSafe)
                    startActivity(mapIntent);
                else
                    Toast.makeText(this, "Don't Find The Map Application", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
