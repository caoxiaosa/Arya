package stark;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * 测试类
 *
 */
public class Test {
	public static void main(String[] args) {
		//数据库模拟
		DB db=new DB();
		//目标试卷
		Paper paper=new Paper();
		paper.setId(1);
		paper.setTotalScore(consts.SCORE);
		paper.setDifficulty(consts.DIFFICULTY);
		paper.setDistinguish(consts.DISTINGUISH);
		//认知层次
		paper.setCognitive(0.62);
		paper.setExposure(consts.EXPOSURE);
		List<Integer> points=new ArrayList<Integer>();
		for(int i=1;i<=81;i+=2){
			points.add(i);
		}
		int[] eachTypeCount=new int[]{20,15,15,6,2};
		paper.setPoints(points);
		paper.setEachTypeCount(eachTypeCount);
		int count=1;
		double expand=consts.EXPAND;
		int runCount=consts.RUNCOUNT;
		//产生初始群体
		List<Population> populationList=MainFunction.Init_population(consts.POPULATION_COUNT, paper, db.getProblemDB());
		System.out.println(consts.Welcome);
		System.out.println("初始种群:");
		showPopulation(populationList.get(count));
		System.out.println(consts.Begin);
		while(!isEnd(populationList,expand)){
			System.out.println("在第"+(++count)+"代未得到结果");
			if(count>=runCount){
				System.out.println("计算"+runCount+"代仍未得到结果，请重新设置条件");
				break;
			}
			populationList=MainFunction.select(populationList,5);
			populationList=MainFunction.crossover(populationList, 20, paper);
			if(isEnd(populationList,expand)){
				break;
			}
			populationList=MainFunction.mutate(populationList, db.getProblemDB(), paper);
		}
		if(count<runCount){
			System.out.println("在第"+(++count)+"代得到结果");
			System.out.println("期望试卷难度"+paper.getDifficulty());
			System.out.println("期望试卷区分度"+paper.getDistinguish());
			System.out.println("期望试卷认知层次"+paper.getCognitive());
			System.out.println("期望试卷曝光率"+paper.getExposure());
			showResult(populationList,expand);
		}
	}
	/**
	 * 是否到达目标
	 * @param unitList
	 * @param endcondition
	 * @return
	 */
	public static boolean isEnd(List<Population> populationList,double endcondition){
		if(populationList.size()>0){
			for(int i=0;i<populationList.size();i++){
				if(populationList.get(i).getAdaptationDegree()>=endcondition){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 显示结果
	 * @param unitList
	 * @param expand
	 */
	public static void showResult(List<Population> populationList,double expand){
		DecimalFormat df=new DecimalFormat("#.###");
		Collections.sort(populationList,new Population());
		for(Population population:populationList){
			if(population.getAdaptationDegree()>=expand){
				System.out.println("第"+population.getId()+"套");
				System.out.println("题目数量                知识点分布                  难度系数                   适应度                    区分度                    曝光率                 认知层次");
				System.out.println(population.getQuestionCount()+"          "+df.format(population.getKpCoverage())+"         "+df.format(population.getDifficuty())
					+"        "+df.format(population.getAdaptationDegree())+"      "+df.format(population.getDistinguish())
					+"      "+df.format(population.getExposure())+"       "+df.format(population.getCognitive()));
			}
		}
	}
	/**
	 * 显示试卷及个体的题目编号
	 * 
	 */
	public static void showPopulation(Population popu){
		DecimalFormat df=new DecimalFormat("#.###");
		System.out.println("编号                       知识点分布                    难度系数                       适应度                        区分度                              曝光率                         认知层次");
		System.out.println(popu.getId()+"          "+df.format(popu.getKpCoverage())+"         "+df.format(popu.getDifficuty(popu.getQuestionList()))
				+"         "+df.format(popu.getAdaptationDegree())+"          "+df.format(popu.getDistinguish(popu.getQuestionList()))
				+"         "+df.format(popu.getExposure(popu.getQuestionList()))+"       "+df.format(popu.getCognitive(popu.getQuestionList())));
		for(int i=0;i<popu.getQuestionList().size();i++){
			System.out.print(popu.getQuestionList().get(i).getId()+" ");
		}
		System.out.println();
	}
}