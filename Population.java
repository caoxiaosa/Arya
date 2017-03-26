package stark;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * 试卷群体
 *
 */
public class Population implements Comparator<Population>{
	/**
     * 试卷个体id
    */
	private int id;
	/**
     * 试卷个体适应度值
    */
	private double adaptationDegree;
	/**
     * 试卷个体中试题
    */
	private List<Question> questionList;
	/**
     * 试卷个体知识点分布
    */
	private double kpCoverage;
	/**
     * 对应题型试题的数量
    */
	private int questionCount;
	/**
     * 试卷个体总分
    */
	private int sumScore;
	/**
     * 试卷个体的难度系数
    */
	private double difficuty;
	
	/**
     * 试卷个体的曝光率
    */
	private double exposure;
	
	/**
     * 试卷个体的区分度
    */
	private double distinguish;
	
	/**
     * 试卷个体的认知层次
    */
	private double cognitive;
	
	public Population(){
		id=0;
		adaptationDegree=0.0;
		kpCoverage=0.0;
		questionList=new ArrayList<Question>();
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
	
	public List<Question> getQuestionList() {
		return questionList;
	}
	
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	
	public double getKpCoverage() {
		return kpCoverage;
	}
	
	public void setKpCoverage(double kpCoverage) {
		this.kpCoverage = kpCoverage;
	}
	
	public int getQuestionCount(List<Question> questionList) {
		return questionList.size();
	}
	
	public int getQuestionCount() {
		return questionCount;
	}
	
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	
	public int getSumScore(List<Question> questionList) {
		int sum=0;
		for(int i=0;i<questionList.size();i++){
			sum+=questionList.get(i).getScore();
		}
		return sum;
	}
	
	public int getSumScore() {
	   return sumScore;
	}
	
	public void setSumScore(int sumScore) {
		this.sumScore += sumScore;
	}
	
	public double getDifficuty(List<Question> questionList) {
		double diff=0.0;
		for(int i=0;i<questionList.size();i++){
			diff+=questionList.get(i).getDifficulty()*questionList.get(i).getScore();
		}
		return diff/sumScore;
	}

	public double getDifficuty() {
		return difficuty;
	}
	
	public void setDifficuty(List<Question> questionList) {
		double diff=0.0;
		for(int i=0;i<questionList.size();i++){
			diff+=questionList.get(i).getDifficulty()*questionList.get(i).getScore();
		}
		this.difficuty=diff/sumScore;
	}
	
	
	public double getDistinguish(List<Question> questionList) {
		double dist=0.0;
		for(int i=0;i<questionList.size();i++){
			dist+=questionList.get(i).getDistinguish()*questionList.get(i).getScore();
		}
		return dist/sumScore;
	}

	public double getDistinguish() {
		return distinguish;
	}
	
	public void setDistinguish(List<Question> questionList) {
		double dist=0.0;
		for(int i=0;i<questionList.size();i++){
			dist+=questionList.get(i).getDistinguish()*questionList.get(i).getScore();
		}
		this.distinguish=dist/sumScore;
	}
	
	
	public double getExposure(List<Question> questionList) {
		double expo=0;
		for(int i=0;i<questionList.size();i++){
			expo+=questionList.get(i).getExposure();
		}
		return expo/questionList.size();
	}

	public double getExposure() {
		return exposure;
	}
	
	public void setExposure(List<Question> questionList) {
		double expo=0;
		for(int i=0;i<questionList.size();i++){
			expo+=questionList.get(i).getExposure();
		}
		this.exposure=expo/questionList.size();
	}
	
	
	public double getCognitive(List<Question> questionList) {
		double cogn=0.0;
		for(int i=0;i<questionList.size();i++){
			cogn+=questionList.get(i).getCognitive()*questionList.get(i).getScore();
		}
		return cogn/sumScore;
	}

	public double getCognitive() {
		return cognitive;
	}
	
	public void setCognitive(List<Question> questionList) {
		double cogn=0.0;
		for(int i=0;i<questionList.size();i++){
			cogn+=questionList.get(i).getCognitive()*questionList.get(i).getScore();
		}
		this.cognitive=cogn/sumScore;
	}
	
	
	public int compare(Population o1, Population o2) {
		if(o1.getId()<o2.getId()){
			return 0;
		}
		return 1;
	}


	
}