package paper1_GA;

import java.util.List;
	/**
	 * 试卷实体
	 *
	 */
public class Paper {
	/**
     * 试卷id
    */
	private int id;
	/**
     * 试卷的难度系数
    */
	private double difficulty;
	/**
     * 试卷总分
    */
	private int totalScore;
	/**
     * 试卷包含知识点
    */
	private List<Integer> points;
	/**
     * 试卷中各种题型的试题数量
    */
	private int[] eachTypeCount;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public List<Integer> getPoints() {
		return points;
	}
	
	public void setPoints(List<Integer> points) {
		this.points = points;
	}
	
	public int[] getEachTypeCount() {
		return eachTypeCount;
	}
	
	public void setEachTypeCount(int[] eachTypeCount) {
		this.eachTypeCount = eachTypeCount;
	}
	
}
