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
	public static final double kpCoverage=0.35;
	
	/**
	 * 难度系数所占的权重
	 *
	 */
	public static final double difficulty=0.25;
	
	/**
	 * 认知层次所占的权重
	 *
	 */
	public static final double cognitive=0.15;
	
	/**
	 * 区分度所占的权重
	 *
	 */
	public static final double distinguish=0.15;
	
	/**
	 * 曝光率所占的权重
	 *
	 */
	public static final double exposure=0.1;
	
	/**
	 * 产生初始种群
	 * @param count 个体数量
	 * @param paper 期望种群
	 * @param problemList 题库
	 * @return
	 */
	public static List<Population> Init_population(int count,Paper paper,List<Question> problemList){
		List<Population> populationList=new ArrayList<Population>();
		
		//获取各种题型的题数
		int[] eachTypeCount=paper.getEachTypeCount();
		Question problem=null;
		Population population=null;
		Random rand=new Random();
		
		//为各套试卷加入试题
		for(int i=0;i<count;i++){
			population=new Population();
			population.setId(i+1);
			population.setAdaptationDegree(0.0);
			
			//试卷总分约束
			while(paper.getTotalScore()!=population.getSumScore()){
				population.getProblemList().clear();
				List<Question> li=new ArrayList<Question>();
				
				//各题型题目数量的限制
				for(int j=0;j<eachTypeCount.length;j++){
					List<Question> oneTypeProblem=new ArrayList<Question>();
					for(Question p:problemList){
						//选择该题型的题目且试题需满足组卷要求
						if((p.getType()==(j+1))&&isContain(paper,p)){
							oneTypeProblem.add(p);
						}
					}
						Question temp=new Question();
						
						//从试题库中选择不重复的试题
						for(int k=0;k<eachTypeCount[j];k++){
							int index=rand.nextInt(oneTypeProblem.size()-k);
							population.getProblemList().add(oneTypeProblem.get(index));
							li.add(oneTypeProblem.get(index));
							population.setSumScore(oneTypeProblem.get(index).getScore());
							temp=oneTypeProblem.get(oneTypeProblem.size()-1-k);
							oneTypeProblem.set(oneTypeProblem.size()-1-k,oneTypeProblem.get(index));
							oneTypeProblem.set(index, temp);	
						}
						
						population.setDifficuty(li);
						population.setDistinguish(li);
						population.setExposure(li);
						population.setCognitive(li);
						population.setProblemCount(li.size());
						
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
		List<Integer> kp;
		for(int i=0;i<populationList.size();i++){
			kp=new ArrayList<Integer>();
			for(int j=0;j<populationList.get(i).getProblemList().size();j++){
				kp.addAll(populationList.get(i).getProblemList().get(j).getPoints());
			}
			//期望试卷知识点与整个个体知识点的交集
			Set<Integer> set=new HashSet<Integer>(kp);
			kp=new ArrayList<Integer>(set);
			kp.retainAll(paper.getPoints());
			populationList.get(i).setKpCoverage(kp.size()*1.00/paper.getPoints().size());
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
	//此处需要经过进一步的修改    ！！！！！
	public static List<Population> getAdaptationDegree(List<Population> populationList,Paper paper,double kpCoverage,double difficulty,double cognitive,double distinguish,double exposure){
		populationList=getKPCoverage(populationList, paper);
		for(int i=0;i<populationList.size();i++){
			populationList.get(i).setAdaptationDegree(1-(1-populationList.get(i).getKpCoverage())*kpCoverage-
					Math.abs(populationList.get(i).getDifficuty()-paper.getDifficulty())*difficulty-
				    Math.abs(populationList.get(i).getDistinguish()-paper.getDistinguish())*distinguish-
				    Math.abs(populationList.get(i).getExposure()-paper.getExposure())*exposure
					-Math.abs(populationList.get(i).getCognitive()-paper.getCognitive())*cognitive
					);
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
		List<Population> selectPopulationList=new ArrayList<Population>();
		//种群个体适应度的和
		double allAdaptationDegree=0.0;
		for(int i=0;i<populationList.size();i++){
			allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
		}
			Random rand=new Random();
			while(selectPopulationList.size()!=count){
				//生成0~1之间的随机数
				double degree=0.0;
				double randDegree=(rand.nextInt(99)+1)*0.01*allAdaptationDegree;
				//选择符合要求的个体
				for(int j=0;j<populationList.size();j++){
					degree+=populationList.get(j).getAdaptationDegree();
					if(degree>=randDegree){
						//不重复选择
						if(!selectPopulationList.contains(populationList.get(j))){
							selectPopulationList.add(populationList.get(j));
						}
						break;
					}
				}
			}
			return selectPopulationList;
		
	}
	/**
	 * 交叉算子
	 * @param populationList
	 * @param count
	 * @param papere
	 * @return
	 */
	public static List<Population> cross(List<Population> populationList,int count,Paper paper){
		List<Population> crossedPopulationList=new ArrayList<Population>();
		Random rand=new Random();
		while(crossedPopulationList.size()!=count){
			//随机选择两个个体
			int indexone=rand.nextInt(populationList.size());
			int indextow=rand.nextInt(populationList.size());
			Population unitone;
			Population unittow;
			if(indexone!=indextow){
				unitone=populationList.get(indexone);
				unittow=populationList.get(indextow);
				//随机选择一个交叉位置
				int crossPosition=rand.nextInt(51-2);
				//保证交叉题目的题型以及分数和相同
				double scoreone=unitone.getProblemList().get(crossPosition).getScore()+unitone.getProblemList().get(crossPosition+1).getScore();
				double scoretow=unittow.getProblemList().get(crossPosition).getScore()+unittow.getProblemList().get(crossPosition+1).getScore();
				if(scoreone==scoretow){
					//两个新个体
					Population unitNewOne=new Population();
					unitNewOne.getProblemList().addAll(unitone.getProblemList());
					Population unitNewTow=new Population();
					unitNewTow.getProblemList().addAll(unittow.getProblemList());
					//交换交叉位置后的两道试题
					for(int i=crossPosition;i<crossPosition+2;i++){
						unitNewOne.getProblemList().add(i,new Question(unittow.getProblemList().get(i)));
						unitNewTow.getProblemList().add(i,new Question(unitone.getProblemList().get(i)));
					}
					unitNewOne.setId(crossedPopulationList.size());
					unitNewTow.setId(unitNewOne.getId()+1);
					//将两个个体添加到新种群集合中去
					if(crossedPopulationList.size()<count){
						crossedPopulationList.add(unitNewOne);
					}if(crossedPopulationList.size()<count){
						crossedPopulationList.add(unitNewTow);
					}
				}
			}
		//过滤重复个体
		//crossedUnitList = crossedUnitList.Distinct(new ProblemComparer()).ToList();
		}
		//计算知识点覆盖率及适应度值
		crossedPopulationList=getKPCoverage(crossedPopulationList, paper);
		crossedPopulationList=getAdaptationDegree(crossedPopulationList, paper, kpCoverage, difficulty, cognitive, distinguish, exposure);
		return crossedPopulationList;
	}
	/**
	 * 变异算子
	 * @param populationList
	 * @param problemList
	 * @param paper
	 * @return
	 */
	public static List<Population> change(List<Population> populationList,List<Question> problemList,Paper paper){
		Random rand=new Random();
		int index=0;
		for(int i=0;i<populationList.size();i++){
			//随机选择一道题
			index=rand.nextInt(populationList.get(i).getProblemList().size());
			Question temp=populationList.get(i).getProblemList().get(index);
			Question problem=new Question();
			//得到这道题的知识点
			for(int j=0;j<temp.getPoints().size();j++){
				if(paper.getPoints().contains(temp.getPoints().get(j))){
					problem.getPoints().add(temp.getPoints().get(j));
				}
			}
			//从数据库中选择包含此题知识点的同种类型、同分数、不同题号的试题
			List<Question> smallDB=new ArrayList<Question>();
			for(Question p:problemList){
				if(isContain(paper,problem)&&(p.getScore()==temp.getScore())&&(p.getType()==temp.getType())&&(p.getId()!=temp.getId())){
					smallDB.add(p);
				}
			}
			//从符合要求的试题中随机选择一个
			if(smallDB.size()>0){
				int indexo=rand.nextInt(smallDB.size());
				System.out.println(smallDB.size());
				populationList.get(i).getProblemList().set(index, smallDB.get(indexo));
			}
		}
		//计算知识点覆盖率和适应度
		populationList=getKPCoverage(populationList, paper);
		populationList=getAdaptationDegree(populationList, paper, kpCoverage, difficulty, cognitive, distinguish, exposure);
		return populationList;
	}
	/**
	 * 题目知识点是否符合试卷要求
	 * @param paper
	 * @param problem
	 * @return 
	 * @return
	 */
	private static boolean isContain(Paper paper,Question problem){
		for(int i=0;i<problem.getPoints().size();i++){
			if(paper.getPoints().contains(problem.getPoints().get(i))){
				return true;
			}
		}
		return false;
	}
}