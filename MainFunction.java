package stark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainFunction {
	
	/**
	 * 知识点分布所占的权重
	 *
	 */
	public static final double kpCoverage=0.30;
	
	/**
	 * 难度系数所占的权重
	 *
	 */
	public static final double difficulty=0.25;
	
	/**
	 * 认知层次所占的权重
	 *
	 */
	public static final double cognitive=0.25;
	
	/**
	 * 区分度所占的权重
	 *
	 */
	public static final double distinguish=0.15;
	
	/**
	 * 曝光率所占的权重
	 *
	 */
	public static final double exposure=0.05;
	
	/**
	 * 产生初始种群
	 * @param count 个体数量
	 * @param paper 期望种群
	 * @param problemList 题库
	 * @return
	 */
	public static List<Population> Init_population(int population_size,Paper paper,List<Question> questionList){
		List<Population> populationList=new ArrayList<Population>();
		//获取各种题型的题数
		int[] eachTypeCount=paper.getEachTypeCount();
		//种群对象
		Population population=null;
		Random rand=new Random();
		
		//为各个试卷个体加入试题
		for(int i=0;i<population_size;i++){
			population=new Population();
			population.setId(i+1);
			population.setAdaptationDegree(0.0);
			
			//试卷个体不满足总分约束
			while(paper.getTotalScore()!=population.getSumScore()){
				population.getQuestionList().clear();
				
				//种群试题列表
				List<Question> questions=new ArrayList<Question>();
				//各题型试题数量
				for(int j=0;j<eachTypeCount.length;j++){
					
					//各个题型所对应的试题列表
					List<Question> problems=new ArrayList<Question>();
					for(Question que:questionList){
						//选择该题型的试题
						if((que.getType()==(j+1))&&isContain(paper,que)){
							problems.add(que);
						}
					}
					Question question=new Question();
					
					//从试题库中选择不重复的试题
					for(int k=0;k<eachTypeCount[j];k++){
						int index=rand.nextInt(problems.size()-k);
						population.getQuestionList().add(problems.get(index));
						questions.add(problems.get(index));
						population.setSumScore(problems.get(index).getScore());
						question=problems.get(problems.size()-1-k);
						problems.set(problems.size()-1-k,problems.get(index));
						problems.set(index, question);	
					}
					
					population.setDifficuty(questions);
					population.setDistinguish(questions);
					population.setExposure(questions);
					population.setCognitive(questions);
					population.setQuestionCount(questions.size());		
				}
			}
			populationList.add(population);
		}
		//计算试卷的知识点覆盖率和适应度值
		populationList=getKPCoverage(populationList,paper);
		populationList=getAdaptationDegree(populationList, paper, kpCoverage, difficulty,cognitive,distinguish,exposure);
		return populationList;
	}
	
	/**
	 * 计算知识点覆盖率
	 * @param populationList
	 * @param paper
	 * @return
	 */
	public static List<Population> getKPCoverage(List<Population> populationList,Paper paper){
		//知识点集合
		List<Integer> knowledge_points;
		for(int i=0;i<populationList.size();i++){
			knowledge_points=new ArrayList<Integer>();
			for(int j=0;j<populationList.get(i).getQuestionList().size();j++){
				knowledge_points.addAll(populationList.get(i).getQuestionList().get(j).getPoints());
			}
			//期望试卷知识点与整个个体知识点的交集
			Set<Integer> set=new HashSet<Integer>(knowledge_points);
			knowledge_points=new ArrayList<Integer>(set);
			//retainAll方法将保留与指定集合中相同的对象，而删除不相同的对象，它和removeAll方法正好相反
			knowledge_points.retainAll(paper.getPoints());
			populationList.get(i).setKpCoverage(knowledge_points.size()*1.00/paper.getPoints().size());
		}
		return populationList;
 	}
	
	/**
	 * 计算种群适应度
	 * @param populationList
	 * @param paper
	 * @param kpCoverage
	 * @param difficulty
	 * @return
	 */
	public static List<Population> getAdaptationDegree(List<Population> populationList,Paper paper,double kpCoverage,
		double difficulty,double cognitive,double distinguish,double exposure){
		double adaptionDegree,f1,f2,f3,f4,f5;
		populationList=getKPCoverage(populationList, paper);
		for(int i=0;i<populationList.size();i++){
			//计算适应度函数
			f1=populationList.get(i).getKpCoverage()*kpCoverage;//知识点覆盖率
			f2=Math.abs(populationList.get(i).getDifficuty()-paper.getDifficulty())*difficulty;//难度系数
			f3=Math.abs(populationList.get(i).getDistinguish()-paper.getDistinguish())*distinguish;//区分度
			f4=Math.abs(populationList.get(i).getExposure()-paper.getExposure())*exposure;//曝光率
			f5=Math.abs(populationList.get(i).getCognitive()-paper.getCognitive())*cognitive;//认知层次
			adaptionDegree=kpCoverage*Math.exp(-f1)+difficulty*Math.exp(-f2)+distinguish*Math.exp(-f3)+exposure*Math.exp(-f4)+cognitive*Math.exp(-f5);
			populationList.get(i).setAdaptationDegree(adaptionDegree);
		}
		return populationList;
	}
	
	/**
	 * 选择算子
	 * @param populationList
	 * @param count
	 * @return
	 */
	public static List<Population> select(List<Population> populationList,int count){
		Random rand=new Random();
		List<Population> selectPopulationList=new ArrayList<Population>();
		double k;
		double t;
	
		//试卷种群个体适应度值的和
		while(selectPopulationList.size()!=count){
			double maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
			double allAdaptationDegree=0.0;
			double avgAdaptationDegree=0.0;
			for(int i=0;i<populationList.size();i++){
				t=populationList.get(i).getAdaptationDegree();
				if(t>maxAdaptationDegree){
					maxAdaptationDegree=t;
				}
			}
			for(int i=0;i<populationList.size();i++){
				allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
			}
			avgAdaptationDegree=allAdaptationDegree/populationList.size();
			k=Math.floor(Math.exp(avgAdaptationDegree/maxAdaptationDegree));
			double max=-1;
			int max_position = 0;
			for(int i=0;i<k;i++){
				double[] arr=new double[(int)k];
				int rand_num=rand.nextInt(populationList.size());
				arr[i]=populationList.get(rand_num).getAdaptationDegree();
				if(max<arr[i]){
					max=arr[i];
					max_position = i;
				}
			}
			populationList.remove(max_position);
			if(selectPopulationList.contains(populationList.get(max_position))){
				continue;
			}
			selectPopulationList.add(populationList.get(max_position));
			
		}
		return selectPopulationList;
	}
	/**
	 * 交叉算子
	 * @param populationList
	 * @param count
	 * @param paper
	 * @return
	 */
	public static List<Population> crossover(List<Population> populationList,int count,Paper paper){
		List<Population> crossedPopulationList=new ArrayList<Population>();
		Random rand=new Random();
		
		//求解动态的交叉概率
		double allAdaptationDegree=0.0;
		double maxAdaptationDegree;
		double avgAdaptationDegree;
		for(int i=0;i<populationList.size();i++){
			allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
		}
		for(int i=0;i<populationList.size();i++){
			maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
			double t=populationList.get(i).getAdaptationDegree();
			if(t>maxAdaptationDegree){
				maxAdaptationDegree=t;
			}
		}
		avgAdaptationDegree=allAdaptationDegree/populationList.size();
		
		//求	pc 当随机数小于等于pc，则交叉 
		
		while(crossedPopulationList.size()!=count){
			
			//随机选择两个试卷个体,且两个试卷个体不同
			Population typeone;
			Population typetwo;
			int indexone=rand.nextInt(populationList.size());
			int indextwo=rand.nextInt(populationList.size());
			
			if(indexone!=indextwo){
				typeone=populationList.get(indexone);
				typetwo=populationList.get(indextwo);
				
				//1.在两个试卷个体中不存在相同试题的情况下(如何判断是否存在重复试题)
				//2.在两个试卷个体中存在相同试题的情况下
				
				
				//当满足交叉的情况下，随机选择一个交叉位置
				int crossPosition=rand.nextInt(populationList.size());
				/*if(){
					
				}*/
				//保证交叉题目的题型和知识点相同!!!!
				double scoreone=typeone.getQuestionList().get(crossPosition).getScore()+typeone.getQuestionList().get(crossPosition+1).getScore();
				double scoretwo=typetwo.getQuestionList().get(crossPosition).getScore()+typetwo.getQuestionList().get(crossPosition+1).getScore();
				
				
				if(scoreone==scoretwo){
					//两个新个体
					Population populationNewOne=new Population();
					populationNewOne.getQuestionList().addAll(typeone.getQuestionList());
					
					Population populationNewTwo=new Population();		
					populationNewTwo.getQuestionList().addAll(typetwo.getQuestionList());
					
					
					//交换交叉位置后的两道试题
					for(int i=crossPosition;i<crossPosition+2;i++){
						populationNewOne.getQuestionList().add(i,new Question(typetwo.getQuestionList().get(i)));
						populationNewTwo.getQuestionList().add(i,new Question(typeone.getQuestionList().get(i)));
					}
					
					populationNewOne.setId(crossedPopulationList.size());
					populationNewTwo.setId(populationNewOne.getId()+1);
					
					
					//将两个个体添加到新种群集合中去
					if(crossedPopulationList.size()<count){
						crossedPopulationList.add(populationNewOne);
					}
					if(crossedPopulationList.size()<count){
						crossedPopulationList.add(populationNewTwo);
					}
				}
			}
		}
		//计算知识点覆盖率及适应度值
		crossedPopulationList=getKPCoverage(crossedPopulationList, paper);
		crossedPopulationList=getAdaptationDegree(crossedPopulationList, paper, kpCoverage, difficulty, cognitive, distinguish, exposure);
		return crossedPopulationList;
	}
	/**
	 * 变异算子
	 * @param populationList
	 * @param questionList
	 * @param paper
	 * @return
	 */
	public static List<Population> mutate(List<Population> populationList,List<Question> questionList,Paper paper){
		int index=0;
		Random rand=new Random();
		
		//求解动态的变异概率
		double allAdaptationDegree=0.0;
		double maxAdaptationDegree;
		double avgAdaptationDegree;
		for(int i=0;i<populationList.size();i++){
			allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
		}
		for(int i=0;i<populationList.size();i++){
			double t;
			maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
			t=populationList.get(i).getAdaptationDegree();
			if(t>maxAdaptationDegree){
				maxAdaptationDegree=t;
			}
		}
		avgAdaptationDegree=allAdaptationDegree/populationList.size();
		
		//求	pm，当随机数小于等于pm，则变异
		
		for(int i=0;i<populationList.size();i++){	
			/*		
	 		if(){
	 			
			}
		 	*/
			//随机选择一道题
			index=rand.nextInt(populationList.get(i).getQuestionList().size());
			Question temp=populationList.get(i).getQuestionList().get(index);
			Question problem=new Question();
			
			//得到这道题的知识点
			for(int j=0;j<temp.getPoints().size();j++){
				if(paper.getPoints().contains(temp.getPoints().get(j))){
					problem.getPoints().add(temp.getPoints().get(j));
				}
			}
			
			//从数据库中选择包含此题知识点的同种类型、同分数、不同题号的试题
			List<Question> smallDB=new ArrayList<Question>();
			for(Question q:questionList){
				if(isContain(paper,problem)&&(q.getScore()==temp.getScore())&&(q.getType()==temp.getType())&&(q.getId()!=temp.getId())){
					smallDB.add(q);
				}
			}
			
			//从符合要求的试题中随机选择一个
			if(smallDB.size()>0){
				int indexo=rand.nextInt(smallDB.size());
				System.out.println(smallDB.size());
				populationList.get(i).getQuestionList().set(index, smallDB.get(indexo));
			}
		}
		
		//计算知识点覆盖率和适应度
		populationList=getKPCoverage(populationList, paper);
		populationList=getAdaptationDegree(populationList, paper, kpCoverage, difficulty, cognitive, distinguish, exposure);
		return populationList;
	}
	/**
	 * 最优保全策略
	 * @param populationList
	 * @param questionList
	 * @param paper
	 * @return
	 */
	/*public static Population evolvePopulation(Population p, Paper paper) {
	    Population newPopulation = new Population(pop.getLength());
	    int elitismOffset;
	    // 精英主义
	    if (elitism) {
	        elitismOffset = 1;
	        // 保留上一代最优秀个体
	        Paper fitness = pop.getFitness();
	        fitness.setId(0);
	        newPopulation.setPaper(0, fitness);
	    }
	    // 种群交叉操作，从当前的种群pop 来 创建下一代种群 newPopulation
	    for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
	        // 较优选择parent
	        Paper parent1 = select(pop);
	        Paper parent2 = select(pop);
	        while (parent2.getId() == parent1.getId()) {
	            parent2 = select(pop);
	        }
	        // 交叉
	        Paper child = crossover(parent1, parent2, rule);
	        child.setId(i);
	        newPopulation.setPaper(i, child);
	    }
	    // 种群变异操作
	    Paper tmpPaper;
	    for (int i = elitismOffset; i < newPopulation.getLength(); i++) {
	        tmpPaper = newPopulation.getPaper(i);
	        mutate(tmpPaper);
	        // 计算知识点覆盖率与适应度
	        tmpPaper.setKpCoverage(rule);
	        tmpPaper.setAdaptationDegree(rule, Global.KP_WEIGHT, Global.DIFFCULTY_WEIGHt);
	    }
	    return newPopulation;
	}*/
	/**
	 * 题目知识点是否符合试卷要求
	 * @param paper
	 * @param problem
	 * @return 
	 * @return
	 */
	private static boolean isContain(Paper paper,Question question){
		for(int i=0;i<question.getPoints().size();i++){
			if(paper.getPoints().contains(question.getPoints().get(i))){
				return true;
			}
		}
		return false;
	}
}