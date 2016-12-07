package test.mzj.com.appstructureproject.mvp;

import java.util.ArrayList;

/**
 * mvp架构示例
 */
public class MvpStructure {
    public interface Model{
        void loadContent();
    }

    public interface View<T>{
        void refreshContentView(ArrayList<T> data);
        void loadMoreContentView(ArrayList<T> data);
        void onLoadDataFail(int errorCode, String errorMsg);
    }

    public interface Presenter{
        //开始加载数据
        void start();
        //停止加载数据
        void stop();
        //加载更多数据
        void loadMore();
    }

    /**
     * model与Presenter的接口
     * @param <T>
     */
    public interface InteractionListener<T>{
        void onInteractionSuccess(T t);
        void onInteractionFail(int errorCode, String errorMsg);
    }
}
