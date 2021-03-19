import javax.swing.*;
/*This class initializes the data for a bar-chart that represents a graph of the average temperatures during a specific
* year in a range of 5 years from which a user chooses to display a specific graph. This data is real data of the temperatures
* that were measured a few times a day during a specif year in Tel Aviv. This data was taken from the Israeli Meteorologic
* website*/
public class GraphData {
    //Private attributes of the class
    private double highestTemp;//The highest temperature during that year
    private double lowestTemp;//The lowest temperature during that year
    private String title;//The title of the graph
    private final String[] monthNums;//The months represented as numbers (1-12) array
    private double[] temperatures;//The average temperatures array
    private final int firstYear = 2015;//the first year in the range of years


    //The default constructor where the data is initialized according to the user's choice of year in the range
    public GraphData(){
        String input;//the answer of the user
        int curYear;//the current year that they user chose

        monthNums = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};;
        //Asking the user to enter a year
        input = JOptionPane.showInputDialog("Please enter a year between 2015 - 2019 \n press \"enter\" to quit");
        if(input==null||input.equals(""))//if the user doesn't enter any year or presses cancel, the program terminates
            System.exit(0);
        curYear = Integer.parseInt(input);//we'll convert the string representation of the year to an integer which is
        // easier that way to check if that year is in range of the data
        while(curYear-firstYear>4||curYear-firstYear<0){//if that year is out of range, keep asking him for a valid one
            JOptionPane.showMessageDialog(null,"Invalid year. Please enter a year between 2015 - 2019");
            input = JOptionPane.showInputDialog("Please enter a year \n(between 2015 - 2019)");
            if(input==null||input.equals(""))//if the user decides to quit by pressing enter or cancel, the program terminates
                System.exit(0);
            curYear = Integer.parseInt(input);//once again convert to integer and make sure the new year provided is within range

        }
        switch (curYear){//switch case according to the year given by the user
            //the array of the temperatures, the highest and lowest temperatures will be initialized according to that year
            case 2015:
                this.temperatures = new double[]{13.6,15.1,17.9,18.5,22.5,24.3,26.5,28.9,28.4,26,22.5,15.7};
                highestTemp = 28.9;
                lowestTemp = 13.6;
                break;

            case 2016:
                this.temperatures = new double[]{13.9,16.3,18.8,20.9,22.6,26,27.8,28.5,27.1,24.8,21.3,14.8};
                this.highestTemp = 28.5;
                this.lowestTemp = 13.9;
                break;

            case 2017:
                this.temperatures = new double[]{14,14.1,17.2,19.6,22.6,25.3,28.5,28.6,27.2,24.2,20.3,18.3};
                this.highestTemp = 28.6;
                this.lowestTemp = 14;
                break;

            case 2018:
                this.temperatures = new double[]{15.1,16.8,18.9,20,24.5,26.1,27.9,28.4,27.5,25.7,20.5,16.9};
                this.highestTemp = 28.4;
                this.lowestTemp = 15.1;
                break;

            case 2019:
                this.temperatures = new double[]{14.4,15.5,15.8,18.1,22.1,25.9,27.9,28.5,27.4,25.2,22,16.6};
                this.highestTemp = 28.5;
                this.lowestTemp = 14.4;
                break;
        }
        //the title also will be according to that year
        title = "Average Temperatures in the year "+ input;
    }

    //Getters methods that will be used by the drawing chart class ("BarChart") to get the data
    public double getTemperatures(int i) {
        return temperatures[i];
    }

    public double getHighest() {
        return highestTemp;
    }

    public double getLowest() {
        return lowestTemp;
    }

    public String getTitle() {
        return title;
    }

    public String getMonths(int i) {
        return monthNums[i];
    }



}
