package stark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainFunction {
	
	/**
	 * 知识点分布所占的权重
	 *
	 */
	public static final double kpCoverage=0.38;
	
	/**
	 * 难度系数所占的权重
	 *
	 */
	public static final double difficulty=0.27;
	
	/**
	 * 区分度所占的权重
	 *
	 */
	public static final double distinguish=0.23;
	
	/**
	 * 曝光率所占的权重
	 *
	 */
	public static final double exposure=0.12;
	
	/**
	 * 产生初始种群
	 * @param population_size   个体数量
	 * @param paper   期望种群
	 * @return populationList    population_size个种群
	 */
	public static List<Population> Init_population(int population_size,Paper paper){
		DB db=new DB();
		List<Question> questionList=db.getProblemDB();	
		List<Population> populationList=new ArrayList<Population>();
		int[] eachTypeCount=paper.getEachTypeCount();
		int[][] eachTypePoints=paper.getEachTypePoints();
		Random rand=new Random();
		for(int i=0;i<population_size;i++){
			Population population=new Population();
			population.setId(i+1);
			population.setAdaptationDegree(0.0);
			int num=0;
			while(paper.getTotalScore()!=population.getSumScore()){
				num+=1;
				population.getQuestionList().clear();
				List<Question> questions=new ArrayList<Question>();
				for(int j=0;j<eachTypeCount.length;j++){
					List<Question> problems=new ArrayList<Question>();
					for(Question q:questionList){
						if((q.getType()==(j+1))&&isContain(paper,q)){
							problems.add(q);
						}
					}
					Question question=new Question();
					for(int k=0;k<eachTypeCount[j];k++){
						int [] type_points = eachTypePoints[j];
						int index=rand.nextInt(problems.size()-k);
						Question tmp_pro = problems.get(index);
						String pro_point = tmp_pro.getPoint()+"";
						boolean is_contain = true;
						for(int s: type_points){
					        if(Integer.toString(s)==pro_point)
					        	is_contain=true;
					        	break;
						}
						if(is_contain){
							population.getQuestionList().add(tmp_pro);
							questions.add(tmp_pro);
							population.setSumScore(tmp_pro.getScore());
							question=problems.get(problems.size()-1-k);
							problems.set(problems.size()-1-k,tmp_pro);
							problems.set(index, question);
						}
					}
					population.setDifficuty(questions);
					population.setDistinguish(questions);
					population.setExposure(questions);
					population.setQuestionCount(questions.size());		
				}
			}
			populationList.add(population);
		}
		populationList=getKPCoverage(populationList,paper);
		populationList=getAdaptationDegree(populationList, paper, kpCoverage, difficulty,distinguish,exposure);
		return populationList;
	}
	
	/**
	 * 计算知识点覆盖率
	 * @param populationList
	 * @param paper
	 * @return
	 */
	public static List<Population> getKPCoverage(List<Population> populationList,Paper paper){
		List<Integer> knowledge_points;
		int[] eachTypeCount=paper.getEachTypeCount();
		int[][] eachTypePoints=paper.getEachTypePoints();
		Set<Integer> eachType_points = new HashSet<Integer>();
		for(int i=0;i<populationList.size();i++){
			double avg=0.0;
			double sum=0.0;
			knowledge_points=new ArrayList<Integer>();
			//将每一个试卷个体中各个题型所对应的试题的知识点添加至knowledge_points集合中
			for(int j=0;j<eachTypeCount.length;j++){
				for(int k=0;k<populationList.get(i).getQuestionList().size();k++){
					if(populationList.get(i).getQuestionList().get(k).getType()==(j+1)){
						//将每一个试卷个体中所有试题的知识点添加至knowledge_points集合中
						knowledge_points.add(populationList.get(i).getQuestionList().get(k).getPoint());
					}
				}
				//去除试卷个体中重复的知识点
				Set<Integer> set=new HashSet<Integer>(knowledge_points);
				knowledge_points=new ArrayList<Integer>(set);
				int [] type_points = eachTypePoints[j];
				for(int s: type_points){
					eachType_points.add(s);
				}
				//retainAll方法将保留与指定集合中相同的对象，而删除不相同的对象，它和removeAll方法正好相反
				knowledge_points.retainAll(eachType_points);
				//试卷个体各个题型所包含的知识点数/各个题型期望包含的知识点数
				sum+=(knowledge_points.size()*1.00/eachType_points.size());
				}
				avg=sum/eachTypeCount.length;
				populationList.get(i).setKpCoverage(avg);
			}
		return populationList;
 	}
	
	/**
	 * 计算种群适应度
	 * @param populationList
	 * @param paper
	 * @param kpCoverage
	 * @param difficulty
	 * @param distinguish
	 * @param explosure
	 * @return populationList
	 */
	public static List<Population> getAdaptationDegree(List<Population> populationList,Paper paper,double kpCoverage,
		double difficulty,double distinguish,double exposure){
		double adaptionDegree,f1,f2,f3,f4;
		populationList=getKPCoverage(populationList, paper);
		for(int i=0;i<populationList.size();i++){
			//计算适应度函数
			f1=populationList.get(i).getKpCoverage()*kpCoverage;//知识点覆盖率
			f2=Math.abs(populationList.get(i).getDifficuty()-paper.getDifficulty())*difficulty;//难度系数
			f3=Math.abs(populationList.get(i).getDistinguish()-paper.getDistinguish())*distinguish;//区分度
			f4=Math.abs(populationList.get(i).getExposure()-paper.getExposure())*exposure;//曝光率
			adaptionDegree=kpCoverage*Math.exp(-f1)+difficulty*Math.exp(-f2)+distinguish*Math.exp(-f3)+exposure*Math.exp(-f4);
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
		double k,t;
		double r=2.1;
		int num=0;
		int max_position = 0;
		double maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
		while(selectPopulationList.size()!=count){
			double allAdaptationDegree=0.0;
			double avgAdaptationDegree=0.0;
			double max=-1;
			//计算最大适应度值
			for(int i=0;i<populationList.size();i++){
				t=populationList.get(i).getAdaptationDegree();
				if(maxAdaptationDegree<t){
					maxAdaptationDegree=t;
				}
			}
			for(int i=0;i<populationList.size();i++){
				allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
			}
			//计算平均适应度值
			avgAdaptationDegree=allAdaptationDegree/populationList.size();
			//计算k值
			k=Math.floor(r*Math.exp(avgAdaptationDegree/maxAdaptationDegree));
			
			for(int i=0;i<k;i++){
				int rand_num=rand.nextInt(populationList.size());
				double adp_degree=populationList.get(rand_num).getAdaptationDegree();
				if(max<adp_degree){
					max=adp_degree;
					max_position = rand_num;
				}
			}
			//不重复试卷个体复制
			if(!selectPopulationList.contains(populationList.get(max_position))){
				selectPopulationList.add(populationList.get(max_position));
			}
			populationList.remove(max_position);
			num +=1;
		}
		return selectPopulationList;
	}
	
	/**
	 * 交叉算子,所有个体都有平等交叉机会
	 * @param populationList
	 * @param count
	 * @param paper
	 * @return
	 */
	public static List<Population> crossover(List<Population> populationList,int count,Paper paper){
		List<Population> crossedPopulationList=new ArrayList<Population>();
		Random rand=new Random();
		double Pc1=0.9,Pc2=0.6;
		double Pc,t;
		double maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
		int[] eachTypeCount=paper.getEachTypeCount();
		List<String> cross_indexs=new ArrayList<String>();
		while(crossedPopulationList.size()!=count){
			double Fc,adapt1,adapt2;
			double randNum=rand.nextFloat();
			double allAdaptationDegree=0.0;
			double avgAdaptationDegree=0.0;
			int popu1=rand.nextInt(populationList.size());
			int popu2=rand.nextInt(populationList.size());
			if(cross_indexs.contains(popu1+""+popu2)||cross_indexs.contains(popu2+""+popu1)){
				continue;
			}
			if(popu1==popu2){
				continue;
			}
			cross_indexs.add(popu1+""+popu2);
			cross_indexs.add(popu2+""+popu1);
			for(int i=0;i<populationList.size();i++){
				t=populationList.get(i).getAdaptationDegree();
				if(maxAdaptationDegree<t){
					maxAdaptationDegree=t;
				}
			}
			for(int i=0;i<populationList.size();i++){
				allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
			}
			avgAdaptationDegree=allAdaptationDegree/populationList.size();
			Population paper_indiv1=populationList.get(popu1);
			Population paper_indiv2=populationList.get(popu2);
			adapt1=paper_indiv1.getAdaptationDegree();
			adapt2=paper_indiv2.getAdaptationDegree();
			if(adapt1>=adapt2){
				Fc=adapt1;
			}else{
				Fc=adapt1;
			}
			if(maxAdaptationDegree>avgAdaptationDegree||maxAdaptationDegree==avgAdaptationDegree){
				Pc=Pc1-(Pc1-Pc2)*(Fc-avgAdaptationDegree)/(maxAdaptationDegree-avgAdaptationDegree);
			}else{
				Pc=Pc1;
			}
			if(randNum<=Pc){
				List<Question> questionList1=paper_indiv1.getQuestionList();
				List<Question> questionList2=paper_indiv2.getQuestionList();
				List<Question> crossed_list1=paper_indiv1.getQuestionList();
				List<Question> crossed_list2=paper_indiv2.getQuestionList();
				Population crossed_paper1 = paper_indiv1;
				Population crossed_paper2 = paper_indiv2;
				crossed_paper1.setId(paper_indiv2.getId()+crossedPopulationList.size()+1);
				for(int j=0;j<eachTypeCount.length;j++){
					List<String> idList1=new ArrayList<String>();
					List<String> idList2=new ArrayList<String>();
					List<Question> type_List1=new ArrayList<Question>();
					List<Question> type_List2=new ArrayList<Question>();
					for(Question q:questionList1){
						if(q.getType()==(j+1)){
							idList1.add(q.getId()+"");
							type_List1.add(q);
						}
					}
					for(Question q:questionList2){
						if(q.getType()==(j+1)){
							idList2.add(q.getId()+"");
							type_List2.add(q);
						}
					}
					boolean is_repeat=false;
					for(String s:idList1){
						if(idList2.contains(s)){
							is_repeat=true;
						}
					}
					if(is_repeat){
						continue;
					}else{
						int position=rand.nextInt(eachTypeCount[j]);
						Question tmp_q1=type_List1.get(position);
						Question tmp_q2=type_List2.get(position);
						type_List1.remove(position);
						type_List2.remove(position);
						type_List1.add(tmp_q1);
						type_List2.add(tmp_q2);
						crossed_list1.addAll(type_List1);
						crossed_list2.addAll(type_List2);
					}
				}
				crossed_paper1.setQuestionList(crossed_list1);
				crossed_paper2.setQuestionList(crossed_list2);
				int crossed_add = crossedPopulationList.size()+1;
				if(crossedPopulationList.size()<count){
					crossed_paper1.setId(paper_indiv1.getId()+crossed_add);
					crossedPopulationList.add(crossed_paper1);
				}
				if(crossedPopulationList.size()<count){
					crossed_paper2.setId(paper_indiv2.getId()+crossed_add);
					crossedPopulationList.add(crossed_paper2);
				}
			}
		}
		//计算知识点覆盖率及适应度值
		crossedPopulationList=getKPCoverage(crossedPopulationList, paper);
		crossedPopulationList=getAdaptationDegree(crossedPopulationList, paper, kpCoverage, difficulty, distinguish, exposure);
		return crossedPopulationList;
	}
	
	/**
	 * 变异算子
	 * @param populationList
	 * @param questionList
	 * @param paper
	 * @return
	*/
	public static List<Population> mutate(List<Population> populationList,Paper paper){
		DB db=new DB();
		List<Question> questionList=new ArrayList<Question>();
		questionList=db.getProblemDB();
		int index=0;
		double Fm,t,Pm = 0;
		double Pm1=0.1,Pm2=0.0001;
		Random rand=new Random();
		for(int i=0;i<populationList.size();i++){	
			double randNum=rand.nextFloat();
			for(int k=0;k<populationList.size();k++){
				double maxAdaptationDegree=populationList.get(0).getAdaptationDegree();
				double allAdaptationDegree=0.0;
				double avgAdaptationDegree=0.0;
				for(int j=0;j<populationList.size();j++){
					t=populationList.get(k).getAdaptationDegree();
					if(maxAdaptationDegree<t){
						maxAdaptationDegree=t;
					}
				}
				for(int j=0;j<populationList.size();j++){
					allAdaptationDegree+=populationList.get(i).getAdaptationDegree();
				}
				avgAdaptationDegree=allAdaptationDegree/populationList.size();
				Fm=populationList.get(i).getAdaptationDegree();
				if(Fm>avgAdaptationDegree||Fm==avgAdaptationDegree){
					Pm=Pm1-(Pm1-Pm2)*(maxAdaptationDegree-Fm)/(maxAdaptationDegree-avgAdaptationDegree);
				}else{
					Pm=Pm1;
				}
			}
			
			if(randNum<=Pm){
				index=rand.nextInt(populationList.get(i).getQuestionList().size());
				Question tmp=populationList.get(i).getQuestionList().get(index);
				Question question=new Question();
				//得到这道题的知识点
				for(int j=0;j<tmp.getPoints().size();j++){
					if(paper.getPoints().contains(tmp.getPoints().get(j))){
						question.getPoints().add(tmp.getPoints().get(j));
					}
				}
				//从数据库中选择包含此题知识点的同种类型、同分数、不同题号的试题
				List<Question> questionDB=new ArrayList<Question>();
				for(Question que:questionList){
					if(isContain(paper,que)&&(que.getScore()==tmp.getScore())&&(que.getType()==tmp.getType())&&(que.getId()!=tmp.getId())){
						questionDB.add(que);
					}
				}
				//从符合要求的试题中随机选择一个
				if(questionDB.size()>0){
					int indexo=rand.nextInt(questionDB.size());
					System.out.println("i am mu:"+questionDB.size());
					populationList.get(i).getQuestionList().set(index, questionDB.get(indexo));
				}
			}
		
		}
		//计算知识点覆盖率和适应度
		populationList=getKPCoverage(populationList, paper);
		populationList=getAdaptationDegree(populationList, paper, kpCoverage, difficulty, distinguish, exposure);
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
	 * 题目知识点是否符合试卷参数要求
	 * @param paper
	 * @param problem
	 * @return 
	 * @return
	 */
	private static boolean isContain(Paper paper,Question question){
		int[][] eachTypePoints=paper.getEachTypePoints();
		int[] eachTypeCount=paper.getEachTypeCount();
		Set<Integer> eachType_points = new HashSet<Integer>();
		for(int i=0;i<eachTypeCount.length;i++){
			int [] type_points = eachTypePoints[i];
			for(int s: type_points){
				eachType_points.add(s);
			}
		}
		if(eachType_points.contains(question.getPoint())){
			return true;
		}else{
			return false;
		}
	}
	
	public static Paper initPaper() {
		List<Integer> points=new ArrayList<Integer>();
		//目标试卷
		Paper paper=new Paper();
		paper.setId(1);
		paper.setTotalScore(consts.SCORE);
		paper.setDifficulty(consts.DIFFICULTY);
		paper.setDistinguish(consts.DISTINGUISH);
		//认知层次
		paper.setCognitive(0.62);
		paper.setExposure(consts.EXPOSURE);
		//选择、填空、判断、读程序、写程序
		int[] eachTypeCount=new int[]{20,15,15,6,2};
		paper.setEachTypeCount(eachTypeCount);
		
		int[][] eachTypePoints=new int[][] {
			{1,2,3,4,5,7,8,9,11,10,12,24,17,25,19},
			{1,2,13,4,25,16,7,8,12,11,20,21,26},
			{3,4,5,6,7,8,9,17,12,23,1,2,26,22},
			{1,2,6,7,8,9,3,2,12,14,18},
			{1,2,4,8,9,7,18,19}
		};
		paper.setEachTypePoints(eachTypePoints);
		
		/*for(int i=1;i<=81;i+=2){
		points.add(i);
		}
		paper.setPoints(points);*/
		return paper;
	}
	
	/**
	 * 是否到达适应度值的期望值目标
	 * @param populationList   Population集合
	 * @param endcondition  期望适应度值
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
				Util.formatPrintout(population);
			}
		}
	}
	
	/**
	 * 显示试卷及个体的题目编号
	 * 
	 */
	public static void showPopulation(List<Population> populationList){
		int k = 0;
		double max=populationList.get(0).getAdaptationDegree();
		for(int i=0;i<populationList.size();i++){
			if(max<populationList.get(i).getAdaptationDegree()){
				max=populationList.get(i).getAdaptationDegree();
				k=i;
			}
		}
		Util.formatPrintPopulation(populationList.get(k));
		for(int i=0;i<populationList.get(k).getQuestionList().size();i++){
			System.out.print(populationList.get(k).getQuestionList().get(i).getId()+" ");
		}
		System.out.println("        ");
	}
	
}