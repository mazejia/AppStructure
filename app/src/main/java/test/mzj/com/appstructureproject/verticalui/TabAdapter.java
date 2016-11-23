package test.mzj.com.appstructureproject.verticalui;


/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public interface TabAdapter {
    int getCount();

    int getBadge(int position);

    QTabView.TabIcon getIcon(int position);

    QTabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
