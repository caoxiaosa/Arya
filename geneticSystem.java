package stark;

import java.util.List;

/**
 * 
 * 测试类
 *
 */
public class geneticSystem {
	public static void main(String[] args) {
		int count=0;
		double expand=consts.EXPAND;
		int runCount=consts.RUNCOUNT;
		int pCount=consts.POPULATION_COUNT;
		Paper paper = MainFunction.initPaper();
		//产生初始群体
		List<Population> populationList=MainFunction.Init_population(pCount, paper);
		
		Util.showSystemWelcome();
		while(!MainFunction.isEnd(populationList,expand)){
			System.out.println("在第"+(++count)+"代未得到结果");
			if(count>=runCount){
				System.out.println("计算"+runCount+"代仍未得到结果，请重新设置条件");
				break;
			}
			populationList=MainFunction.select(populationList,10);
			populationList=MainFunction.crossover(populationList, 10, paper);
			populationList=MainFunction.mutate(populationList, paper);
			/*if(MainFunction.isEnd(populationList,expand)){
				break;
			}*/
		}
		if(count<runCount){
			System.out.println("在第"+(++count)+"代得到结果");
			System.out.println("期望试卷难度"+paper.getDifficulty());
			System.out.println("期望试卷区分度"+paper.getDistinguish());
			System.out.println("期望试卷认知层次"+paper.getCognitive());
			System.out.println("期望试卷曝光率"+paper.getExposure());
			MainFunction.showPopulation(populationList);
			MainFunction.showResult(populationList,expand);
		}
	}

	
	
}