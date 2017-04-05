package stark;

import java.text.DecimalFormat;

public class Util {

	public static void formatPrintout(Population population) {
		DecimalFormat df=new DecimalFormat("#.###");
		System.out.println("第"+population.getId()+"套");
		System.out.println("题目数量                           知识点分布                           难度系数                           适应度                         区分度                          曝光率");
		System.out.println(population.getQuestionCount()
			+"          "+df.format(population.getKpCoverage())
			+"         "+df.format(population.getDifficuty())
			+"        "+df.format(population.getAdaptationDegree())
			+"      "+df.format(population.getDistinguish())
			+"      "+df.format(population.getExposure()));
	}
	
	public static void formatPrintPopulation(Population popu) {
		System.out.println("-----最优试卷个体相关信息--------");
		System.out.println("编号                              知识点分布                                难度系数                                  适应度                                区分度                                 曝光率");
		DecimalFormat df=new DecimalFormat("#.###");
		System.out.println(popu.getId()+"          "+df.format(popu.getKpCoverage())+"         "+df.format(popu.getDifficuty(popu.getQuestionList()))
				+"         "+df.format(popu.getAdaptationDegree())+"          "+df.format(popu.getDistinguish(popu.getQuestionList()))
				+"         "+df.format(popu.getExposure(popu.getQuestionList())));
	}
	
	public static void showSystemWelcome() {
		System.out.println("------------遗传算法组卷系统--------------");
		System.out.println("初始种群:");
		System.out.println("----------------迭代开始--------------------");
	}
	
}
