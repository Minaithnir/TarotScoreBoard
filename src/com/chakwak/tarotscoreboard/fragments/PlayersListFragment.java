package com.chakwak.tarotscoreboard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract;
import com.chakwak.tarotscoreboard.activities.AddPlayerActivity;
import com.chakwak.tarotscoreboard.activities.MainActivity;
import com.chakwak.tarotscoreboard.dao.PlayerDao;

public class PlayersListFragment extends Fragment {
	
	private ListView listView = null;
	
	private Integer playerId = null;

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
		
		String[] from = new String[] {ScoreBoardContract.Player.COLUMN_NAME_PLAYER_NAME,
									ScoreBoardContract.Player.COLUMN_NAME_PLAYER_ID};
		int[] to = new int[] {android.R.id.text1, android.R.id.text2};
		
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(
			getActivity(), android.R.layout.simple_list_item_1,	c, from, to) {
    		
    		@Override
    		public View getView(int arg0, View arg1, ViewGroup arg2) {
    			View view = super.getView(arg0, arg1, arg2);
    			view.setTag(getCursor().getString(getCursor().getColumnIndex(ScoreBoardContract.Player.COLUMN_NAME_PLAYER_ID)));
    			return view;
    		}
    	};
    	
    	listView = (ListView)root.findViewById(R.id.fragment_players_list);
    	listView.setAdapter(adapter);

    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
    				int position, long id) {
				
				playerId = Integer.valueOf((String)view.getTag());
				
				// Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				// Chain together various setter methods to set the dialog characteristics
				builder.setMessage(R.string.dialog_message_data_losed)
				       .setTitle(R.string.dialog_title_delete_player);
				
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   	if(PlayerDao.isPlayerInAnyEvent(PlayersListFragment.this.getActivity(), playerId))
			   					Toast.makeText(PlayersListFragment.this.getActivity(),
			   							R.string.toast_cant_suppress_player, Toast.LENGTH_SHORT).show();
			        	   	else
			        	   		PlayerDao.deletePlayer(PlayersListFragment.this.getActivity(), PlayersListFragment.this.playerId);

			        	   	dialog.dismiss();
							Intent intent = new Intent(PlayersListFragment.this.getActivity(), MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra(AddPlayerActivity.PLAYER_CREATED, "player deleted");
							startActivity(intent);
			           }
			       });

				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   dialog.cancel();
			           }
			       });

				// Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				
				dialog.show();
				return false;
			}
		});
    	
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
