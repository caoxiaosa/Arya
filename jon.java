package stark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("serial")
public class jon {

	/**
	 * @param args
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> kpCoverage=new ArrayList<Integer>();
		kpCoverage.add(3);
		for(int i=0;i<5;i++){
			kpCoverage.add(i);
		}
		Set<Integer> set=new HashSet<Integer>(kpCoverage);
		kpCoverage=new ArrayList<Integer>(set);
		List<Integer> points=new ArrayList<Integer>();
		for(int i=1;i<=81;i+=2){
			points.add(i);
		}
        /*System.out.println(points);*/
        Map<String,String> map= new HashMap<String, String>();
		map.put("1", "2");
		map.put("2", "2");
		map.put("4", "1");
		map.put("8", "1");
		map.put("9", "2");
		for (String key : map.keySet()) {
			//System.out.println("key= "+ key + " and value= " + map.get(key));
		}
		int index=(int) (Math.random()*3)+1;
		//System.out.println("_____"+index);
        Random rand=new Random();
		get_knowledge(rand);
		
		DB db=new DB();
		List<Question> questionList1=new ArrayList<Question>();
		questionList1=db.getProblemDB();
		//System.out.println(questionList1);
		questionList1.clear();
		List<Question> questionList=db.getProblemDB();
		//System.out.println(questionList1);
		//System.out.println(questionList);
		
		int pCount=consts.POPULATION_COUNT;
		Paper paper = MainFunction.initPaper();
		//产生初始群体
		//List<Population> populationList=MainFunction.Init_population(pCount, paper);
		
		//System.out.println(eachTypeCount.length);
		
		 int arr[][] = new int[][] { { 1 }, { 1, 2 }, { 1, 2, 3 } };
		 for (int i = 0; i < arr.length; i++) {
		  int[] arr2 = arr[i];
		  //System.out.println(Arrays.asList(arr2).contains("1"));
		  for(int s: arr2){
	        if(Integer.toString(s).equals(Integer.toString(1)))
	            System.out.println("ok");
		    }
		  //System.out.println(arr2);
		 }
		 	/*int[] eachTypeCount=new int[]{20,15,15,6,2};*/
		 	int[] eachTypeCount=paper.getEachTypeCount();
			int[][] eachTypePoints=paper.getEachTypePoints();
			Set<String> eachType_points = new HashSet<String>();
			
			for(int j=0;j<eachTypeCount.length;j++){
				System.out.println("-------"+eachTypeCount[j]);
				int [] type_points = eachTypePoints[j];
				for(int s: type_points){
					System.out.println(j+":"+s);
					eachType_points.add(""+s);
					for (String str : eachType_points) {  
					      System.out.println("-----"+str.toString());  
					}    
				}
			}
			

	}
	static List<String> kpNameList=new ArrayList<String>(){{add("第一章"); add("指针");add("函数");add("for循环");add("第六节");add("第三章");add("while循环");add("第五节");add("嵌套");add("结构体");}};  
	static List<Integer> kpLevel = new ArrayList<Integer>(Arrays.asList(1,2,3));
	private static void get_knowledge(Random rand) {
		Knowledge_points knowledge_points = null;
		for(int i=0;i<10;i++){
			int index=(int)(Math.random()*2)+1;
        	knowledge_points=new Knowledge_points();
        	knowledge_points.setId(i);
        	knowledge_points.setLevel(kpLevel.get(index));
        	knowledge_points.setLe3PName(kpNameList.get(i));
        	knowledge_points.setkpContent("C语言");
        	//System.out.println("---"+knowledge_points.getId()+"---"+knowledge_points.getLevel()+"---"+knowledge_points.getLe3PName()+"---"+knowledge_points.getkpContent());
        }
	}

}
