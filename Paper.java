package paper1_GA;

import java.util.List;
	/**
	 * �Ծ�ʵ��
	 *
	 */
public class Paper {
	/**
     * �Ծ�id
    */
	private int id;
	/**
     * �Ծ���Ѷ�ϵ��
    */
	private double difficulty;
	/**
     * �Ծ��ܷ�
    */
	private int totalScore;
	/**
     * �Ծ����֪ʶ��
    */
	private List<Integer> points;
	/**
     * �Ծ��и������͵���������
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
