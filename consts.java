package stark;

import java.util.HashMap;
import java.util.Map;

public class consts{
	//设置目标试卷的总分
	public static final int SCORE = 100;
	//设置目标试卷的难度系数
	public static final double DIFFICULTY=0.72;
	//设置目标试卷的认知层次
	public static final double DISTINGUISH = 0.62;
	//设置目标试卷的曝光率
	public static final double EXPOSURE = 0.22;
	//设置适应度期望值
	public static final double EXPAND =0.65;
	//种群进化代数
	public static final int RUNCOUNT = 3;
	//种群数量
	public static final int POPULATION_COUNT = 100;
	public static String Welcome="------------遗传算法组卷系统--------------";
	public static String Begin="----------------迭代开始--------------------";
	final Map<String,String> my_map= new HashMap<String, String>();
}