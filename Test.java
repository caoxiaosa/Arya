package stark;

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
		//试卷试题
		Paper paper=new Paper();
		paper.setId(1);
		//设置试卷的总分
		paper.setTotalScore(100);
		paper.setDifficulty(0.72);
		paper.setDistinguish(0.32);
		paper.setCognitive(0.42);
		paper.setExposure(0.32);
		//设置试卷的知识点
		List<Integer> points=new ArrayList<Integer>();
		for(int i=1;i<=81;i+=2){
			points.add(i);
		}
		//设置试卷各个题型的数量
		int[] eachTypeCount=new int[]{20,15,15,6,2};
		paper.setPoints(points);
		paper.setEachTypeCount(eachTypeCount);
		int count=1;
		//设置适应度期望值
		double expand=0.90;
		//设置迭代次数
		int runCount=500;
		//产生初始群体
		List<Population> populationList=MainFunction.Init_population(100, paper, db.getProblemDB());
		System.out.println("------------遗传算法组卷系统--------------");
		System.out.println("初始种群:");
		showPopulation(populationList.get(count));
		System.out.println("----------------迭代开始--------------------");
		while(!isEnd(populationList,expand)){
			System.out.println("在第"+(++count)+"代未得到结果");
			if(count>=runCount){
				System.out.println("计算"+runCount+"代仍未得到结果，请重新设置条件");
				break;
			}
			populationList=MainFunction.select(populationList,10);
			populationList=MainFunction.cross(populationList, 20, paper);
			if(isEnd(populationList,expand)){
				break;
			}
			populationList=MainFunction.change(populationList, db.getProblemDB(), paper);
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
		Collections.sort(populationList,new Population());
		for(Population population:populationList){
			if(population.getAdaptationDegree()>=expand){
				System.out.println("第"+population.getId()+"套");
				System.out.println("题目数量         知识点分布                      难度系数                                 适应度                                                         区分度                                  曝光率");
				System.out.println(population.getProblemCount()+"          "+population.getKpCoverage()+"         "+population.getDifficuty()+"        "+population.getAdaptationDegree()
					+"      "+population.getDistinguish()+"             "+population.getExposure()+"             "+population.getCognitive());
			}
		}
	}
	/**
	 * 显示种群个体的题目编号
	 * 
	 */
	public static void showPopulation(Population popu){
		System.out.println("编号                                  知识点分布                                 难度系数");
		System.out.println(popu.getId()+"          "+popu.getKpCoverage()+"          "+popu.getDifficuty(popu.getProblemList()));
		for(int i=0;i<popu.getProblemList().size();i++){
			System.out.print(popu.getProblemList().get(i).getId()+"  ");
		}
		System.out.println();
	}
}