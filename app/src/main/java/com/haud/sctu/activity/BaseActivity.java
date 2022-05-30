package com.haud.sctu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.haud.sctu.R;


public class BaseActivity extends AppCompatActivity {

    private final String TAG = BaseActivity.this.getClass().getSimpleName();

    protected AppBarLayout appBarLayout;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    protected Spinner spinnerSimWhenHeaderExpanded, spinnerSimWhenHeaderContracted;
    protected FrameLayout frameLayout;
    protected RelativeLayout simSpinnerWrapperWhenHeaderExpanded, simSpinnerWrapperWhenHeaderContracted, selectionFeature, bottomNavigation;
    protected ImageView backBtn, moreMenu, deleteSelected, uploadSelected;
    protected TextView pageTitle, selectedSimInfo;
    protected ProgressBar mProgressBar;


    @Override
    public void setContentView(int layoutResID) {

        RelativeLayout relativeLayoutBase = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        frameLayout = relativeLayoutBase.findViewById(R.id.activityFrameLayout);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(relativeLayoutBase);
    }


    // GET BASE WIDGETS
    public AppBarLayout getAppBarLayout() {
        appBarLayout = findViewById(R.id.appbar);
        return appBarLayout;
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
        return collapsingToolbarLayout;
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

    public RelativeLayout getSelectionFeature() {
        selectionFeature = findViewById(R.id.selectionFeature);
        return selectionFeature;
    }

    public ImageView getMoreMenu() {
        moreMenu = findViewById(R.id.moreMenu);
        return moreMenu;
    }

    public ImageView getDeleteSelected() {
        deleteSelected = findViewById(R.id.deleteSelected);
        return deleteSelected;
    }

    public ImageView getUploadSelected() {
        uploadSelected = findViewById(R.id.uploadSelected);
        return uploadSelected;
    }

    public ProgressBar getmProgressBar() {
        mProgressBar = findViewById(R.id.mProgressBar);
        return mProgressBar;
    }

    public RelativeLayout getBottomNavigation() {
        bottomNavigation = findViewById(R.id.bottom_nav);
        return bottomNavigation;
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
                    if (getPageTitle().getVisibility() != View.VISIBLE) {
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
        lp.height = (int) getResources().getDimension(androidx.appcompat.R.attr.actionBarSize);


        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return false;
            }
        });
        lp.setBehavior(behavior);
    }


    public void enableToolBarWhenCollapsableEnabled() {
        getBackBtn().setVisibility(View.GONE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.VISIBLE);
        getSelectionFeature().setVisibility(View.GONE);
        getMoreMenu().setVisibility(View.VISIBLE);
        getPageTitle().setVisibility(View.GONE);
        getBottomNavigation().setVisibility(View.VISIBLE);
    }

    public void enableToolBarWhenCollapsableDisabledMode() {
        getBackBtn().setVisibility(View.VISIBLE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getSelectionFeature().setVisibility(View.GONE);
        getMoreMenu().setVisibility(View.VISIBLE);
        getBottomNavigation().setVisibility(View.GONE);
    }

    public void enableToolbarWhenSelectionMode(){
        getBackBtn().setVisibility(View.VISIBLE);
        getSimSpinnerWrapperWhenHeaderContracted().setVisibility(View.GONE);
        getSelectionFeature().setVisibility(View.VISIBLE);
        getMoreMenu().setVisibility(View.GONE);
        getPageTitle().setVisibility(View.VISIBLE);
        getBottomNavigation().setVisibility(View.GONE);
    }


    public void setPageTitle(String pageTitle) {
        getPageTitle().setText(pageTitle);
    }

    public void setUploadImageToSynchImage() {
        getUploadSelected().setImageResource(R.drawable.ic_baseline_sync);
    }

    public void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
