package wick3d_poison.alphanixom3ga;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import static wick3d_poison.alphanixom3ga.R.id.webview;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView myWebView;



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);


        myWebView = (WebView) findViewById(webview);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAppCacheMaxSize(1024*1024*25);
        myWebView.getSettings().setAppCachePath("/data/data/"+ getPackageName() +"/cache");
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setAppCacheEnabled(true);

        if(isNetworkAvailable()){
            myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }


        final MyWebClient myWebViewClient = new MyWebClient();
        myWebView.setWebViewClient(myWebViewClient);
        myWebView.loadUrl("https://alphanixomega.blogspot.com");


        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);

        FloatingActionButton fabGoToTop = (FloatingActionButton) findViewById(R.id.fabGoToTop);
        fabGoToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator anim = ObjectAnimator.ofInt(myWebView, "scrollY", myWebView.getScrollY(), 0);
                anim.setDuration(500).start();
                fabMenu.close(true);
            }
        });

        FloatingActionButton fabHome = (FloatingActionButton) findViewById(R.id.fabHome);
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myWebView.loadUrl("https://alphanixomega.blogspot.com");
                fabMenu.close(true);
            }
        });

        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fabShare);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePage();
                fabMenu.close(true);
            }
        });

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = metrics.widthPixels;
        mNavigationView.setLayoutParams(params);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // Go back feature
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.top_picks) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/TOP%20PICKS");
        } else if (id == R.id.recent_posts) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/xALL?max-results=8");
        } else if (id == R.id.sk3tch_machin3) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/SK3TCH%20MACHiN3");
        } else if (id == R.id.wick3d_poison) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/WiCK3D%20POiSON");
        } else if (id == R.id.g3cko_taco) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/G3CKO%20TACO");
        } else if (id == R.id.shutt3r_sh3ph3rd) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/SHUTT3R%20SH3PH3RD");
        } else if (id == R.id.opul3nt_brood) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/OPUL3NT%20BROOD");
        } else if (id == R.id.misc) {
            myWebView.loadUrl("https://alphanixomega.blogspot.in/search/label/MiSC");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            view.loadUrl("javascript:document.getElementsByClassName('viennatop-wrapper')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('status-msg-wrap')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('breadcrumbs')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('authorboxwrap')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('sharetitle')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('sharethis')[0].style.display='none';" +
                    "javascript:document.getElementsByClassName('halaman')[0].style.display='none';" +
                    "void(0);");

            view.loadUrl("javascript:document.getElementById('header-wrapper').style.display = 'none';" +
                    "javascript:document.getElementById('nav').style.display = 'none';" +
                    "javascript:document.getElementById('sidebar-wrapper').style.display = 'none';" +
                    "javascript:document.getElementById('footer-wrapper').style.display = 'none';" +
                    "javascript:document.getElementById('virelated-post').style.display = 'none';" +
                    "javascript:document.getElementById('comments').style.display = 'none';" +
                    "javascript:document.getElementById('SmoothToTop').style.display = 'none';" +
                    "void(0);");

            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Snackbar.make(view, "Check your wifi or mobile data.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Action", null).show();
        }
    }

    private void sharePage() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String webUrl = myWebView.getUrl();
        String webTitle = myWebView.getTitle();
        shareIntent.putExtra(Intent.EXTRA_TEXT, webTitle + ": " + webUrl);
        startActivity(Intent.createChooser(shareIntent, "How would you like to share ?"));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
