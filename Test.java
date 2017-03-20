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
		//模拟数据库
		DB db=new DB();
		//试卷试题
		Paper paper=new Paper();
		paper.setId(1);
		//设置试卷的总分
		paper.setTotalScore(100);
		paper.setDifficulty(0.72);
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
		int runCount=100;
		//产生初始群体
		List<Population> unitList=MainFunction.CSZQ(10, paper, db.getProblemDB());
		System.out.println("------------遗传算法组卷系统--------------");
		System.out.println("初始种群:");
		showUnit(unitList.get(count));
		System.out.println("----------------迭代开始--------------------");
		while(!isEnd(unitList,expand)){
			System.out.println("在第"+(++count)+"代未得到结果");
			if(count>=runCount){
				System.out.println("计算"+runCount+"代仍未得到结果，请重新设置条件");
				break;
			}
			unitList=MainFunction.select(unitList,10);
			unitList=MainFunction.cross(unitList, 20, paper);
			if(isEnd(unitList,expand)){
				break;
			}
			unitList=MainFunction.change(unitList, db.getProblemDB(), paper);
		}
		if(count<runCount){
			System.out.println("在第"+(++count)+"代得到结果");
			System.out.println("期望试卷难度"+paper.getDifficulty());
			showResult(unitList,expand);
		}
	}
	/**
	 * 是否到达目标
	 * @param unitList
	 * @param endcondition
	 * @return
	 */
	public static boolean isEnd(List<Population> unitList,double endcondition){
		if(unitList.size()>0){
			for(int i=0;i<unitList.size();i++){
				if(unitList.get(i).getAdaptationDegree()>=endcondition){
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
	public static void showResult(List<Population> unitList,double expand){
		Collections.sort(unitList,new Population());
		for(Population unit:unitList){
			if(unit.getAdaptationDegree()>=expand){
				System.out.println("第"+unit.getId()+"套");
				System.out.println("题目数量                                   知识点分布                                    难度系数                                 适应度");
				System.out.println(unit.getProblemCount()+"           "+unit.getKpCoverage()+"           "+unit.getDifficuty()+"          "+unit.getAdaptationDegree());
			}
		}
	}
	/**
	 * 显示种群个体的题目编号
	 * 
	 */
	public static void showUnit(Population u){
		System.out.println("编号                                  知识点分布                                 难度系数");
		System.out.println(u.getId()+"          "+u.getKpCoverage()+"          "+u.getDifficuty(u.getProblemList()));
		for(int i=0;i<u.getProblemList().size();i++){
			System.out.print(u.getProblemList().get(i).getId()+"  ");
		}
		System.out.println();
	}
}