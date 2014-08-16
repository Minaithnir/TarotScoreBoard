package com.chakwak.tarotscoreboard.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.fragments.EventsListFragment;
import com.chakwak.tarotscoreboard.fragments.PlayersListFragment;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {
	
	static final String KEY_SAVEOPENEDTAB = "com.chakwak.tarotscoreboard.mainactivity.KEY_SAVEOPENEDTAB";
	
	private PlayersListFragment plf = null;
	private EventsListFragment elf = null;
	private int selectedTab = 0;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ActionBar bar = getSupportActionBar();

        bar.addTab(bar.newTab().setText(R.string.tab_events_name).setTabListener(this).setIcon(R.drawable.ic_action_event));
        bar.addTab(bar.newTab().setText(R.string.tab_players_name).setTabListener(this).setIcon(R.drawable.ic_action_group));
        
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowHomeEnabled(false);
        
        if(savedInstanceState != null) {
			selectedTab = savedInstanceState.getInt(KEY_SAVEOPENEDTAB);
			bar.selectTab(bar.getTabAt(selectedTab));
        } else if(getIntent().getStringExtra(AddPlayerActivity.PLAYER_CREATED) != null) {
        	bar.selectTab(bar.getTabAt(1));
        }
    }
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt(KEY_SAVEOPENEDTAB, getSupportActionBar().getSelectedTab().getPosition());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(savedInstanceState.containsKey(KEY_SAVEOPENEDTAB))
		{
	        ActionBar bar = getSupportActionBar();
			selectedTab = savedInstanceState.getInt(KEY_SAVEOPENEDTAB);
			bar.selectTab(bar.getTabAt(selectedTab));
		}
		super.onRestoreInstanceState(savedInstanceState);
	}
    
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		if(tab.getPosition()==0) {
			if(elf == null) {
				elf = new EventsListFragment();
			}
			ft.replace(R.id.main_container, elf);
		} else {
			if(plf == null) {
				plf = new PlayersListFragment();
			}
			ft.replace(R.id.main_container, plf);
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do Nothing
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// Do Nothing
	}
}
