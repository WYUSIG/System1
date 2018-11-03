import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ProgressCellRender extends DefaultTableCellRenderer {
    public ProgressCellRender() {
    }
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        this.setOpaque(true);//render是否透明

        JProgressBar progressBar = new JProgressBar();

        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        //进度条边框线
//   progressBar.setBorder(BorderFactory.createEmptyBorder());
        progressBar.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        progressBar.setForeground(new Color(0, 255, 128));//前景色
        progressBar.setStringPainted(true);//是否显示ToolTipText
        if (value != null) {
            int progressValue = ((Integer.valueOf(value.toString())) ).intValue();
            progressBar.setValue(progressValue);
            //设置背景色
            if (isSelected) {
                progressBar.setBackground(new Color(206, 207, 255));
            } else {
                progressBar.setBackground(Color.white);
            }
        }
        progressBar.setToolTipText(String.valueOf(progressBar.getValue())
                + "% ");
        return progressBar;
    }
}
