package com.chakwak.tarotscoreboard.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.chakwak.tarotscoreboard.R;
import com.chakwak.tarotscoreboard.dao.GameDao;
import com.chakwak.tarotscoreboard.dao.PlayerDao;
import com.chakwak.tarotscoreboard.entities.Player;
import com.chakwak.tarotscoreboard.enums.Appel;
import com.chakwak.tarotscoreboard.enums.Prise;

public class EventBoardActivity extends ActionBarActivity {
	
	public static final String EVENT_BOARD_ID = "com.chakwak.tarotscoreboard.EVENT_BOARD_ID";
	
	private Long eventId = null;
	private ArrayList<Player> players = null; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_board);
		
		if(eventId == null) {
			eventId = Long.valueOf(getIntent().getStringExtra(EVENT_BOARD_ID));
		}

		Cursor c = PlayerDao.getPlayersInEvent(this, eventId);
		
		// création du header et population de la liste de joueurs
		TableRow header = (TableRow)findViewById(R.id.row_header_event_board);
		
		TextView cell = null;
		if(players!=null) {
			players.clear();
		} else {
			players = new ArrayList<Player>();
		}
		
		c.moveToFirst();
		while(!c.isAfterLast()) {
			players.add(new Player(c.getInt(0), c.getString(1)));
			
			getLayoutInflater().inflate(R.layout.cell_event_board, header, true);
			cell = (TextView)header.getChildAt(header.getChildCount()-1);
			cell.setText(players.get(players.size()-1).getName());
			cell.setBackgroundResource(c.getPosition()%2 == 1 ? R.color.head_odd : R.color.head_pair);
			c.moveToNext();
		}
		
		c.close();

		c = GameDao.getGames(this, eventId);
		
		// création des lignes correspondant à chaque partie
		TableLayout root = (TableLayout)findViewById(R.id.table_event_board);
		TableRow row = null;
		Integer[] scores = new Integer[players.size()];
		Integer[] playersId = new Integer[players.size()];
		for(int i=0;i<players.size();i++){
			playersId[i] = players.get(i).getId();
		}
		
		Cursor cAPlayers = null;
		Integer[] aPlayers = null;
		c.moveToFirst();
		while(!c.isAfterLast()) {
			getLayoutInflater().inflate(R.layout.row_event_board, root, true);
			row = (TableRow)root.getChildAt(root.getChildCount()-1);
			
			cAPlayers = PlayerDao.getPlayersInGame(this, Long.valueOf(c.getInt(6)));
			aPlayers = new Integer[cAPlayers.getCount()];
			cAPlayers.moveToFirst();
			while(!cAPlayers.isAfterLast()){
				aPlayers[cAPlayers.getPosition()] = cAPlayers.getInt(0);
				cAPlayers.moveToNext();
			}
			
			scores = getScores(scores,//previousScores
					playersId,//playersId
					aPlayers,//players
					c.getInt(0),//taker
					Prise.get(c.getString(1)),//prise
					Appel.get(c.getString(2)),//appel
					c.getInt(3),//associate
					c.getInt(4)>0,//valid
					c.getInt(5));//resultat
			
			for(int i=0; i<scores.length; i++) {
				getLayoutInflater().inflate(R.layout.cell_event_board, row, true);
				cell = (TextView)row.getChildAt(row.getChildCount()-1);
				cell.setText(String.valueOf(scores[i]));
				cell.setBackgroundResource((i+c.getPosition())%2 == 1 ? R.color.odd_odd : R.color.odd_pair);
			}
			c.moveToNext();
		}
	}
	
	private Integer[] getScores(
			Integer[] scores, Integer[] playersId, Integer[] actualPlayers, Integer takerId, 
			Prise prise, Appel appel, Integer associateId, Boolean valid, Integer diff) {

		int pCount = actualPlayers.length;
		int base = diff;
		switch(prise) {
			case PETITE :
				base+=10;
				break;
			case POUSSE :
				base+=20;
				break;
			case GARDE :
				base+=50;
				break;
			case GARDE_SANS :
				base+=70;
				break;
			case GARDE_CONTRE :
				base+=90;
				break;
			default:
				break;
		}
		if(!valid) {
			base *= -1;
		}
		/*
		 * taker get double point playing 1vs2 or 2vs3
		 * taker get triple points playing 1vs3 or 2vs2
		 * taker get quadruple points playings 1vs4
		 */
		int takerAdd = base*2;
		if(pCount == 4) {
			takerAdd+=base;
		} else if(pCount == 5) {
			takerAdd += associateId==takerId ? base*2:0;
		}
		//Associate always get simple points
		//Defense get double inverse point when playing 2vs2
		int defAdd = base;
		if(pCount==4 && appel!=Appel.AUCUN && associateId!=takerId) {
			defAdd+=base;
		}
		
		for(int i=0; i<scores.length; i++) {
			if(scores[i] == null) {
				scores[i] = 0;
			}
			
			if(playersId[i]==takerId) {
				scores[i]+=takerAdd;
			} else if(pCount>3 && appel != Appel.AUCUN && playersId[i]==associateId) {
				scores[i]+=base;
			} else if(played(actualPlayers, playersId[i])) {
				scores[i]-=defAdd;
			}
		}
		
		return scores;
	}
	
	private boolean played(Integer[] pool, Integer id) {
		for(Integer i:pool) {
			if(i==id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.event_board, menu);

		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
			case R.id.action_add_game:
				onGameAdd();
				break;
			default:
				break;
		}
    	return super.onOptionsItemSelected(item);
    }
    
    private void onGameAdd() {
		Intent intent = new Intent(this, CreateGameActivity.class);
		intent.putExtra(EVENT_BOARD_ID, eventId.toString());
		startActivity(intent);
    }
}
