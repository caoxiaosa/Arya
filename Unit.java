package paper1_GA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * ��Ⱥ����ʵ����
 *
 */
public class Unit implements Comparator<Unit>{
	/**
     * �Ծ�id
    */
	private int id;
	/**
     * �Ծ���Ӧ��
    */
	private double adaptationDegree;
	/**
     * �Ծ���Ŀ
    */
	private List<Problem> problemList;
	/**
     * �Ծ�֪ʶ��ֲ�
    */
	private double kpCoverage;
	/**
     * ��Ŀ��Ӧ���͵�����
    */
	private int problemCount;
	/**
     * �Ծ��ܷ�
    */
	private int sumScore;
	/**
     * �Ծ���Ѷ�ϵ��
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