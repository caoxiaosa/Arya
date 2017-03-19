package paper1_GA;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * �����
 *
 */
public class DB {
	
    //�����
    private List<Problem> problemDB;
    
    public DB(){
        problemDB=new ArrayList<Problem>();
        Problem model;
        Random rand=new Random();
        List<Integer> points;
        
        for(int i=1;i<=500;i++){
            model=new Problem();
            model.setId(i);
            //���������Ѷ����ã�0.0~1.0
            model.setDifficulty((rand.nextInt(71)+30)*0.01);
            
            //ѡ����1��
            if(i<100){
            	model.setType(1);
                model.setScore(1);
            }
            
            //�����1��
            if(i>100&&i<200){
                model.setType(2);
                model.setScore(1);
            }
            
            //�ж���1��
            if(i>200&&i<300){
                model.setType(3);
                model.setScore(1);
            }
            
            //������5��
            if(i>300&&i<400){
                model.setType(4);
                model.setScore(5);
            }
            
            //д����10��
            if(i>400&&i<500){
                model.setType(5);
                //�����Ѷ�ϵ������10ȡ��
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
            
            //ÿ�����Ͱ���1��6��֪ʶ��
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
