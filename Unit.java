package paper1_GA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * 种群个体实体类
 *
 */
public class Unit implements Comparator<Unit>{
	/**
     * 试卷id
    */
	private int id;
	/**
     * 试卷适应度
    */
	private double adaptationDegree;
	/**
     * 试卷题目
    */
	private List<Problem> problemList;
	/**
     * 试卷知识点分布
    */
	private double kpCoverage;
	/**
     * 题目对应题型的数量
    */
	private int problemCount;
	/**
     * 试卷总分
    */
	private int sumScore;
	/**
     * 试卷的难度系数
    */
	private double difficuty;
	
	
    
	
	public Unit(){
		id=0;
		adaptationDegree=0.0;
		kpCoverage=0.0;
		problemList=new ArrayList<Problem>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getAdaptationDegree() {
		return adaptationDegree;
	}
	
	public void setAdaptationDegree(double adaptationDegree) {
		this.adaptationDegree = adaptationDegree;
	}
	
	public List<Problem> getProblemList() {
		return problemList;
	}
	
	public void setProblemList(List<Problem> problemList) {
		this.problemList = problemList;
	}
	
	public double getKpCoverage() {
		return kpCoverage;
	}
	
	public void setKpCoverage(double kpCoverage) {
		this.kpCoverage = kpCoverage;
	}
	
	public int getProblemCount(List<Problem> problemList) {
		return problemList.size();
	}
	
	public int getProblemCount() {
		return problemCount;
	}
	
	public void setProblemCount(int problkemCount) {
		this.problemCount = problkemCount;
	}
	
	public int getSumScore(List<Problem> problemList) {
		int sum=0;
		for(int i=0;i<problemList.size();i++){
			sum+=problemList.get(i).getScore();
		}
		return sum;
	}
	
	public int getSumScore() {
	   return sumScore;
	}
	
	public void setSumScore(int sumScore) {
		this.sumScore += sumScore;
	}
	
	public double getDifficuty(List<Problem> problemList) {
		double diff=0.0;
		for(int i=0;i<problemList.size();i++){
			diff+=problemList.get(i).getDifficulty()*problemList.get(i).getScore();
		}
		return diff/sumScore;
	}

	public double getDifficuty() {
		return difficuty;
	}
	
	public void setDifficuty(List<Problem> problemList) {
		double diff=0.0;
		for(int i=0;i<problemList.size();i++){
			diff+=problemList.get(i).getDifficulty()*problemList.get(i).getScore();
		}
		this.difficuty=diff/sumScore;
	}
	
	public int compare(Unit o1, Unit o2) {
		if(o1.getId()<o2.getId()){
			return 0;
		}
		return 1;
	}
	
}