package com.chakwak.tarotscoreboard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.ScoreBoardContract.Event;
import com.chakwak.tarotscoreboard.activities.CreateEventActivity;
import com.chakwak.tarotscoreboard.activities.EventBoardActivity;
import com.chakwak.tarotscoreboard.activities.MainActivity;
import com.chakwak.tarotscoreboard.dao.EventDao;

public class EventsListFragment extends Fragment {
	
	private ListView listView = null;
	
	private Integer eventId = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    }
	
    @SuppressWarnings("deprecation")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		View root = inflater.inflate(R.layout.fragment_events_list, container, false);
		
		String[] from = new String[] {Event.COLUMN_NAME_EVENT_DATE,
									Event.COLUMN_NAME_LOCATION};
		int[] to = new int[] {android.R.id.text1, android.R.id.text2};
		
		Cursor c = EventDao.getAllEvents(getActivity());
		
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(
			getActivity(), android.R.layout.simple_list_item_2,	c, from, to) {
    		
    		@Override
    		public View getView(int arg0, View arg1, ViewGroup arg2) {
    			View view = super.getView(arg0, arg1, arg2);
    			view.setTag(getCursor().getString(getCursor().getColumnIndex(Event.COLUMN_NAME_EVENT_ID)));
    			return view;
    		}
    	};
    	
    	listView = (ListView)root.findViewById(R.id.list_event);
    	listView.setAdapter(adapter);
    	
    	listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
            	
                Intent intent = new Intent(EventsListFragment.this.getActivity(), EventBoardActivity.class);
                intent.putExtra(EventBoardActivity.EVENT_BOARD_ID, (String)view.getTag());
                startActivity(intent);
            }
        });
    	
    	listView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				return false;
			}
		});
    	

    	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
    				int position, long id) {
				
				eventId = Integer.valueOf((String)view.getTag());
				
				// Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

				// Chain together various setter methods to set the dialog characteristics
				builder.setMessage(R.string.dialog_message_delete_event)
				       .setTitle(R.string.dialog_title_delete_event);
				
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
			        	   	EventDao.deleteEvent(EventsListFragment.this.getActivity(), EventsListFragment.this.eventId);

			        	   	dialog.dismiss();
							Intent intent = new Intent(EventsListFragment.this.getActivity(), MainActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    	
	    Button button = (Button) root.findViewById(R.id.button_create_event);
	   
	    button.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
         	Intent intent = new Intent(EventsListFragment.this.getActivity(), CreateEventActivity.class);
        	startActivity(intent);
         	} 
		});
		
    	return root;
    }
    
}
