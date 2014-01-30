package tapatan.a;


import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;


public class DisplayGame extends Activity{
	//boolean p1 = true;
	TextView player1Text;
    TextView player2Text;
    private tapatanBoard board;	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        board = new tapatanBoard();
        player1Text = (TextView)findViewById(R.id.textView1);
        player2Text = (TextView)findViewById(R.id.textView2);
        
        // Initialize turn color
		player1Text.setBackgroundResource(R.color.lightBlue);
		player2Text.setBackgroundResource(R.color.white);
    }   

public void onClick(View v){
	int position = Integer.parseInt(v.getTag().toString());
	if (board.place(position)) {	
		// Player one's turn to drop piece
		if (!board.getTurn()) { // End of player 1's turn
			v.setBackgroundResource(R.drawable.movable_player1);
			v.setVisibility(v.VISIBLE);
			
			// Highlight player 2's turn
			player1Text.setBackgroundResource(R.color.white);
			player2Text.setBackgroundResource(R.color.lightRed);
			
		} else { // End of player 2's turn
			v.setBackgroundResource(R.drawable.movable_player2);
			v.setVisibility(v.VISIBLE);
			
			// Highlight player 1's turn
			player1Text.setBackgroundResource(R.color.lightBlue);
			player2Text.setBackgroundResource(R.color.white);
		} 
	}
	
	checkWin();
}


private void checkWin(){
	int check = board.checkWin();
	int player;

	if(board.turn){
		player = 1;
	}else{
		player = 2;
	}

	if(check == 0){
		return;
	} else{ 
		winDialog winScreen = new winDialog(player);
	    FragmentManager fragmentManager = getFragmentManager();
		winScreen.show(fragmentManager,"tag");
	}		
}

@SuppressLint("ValidFragment")
public class winDialog extends DialogFragment{
	private int winner;

	public winDialog(int winner){
		this.winner = winner;
	}
	public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.winner1 + winner + R.string.winner2 ).setPositiveButton("OK",
        		new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
            	
            }
        
        });

		return builder.create();

	}
	
	
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
