package test.mzj.com.appstructureproject.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ImageView;

import test.mzj.com.appstructureproject.utils.ScreenUtil;

/**
 * Created by mazejia on 2016/11/10.
 */

public class BaseFragment extends Fragment {
    protected boolean isVisible;
    private String title = "unkown";
    private boolean isFirstNet = true;

    public BaseFragment() {
        super();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void onBackClicked() {
        if (!isAdded()) {
            return;
        }
        getActivity().finish();
    }

    /**
     * 某一个fragment被选中
     */
    public void onPageSelected() {
    }

    protected void unbindDrawables(View view) {
        if (view == null) {
            return;
        }

        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(null);
        } else if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            if (!(view instanceof AbsSpinner) && !(view instanceof AbsListView)) {
                ((ViewGroup) view).removeAllViews();
            }
        }
    }

    /**
     * @param rootView
     * @param subId
     * @param inflateId
     * @return 有可能返回null 注意异常处理
     */
    protected View inflateSubView(View rootView, int subId, int inflateId) {
        View noNetSubTree = rootView.findViewById(inflateId);
        if (noNetSubTree == null) {
            ViewStub viewStub = (ViewStub) rootView.findViewById(subId);
            if (viewStub != null) {
                //subId被inflateId替代
                noNetSubTree = viewStub.inflate();
            }
        }
      /*  if (noNetSubTree != null) {
            noNetSubTree.setVisibility(View.VISIBLE);
        }*/
        return noNetSubTree;
    }

    /**
     * 沉浸式布局
     *
     * @param view
     */
    protected void setImmerseLayout(View view) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtil.getStatusBarHeight(getActivity());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 内容为空的时候显示
     */
    protected void showInflateSubView(int stub, int subTree) {
        View rootView = getView();
        if (rootView == null) {
            return;
        }
        View view = inflateSubView(rootView, stub,
                subTree);
        view.setVisibility(View.VISIBLE);
    }


    /**
     * 检测到网络发生变化
     *
     * @param netType 0 = 没有网络; 1 = 3G/2G; 2 = wifi
     */
    protected void onNetChanged(int netType) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isVisible) {
            onVisible();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isVisible) {
            onInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void onVisible() {
    }

    protected void onInvisible() {
    }
}
