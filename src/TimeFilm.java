import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TimeFilm extends JPanel implements ActionListener {
    private static Font defaultFont = new Font("SimSun", Font.PLAIN, 12); //默认字体
    private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    private JTable table;  //表格
    private MyTableModel model=new MyTableModel();  //声明模型对象
    private JButton createButton;
    private JLabel jLabel;
    private int beginNumber=1;
    private List<Work> list=new ArrayList<>();//作业队列
    private List<Work> ready=new ArrayList<>();//就绪队列
    private int time_film=10;//时间片
    private int time=0;//程序时间
    private boolean flag=true;
    public TimeFilm(){
        init();
        run();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==createButton){
            createWork();
//            if(flag){
//                run();
//            }else {
//                flag=false;
//            }
        }
    }
    private void createWork(){
        int length=(int) (Math.random() * 50)+51;
        String flag="就绪";
        Work work=new Work(flag,"作业"+Integer.toString(beginNumber),length,Integer.toString(time));
        beginNumber++;
        list.add(work);
        ready.add(work);
    }
    private void run(){
//            work.run(time);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //                Thread.sleep(2000);
                try {
                    while(true){
                        if(ready.size()>0){
                            Work work=ready.get(0);
                            for(int i=0;i<time_film;i++) {
                                if(work.done== work.length){
                                    break;
                                }
                                Thread.sleep(1000);
                                time++;
                                //                        System.out.println("123");
                                work.setFlag("CPU执行");
                                work.run();
                                updateTable();
                            }
                            ready.remove(work);
                            if(work.done<work.length){
                                work.setFlag("就绪");
                                ready.add(work);
                                updateTable();
                            }else if(work.done==work.length){
                                work.setFlag("已完成");
                                work.setEndTime(Integer.toString(time));
                                updateTable();
                            }
                        }else {
                            Thread.sleep(1000);
                            time++;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    private void updateTable(){
        data.clear();
        for(int i=0;i<list.size();i++){
            Vector<Object> v = new Vector<Object>();
            Work work=list.get(i);
            v.add(new String(work.flag));
            v.add(new String(work.name));
            v.add(work.getTime);
            v.add(new String(Integer.toString(work.length)));
            if(work.endTime!=null){
                v.add(work.endTime);
            }else {
                v.add(new String(""));
            }
//            v.add(new String(Integer.toString(model.size)));
            float length_temp=work.length;
            float done_temp=work.done;
            float currentProgress=(done_temp/length_temp)*100;
            v.add(Integer.toString((int)currentProgress));
            v.add(new String(work.done+"/"+work.length));
            data.add(v);
        }
        TableColumn statusColumn = table.getColumnModel().getColumn(5);
        statusColumn.setCellRenderer(new ProgressCellRender());
        model.addTableItem();                          //自适应行数
        table.repaint();
    }

    private void init(){
        Box baseBox = Box.createVerticalBox();     //根盒子
        baseBox.setSize(5000, 200);

//        baseBox.add(bannerLabel);
        //-------------------容器内容------------------------------
        JPanel showPanel = new JPanel();
        Box vtemp = Box.createVerticalBox();
        Box htemp1 = Box.createHorizontalBox();
        Box htemp2 = Box.createHorizontalBox();
        //新建作业
        createButton=new JButton("新建作业");
        createButton.addActionListener(this);
        jLabel = new JLabel("每个作业的长度都为50-100之间的随机值:");
        jLabel.setFont(defaultFont);
        htemp1.add(createButton);
        htemp1.add(Box.createHorizontalStrut(50));//创建间隔
        htemp1.add(jLabel);
        //表格
        table = new JTable(model);                     //把模型对象作为参数构造表格对象，这样就可以用表格显示出数据
        DefaultTableCellRenderer   r   =   new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,   r);
        JScrollPane scroll = new JScrollPane(table);   //把表格控件放到滚动容器里，页面不够长显示可以滚动
        scroll.setPreferredSize(new Dimension(600, 300));
        htemp2.add(scroll);

        //-------------------容器内容------------------------------
        vtemp.add(htemp1);
        vtemp.add(Box.createVerticalStrut(15));                  //创建上下空间距离
        vtemp.add(htemp2);
        showPanel.add(vtemp);
        baseBox.add(showPanel);


        this.add(baseBox, BorderLayout.NORTH);
    }
    class MyTableModel extends AbstractTableModel  //模型类
    {

        Vector<String> title = new Vector<String>();// 列名


        /**
         * 构造方法，初始化二维动态数组data对应的数据
         */
        public MyTableModel()                       //构造方法
        {
            title.add("作业状态");

            title.add("作业名");

            title.add("到达时间");

            title.add("作业长度");

            title.add("结束时间");

            title.add("进度");

            title.add("完成率");

        }

        // 以下为继承自AbstractTableModle的方法，可以自定义
        /**
         * 得到列名
         */
        @Override
        public String getColumnName(int column)
        {
            return title.elementAt(column);
        }

        /**
         * 重写方法，得到表格列数
         */
        @Override
        public int getColumnCount()
        {
            return title.size();
        }

        /**
         * 得到表格行数
         */
        @Override
        public int getRowCount()
        {
            return data.size();
        }

        /**
         * 得到数据所对应对象
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            //return data[rowIndex][columnIndex];
            return data.elementAt(rowIndex).elementAt(columnIndex);
        }

        /**
         * 得到指定列的数据类型
         */
        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            return data.elementAt(0).elementAt(columnIndex).getClass();
        }

        /**
         * 指定设置数据单元是否可编辑.
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return false;
        }

        /*
         * 适配数据行数
         */
        public void addTableItem() {
            fireTableDataChanged();
        }

    }
}
