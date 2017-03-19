package paper1_GA;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 试题库
 *
 */
public class DB {
	
    //试题库
    private List<Problem> problemDB;
    
    public DB(){
        problemDB=new ArrayList<Problem>();
        Problem model;
        Random rand=new Random();
        List<Integer> points;
        
        for(int i=1;i<=500;i++){
            model=new Problem();
            model.setId(i);
            //试题试题难度设置，0.0~1.0
            model.setDifficulty((rand.nextInt(71)+30)*0.01);
            
            //选择题1分
            if(i<100){
            	model.setType(1);
                model.setScore(1);
            }
            
            //填空题1分
            if(i>100&&i<200){
                model.setType(2);
                model.setScore(1);
            }
            
            //判断题1分
            if(i>200&&i<300){
                model.setType(3);
                model.setScore(1);
            }
            
            //读程序5分
            if(i>300&&i<400){
                model.setType(4);
                model.setScore(5);
            }
            
            //写程序10分
            if(i>400&&i<500){
                model.setType(5);
                //分数难度系数乘以10取得
                /*double diff=model.getDifficulty();
                int score;
                if(diff>0.3){
                    score=(int)diff*10;
                }else{
                    score=3;
                }
                model.setScore(score);*/
                model.setScore(10);
            }
            
            points=new ArrayList<Integer>();
            
            //每种题型包含1到6个知识点
            int count=rand.nextInt(6)+1;
            for(int j=0;j<count;j++){
                points.add(rand.nextInt(99)+1);
            }
            model.setPoints(points);
            problemDB.add(model);
        }
    }
 
    public List<Problem> getProblemDB() {
        return problemDB;
    }
 
    public void setProblemDB(List<Problem> problemDB) {
        this.problemDB = problemDB;
    }
}
