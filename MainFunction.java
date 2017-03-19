package paper1_GA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainFunction {
	/**
	 * ֪ʶ��ֲ���ռ��Ȩ��
	 *
	 */
	public static final double kpCoverage=0.6;
	/**
	 * �Ѷ�ϵ����ռ��Ȩ��
	 *
	 */
	public static final double difficulty=0.4;
	/**
	 * ������ʼ��Ⱥ
	 * @param count ��������
	 * @param paper ������Ⱥ
	 * @param problemList ���
	 * @return
	 */
	public static List<Unit> CSZQ(int count,Paper paper,List<Problem> problemList){
		List<Unit> unitList=new ArrayList<Unit>();
		//��ȡ�������͵�����
		int[] eachTypeCount=paper.getEachTypeCount();
		Unit unit;
		Random rand=new Random();
		//Ϊ�����Ծ��������
		for(int i=0;i<count;i++){
			unit=new Unit();
			unit.setId(i+1);
			unit.setAdaptationDegree(0.0);
			//�Ծ��ܷ�Լ��
			while(paper.getTotalScore()!=unit.getSumScore()){
				unit.getProblemList().clear();
				List<Problem> li=new ArrayList<Problem>();
				//��������Ŀ����������
				for(int j=0;j<eachTypeCount.length;j++){
					List<Problem> oneTypeProblem=new ArrayList<Problem>();;
					for(Problem p:problemList){
						//ѡ������͵���Ŀ���������������Ҫ��
						if((p.getType()==(j+1))&&isContain(paper,p)){
							oneTypeProblem.add(p);
						}
					}
						Problem temp=new Problem();
						
						//���������ѡ���ظ�������
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
		
		//�����Ծ��֪ʶ�㸲���ʺ���Ӧ��ֵ
		unitList=getKPCoverage(unitList,paper);
		unitList=getAdaptationDegree(unitList, paper, kpCoverage, difficulty);
		return unitList;
	}
	/**
	 * ����֪ʶ�㸲����
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
			//�����Ծ�֪ʶ������������֪ʶ��Ľ���
			Set<Integer> set=new HashSet<Integer>(kp);
			kp=new ArrayList<Integer>(set);
			kp.retainAll(paper.getPoints());
			unitList.get(i).setKpCoverage(kp.size()*1.00/paper.getPoints().size());
		}
		return unitList;
 	}
	/**
	 * ������Ⱥ��Ӧ��
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
	 * ѡ������
	 * @param unitList
	 * @param count
	 * @return
	 */
	public static List<Unit> select(List<Unit> unitList,int count){
		List<Unit> selectUnitList=new ArrayList<Unit>();
		//��Ⱥ������Ӧ�ȵĺ�
		double allAdaptationDegree=0.0;
		for(int i=0;i<unitList.size();i++){
			allAdaptationDegree+=unitList.get(i).getAdaptationDegree();
		}
			Random rand=new Random();
			while(selectUnitList.size()!=count){
				//����0~1֮��������
				double degree=0.0;
				double randDegree=(rand.nextInt(99)+1)*0.01*allAdaptationDegree;
				//ѡ�����Ҫ��ĸ���
				for(int j=0;j<unitList.size();j++){
					degree+=unitList.get(j).getAdaptationDegree();
					if(degree>=randDegree){
						//���ظ�ѡ��
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
	 * ��������
	 * @param unitList
	 * @param count
	 * @param papere
	 * @return
	 */
	public static List<Unit> cross(List<Unit> unitList,int count,Paper paper){
		List<Unit> crossedUnitList=new ArrayList<Unit>();
		Random rand=new Random();
		while(crossedUnitList.size()!=count){
			//���ѡ����������
			int indexone=rand.nextInt(unitList.size());
			int indextow=rand.nextInt(unitList.size());
			Unit unitone;
			Unit unittow;
			if(indexone!=indextow){
				unitone=unitList.get(indexone);
				unittow=unitList.get(indextow);
				//���ѡ��һ������λ��
				int crossPosition=rand.nextInt(51-2);
				//��֤������Ŀ�������Լ���������ͬ
				double scoreone=unitone.getProblemList().get(crossPosition).getScore()+unitone.getProblemList().get(crossPosition+1).getScore();
				double scoretow=unittow.getProblemList().get(crossPosition).getScore()+unittow.getProblemList().get(crossPosition+1).getScore();
				if(scoreone==scoretow){
					//�����¸���
					Unit unitNewOne=new Unit();
					unitNewOne.getProblemList().addAll(unitone.getProblemList());
					Unit unitNewTow=new Unit();
					unitNewTow.getProblemList().addAll(unittow.getProblemList());
					//��������λ�ú����������
					for(int i=crossPosition;i<crossPosition+2;i++){
						unitNewOne.getProblemList().add(i,new Problem(unittow.getProblemList().get(i)));
						unitNewTow.getProblemList().add(i,new Problem(unitone.getProblemList().get(i)));
					}
					unitNewOne.setId(crossedUnitList.size());
					unitNewTow.setId(unitNewOne.getId()+1);
					//������������ӵ�����Ⱥ������ȥ
					if(crossedUnitList.size()<count){
						crossedUnitList.add(unitNewOne);
					}if(crossedUnitList.size()<count){
						crossedUnitList.add(unitNewTow);
					}
				}
			}
		//�����ظ�����
		//crossedUnitList = crossedUnitList.Distinct(new ProblemComparer()).ToList();
		}
		//����֪ʶ�㸲���ʼ���Ӧ��ֵ
		crossedUnitList=getKPCoverage(crossedUnitList, paper);
		crossedUnitList=getAdaptationDegree(crossedUnitList, paper, kpCoverage, difficulty);
		return crossedUnitList;
	}
	/**
	 * ��������
	 * @param unitList
	 * @param problemList
	 * @param paper
	 * @return
	 */
	public static List<Unit> change(List<Unit> unitList,List<Problem> problemList,Paper paper){
		Random rand=new Random();
		int index=0;
		for(int i=0;i<unitList.size();i++){
			//���ѡ��һ����
			index=rand.nextInt(unitList.get(i).getProblemList().size());
			Problem temp=unitList.get(i).getProblemList().get(index);
			Problem problem=new Problem();
			//�õ�������֪ʶ��
			for(int j=0;j<temp.getPoints().size();j++){
				if(paper.getPoints().contains(temp.getPoints().get(j))){
					problem.getPoints().add(temp.getPoints().get(j));
				}
			}
			//�����ݿ���ѡ���������֪ʶ���ͬ�����͡�ͬ��������ͬ��ŵ�����
			List<Problem> smallDB=new ArrayList<Problem>();
			for(Problem p:problemList){
				if(isContain(paper,problem)&&(p.getScore()==temp.getScore())&&(p.getType()==temp.getType())&&(p.getId()!=temp.getId())){
					smallDB.add(p);
				}
			}
			//�ӷ���Ҫ������������ѡ��һ��
			if(smallDB.size()>0){
				int indexo=rand.nextInt(smallDB.size());
				System.out.println(smallDB.size());
				unitList.get(i).getProblemList().set(index, smallDB.get(indexo));
			}
		}
		//����֪ʶ�㸲���ʺ���Ӧ��
		unitList=getKPCoverage(unitList, paper);
		unitList=getAdaptationDegree(unitList, paper, kpCoverage, difficulty);
		return unitList;
	}
	/**
	 * ��Ŀ֪ʶ���Ƿ�����Ծ�Ҫ��
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