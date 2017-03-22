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
        Question model;
        Random rand=new Random();
        List<Integer> points;
        
        for(int i=1;i<=5000;i++){
            model=new Question();
            model.setId(i);
            //试题试题难度设置，0.0~1.0
            model.setDifficulty((rand.nextInt(71)+30)*0.01);
            model.setDistinguish((rand.nextInt(71)+30)*0.01);
            model.setExposure((rand.nextInt(5)));
            model.setCognitive((rand.nextInt(71)+30)*0.01);
            
            //选择题1分
            if(i<1001){
            	model.setType(1);
                model.setScore(1);
            }
            
            //填空题1分
            if(i>1000&&i<2001){
                model.setType(2);
                model.setScore(1);
            }
            
            //判断题1分
            if(i>2000&&i<3001){
                model.setType(3);
                model.setScore(1);
            }
            
            //读程序5分
            if(i>3000&&i<4001){
                model.setType(4);
                model.setScore(5);
            }
            
            //写程序10分
            if(i>4000&&i<5001){
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
            int count=rand.nextInt(7)+1;
            
            for(int j=0;j<count;j++){
                points.add(rand.nextInt(99)+1);
            }
            model.setPoints(points);
            problemDB.add(model);
        }
    }
 
    public List<Question> getProblemDB() {
        return problemDB;
    }
 
    public void setProblemDB(List<Question> problemDB) {
        this.problemDB = problemDB;
    }
}