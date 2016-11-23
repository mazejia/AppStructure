package test.mzj.com.appstructureproject.XlistView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import test.mzj.com.appstructureproject.R;


public class XHeaderView extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_FINISHED = 3;
    public final static String TYPE_LIVE = "live";

    private final int ROTATE_ANIM_DURATION = 180;

    private LinearLayout mContainer;

    private ImageView bgHeader;

    private ProgressBar mProgressBar;

    private TextView mHintTextView;

    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private AnimationDrawable mAnimDrawable;
    private Context context;


    private boolean mIsFirst;
    private RelativeLayout headerContent;

    public XHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public XHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // Initial set header view height 0
        this.context = context;
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.vw_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        bgHeader = (ImageView) findViewById(R.id.bg_header);
        headerContent = (RelativeLayout) findViewById(R.id.header_content);
        mHintTextView = (TextView) findViewById(R.id.header_hint_text);
        mProgressBar = (ProgressBar) findViewById(R.id.header_progressbar);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
    }

    public void setState(int state, String type) {
        if (state == mState && mIsFirst) {
            mIsFirst = true;
            return;
        }
        if(mAnimDrawable == null){
            mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_READY) {
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                }

                if (mState == STATE_FINISHED) {
                    mAnimDrawable.stop();
                    mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                }


                if (TextUtils.equals(type, TYPE_LIVE)) {
                    mHintTextView.setText("下拉显示更多比赛");
                } else {
                    mHintTextView.setText("下拉刷新");
                }

                break;

            case STATE_READY:
                if (mState != STATE_READY) {
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                    if (TextUtils.equals(type, TYPE_LIVE)) {
                        mHintTextView.setText(R.string.header_hint_refresh_ready_more_match);
                    } else {
                        mHintTextView.setText(R.string.header_hint_refresh_ready);
                    }

                }
                if (mState == STATE_FINISHED) {
                    mAnimDrawable.stop();
                    mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                }
                break;

            case STATE_REFRESHING:
                mHintTextView.setText(R.string.header_hint_refresh_loading);
                if (mState == STATE_FINISHED) {
                    mAnimDrawable.stop();
                    mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                }
                break;
            case STATE_FINISHED:
                if(mState == STATE_REFRESHING){
                    mAnimDrawable.stop();
                    mAnimDrawable = (AnimationDrawable) bgHeader.getBackground();
                    if (!mAnimDrawable.isRunning()){
                        mAnimDrawable.start();
                    }
                }
                mHintTextView.setText("刷新完成");
                break;

            default:
                break;
        }

        mState = state;
    }

    public void releaseAnimation(){
        if(mAnimDrawable != null) {
            mAnimDrawable.stop();
            mAnimDrawable = null;
        }
    }

    /**
     * Set the header view visible height.
     *
     * @param height
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * Get the header view visible height.
     *
     * @return
     */
    public int getVisibleHeight() {
        return mContainer.getHeight();
    }


    public void setBackground(int resId){
        mContainer.setBackgroundResource(resId);
    }

    public void setTextColor(int color){
        mHintTextView.setTextColor(color);
    }

    public int getState(){
        return mState;
    }

}
