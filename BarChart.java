import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
//This class creates a JFrame window in which a car chart is drawn according to the data of the year the user chose.
//After the graph is shown in the window, the user can exit by pressing the "x" button. Then the program will ask the
//user if he wants to display a graph of a different year and continue to show graphs until the user decides to exit
public class BarChart extends JPanel {
    //private attributes of the class
    private final int MAX_VALUES = 12;//the maximal number of bars in the chart
    private double[] values = new double[MAX_VALUES];//the heights of the bars are according ot the values
    private String[] names = new String[MAX_VALUES];//the names are drawn as labels beneath the bars
    private String title;//the title of the graph which is drawn at the top of the window
    private GraphData graph;//the graph which is drawn according to the data
    private double maxValue;//the highest bar
    private double minValue;//the lowest bar


    //the constructor
    public BarChart() {
        graph = new GraphData();//creating a new data base for the graph
        for (int i = 0; i < 12; i++) {
            values[i] = graph.getTemperatures(i);//initializing the values according to the data
        }
        for (int i = 0; i < 12; i++) {
            names[i] = graph.getMonths(i);//initializing the names
        }//getting the other data
        title = graph.getTitle();
        maxValue = graph.getHighest();
        minValue = graph.getLowest();
    }
    //This method creates the window in which the graph will be drawn and also calls the method that draws the graph
    public static void createGraph(){
        //Creating the JFrame and initializing it
        JFrame f = new JFrame();
        f.setSize(1200, 700);
        f.add(new BarChart());
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //Asking the user if he wants to display another graph or simply exit
                int reply = JOptionPane.showConfirmDialog(null, "Would you like to see the chart of a different year?");
                if(reply==1)
                    System.exit(0);
                else
                    createGraph();
            }
        };
        f.addWindowListener(wndCloser);
        f.setVisible(true);
    }
    //This method create the paint component of the graph which will be displayed in the JFrame.
    public void paintComponent(Graphics g) {
        //first calling the super method
        super.paintComponent(g);
        //if there's no data to show in the graph, simply return
        if (values == null || values.length == 0)
            return;

        Dimension d = getSize();//Gets the size of the JPanel
        //The chart will be changed when the user changes the size of the window and so all the graphic
        //sizes will be proportional to the window size and change accordingly
        final int CHART_WIDTH = d.width - (d.width/20);//the width of the chart
        final int CHART_HEIGHT = d.height - (d.height/80);//the height of the chart
        final int BAR_WIDTH = CHART_WIDTH / values.length - CHART_WIDTH/150;//the width of each bar
        final int TITLE_SIZE = d.width/30;//the title font size
        final int LABEL_SIZE = d.width/50;//the labels font size
        final int VALUE_SIZE = d.width/68;//the values on top of the bars' font size
        final int SCALE_NUMS = d.width/55;//the scale numbers' font size
        final int AXIS_X = d.width/145;//the x placement of the axis
        final int AXIS_WIDTH = d.width/135;//the axis width
        final int BLANK_WIDTH = d.width/15;//Blank space in width for aesthetic reasons
        final int BLANK_HEIGHT1 = CHART_HEIGHT/14;//Blank space in height for aesthetic purpose
        final int BLANK_HEIGHT2 = CHART_HEIGHT/16;//Blank space in height for aesthetic purpose
        final int LABEL_SPACE = CHART_HEIGHT/65;//space between the labels and the bars
        final int SCALE_MARK = 5;//the difference between the scale marks
        final int SPACE = 10; //some extra or less space when needed for aesthetic purpose

        //Initializing the font properties of the title, the labels, the numbers over the bars and the numbers on the scale
        Font titleFont = new Font("SansSerif", Font.BOLD, TITLE_SIZE);
        FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
        Font labelFont = new Font("SansSerif", Font.PLAIN, LABEL_SIZE);
        FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);
        Font valuesFont = new Font("SansSerif",Font.PLAIN,VALUE_SIZE);
        FontMetrics valuesFontMetrics = g.getFontMetrics(valuesFont);
        Font yAxisFont = new Font("SansSerif",Font.PLAIN,SCALE_NUMS);

        int titleWidth = titleFontMetrics.stringWidth(title);//the width of the title

        int y = titleFontMetrics.getAscent();
        int x = (d.width - titleWidth) / 2; // puts the title in the center of the window

        g.setFont(titleFont);//setting the font to that of the title
        g.drawString(title, x, y);//drawing the title

        int top = titleFontMetrics.getHeight();//The height of the title
        int bottom = labelFontMetrics.getHeight();//The height of the months' numbers
        //setting the scale for drawing the bars and other components
        double scale = (CHART_HEIGHT - top - bottom - BLANK_HEIGHT1) / maxValue;

        y = CHART_HEIGHT - labelFontMetrics.getDescent();

        int yAxisY = top + BLANK_HEIGHT1 - SPACE;//the y component of the point of the Y axis of the chart
        int yAxisHeight = (int)(maxValue * scale)+SPACE;//the height of the Y axis of the chart

        g.fillRect(AXIS_X,yAxisY,AXIS_WIDTH,yAxisHeight);//drawing the Y axis
        g.setFont(yAxisFont);
        int num = 0;
        //drawing the scale marks and writing the scale numbers on the Y axis of the chart
        for(int i = yAxisY+yAxisHeight; i>=yAxisY; i-= (double)(yAxisHeight/(maxValue/SCALE_MARK))) {
            if(num!=0){
                g.fillRect(AXIS_X/2, i, 2 * AXIS_WIDTH, AXIS_WIDTH / 2);//draw the scales
                g.drawString(String.valueOf(num),2*AXIS_X+AXIS_WIDTH,i+AXIS_X);//write the numbers
            }
            num+=SCALE_MARK;
        }
        //draw the bottom X axis of the chart
        g.fillRect(AXIS_X,yAxisY+yAxisHeight,BLANK_WIDTH + (MAX_VALUES* BAR_WIDTH),AXIS_WIDTH);

        //Now we will draw the bars, the labels underneath and the values on top of them
        for (int i=0; i < values.length; i++) {
            g.setFont(labelFont);
            int valueX = BLANK_WIDTH + (i * BAR_WIDTH);//the x component of the bar
            int valueY = top+ BLANK_HEIGHT1 + (int) ((maxValue - values[i]) * scale);;//the y component of the bar is
            // according to the value of that specific bar
            int height = (int) (values[i] * scale);//the height of the bar
            int z = bottom + valueY - BLANK_HEIGHT2;//the placement for the label under the bar
            //Painting the bar according to the temperature:
            if(values[i]==maxValue)//if it's the hottest month it's red
                g.setColor(Color.RED);
            else if(values[i]==minValue)//if it's the coldest month it's blue
                g.setColor(Color.BLUE);
            else//others are gray
                g.setColor(Color.GRAY);

            g.fillRect(valueX, valueY, BAR_WIDTH, height);//now draw the rectangular which is the bar
            g.setColor(Color.black);//draw a black lining
            g.drawRect(valueX, valueY, BAR_WIDTH, height);

            int labelWidth = labelFontMetrics.stringWidth(names[i]);//the width of the label
            x = BLANK_WIDTH + i * BAR_WIDTH + (BAR_WIDTH - labelWidth) / 2;//the placement of the label
            g.drawString(names[i], x, y+LABEL_SPACE);//draw the label
            //now do the same for the value on top of the bar
            g.setFont(valuesFont);
            String strValue = String.valueOf(values[i]);
            int valueWidth = valuesFontMetrics.stringWidth(strValue);
            x = BLANK_WIDTH + i * BAR_WIDTH + (BAR_WIDTH - valueWidth)/2;
            g.drawString(strValue,x,z);
        }

    }
}