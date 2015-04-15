package com.isme.gamble.activity;

import com.isme.gamble.AnalyzeApplication;
import com.isme.gamble.R;
import com.isme.gamble.game.Start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

/**
 * 该应用用来计算 比率
 * @author and
 *     2015-4-14
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button btnPlay;
	private Button btnAnalyze;
	private TextView tvProgress;
	private EditText etNum;
	private EditText etTimes;
	
	private int num;
	private int times;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeView();
    }

	private void initializeView() {
		btnPlay = (Button) findViewById(R.id.btn_shuffle);
		btnAnalyze = (Button) findViewById(R.id.btn_analyze);
		tvProgress = (TextView) findViewById(R.id.tv_progress);
		etNum = (EditText) findViewById(R.id.et_num);
		etTimes = (EditText) findViewById(R.id.et_times);
		
		btnPlay.setOnClickListener(this);
		btnAnalyze.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_shuffle:
			
			num = Integer.parseInt(etNum.getText().toString());
			if(num > 10)
			{
				Toast.makeText(MainActivity.this, "超过了10人", Toast.LENGTH_SHORT).show();
				break;
			}
			times = Integer.parseInt(etTimes.getText().toString());
			
			final Start start = new Start(num, MainActivity.this);
			
			new Thread(){
				public void run() {
					for(int i=0; i<times; i++)
					{
						start.play();
						
//						try {
//							Thread.sleep(50);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
					}
				};
			}.start();
			
			break;
			
		case R.id.btn_analyze:
			tvProgress.setText("0:"+AnalyzeApplication.analyze[0] +"\n"+
					"1:"+AnalyzeApplication.analyze[1] +"\n"+
					"2:"+AnalyzeApplication.analyze[2] +"\n"+
					"3:"+AnalyzeApplication.analyze[3] +"\n"+
					"4:"+AnalyzeApplication.analyze[4] +"\n"+
					"5:"+AnalyzeApplication.analyze[5] +"\n");
			break;

		default:
			break;
		}
	}

	/**
	 * 显示进度
	 * @param i
	 */
	protected void show(final int i) {
		if(i %100 == 0)
		{
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				
				@Override
				public void run() {
					tvProgress.setText(String.valueOf(i));
				}
			});
		}
	}

}
