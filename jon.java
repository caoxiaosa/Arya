package stark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class jon {

	/**
	 * @param args
	 */
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
        System.out.println(points);
        final Map<String,String> my_map= new HashMap<String, String>();
	}

}
