package stark;


import java.util.ArrayList;
import java.util.List;


//试题类
public class Question {	
	/**
     * 试题的id
    */
    private int id;
    /**
     * 试题类型： 1-选择    2-填空    3-判断    4-读程序    5-写程序
    */
    private int type;
    /**
     * 试题难度系数 0.0-1.0之间
    */
    private double difficulty;
    /**
     * 试题的分值
    */
    private int score;
    /**
     * 试题的区分度
    */
    private double distinguish;
    /**
     * 试题的曝光次数
    */
    private int exposure;
    /**
     * 试题的认知层次
    */
    private double cognitive;
    /**
     * 试题所包含的知识点
    */
	private List<Integer> points;
	
	public Question(){
	    id = 0;
	    type =0;
	    score = 0;
	    difficulty =0.0;
	    exposure=0;
	    cognitive=0.0;
	    distinguish=0.0;
	    points = new ArrayList<Integer>();
	}
	
	public Question(Question p){
	    this.id = p.id;
	    this.type =p.type;
	    this.score = p.score;
	    this.difficulty =p.difficulty;
	    this.cognitive=p.cognitive;
	    this.distinguish=p.distinguish;
	    this.exposure=p.exposure;
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
	
	public double getCognitive() {
		return cognitive;
	}
	
	public void setCognitive(double cognitive) {
		this.cognitive=cognitive;
	}
	
	public double getDistinguish() {
		return distinguish;
	}
	
	public void setDistinguish(double distinguish) {
		this.distinguish=distinguish;
	}
	
	public int getExposure() {
		return exposure;
	}
	
	public void setExposure(int exposure) {
		this.exposure=exposure;
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
