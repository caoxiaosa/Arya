package stark;

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
     * 试卷区分度
    */
	private double distinguish;
	
	/**
     * 试卷曝光率
    */
	private double exposure;
	
	/**
     * 试卷认知层次比例
    */
	private double cognitive;
	
	/**
     * 试卷所包含知识点
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
	
	public void setExposure(double exposure) {
		this.exposure = exposure;
	}
	
	public double getExposure() {
		return exposure;
	}
	
	public double getCognitive() {
		return cognitive;
	}
	
	public void setCognitive(double cognitive) {
		this.cognitive = cognitive;
	}
	
	public double getDistinguish() {
		return distinguish;
	}
	
	public void setDistinguish(double distinguish) {
		this.distinguish = distinguish;
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
