package com.kk.taurus.xfolder.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.FileType;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.kk.taurus.baseframe.ui.activity.ToolsActivity;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.FileListData;
import com.kk.taurus.xfolder.engine.AnimationEngine;
import com.kk.taurus.xfolder.mvp.impl.HomePresenter;
import com.kk.taurus.xfolder.mvp.inter.view.IHomeView;
import com.kk.taurus.xfolder.server.BaseServer;
import com.kk.taurus.xfolder.widget.StorageItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by Taurus on 2017/6/6.
 */

public class HomeActivity extends AppCompatActivity implements IHomeView, View.OnClickListener{

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
//    private View mNavigationView;
    private NavigationView mNavigationView;

    private TextView mNavRemoteManage;
    private TextView mNavAppManage;

    private View mImage;
    private View mVideo;
    private View mMusic;
    private View mApk;
    private View mZip;
    private View mDoc;

    private TextView mTvImageNum;
    private TextView mTvVideoNum;
    private TextView mTvMusicNum;
    private TextView mTvApkNum;
    private TextView mTvZipNum;
    private TextView mTvDocNum;

    private LinearLayout mStorageContainer;

    private HomePresenter mHomePresenter;

//    @Override
//    public View getContentView(Bundle savedInstanceState) {
//        return View.inflate(this,R.layout.activity_home,null);
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        afterSetContentView();
        initData();
    }

    protected void afterSetContentView() {
        mToolBar = getViewById(R.id.id_toolbar);
        mDrawerLayout = getViewById(R.id.id_drawer_layout);
//        mNavigationView = getViewById(R.id.drawer_navigation);
        mNavigationView = getViewById(R.id.id_navigationView);

//        mNavRemoteManage = getViewById(R.id.tv_remote_manage);
//        mNavAppManage = getViewById(R.id.tv_app_manage);

//        mNavRemoteManage.setOnClickListener(this);
//        mNavAppManage.setOnClickListener(this);

        mImage = getViewById(R.id.ll_image);
        mVideo = getViewById(R.id.ll_video);
        mMusic = getViewById(R.id.ll_music);
        mApk = getViewById(R.id.ll_apk);
        mZip = getViewById(R.id.ll_zip);
        mDoc = getViewById(R.id.ll_doc);

        mTvImageNum = getViewById(R.id.tv_image_num);
        mTvVideoNum = getViewById(R.id.tv_video_num);
        mTvMusicNum = getViewById(R.id.tv_music_num);
        mTvApkNum = getViewById(R.id.tv_apk_num);
        mTvZipNum = getViewById(R.id.tv_zip_num);
        mTvDocNum = getViewById(R.id.tv_doc_num);

        mImage.setOnClickListener(this);
        mVideo.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mApk.setOnClickListener(this);
        mZip.setOnClickListener(this);
        mDoc.setOnClickListener(this);

        mStorageContainer = getViewById(R.id.ll_storage);
    }

    private <T> T getViewById(int id){
        return (T) findViewById(id);
    }

    public void initData() {

        BaseServer server = new BaseServer(8080);

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHomePresenter = new HomePresenter(this);
        mHomePresenter.onStart(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mToolBar.setElevation(55f);
        }
        //设置显示Toolbar
        setSupportActionBar(mToolBar);
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mNavigationView.inflateHeaderView(R.layout.layout_home_nav_header);
        mNavigationView.inflateMenu(R.menu.menu_home_nav);

        initDrawerLayout();
    }

    private void initDrawerLayout() {
        ViewGroup.LayoutParams layoutParams = mNavigationView.getLayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.width = displayMetrics.widthPixels*2/3;
        mNavigationView.setLayoutParams(layoutParams);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.main_search:
////                intentTo(SearchActivity.class);
//                break;
//            case R.id.setting:
////                intentTo(SettingActivity.class);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mNavigationView)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private String getNumberText(int number){
        return "(" + number + ")";
    }

    @Override
    public void animation() {
        mImage.post(new Runnable() {
            @Override
            public void run() {
                int width = mImage.getMeasuredWidth();
                int height = mImage.getMeasuredHeight();
                AnimationEngine.mainMediaAnimator(mImage,width/2,height/2);
                AnimationEngine.mainMediaAnimator(mVideo,width/2,height/2);
                AnimationEngine.mainMediaAnimator(mMusic,width/2,height/2);
                AnimationEngine.mainMediaAnimator(mApk,width/2,height/2);
                AnimationEngine.mainMediaAnimator(mZip,width/2,height/2);
                AnimationEngine.mainMediaAnimator(mDoc,width/2,height/2);
            }
        });
    }

    @Override
    public void updateImageInfo(PhotoResult result) {
        mTvImageNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateVideoInfo(VideoResult result) {
        mTvVideoNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateAudioInfo(AudioResult result) {
        mTvMusicNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateApkInfo(FileResult result) {
        mTvApkNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateZipInfo(FileResult result) {
        mTvZipNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateDocInfo(FileResult result) {
        mTvDocNum.setText(getNumberText(result.getItems().size()));
    }

    @Override
    public void updateStorageInfo(List<Storage> storageList) {
        mStorageContainer.removeAllViews();
        StorageItem item;
        for(final Storage storage : storageList){
            item = new StorageItem(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 5;
            mStorageContainer.addView(item.build(storage, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ExplorerActivity.KEY_STORAGE_DATA,storage);
//                    intentTo(ExplorerActivity.class,bundle);
                }
            }),params);
        }
    }

    @Override
    public void onClick(View v) {
        FileListData data = new FileListData();
        switch (v.getId()){
//            case R.id.tv_remote_manage:
//                mDrawerLayout.closeDrawers();
//                break;
//            case R.id.tv_app_manage:
//                mDrawerLayout.closeDrawers();
//                break;
            case R.id.ll_image:
//                intentTo(PhotoListActivity.class);
                break;
            case R.id.ll_video:
//                intentTo(VideoListActivity.class);
                break;
            case R.id.ll_music:
//                intentTo(AudioListActivity.class);
                break;
            case R.id.ll_apk:
                data.setTitle("安装包");
                data.setType(FileType.APK);
                break;
            case R.id.ll_zip:
                data.setTitle("压缩包");
                data.setType(FileType.ZIP);
                break;
            case R.id.ll_doc:
                data.setTitle("文档");
                data.setType(FileType.DOC);
                break;
        }
        switch (v.getId()){
            case R.id.ll_apk:
            case R.id.ll_zip:
            case R.id.ll_doc:
                Bundle bundle = new Bundle();
                bundle.putSerializable(FileListActivity.KEY_FILE_LIST_DATA,data);
//                intentTo(FileListActivity.class,bundle);
                break;
        }
    }
}
