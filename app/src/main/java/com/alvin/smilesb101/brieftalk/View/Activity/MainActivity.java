package com.alvin.smilesb101.brieftalk.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean.UserInfo;
import com.alvin.smilesb101.brieftalk.Bean.BmobTableBean._User;
import com.alvin.smilesb101.brieftalk.R;
import com.alvin.smilesb101.brieftalk.View.Activity.BaseActivity.ThemeBaseActivity;
import com.alvin.smilesb101.brieftalk.View.CustomView.RoundImageView;
import com.alvin.smilesb101.brieftalk.View.Fragment.CommunityFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.DictionaryFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.DiscoryFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.TranslateFragment;
import com.alvin.smilesb101.brieftalk.View.Fragment.WordBookFragment;
import com.alvin.smilesb101.brieftalk.View.Utils.ToastUtils;
import com.alvin.smilesb101.brieftalk.databinding.ActivityNavMainBinding;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends ThemeBaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private int lastSelectedPosition = 0;
    private BottomNavigationBar navigationBar;
    private Context context;

    private BottomNavigationBar.OnTabSelectedListener bottomNavSelect = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            selectFragment(position);
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };

    private FragmentManager mFm;
    private String TAG = "MainActivity";
    private DrawerLayout drawer;

    private DictionaryFragment dictionaryFragment;
    private TranslateFragment translateFragment;
    private DiscoryFragment discoryFragment;
    private WordBookFragment wordBookFragment;
    private CommunityFragment communityFragment;

    private ActivityNavMainBinding binding;

    private View drawerHeader;
    private AppCompatImageView headerBackImage;
    private RoundImageView headerImage;
    private TextView signInText;

    private String appkey = "29a43c5b31ca43fe";

    private _User user;
    private UserInfo userInfo;

    private NavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.navigation_mine:
                    //mTextMessage.setText(R.string.title_mine);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_nav_main);
        //setContentView(R.layout.activity_nav_main);
        Log.i(TAG, "onCreate: "+binding);

        initView();
        bindingValue();
        initValue();
    }
    void bindingValue(){

        Intent intent = getIntent();
        if(intent!=null){

            user = (_User) intent.getSerializableExtra("userInfo");
            if(user==null)
            {
                    user = BmobUser.getCurrentUser(this,_User.class);
            }
            View headerView = navigationView.getHeaderView(0);
            TextView drawerUser = headerView.findViewById(R.id.userName);
            if(user!=null) {
                drawerUser.setText(user.getUsername());

                Glide.with(this)
                        .load(user.userHeader)
                        .into((ImageView) findViewById(R.id.headImage));

                Glide.with(this)
                        .load(user.userHeader)
                        .into((ImageView) headerView.findViewById(R.id.userHeaderImage));

                //获取用户信息
                BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();

                bmobQuery.addWhereEqualTo("userId",user.userId);

                bmobQuery.setLimit(1);
                bmobQuery.findObjects(this, new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {
                        if(list.size()>0) {
                            userInfo = list.get(0);
                            signInText.setText("连续 "+userInfo.getSignInTime()+" 天签到");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        ToastUtils.show(MainActivity.this,"呀，服务器被黑洞吃掉了呢。。\n原因："+s);
                    }
                });
            }
        }

        binding.setMActivity(this);

        //注册应用ID ，建议在应用启动时，初始化，所有功能的使用都需要该初始化，调用一次即可，demo中在MainActivity类中
        //YouDaoApplication.init(this, appkey);
    }

    void initView(){
        /*
        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        navigationBar = findViewById(R.id.bottom_navigation_bar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//显示原色
        navigationView.setNavigationItemSelectedListener(this);

        drawerHeader = navigationView.getHeaderView(0);
        View relativeLayout = drawerHeader.findViewById(R.id.headerLayout);

        View signBtn = drawerHeader.findViewById(R.id.signInBtn);
        signInText = drawerHeader.findViewById(R.id.textView);

        signBtn.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

        headerBackImage = drawerHeader.findViewById(R.id.headerbackGround);
        headerImage = drawerHeader.findViewById(R.id.userHeaderImage);
        context = this;
    }

    void initValue(){
        navigationBar.setTabSelectedListener(bottomNavSelect)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setActiveColor(R.color.colorSliteWrite)//背景色
                .setInActiveColor(R.color.black_overlay)
                .setBarBackgroundColor(R.color.colorRed);//Active 色

        navigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_dictionary, "词典"))
                .addItem(new BottomNavigationItem(R.drawable.ic_dictionaryleft, "翻译"))
                .addItem(new BottomNavigationItem(R.drawable.ic_wordlist, "单词本"))
                .addItem(new BottomNavigationItem(R.drawable.ic_cummunity, "圈子"))
                .addItem(new BottomNavigationItem(R.drawable.ic_discoery, "发现"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        setDefaultFragment();
    }

    void setDefaultFragment(){
        mFm = getSupportFragmentManager();
        FragmentTransaction transaction = mFm.beginTransaction();
        dictionaryFragment = DictionaryFragment.newInstance(null);
        binding.setTitleText(dictionaryFragment.getTitle());
        transaction.replace(R.id.contentPanel,dictionaryFragment);
        transaction.commit();
    }

    void selectFragment(int position){
        FragmentTransaction transaction = mFm.beginTransaction();
        if(position>=0){
            switch (position){
                case 0://词典
                    if(dictionaryFragment!=null){
                        transaction.replace(R.id.contentPanel,dictionaryFragment);
                        binding.setTitleText(dictionaryFragment.getTitle());
                    }
                    break;
                case 1://翻译
                    if(translateFragment==null){
                        translateFragment = TranslateFragment.newInstance(null);
                    }
                    if(translateFragment!=null){
                        transaction.replace(R.id.contentPanel,translateFragment);
                        binding.setTitleText(translateFragment.getTitle());
                    }
                    break;
                case 2://单词本
                    if(wordBookFragment==null){
                        wordBookFragment = WordBookFragment.newInstance(null);
                    }
                    if(wordBookFragment!=null){
                        transaction.replace(R.id.contentPanel,wordBookFragment);
                        binding.setTitleText(wordBookFragment.getTitle());
                    }
                    break;
                case 3://圈子
                    if(communityFragment==null){
                        communityFragment = CommunityFragment.newInstance(null);
                    }
                    if(communityFragment!=null)
                    {
                        transaction.replace(R.id.contentPanel,communityFragment);
                        binding.setTitleText(communityFragment.getTitle());
                    }
                    break;
                case 4://发现
                    if(discoryFragment==null){
                        discoryFragment = DiscoryFragment.newInstance(null);
                    }
                    if(discoryFragment!=null)
                    {
                        transaction.replace(R.id.contentPanel,discoryFragment);
                        binding.setTitleText(discoryFragment.getTitle());
                    }
                    break;
            }
        }

        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //home
        //recreate();//重启
        int id = item.getItemId();

        switch (id){
            case R.id.nav_message:
                break;
            case R.id.nav_plan:
                break;
            case R.id.nav_view:
                break;
            case R.id.nav_skin:
                // 打开皮肤界面
                startActivity(new Intent(this,SkinActivity.class));
                break;
            case R.id.nav_setting:
                // 打开设置
                break;
            case R.id.nav_send:
                // 打开反馈
                break;
            case R.id.nav_about:
                // 打开关于
                startActivity(new Intent(this,AboutProgrammAvtivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.headImage:
                //Log.i(TAG,"onClick: 展开导航");
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.camera_translate:
                //开始拍照，search
                Log.i(TAG,"onClick: 拍照翻译");
                break;
            case R.id.headerLayout:
                //个人信
                Intent intent = new Intent(this,UserInfoActivity.class);
                intent.putExtra("userInfo",userInfo);
                intent.putExtra("user",user);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        new Pair<View, String>(headerBackImage,"headerbackGround"),
                        new Pair<View, String>(headerImage,"headerImage"));

                context.startActivity(intent,options.toBundle());
                break;
            case R.id.signInBtn:
                Intent userInfoIntent = new Intent(this,SignInActivity.class);
                userInfoIntent.putExtra("userInfo",userInfo);
                context.startActivity(userInfoIntent);
                break;
            default:
                break;
        }
    }


}
