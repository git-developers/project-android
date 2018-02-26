package com.example.software.activity.father;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.software.activity.BaseActivity;
import com.example.software.activity.R;
import com.example.software.utils.Const;

public class ReportsActivity extends BaseActivity {

    private static final String TAG = "ReportsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.father_report);
        toolBar("Reporte", R.string.app_name);

        showProgress(true);
        initialize();
    }

    private void initialize() {

        String webViewRoute = getIntent().getExtras().getString(Const.BUNDLE_ROUTE);

        WebView browser = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        browser.loadUrl(webViewRoute);

        browser.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                if(progress == 100){
                    showProgress(false);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_piechart) {
            gotoReport(Const.ROUTE_REPORTS_PIECHART_INT);
        }else if(id == R.id.action_steppedareachart){
            gotoReport(Const.ROUTE_REPORTS_STEPPEDAREACHART_INT);
        }else if(id == R.id.action_gauge){
            gotoReport(Const.ROUTE_REPORTS_GAUGE_INT);
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoReport(int route) {
        Intent intent = new Intent();
        intent.setClass(ReportsActivity.this, ReportsActivity.class);
        Bundle bundle = new Bundle();

        switch (route){
            case Const.ROUTE_REPORTS_PIECHART_INT:
                bundle.putString(Const.BUNDLE_ROUTE, Const.ROUTE_REPORTS_PIECHART);
                break;
            case Const.ROUTE_REPORTS_STEPPEDAREACHART_INT:
                bundle.putString(Const.BUNDLE_ROUTE, Const.ROUTE_REPORTS_STEPPEDAREACHART);
                break;
            case Const.ROUTE_REPORTS_GAUGE_INT:
                bundle.putString(Const.BUNDLE_ROUTE, Const.ROUTE_REPORTS_GAUGE);
                break;
            default:
                bundle.putString(Const.BUNDLE_ROUTE, Const.ROUTE_REPORTS_PIECHART);
                break;
        }

        intent.putExtras(bundle);
        startActivity(intent);
//        CoursesActivity.this.finish();

        /* Apply our act_1_splash exit (fade out) and menu_reports entry (fade in) animation transitions. */
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


}
