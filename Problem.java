package paper1_GA;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	/**
     * 试题题目的id
    */
    private int id;
    /**
     * 题目类型： 1-选择  2-填空 3-判断 4-读程序 5-写程序
    */
    private int type;
    /**
     * 难度系数 0.0-1.0之间
    */
    private double difficulty;
    /**
     * 试题的分数
    */
    private int score;
    /**
     * 知识点
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
