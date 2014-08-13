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
	
	private PlayersListFragment plf = null;
	private EventsListFragment elf = null;
	
	
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
