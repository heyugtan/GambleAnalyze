package com.isme.gamble.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.isme.gamble.AnalyzeApplication;
import com.isme.gamble.db.DataBaseRecord;
import com.isme.gamble.entity.Banker;
import com.isme.gamble.entity.Card;
import com.isme.gamble.entity.Gambler;

import android.content.Context;
import android.util.Log;

public class Start {

	private static final String SQL_INSERT_DATA = "INSERT INTO t_cards(user_name, point_a, color_a, point_b, color_b, point_c, color_c, max, status, win) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 赌徒数量
	private int number;

	// 至少一个庄家
	private Banker banker;

	// 由主界面决定几个玩家
	private List<Gambler> gamblers;

	// 一副扑克牌
	private Card[] cards;

	// 写入数据库
	private DataBaseRecord db;

	public Start(int number, Context context) {
		this.number = number;

		db = new DataBaseRecord(context);
	}

	/**
	 * 游戏开始
	 */
	public void play() {
		initialiePosition();
		initializeCard();
		shuffle();
		
		compareCard();
	}

	/**
	 * 人员就位， 开始play
	 */
	private void initialiePosition() {
		banker = new Banker("isme");
		gamblers = new ArrayList<Gambler>();
		for (int i = 0; i < number; i++) {
			Gambler gambler = new Gambler("tom" + i);
			gamblers.add(gambler);
		}

		// Log.i("palyer", banker.toString());
		// Log.i("palyer", gamblers.toString());
	}

	/**
	 * 一副新牌
	 */
	private void initializeCard() {
		cards = new Card[52];
		int position = 1;

		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 4; j++) {
				cards[position - 1] = new Card();
				cards[position - 1].setPoint(i);
				cards[position - 1].setColor(j);
				// Log.i("new_card", cards[position - 1].toString());

				position++;
			}
		}
	}

	/**
	 * 模拟发牌
	 */
	private void shuffle() {

		banker.setCards(deal());
		banker.sortCard();
		
//		writerDB();
		
		for (int i = 0; i < number; i++) {
			gamblers.get(i).setCards(deal());
			gamblers.get(i).sortCard();

//			writerDB(i);

			Log.i("shuffle", gamblers.get(i).toString());
		}

		Log.i("shuffle", banker.toString());

	}

	
	/**
	 * 牌已经发完，然后就是比较大小了
	 */
	private void compareCard() {
		// TODO Auto-generated method stub
		//首先所有玩家比较 - 然后和庄家比较
		int [] gam = new int[number];
		int [] maxP = new int[number];
		for(int i=0; i<number; i++)
		{
			gam[i] = gamblers.get(i).getStatus();
			maxP[i] = gamblers.get(i).getMax();
		}
		
		int gamWin =  getMaxIndex(gam, maxP);
		
		if(gam[gamWin] > banker.getStatus())
		{
			gamblers.get(gamWin).setWin(1);
//			writerDB(gamWin);
			
			AnalyzeApplication.analyze[gamblers.get(gamWin).getStatus()]++;
		}
		else if(gam[gamWin] == banker.getStatus())
		{
			if(gamblers.get(gamWin).getCards()[0].getColor() > banker.getCards()[0].getColor())
			{
				gamblers.get(gamWin).setWin(1);
//				writerDB(gamWin);
				
				AnalyzeApplication.analyze[gamblers.get(gamWin).getStatus()]++;
			}
			else {
				banker.setWin(1);
//				writerDB();
				
				AnalyzeApplication.analyze[banker.getStatus()]++;
			}
		}
		else {
			banker.setWin(1);
//			writerDB();
			
			AnalyzeApplication.analyze[banker.getStatus()]++;
		}
//		db.executeSQL(SQL_INSERT_DATA, "", "", "", "", "", "", "", "", "", "");
	}
	
	
	/**
	 * 一次性给一个人发玩牌 - 与现实有区别
	 * 
	 * @return Card[]
	 */
	private Card[] deal() {
		// 随机发牌 - 不打乱牌了 都一样
		Random rand = new Random(System.currentTimeMillis());

		Card[] c = new Card[3];

		// 模拟发三次牌
		for (int i = 0; i < 3; i++) {
			int p = Math.abs(rand.nextInt() % 52);

//			Log.i("rand", String.valueOf(p));

			while (cards[p].getPoint() == -1) {
				p = Math.abs(rand.nextInt() % 52);
//				Log.i("rand", String.valueOf(p));
			}

			c[i] = new Card();
			c[i].setPoint(cards[p].getPoint());
			c[i].setColor(cards[p].getColor());

			// 发玩牌之后，将这张牌 点置为 0
			cards[p].setPoint(-1);
		}

		return c;
	}

	/**
	 * 冒泡排序 - 其实不用  只要查找最大的就行了
	 * @param sort
	 * @return
	 */
	@Deprecated
	private int[] bubbleSort(int[] sort)
	{
		int len = sort.length;
		int temp = 0;
		for(int i=0; i<len; i++)
		{
			for(int j=i+1; j<len-1; j++)
			{
				if(sort[i] < sort[j])
				{
					temp = sort[i];
					sort[i] = sort[j];
					sort[j] = temp;
				}
			}
		}
		return sort;
	}
	
	/**
	 * 获取最大的    - 必须取最大的值  因为有     A 是14点   最大就是14
	 * @param arr
	 * @return  int 最大的玩家
	 */
	private int getMaxIndex(int[] arr, int[] maxP){
		
		int max = 0;
		int t = arr[0];
		for(int i=1; i<arr.length; i++)
		{
			// 首先比较状态   豹子  同花顺   同花。。。
			if(arr[i] > t)
			{
				max = i;
				t = arr[i];
			}
			//状态相同
			else if(arr[i] == t){
				
				//比较大小
				if(maxP[i] > maxP[max])
				{
					max = i;
					t = arr[i];
				}
				//相同的  最大的点数相同    - 接下来应该比较  第二大的牌
				else if(maxP[i] == maxP[max])
				{
					//不比了
				}
				
				//花色不做比较
//				if(gamblers.get(i).getCards()[0].getColor() > gamblers.get(max).getCards()[0].getColor())
//				{
//					max = i;
//					t = arr[i];
//				}
				
			}
		}
		
		return max;
	}
	
	/**
	 * 写入数据库  - 重载
	 * @param i
	 */
	private void writerDB(final int i)
	{
		new Thread(){
			public void run() {
				db.executeSQL(SQL_INSERT_DATA, gamblers.get(i).getName(), gamblers
						.get(i).getCards()[0].getPoint(), gamblers.get(i)
						.getCards()[0].getColor(), gamblers.get(i).getCards()[1]
						.getPoint(), gamblers.get(i).getCards()[1].getColor(),
						gamblers.get(i).getCards()[2].getPoint(), gamblers.get(i)
						.getCards()[2].getColor(), gamblers.get(i).getMax(), gamblers.get(i).getStatus(), gamblers.get(i).getWin());
			};
		}.start();
		
	}
	
	
	/**
	 * 写入数据库  -  重载
	 * @param i
	 */
	private void writerDB()
	{
		new Thread(){
			public void run() {
				db.executeSQL(SQL_INSERT_DATA, banker.getName(),
						banker.getCards()[0].getPoint(),
						banker.getCards()[0].getColor(),
						banker.getCards()[1].getPoint(),
						banker.getCards()[1].getColor(),
						banker.getCards()[2].getPoint(),
						banker.getCards()[2].getColor(), banker.getMax(), banker.getStatus(), banker.getWin());
			};
		}.start();
		
	}
	
	
	
	
}
