package com.isme.gamble.entity;


/**
 * 玩家 - 基类
 * 
 * @author and
 * 
 */
public abstract class Person {

	public static final int BAOZI = 5;
	public static final int TONGHUASHUN = 4;
	public static final int TONGHUA = 3;
	public static final int SHUNZI = 2;
	public static final int DUIZI = 1;
	public static final int ZAPAI = 0;
	
	/**
	 * 状态     5-豹子    4-同花顺   3-同花    2-顺子    1-对子    0-杂牌
	 */
	private int status;
	
	private int max;
	
	private int win = 0;
	
	//3张牌
	private Card[] cards = new Card[3];
	
	//玩家名称
	private String name;

	public Person(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getMax() {
		return max;
	}


	public void setMax(int max) {
		this.max = max;
	}
	
	public int getWin() {
		return win;
	}


	public void setWin(int win) {
		this.win = win;
	}
	
	/**
	 * 给自己的牌 - 排序 - 花色不管！！
	 */
	public void sortCard() {

		Card temp = new Card();

		// 有相同的牌的情况 - 在这里就处理 牌的情况 ， 豹子 。。。 好像太多了

		// 第一张比第二张大
		if (cards[0].getPoint() > cards[1].getPoint()) {
			// 第一张比第三张大
			if (cards[0].getPoint() > cards[2].getPoint()) {
				
				//第二张  大于 第三张
				if(cards[1].getPoint() > cards[2].getPoint())
				{
				}
				else
				{
					temp = cards[1];
					cards[1] = cards[2];
					cards[2] = temp;
				}
				
			}
			// 第三张比第一张还大 - 替换 3张牌的位置都交换了
			else {
				temp = cards[0];
				cards[0] = cards[2];
				cards[2] = cards[1];
				cards[1] = temp;
			}
		}
		// 第一张比第二章小
		else {
			// 第三张比第二章还大 - 只要第一张和第三张交换
			if (cards[2].getPoint() > cards[1].getPoint()) {
				temp = cards[0];
				cards[0] = cards[2];
				cards[2] = temp;
			}
			// 第三张比第二章小
			else {
				// 第三张比第一大 都交换
				if (cards[2].getPoint() > cards[0].getPoint()) {
					temp = cards[0];
					cards[0] = cards[1];
					cards[1] = cards[2];
					cards[2] = temp;
				}
				// 第三张比第一张小 交换前2张
				else {
					temp = cards[0];
					cards[0] = cards[1];
					cards[1] = temp;
				}
			}
		}
		
		calculateCard();
	}

	
	private void calculateCard()
	{
		max = cards[0].getPoint();
		if(cards[0].getPoint() == cards[1].getPoint() && cards[1].getPoint() == cards[2].getPoint())
		{
			status = 5;
		}
		//花色相同
		else if(cards[0].getColor() == cards[1].getColor() && cards[0].getColor() == cards[2].getColor())
		{
			//有序
			if((cards[0].getPoint() == (cards[1].getPoint()+1)) && (cards[1].getPoint() == (cards[2].getPoint() +1)))
			{
				status = 4;
			}
			if(cards[0].getPoint()==13 && cards[1].getPoint()==12 && cards[2].getPoint() == 1)
			{
				status = 4;
				max = 14;
			}
			else {
				//剩下的就是无序的
				status = 3;
			}
		}
		//顺子
		else if((cards[0].getPoint() == (cards[1].getPoint()+1)) && (cards[1].getPoint() == (cards[2].getPoint() +1)) )
		{
			status = 2;
		}
		else if(cards[0].getPoint()==13 && cards[1].getPoint()==12 && cards[2].getPoint() == 1)
		{
			status = 2;
			max = 14;
		}
		//对子
		else if (cards[0].getPoint() == cards[1].getPoint())
		{
			status = 1;
		}
		else if(cards[1].getPoint() == cards[2].getPoint())
		{
			status = 1;
			max = cards[1].getPoint();
		}
		else {
			status = 0;
		}
		
		//设置最大的值  - 考虑 A
		if(cards[0].getPoint() == 1 || cards[1].getPoint() ==1 || cards[2].getPoint() == 1)
		{
			max = 14;
			//最大的不是 3  2  1   顺子还有同花顺
			if(status == 4 || status == 2)
			{
				//  A 2 3的情况  最小
				if(cards[0].getPoint() == 3 && cards[1].getPoint() == 2)
				{
					//
				}
				else
				{
					max = 14;
				}
			}
			
		}
		
	}
	
	
	@Override
	public String toString() {
		return name + ":[1"+ cards[0] +"2"+ cards[1] +"3"+ cards[2] + "max:"+max +"-status:"+ status +"]";
	}

}
