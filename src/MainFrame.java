import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private JMenuBar menubar;    //声明对象  菜单条
    private JMenu menu;   //菜单
    private JMenuItem mtimeFilm,mdynamic,mhighResponseRatio;
    private TimeFilm timeFilm=new TimeFilm();
    private Dynamic dynamic=new Dynamic();
    private HighResponseRatio highResponseRatio=new HighResponseRatio();
    private CardLayout card=null;   //卡片式布局.可以容纳多个组件，被层叠放入容器中，先加入的在上面
    private JPanel pCenter;         //面板

    //构造函数
    public MainFrame(){
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==mtimeFilm) {
            //打开录入图书信息
            card.show(pCenter,"mtimeFilm");
        }else if(e.getSource()==mdynamic){
            card.show(pCenter,"mdynamic");
        }else if(e.getSource()==mhighResponseRatio){
            card.show(pCenter,"mhighResponseRatio");
        }
    }
    private void init(){
        menubar=new JMenuBar(); //为对象分配变量
        menu=new JMenu("菜单");
        mtimeFilm=new JMenuItem("时间片调度",new ImageIcon("src/res/时间.png"));//创建图标
        mdynamic=new JMenuItem("动态优先级调度",new ImageIcon("src/res/动态.png"));
        mhighResponseRatio=new JMenuItem("高响应比优先调度",new ImageIcon("src/res/响应.png"));
//        mshowBook=new JMenuItem("显示图书信息",new ImageIcon("src/res/显示图书.png"));
//        mshowLend=new JMenuItem("显示借阅信息",new ImageIcon("src/res/借阅查询.png"));
//        mshowPeople=new JMenuItem("显示读者信息",new ImageIcon("src/res/查询人.png"));
//        mreturnBook=new JMenuItem("还书",new ImageIcon("src/res/还书菜单.png"));
        menu.add(mtimeFilm);  //菜单项加到“菜单”下
        menu.add(mdynamic);
        menu.add(mhighResponseRatio);
//        menu.add(mshowBook);
//        menu.add(mshowLend);
//        menu.add(mshowPeople);
//        menu.add(mreturnBook);
        menubar.add(menu);     //菜单加到菜单条
        setJMenuBar(menubar);  //实例化对象 菜单条
        mtimeFilm.addActionListener(this);
        mdynamic.addActionListener(this);
        mhighResponseRatio.addActionListener(this);
//        mreturnBook.addActionListener(this);
//        mshowBook.addActionListener(this);
//        mshowPeople.addActionListener(this);
//        mshowLend.addActionListener(this);
        card=new CardLayout();
        pCenter=new JPanel();
        pCenter.setLayout(card); //设置容器布局
        pCenter.add("mtimeFilm",timeFilm); //向面板添加组件
        pCenter.add("mdynamic",dynamic);
        pCenter.add("mhighResponseRatio",highResponseRatio);
//        pCenter.add("mreturnBook",returnBook);
//        pCenter.add("mshowBook",showBook);
//        pCenter.add("mshowPeople",showPeople);
//        pCenter.add("mshowLend",showLend);
        add(pCenter,BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //点×时结束窗口所在的应用程序
        setVisible(true);            //设置窗口可见
        setBounds(100,500,800,600);  //设置窗口初始位置，距左，距上，宽，高
        validate();//验证此容器及其所有子组件。使用 validate方法会使容器再次布置其子组件。已经布置容器后，在修改此容器的子组件的时候应该调用此方法
    }
}
