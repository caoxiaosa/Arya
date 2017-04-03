# Arya
艾莉亚是临冬城领主兼北境守护艾德·史塔克公爵和凯特琳·徒利夫人的小女儿和第三个孩子。从小她就是一个胆大活泼、生气勃勃的女孩，喜欢打仗和探险的故事
# 名词解释
适应度值：该值表示试卷的优劣，值越大，试卷越接近用户期望。

代码计算
计算：根据知识点覆盖率、难度系数值、区分度和曝光率与对应用户期望值的计算

f1=populationList.get(i).getKpCoverage()*kpCoverage;//知识点覆盖率

f2=Math.abs(populationList.get(i).getDifficuty()-paper.getDifficulty())*difficulty;//难度系数

f3=Math.abs(populationList.get(i).getDistinguish()-paper.getDistinguish())*distinguish;//区分度

f4=Math.abs(populationList.get(i).getExposure()-paper.getExposure())*exposure;//曝光率

adaptionDegree=kpCoverage*Math.exp(-f1)+difficulty*Math.exp(-f2)+distinguish*Math.exp(-f3)+exposure*Math.exp(-f4);//期望度值的计算
