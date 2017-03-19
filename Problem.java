package paper1_GA;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	/**
     * ������Ŀ��id
    */
    private int id;
    /**
     * ��Ŀ���ͣ� 1-ѡ��  2-��� 3-�ж� 4-������ 5-д����
    */
    private int type;
    /**
     * �Ѷ�ϵ�� 0.0-1.0֮��
    */
    private double difficulty;
    /**
     * ����ķ���
    */
    private int score;
    /**
     * ֪ʶ��
    */
	private List<Integer> points;
	
	public Problem(){
	    id = 0;
	    type =0;
	    score = 0;
	    difficulty =0;
	    points = new ArrayList<Integer>();
	}
	
	public Problem(Problem p){
	    this.id = p.id;
	    this.type =p.type;
	    this.score = p.score;
	    this.difficulty =p.difficulty;
	    this.points = p.points;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score=score;
	}
	
	public double getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(double difficulty) {
		this.difficulty=difficulty;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type=type;
	}
	
	public List<Integer> getPoints() {
		return points;
	}
	
	public void setPoints(List<Integer> points) {
		this.points=points;
	}
}
