package com.haud.sctu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haud.sctu.R;


public class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.this.getClass().getSimpleName();

    protected AppBarLayout appBarLayout;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected Toolbar toolbar;
    protected Spinner spinnerSimWhenHeaderExpanded, spinnerSimWhenHeaderContracted;
    protected FrameLayout frameLayout;
    protected RelativeLayout simSpinnerWrapperWhenHeaderExpanded, simSpinnerWrapperWhenHeaderContracted;
    protected ImageView backBtn;
    protected TextView pageTitle, selectedSimInfo, waLogTotal, waLogStatus;
    protected ProgressBar mProgressBar;
    protected BottomNavigationView bottomNavigation;
    protected ActionMenuItemView uploadAllSms, uploadAllCalls, settings, uploadSelected, deleteSelected, searchLogs;

    protected EditText searchInput;

    @Override
    public void setContentView(int layoutResID) {
        RelativeLayout relativeLayoutBase = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        frameLayout = relativeLayoutBase.findViewById(R.id.activityFrameLayout);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(relativeLayoutBase);
    }


    // GET BASE WIDGETS
    public AppBarLayout getAppBarLayout() {
        appBarLayout = findViewById(R.id.appBar);
        return appBarLayout;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        return collapsingToolbarLayout;
    }

    public Toolbar getToolbar() {
        toolbar = findViewById(R.id.toolbar);
        return toolbar;
    }

    public FrameLayout getFrameLayout() {
        frameLayout = findViewById(R.id.activityFrameLayout);
        return frameLayout;
    }

    public Spinner getSpinnerSimWhenHeaderContracted() {
        spinnerSimWhenHeaderContracted = findViewById(R.id.spinnerSimWhenHeaderContracted);
        return spinnerSimWhenHeaderContracted;
    }

    public Spinner getSpinnerSimWhenHeaderExpanded() {
        spinnerSimWhenHeaderExpanded = findViewById(R.id.spinnerSimWhenHeaderExpanded);
        return spinnerSimWhenHeaderExpanded;
    }

    public RelativeLayout getSimSpinnerWrapperWhenHeaderContracted() {
        simSpinnerWrapperWhenHeaderContracted = findViewById(R.id.simSpinnerWrapperWhenHeaderContracted);
        return simSpinnerWrapperWhenHeaderContracted;
    }

    public RelativeLayout getSimSpinnerWrapperWhenHeaderExpanded() {
        simSpinnerWrapperWhenHeaderExpanded = findViewById(R.id.simSpinnerWrapperWhenHeaderContracted);
        return simSpinnerWrapperWhenHeaderExpanded;
    }

    public ImageView getBackBtn() {
        backBtn = findViewById(R.id.backBtn);
        return backBtn;
    }

    public TextView getPageTitle() {
        pageTitle = findViewById(R.id.pageTitle);
        return pageTitle;
    }

    public TextView getSelectedSimInfo() {
        selectedSimInfo = findViewById(R.id.selectedSimInfo);
        return selectedSimInfo;
    }

    public TextView getWaLogTotal() {
        waLogTotal = findViewById(R.id.waLogTotal);
        return waLogTotal;
    }

    public TextView getWaLogStatus() {
        waLogStatus = findViewById(R.id.waLogStatus);
        return waLogStatus;
    }

    public ActionMenuItemView getUploadAllSms() {
        uploadAllSms = findViewById(R.id.uploadAllSms);
        return uploadAllSms;
    }

    public ActionMenuItemView getUploadAllCalls() {
        uploadAllCalls = findViewById(R.id.uploadAllCalls);
        return uploadAllCalls;
    }

    public ActionMenuItemView getSettings() {
        settings = findViewById(R.id.settings);
        return settings;
    }

    public ActionMenuItemView getUploadSelected() {
        uploadSelected = findViewById(R.id.uploadSelected);
        return uploadSelected;
    }

    public ActionMenuItemView getDeleteSelected() {
        deleteSelected = findViewById(R.id.deleteSelected);
        return deleteSelected;
    }

    public ActionMenuItemView getSearchLogs() {
        searchLogs = findViewById(R.id.searchLogs);
        return searchLogs;
    }

    public ProgressBar getmProgressBar() {
        mProgressBar = findViewById(R.id.mProgressBar);
        return mProgressBar;
    }

    public BottomNavigationView getBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_nav);
        return bottomNavigation;
    }

    public EditText getSearchInput() {
        searchInput = findViewById(R.id.searchInput);
        return searchInput;
    }

    /**
     * app bar offset changed listener
     */
    protected void appBarListener() {
        getAppBarLayout().addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(getCollapsingToolbarLayout().getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(getCollapsingToolbarLayout())) {
                    getSimSpinnerWrapperWhenHeaderExpanded().animate().alpha(0).setDuration(600);
                    if (getPageTitle().getVisibility() != View.VISIBLE && getSearchInput().getVisibility() != View.VISIBLE) {
                        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.VISIBLE);
                    }
                    getSimSpinnerWrapperWhenHeaderContracted().animate().alpha(1).setDuration(600);
                } else {
                    getSimSpinnerWrapperWhenHeaderExpanded().animate().alpha(1).setDuration(600);
                    getSimSpinnerWrapperWhenHeaderContracted().animate().alpha(0).setDuration(600);
                    getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
                }
            }
        });
    }

    @SuppressLint("ResourceType")
    public void lockAppBarClosed(AppBarLayout appBarLayout) {
        appBarLayout.setExpanded(false, false);
        appBarLayout.setActivated(false);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        // lp.height = (int) getResources().getDimension(androidx.appcompat.R.attr.actionBarSize);

        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        lp.setBehavior(behavior);
    }

    @SuppressLint("ResourceType")
    public void unlockAppBar(AppBarLayout appBarLayout) {
        appBarLayout.setExpanded(true, true);
        appBarLayout.setActivated(true);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        // lp.height = (int) getResources().getDimension(androidx.appcompat.R.attr.actionBarSize);

        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return true;
            }
        });
        lp.setBehavior(behavior);
    }

    public void enableToolBarWhenCollapsableEnabled() {
        getBackBtn().setVisibility(View.GONE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getPageTitle().setVisibility(View.GONE);
        getBottomNavigation().setVisibility(View.VISIBLE);
        getSearchInput().setVisibility(View.GONE);
        getToolbar().getMenu().findItem(R.id.uploadSelected).setVisible(false);
        getToolbar().getMenu().findItem(R.id.deleteSelected).setVisible(false);
        getToolbar().getMenu().findItem(R.id.searchLogs).setVisible(true);
        getToolbar().getMenu().findItem(R.id.uploadAllCalls).setVisible(true);
        getToolbar().getMenu().findItem(R.id.uploadAllSms).setVisible(true);
        getToolbar().getMenu().findItem(R.id.settings).setVisible(true);
    }

    public void enableToolBarWhenCollapsableDisabledMode() {
        getBackBtn().setVisibility(View.VISIBLE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getPageTitle().setVisibility(View.VISIBLE);
        getBottomNavigation().setVisibility(View.GONE);
        getSearchInput().setVisibility(View.GONE);
    }

    public void enableToolbarWhenSelectionMode() {
        getBackBtn().setVisibility(View.VISIBLE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getPageTitle().setVisibility(View.VISIBLE);
        getBottomNavigation().setVisibility(View.VISIBLE);
        getSearchInput().setVisibility(View.GONE);
        getToolbar().getMenu().findItem(R.id.uploadSelected).setVisible(true);
        getToolbar().getMenu().findItem(R.id.deleteSelected).setVisible(true);
        getToolbar().getMenu().findItem(R.id.searchLogs).setVisible(false);
        getToolbar().getMenu().findItem(R.id.uploadAllCalls).setVisible(false);
        getToolbar().getMenu().findItem(R.id.uploadAllSms).setVisible(false);
        getToolbar().getMenu().findItem(R.id.settings).setVisible(false);
    }

    public void enableToolbarWhenSearchMode() {
        getBackBtn().setVisibility(View.VISIBLE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getPageTitle().setVisibility(View.GONE);
        getBottomNavigation().setVisibility(View.GONE);
        getSearchInput().setVisibility(View.VISIBLE);
        getToolbar().getMenu().clear();
    }

    public void setPageTitle(String pageTitle) {
        getPageTitle().setText(pageTitle);
    }

    public void setWaLogTotal(String waLogTotal) {
        getWaLogTotal().setText(waLogTotal);
    }

    public void setWaLogStatus(int waLogStatus) {
        getWaLogStatus().setText(String.valueOf(waLogStatus));
    }

    public void setUploadImageToSyncImage() {
    }

    public void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
