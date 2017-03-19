package paper1_GA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 
 * ������
 *
 */
public class Test {
	public static void main(String[] args) {
		//ģ�����ݿ�
		DB db=new DB();
		//�Ծ�����
		Paper paper=new Paper();
		paper.setId(1);
		//�����Ծ���ܷ�
		paper.setTotalScore(100);
		paper.setDifficulty(0.72);
		//�����Ծ��֪ʶ��
		List<Integer> points=new ArrayList<Integer>();
		for(int i=1;i<=81;i+=2){
			points.add(i);
		}
		//�����Ծ�������͵�����
		int[] eachTypeCount=new int[]{20,15,15,6,2};
		paper.setPoints(points);
		paper.setEachTypeCount(eachTypeCount);
		int count=1;
		//������Ӧ������ֵ
		double expand=0.90;
		//���õ�������
		int runCount=100;
		//������ʼȺ��
		List<Unit> unitList=MainFunction.CSZQ(10, paper, db.getProblemDB());
		System.out.println("------------�Ŵ��㷨���ϵͳ--------------");
		System.out.println("��ʼ��Ⱥ:");
		showUnit(unitList.get(count));
		System.out.println("----------------������ʼ--------------------");
		while(!isEnd(unitList,expand)){
			System.out.println("�ڵ�"+(++count)+"��δ�õ����");
			if(count>=runCount){
				System.out.println("����"+runCount+"����δ�õ��������������������");
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
			System.out.println("�ڵ�"+(++count)+"���õ����");
			System.out.println("�����Ծ��Ѷ�"+paper.getDifficulty());
			showResult(unitList,expand);
		}
	}
	/**
	 * �Ƿ񵽴�Ŀ��
	 * @param unitList
	 * @param endcondition
	 * @return
	 */
	public static boolean isEnd(List<Unit> unitList,double endcondition){
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
	 * ��ʾ���
	 * @param unitList
	 * @param expand
	 */
	public static void showResult(List<Unit> unitList,double expand){
		Collections.sort(unitList,new Unit());
		for(Unit unit:unitList){
			if(unit.getAdaptationDegree()>=expand){
				System.out.println("��"+unit.getId()+"��");
				System.out.println("��Ŀ����                                   ֪ʶ��ֲ�                                    �Ѷ�ϵ��                                 ��Ӧ��");
				System.out.println(unit.getProblemCount()+"           "+unit.getKpCoverage()+"           "+unit.getDifficuty()+"          "+unit.getAdaptationDegree());
			}
		}
	}
	/**
	 * ��ʾ��Ⱥ�������Ŀ���
	 * 
	 */
	public static void showUnit(Unit u){
		System.out.println("���                                  ֪ʶ��ֲ�                                 �Ѷ�ϵ��");
		System.out.println(u.getId()+"          "+u.getKpCoverage()+"          "+u.getDifficuty(u.getProblemList()));
		for(int i=0;i<u.getProblemList().size();i++){
			System.out.print(u.getProblemList().get(i).getId()+"  ");
		}
		System.out.println();
	}
}