package com.yifan.bookstore.service;

import com.yifan.bookstore.BookstoreApplication;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(value=Parameterized.class)
public class CartManagementTest {
    @Before
    public void setUp() throws Exception {
        TestContextManager testContextManager = new TestContextManager(getClass());
        testContextManager.prepareTestInstance(this);
    }
    @Resource
    CartManagement cartManagement;
    @Test
    public void getBookList() throws SolrServerException, IOException {
        Assert.assertEquals("[{\"_id\" : \"0\", \"Description\":\"undefined\"},{\"_id\" : \"1\", \"Description\":\"undefined\"},{\"_id\" : \"2\", \"Description\":\"程序员必读经典著作！理解计算机系统*书目，10万程序员共同选择。第二版销售突破100000册，第三版重磅上市！\"},{\"_id\" : \"3\", \"Description\":\"大师名著纵横二十载，稳居任一荐书单三甲；称职程序员傍身绝学，通向C++精微奥妙之门。\"},{\"_id\" : \"4\", \"Description\":\"豆瓣9.7高分推荐！旅法翻译家梅子涵之女梅思繁法文直译，舒朗大开本，央美教授高精度还原原作插画。首次收录全球舞台剧、音乐会、电影、动画片等对《小王子》的精彩诠释，通晓名作的前世今生。\"},{\"_id\" : \"5\", \"Description\":\"Java学习必读经典,殿堂级著作！赢得了全球程序员的广泛赞誉。\"},{\"_id\" : \"6\", \"Description\":\"暴雪官方历时二十年编纂而成的史料！三卷《魔兽世界编年史》将呈现大量从未公布的精美原画和插图，读者在阅读故事之余，更能享受一次视觉上的饕餮盛宴，是魔兽粉丝收藏的优选。\"},{\"_id\" : \"7\", \"Description\":\"刘慈欣代表作，亚洲首部“雨果奖”获奖作品！\"},{\"_id\" : \"8\", \"Description\":\"《悲惨世界》是雨果在流亡期间写的长篇小说，是他的代表作，也是世界文学宝库的珍品之一。《悲惨世界》通过冉阿让等人的悲惨遭遇以及冉阿让被卞福汝主教感化后一系列令人感动的事迹，深刻揭露和批判了19世纪法国封建专制社会的腐朽本质及其罪恶现象，对穷苦人民在封建重压下所遭受的剥削欺诈和残酷迫害表示了悲悯和同情。\"},{\"_id\" : \"9\", \"Description\":\"也译“动物庄园”，是“一代人的冷峻良知”乔治·奥威尔经典的讽喻之作。虽然这一场荒诞的动物革命走向歧途，但正是因为这样我们才了解“把权力关进制度的笼子”的重要性。\"},{\"_id\" : \"10\", \"Description\":\"击败AlphaGo的武林秘籍，赢得人机大战的必由之路：人工智能大牛周志华教授巨著，全面揭开机器学习的奥秘。\"},{\"_id\" : \"11\", \"Description\":\"刘易斯基金会独家授权插图！翻译家吴岩，陈良廷，刘文澜经典译本！\"},{\"_id\" : \"12\", \"Description\":\"收录诺贝尔文学奖获奖作品《老人与海》《乞力马扎罗的雪》，深深影响了马尔克斯、塞林格等文学家的创作理念。\"},{\"_id\" : \"13\", \"Description\":\"喜欢《解忧杂货店》，就一定要读这本书。珍藏印签。有了想要守护的东西，生命就会变得有力量。悲凉的人生、千疮百孔的命运、一桩桩悲剧的发生与救赎，读来令人喟叹不已。\"},{\"_id\" : \"14\", \"Description\":\"七篇寻光故事，七种奇遇人生，送给成长路上孤独前行的你，每个人的生活都有被困在井里一样的绝望时刻，而这本书就想做点亮黑夜的那束光芒。耐心一些，保持相信，我们终会穿越漫长黑夜，抵达属于自己的黎明。\"},{\"_id\" : \"15\", \"Description\":\"美国政府不想让全世界读到这本书，欧美上市当日作者便被美国司法部起诉！“棱镜门”主角爱德华·斯诺登首次亲自披露美国政府滥用NSA系统监控世界的真相，袒露从“爱国者”到“叛国者”的心路历程。\"},{\"_id\" : \"16\", \"Description\":\"嫦娥五号探测器系统副总设计师彭兢诚意推荐！纪念人类登月50周年，五大精妙立体机关直观呈现月球的运行轨迹，全方位揭秘人类探月登月的过程，普及基本的航天知识，与孩子一起解读月球的奥秘，种下探索宇宙的种子。\"},{\"_id\" : \"17\", \"Description\":\"五年高考三年模拟，英语五三高考练习册，五三高中同步53全练全解，你值得拥有！\"},{\"_id\" : \"18\", \"Description\":\"中国古典小说佳作，影响整个华人世界的经典！轻松易学、国家教育部推荐读物！\"},{\"_id\" : \"19\", \"Description\":\"人民文学出版社天天出版社出品，经典作品，教师推荐，已有超过150000读者给予好评！\"},{\"_id\" : \"20\", \"Description\":\"“许多年过去了，人们说陈年旧事可以被埋葬，然而我终于明白这是错的，因为往事会自行爬上来。回首前尘，我意识到在过去二十六年里，自己始终在窥视着那荒芜的小径。”\"},{\"_id\" : \"21\", \"Description\":\"从软件工程的本质出发、结合实际案例，系统全面地介绍软件过程、软件建模技术与方法及软件工程管理同时介绍一些热点新技术和新方法。\"},{\"_id\" : \"22\", \"Description\":\"本书内容丰富，不仅讨论了关系数据模型和关系语言、数据库设计过程、关系数据库理论、数据库应用设计和开发、数据存储结构、数据存取技术、查询优化方法、事务处理系统和并发控制、故障恢复技术、数据仓库和数据挖掘，而且对性能调整、性能评测标准、数据库应用测试和标准化等高级应用主题进行了广泛讨论。\"},{\"_id\" : \"23\", \"Description\":\"全书选材经典、内容丰富、结构合理、逻辑清晰，对本科生的数据结构课程和研究生的算法课程都是非常实用的教材，在IT专业人员的职业生涯中，本书也是一本案头必备的参考书或工程实践手册。\"},{\"_id\" : \"24\", \"Description\":\"荣获商务印书馆2019人文社科十大好书，张大可先生《史记》研究的集成之作，精细考证，廓清原书真伪；题解语译，展现著者史观，是一部人人都能读懂、人人都会爱读的文白对照本《史记》。\"},{\"_id\" : \"25\", \"Description\":\"《天龙八部》一书以北宋、辽、西夏、大理并立的历史为宏大背景，将儒释道、琴棋书画等中国传统文化融会贯通其中，书中人物繁多，个性鲜明，情节离奇，尽显芸芸众生百态。\"},{\"_id\" : \"26\", \"Description\":\"一部《辟邪剑谱》引发灭门血案，阴险狡诈，机关算尽，只为争霸武林，真相往往出人意表。酒后高歌磨剑，梦中快意恩仇，一曲《笑傲江湖》，传一段天荒地老。 \"},{\"_id\" : \"27\", \"Description\":\"《楚留香传奇》无疑乃古龙诸作中*为烩炙人口之作，此作固成就古龙之盛名，更成为武侠文学之重要里程碑。楚留香有西方007罗杰摩尔之冷静、优雅、明快及幽默，更因他没有复仇及情爱之纠葛（例如他从来不杀人）而超越007，颇有“本来无一物，何处惹尘埃”的意境。\"},{\"_id\" : \"28\", \"Description\":\"“沉湎于虚幻的梦想，而忘记现实的生活，这是毫无益处的，千万记住。”                                ——阿不思·邓布利多\"},{\"_id\" : \"29\", \"Description\":\"两个人不能都活着，只有一个生存下来，我们中的一个将要永远离开……\"},{\"_id\" : \"102\", \"Description\":\"undefined\"},{\"_id\" : \"106\", \"Description\":\"0\"},{\"_id\" : \"115\", \"Description\":\"undefined\"}]",cartManagement.getBooklist());
    }


    private String expected;
    private String input11;
    private int input22;
    private String expected1;
    private int input1;
    private int input2;

    public CartManagementTest (String expected, String input11, int input22,String expected1,int input1,int input2){
        this.expected = expected;
        this.input11 = input11;
        this.input22 = input22;
        this.expected1=expected1;
        this.input1=input1;
        this.input2=input2;
    }

    @Parameterized.Parameters
    public static Collection prepareData(){
        String n=null;
        Object[][] object = {{"Succeed","admin",5,"Amount cannot be less than zero",303,-1}, {"Succeed","ldx",0,"Succeed",92,2}, {"Not logged in",n,0,"No enough books",303,10000}};
        return Arrays.asList(object);
    }
    @Test
    public void addCart(){
        Assert.assertEquals(expected,cartManagement.addCart(input11,input22));

    }
    @Test
    public void changeAmount(){
        Assert.assertEquals(expected1,cartManagement.changeAmount(input1,input2));
    }
    @Test
    public void fetchCart(){
        Assert.assertEquals("[{\"Book_name\" : \"Effective C++\", \"Order_id\" : \"92\", \"Book_id\" : \"0\", \"Author\" : \"凯S.霍斯特曼\", \"Price\" : \"2.0\", \"Amount\" : \"2\"},{\"Book_name\" : \"Java核心技术卷II\", \"Order_id\" : \"302\", \"Book_id\" : \"1\", \"Author\" : \"凯S.霍斯特曼\", \"Price\" : \"190.0\", \"Amount\" : \"2\"}]",cartManagement.fetchCart("ldx"));
    }
    @Test
    public void fetchCart1(){
        String usn=null;
        Assert.assertEquals("Not logged in",cartManagement.fetchCart(usn));
    }



}