package stark;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 使用随机数模拟试题库
 *
 */
public class DB {
	
    private List<Question> problemDB;
    
    public DB(){
        problemDB=new ArrayList<Question>();
        Question question;
        @SuppressWarnings("unused")
		Paper paper = null;
        
        Random rand=new Random();
        List<Integer> points = null;
        
        for(int i=1;i<=500;i++){
        	question=new Question();
        	question.setId(i);
            //试题试题难度设置，0.0~1.0
        	question.setDifficulty((rand.nextInt(71)+30)*0.01);
        	question.setDistinguish((rand.nextInt(71)+30)*0.01);
        	question.setExposure(rand.nextInt(3));
        	question.setCognitive((rand.nextInt(71)+30)*0.01);
            
            //选择题1分
            if(i<100){
            	question.setType(1);
            	question.setScore(1);
            }
            
            //填空题1分
            if(i>100&&i<200){
            	question.setType(2);
            	question.setScore(1);
            }
            
            //判断题1分
            if(i>200&&i<300){
            	question.setType(3);
            	question.setScore(1);
            }
            
            //读程序5分
            if(i>300&&i<400){
            	question.setType(4);
            	question.setScore(5);
            }
            
            //写程序10分
            if(i>400&&i<500){
            	question.setType(5);
            	question.setScore(10);
            }
            //每道试题1个主知识点，1到4个次要知识点
            int count=rand.nextInt(26);
            
            points=new ArrayList<Integer>();
            for(int j=0;j<count;j++){
                points.add(rand.nextInt(26)+1);
            }
            
            /*question.setPoints(points);*/
            question.setPoint(count);
            problemDB.add(question);
        }
        
       /* get_knowledge(rand);*/
    }

    /*List<String> Le1_name=new ArrayList<String>(){{add("第一章"); add("第二章");add("第三章");add("第四章");add("第五章");add("第六章");add("第七章");add("第八章");}};
    List<String> Le2_name = new ArrayList<String>(){{add("第一节"); add("第二节");add("第三节");add("第四节");add("第五节");add("第六节");add("第七节");}};
    List<String> Le3_name = new ArrayList<String>(){{add("常量"); add("指针");add("函数");add("for循环");add("表达式");add("函数");add("while循环");add("嵌套");}};
    List<Integer> level = new ArrayList<Integer>(Arrays.asList(1,2,3));
    
	private void get_knowledge(Random rand) {
		Knowledge_points knowledge_points;
		for(int i=0;i<10;i++){
        	knowledge_points=new Knowledge_points();
        	knowledge_points.setId(i);
        	knowledge_points.setLe3PName(Le3_name.get(i));
        }
	}*/
	
    public List<Question> getProblemDB() {
        return problemDB;
    }
 
    public void setProblemDB(List<Question> problemDB) {
        this.problemDB = problemDB;
    }
}