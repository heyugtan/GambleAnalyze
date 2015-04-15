package com.isme.gamble.entity;

/**
 * 牌
 * @author and
 *
 */
public class Card {

	//点数
	private int point;
//	private int[] point = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	
	//花色
	private int color;
//	private int[] color = new int[]{1, 2, 3, 4};

	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Card [point=" + point + ", color=" + color + "]";
	}

}
