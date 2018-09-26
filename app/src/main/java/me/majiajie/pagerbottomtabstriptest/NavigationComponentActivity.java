package me.majiajie.pagerbottomtabstriptest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstriptest.utils.BottomNavigationUtils;

/**
 * 官方Navigation组件演示
 */
public class NavigationComponentActivity extends AppCompatActivity {

    private final int[] PAGE_IDS = {
            R.id.navigationComponentPageAFragment,
            R.id.navigationComponentPageBFragment,
            R.id.navigationComponentPageCFragment
    };

    private NavController mNavController;

    private PageNavigationView mNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_component);
        mNavigation = findViewById(R.id.navigation);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        initBottomNavigation(mNavigation);
    }

    /**
     * 初始化底部导航
     */
    private void initBottomNavigation(PageNavigationView pageNavigationView) {
        NavigationController navigationController = pageNavigationView.material()
                .addItem(R.drawable.ic_favorite_gray_24dp, "A")
                .addItem(R.drawable.ic_favorite_gray_24dp, "B")
                .addItem(R.drawable.ic_favorite_gray_24dp, "C")
                .build();

        BottomNavigationUtils.setupWithNavController(PAGE_IDS, navigationController, mNavController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp();
    }


}
