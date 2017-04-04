package stark;

public class Knowledge_points {
    //知识点id
	private int id;
	//知识点级别
	private int level;
	//当前知识点所属章名
	private String Le1_name;
	//当前知识点所属节名
	private String Le2_name;
	//当前知识点名(只有三级知识点参与遗传操作)
	private String Le3_name;
	//知识点内容
	private String kp_content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getLe3PName() {
		return Le3_name;
	}

	public void setLe3PName(String knowledgePointsName) {
		Le3_name = knowledgePointsName;
	}
	
	
	public String getLe1PName() {
		return Le1_name;
	}

	public void setLe1PName(String knowledgePointsName) {
		Le1_name = knowledgePointsName;
	}
	
	public String getLe2PName() {
		return Le2_name;
	}

	public void setLe2PName(String knowledgePointsName) {
		Le2_name = knowledgePointsName;
	}

	public String getkpContent() {
		return kp_content;
	}

	public void setkpContent(String knowledgePointsContent) {
		kp_content = knowledgePointsContent;
	}

}
