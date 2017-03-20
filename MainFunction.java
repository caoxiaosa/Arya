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
	public static final double kpCoverage=0.6;
	/**
	 * 难度系数所占的权重
	 *
	 */
	public static final double difficulty=0.4;
	/**
	 * 产生初始种群
	 * @param count 个体数量
	 * @param paper 期望种群
	 * @param problemList 题库
	 * @return
	 */
	public static List<Unit> CSZQ(int count,Paper paper,List<Problem> problemList){
		List<Unit> unitList=new ArrayList<Unit>();
		//获取各种题型的题数
		int[] eachTypeCount=paper.getEachTypeCount();
		Unit unit;
		Random rand=new Random();
		//为各套试卷加入试题
		for(int i=0;i<count;i++){
			unit=new Unit();
			unit.setId(i+1);
			unit.setAdaptationDegree(0.0);
			//试卷总分约束
			while(paper.getTotalScore()!=unit.getSumScore()){
				unit.getProblemList().clear();
				List<Problem> li=new ArrayList<Problem>();
				//各题型题目数量的限制
				for(int j=0;j<eachTypeCount.length;j++){
					List<Problem> oneTypeProblem=new ArrayList<Problem>();;
					for(Problem p:problemList){
						//选择该题型的题目且试题需满足组卷要求
						if((p.getType()==(j+1))&&isContain(paper,p)){
							oneTypeProblem.add(p);
						}
					}
						Problem temp=new Problem();
						
						//从试题库中选择不重复的试题
						for(int k=0;k<eachTypeCount[j];k++){
							int index=rand.nextInt(oneTypeProblem.size()-k);
							unit.getProblemList().add(oneTypeProblem.get(index));
							li.add(oneTypeProblem.get(index));
							unit.setSumScore(oneTypeProblem.get(index).getScore());
							temp=oneTypeProblem.get(oneTypeProblem.size()-1-k);
							oneTypeProblem.set(oneTypeProblem.size()-1-k,oneTypeProblem.get(index));
							oneTypeProblem.set(index, temp);	
						}
						unit.setDifficuty(li);
						unit.setProblemCount(li.size());
				}
			}
			unitList.add(unit);
		}
		
		//计算试卷得知识点覆盖率和适应度值
		unitList=getKPCoverage(unitList,paper);
		unitList=getAdaptationDegree(unitList, paper, kpCoverage, difficulty);
		return unitList;
	}
	/**
	 * 计算知识点覆盖率
	 * @param unitList
	 * @param paper
	 * @return
	 */
	public static List<Unit> getKPCoverage(List<Unit> unitList,Paper paper){
		List<Integer> kp;
		for(int i=0;i<unitList.size();i++){
			kp=new ArrayList<Integer>();
			for(int j=0;j<unitList.get(i).getProblemList().size();j++){
				kp.addAll(unitList.get(i).getProblemList().get(j).getPoints());
			}
			//期望试卷知识点与整个个体知识点的交集
			Set<Integer> set=new HashSet<Integer>(kp);
			kp=new ArrayList<Integer>(set);
			kp.retainAll(paper.getPoints());
			unitList.get(i).setKpCoverage(kp.size()*1.00/paper.getPoints().size());
		}
		return unitList;
 	}
	/**
	 * 计算种群适应度
	 * @param unitList
	 * @param paper
	 * @param kpCoverage
	 * @param difficulty
	 * @return
	 */
	public static List<Unit> getAdaptationDegree(List<Unit> unitList,Paper paper,double kpCoverage,double difficulty){
		unitList=getKPCoverage(unitList, paper);
		for(int i=0;i<unitList.size();i++){
			unitList.get(i).setAdaptationDegree(1-(1-unitList.get(i).getKpCoverage())*kpCoverage-Math.abs(unitList.get(i).getDifficuty()-paper.getDifficulty())*difficulty);
		}
		return unitList;
	}
	/**
	 * 选择算子
	 * @param unitList
	 * @param count
	 * @return
	 */
	public static List<Unit> select(List<Unit> unitList,int count){
		List<Unit> selectUnitList=new ArrayList<Unit>();
		//种群个体适应度的和
		double allAdaptationDegree=0.0;
		for(int i=0;i<unitList.size();i++){
			allAdaptationDegree+=unitList.get(i).getAdaptationDegree();
		}
			Random rand=new Random();
			while(selectUnitList.size()!=count){
				//生成0~1之间的随机数
				double degree=0.0;
				double randDegree=(rand.nextInt(99)+1)*0.01*allAdaptationDegree;
				//选择符合要求的个体
				for(int j=0;j<unitList.size();j++){
					degree+=unitList.get(j).getAdaptationDegree();
					if(degree>=randDegree){
						//不重复选择
						if(!selectUnitList.contains(unitList.get(j))){
							selectUnitList.add(unitList.get(j));
						}
						break;
					}
				}
			}
			return selectUnitList;
		
	}
	/**
	 * 交叉算子
	 * @param unitList
	 * @param count
	 * @param papere
	 * @return
	 */
	public static List<Unit> cross(List<Unit> unitList,int count,Paper paper){
		List<Unit> crossedUnitList=new ArrayList<Unit>();
		Random rand=new Random();
		while(crossedUnitList.size()!=count){
			//随机选择两个个体
			int indexone=rand.nextInt(unitList.size());
			int indextow=rand.nextInt(unitList.size());
			Unit unitone;
			Unit unittow;
			if(indexone!=indextow){
				unitone=unitList.get(indexone);
				unittow=unitList.get(indextow);
				//随机选择一个交叉位置
				int crossPosition=rand.nextInt(51-2);
				//保证交叉题目的题型以及分数和相同
				double scoreone=unitone.getProblemList().get(crossPosition).getScore()+unitone.getProblemList().get(crossPosition+1).getScore();
				double scoretow=unittow.getProblemList().get(crossPosition).getScore()+unittow.getProblemList().get(crossPosition+1).getScore();
				if(scoreone==scoretow){
					//两个新个体
					Unit unitNewOne=new Unit();
					unitNewOne.getProblemList().addAll(unitone.getProblemList());
					Unit unitNewTow=new Unit();
					unitNewTow.getProblemList().addAll(unittow.getProblemList());
					//交换交叉位置后的两道试题
					for(int i=crossPosition;i<crossPosition+2;i++){
						unitNewOne.getProblemList().add(i,new Problem(unittow.getProblemList().get(i)));
						unitNewTow.getProblemList().add(i,new Problem(unitone.getProblemList().get(i)));
					}
					unitNewOne.setId(crossedUnitList.size());
					unitNewTow.setId(unitNewOne.getId()+1);
					//将两个个体添加到新种群集合中去
					if(crossedUnitList.size()<count){
						crossedUnitList.add(unitNewOne);
					}if(crossedUnitList.size()<count){
						crossedUnitList.add(unitNewTow);
					}
				}
			}
		//过滤重复个体
		//crossedUnitList = crossedUnitList.Distinct(new ProblemComparer()).ToList();
		}
		//计算知识点覆盖率及适应度值
		crossedUnitList=getKPCoverage(crossedUnitList, paper);
		crossedUnitList=getAdaptationDegree(crossedUnitList, paper, kpCoverage, difficulty);
		return crossedUnitList;
	}
	/**
	 * 变异算子
	 * @param unitList
	 * @param problemList
	 * @param paper
	 * @return
	 */
	public static List<Unit> change(List<Unit> unitList,List<Problem> problemList,Paper paper){
		Random rand=new Random();
		int index=0;
		for(int i=0;i<unitList.size();i++){
			//随机选择一道题
			index=rand.nextInt(unitList.get(i).getProblemList().size());
			Problem temp=unitList.get(i).getProblemList().get(index);
			Problem problem=new Problem();
			//得到这道题的知识点
			for(int j=0;j<temp.getPoints().size();j++){
				if(paper.getPoints().contains(temp.getPoints().get(j))){
					problem.getPoints().add(temp.getPoints().get(j));
				}
			}
			//从数据库中选择包含此题知识点的同种类型、同分数、不同题号的试题
			List<Problem> smallDB=new ArrayList<Problem>();
			for(Problem p:problemList){
				if(isContain(paper,problem)&&(p.getScore()==temp.getScore())&&(p.getType()==temp.getType())&&(p.getId()!=temp.getId())){
					smallDB.add(p);
				}
			}
			//从符合要求的试题中随机选择一个
			if(smallDB.size()>0){
				int indexo=rand.nextInt(smallDB.size());
				System.out.println(smallDB.size());
				unitList.get(i).getProblemList().set(index, smallDB.get(indexo));
			}
		}
		//计算知识点覆盖率和适应度
		unitList=getKPCoverage(unitList, paper);
		unitList=getAdaptationDegree(unitList, paper, kpCoverage, difficulty);
		return unitList;
	}
	/**
	 * 题目知识点是否符合试卷要求
	 * @param paper
	 * @param problem
	 * @return 
	 * @return
	 */
	private static boolean isContain(Paper paper,Problem problem){
		for(int i=0;i<problem.getPoints().size();i++){
			if(paper.getPoints().contains(problem.getPoints().get(i))){
				return true;
			}
		}
		return false;
	}
}