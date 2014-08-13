package com.chakwak.tarotscoreboard.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract;
import com.chakwak.tarotscoreboard.activities.AddPlayerActivity;
import com.chakwak.tarotscoreboard.dao.PlayerDao;

public class PlayersListFragment extends Fragment {
	
	private ListView listView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setHasOptionsMenu(true);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_players_list, container, false);
		
		Cursor c = PlayerDao.getAllPlayers(getActivity());
		
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(
			getActivity(), 
			android.R.layout.simple_list_item_1, 
			c, 
			new String[] {ScoreBoardContract.Player.COLUMN_NAME_PLAYER_NAME}, 
			new int[] {android.R.id.text1});
    	
    	listView = (ListView)root.findViewById(R.id.fragment_players_list);
    	listView.setAdapter(adapter);
    	
    	return root;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.players_list, menu);
    	super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.action_add_player:
			onPlayerAdd();
			break;
		default:
			break;
		}
    	return super.onOptionsItemSelected(item);
    }
    
    private void onPlayerAdd() {
    	Intent intent = new Intent(this.getActivity(), AddPlayerActivity.class);
    	startActivity(intent);
    }
}
