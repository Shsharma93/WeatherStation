package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.io.*;
import java.util.*;

import javafx.scene.text.Text;

/*
 * Author: Shashank Kumar - 44003199
 */


public class Controller {


    @FXML
    LineChart<String, Number> stripchart;

    @FXML
    public BarChart barchart1;

    @FXML
    private BarChart barchart2;

    @FXML
    private BarChart barchart3;

    @FXML
    private Text t1;

    @FXML
    private Text t4;

    @FXML
    private Text t2;

    @FXML
    private Text t3;

    private List<String> dataFromCsv = new ArrayList<>();

    private int counter = 0;

    boolean suspended;


    String timestamp = new String();

    String DateTime = new String();


    private Random randomGenerator = new Random();

  /* -------------------Writing and Reading Data to and from CSV------------------------ */

    public void Save(ActionEvent event) {


        try

        {

            /* ---------Writing Data to csv-------------- */

            FileWriter fileWriter = null;
            fileWriter = new FileWriter("file.csv");        /* create a new csv file */

            String COMMA_DELIMITER = ",";
            String NEW_LINE_SEPARATOR = "\n";

            for (int i = 0; i < 10000; i++) {

                Calendar cal = Calendar.getInstance();

                int day = cal.get(Calendar.DAY_OF_MONTH);           /*------Get the day of month-----*/
                int month = cal.get(Calendar.MONTH);                /*------Get the month-----*/
                int year = cal.get(Calendar.YEAR);                  /*------Get the year-----*/

                if (month == 12) {
                    month = 1;
                } else {
                    month += 1;
                }


                int second = cal.get(Calendar.SECOND);
                int minute = cal.get(Calendar.MINUTE);
                int hours = cal.get(Calendar.HOUR);

                /* -------------------Writing Date and Time in timestamp string------------------------ */

                timestamp = (String.valueOf(day) + String.valueOf(String.format("%02d", month)) + String.valueOf(year) + "." + (String.format("%02d", hours)) + (String.format("%02d", minute)) + (String.format("%02d", second)));


                fileWriter.append(timestamp);                   /* ---------Writing timestamp to csv-------------- */
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(randomGenerator.nextInt(100)));        /* ---------Writing windspeed (dummy data) to csv-------------- */
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(randomGenerator.nextInt(100)));        /* ---------Writing temperature (dummy data) to csv-------------- */
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(randomGenerator.nextInt(200)));        /* ---------Writing insolation (dummy data) to csv-------------- */

                fileWriter.append(NEW_LINE_SEPARATOR);          /*-------Add a new line separator after the header--*/

            }


            /* ---------Reading data from csv-------------- */

            BufferedReader fileReader = new BufferedReader(new FileReader("file.csv"));
            String line = "";

            try {
                while ((line = fileReader.readLine()) != null) {

                    //Get all tokens available in line

                    String[] tokens = line.split(COMMA_DELIMITER);
                    if (tokens.length > 0) {
                        List<String> list = new ArrayList<String>(Arrays.asList(tokens));
                        Collection<String> collection = new ArrayList<String>(list);
                        dataFromCsv.addAll(collection);         /*-------Saving data from CSV to Array Lists --------*/

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /* ------------------------------- Pause and Resume ------------------------------------ */

    public void Pause(ActionEvent event) throws InterruptedIOException {

        suspended = true;           /*-------Pause the program-------*/

    }

    public void Resume(ActionEvent event) throws InterruptedIOException {

        suspended = false;          /*-------Resume the program------*/
    }


    int i = 0;


    /*-------Start the Digital, Analog and Strip Display-----*/

    public void Realtime(ActionEvent event) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 6000; x++) {
                    if (suspended == false) {                   /*------Check if pause is pressed-----*/
                        try {
                            Thread.sleep(1000);                 /*------Wait for 1 second-----*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {


                                Calendar cal = Calendar.getInstance();

                                int day = cal.get(Calendar.DAY_OF_MONTH);       /*------Get the day of month-----*/
                                int month = cal.get(Calendar.MONTH);            /*------Get the month-----*/
                                int year = cal.get(Calendar.YEAR);              /*------Get the year-----*/

                                if (month == 12) {
                                    month = 1;
                                } else {
                                    month += 1;
                                }


                                int second = cal.get(Calendar.SECOND);          /*------Get the second-----*/
                                int minute = cal.get(Calendar.MINUTE);          /*------Get the minute-----*/
                                int hours = cal.get(Calendar.HOUR);             /*------Get the hours-----*/


                                /*------Storing Date and time in it's format in DateTime String-----*/

                                DateTime = (String.valueOf(day) + "-" + String.valueOf(String.format("%02d", month)) + "-" + String.valueOf(year) + "." + (String.format("%02d", hours)) + ":" + (String.format("%02d", minute)) + ":" + (String.format("%02d", second)));

                                t4.setText(DateTime);           /*------Set the DateTime for Digital Display-----*/
                                t4.getText();
                                t4.setId("dig4");               /*------Set the ID of Text t4-----*/


                                /*------XY Line Chart for Barchart Display-----*/


                                XYChart.Series set1 = new XYChart.Series<>();           /*------XY Bar Chart for Wind Speed-----*/
                                XYChart.Series set2 = new XYChart.Series<>();           /*------XY Bar Chart for Temperature-----*/
                                XYChart.Series set3 = new XYChart.Series<>();           /*------XY Bar Chart for Insolation-----*/


                                /*------XY Line Chart for Line Chart Display-----*/


                                XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();   /*------Series for Wind Speed-----*/
                                XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();          /*------Series for Temperature-----*/
                                XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();          /*------Series for Insolation-----*/


                                series1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Line chart-----*/
                                series2.setName("Temperature (Celcius)");       /*------Set Name of temperature Line chart-----*/
                                series3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Line chart-----*/


                                set1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Bar chart-----*/
                                set2.setName("Temperature (Celcius)");       /*------Set Name of temperature Bar chart-----*/
                                set3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Bar chart-----*/


                                 /*------Get Data for wind speed for Digital, Analog and Stripchart Display-----*/


                                set1.getData().add(new XYChart.Data("Wind Speed", Integer.parseInt(dataFromCsv.get(counter + 1))));
                                t1.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 1))))));
                                t1.getText();
                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 1))));


                                /*------Get Data for Temperature for Digital, Analog and Stripchart Display-----*/


                                set2.getData().add(new XYChart.Data("Temperature", Integer.parseInt(dataFromCsv.get(counter + 2))));
                                t2.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 2))))));
                                t2.getText();
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 2))));


                                /*------Get Data for Insolation for Digital, Analog and Stripchart Display-----*/


                                set3.getData().add(new XYChart.Data("Insolation", Integer.parseInt(dataFromCsv.get(counter + 3))));
                                t3.setText(String.valueOf(String.format("%03d", (Integer.parseInt(dataFromCsv.get(counter + 3))))));
                                t3.getText();
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 3))));


                                /*------Get All Data (wind, temp, insolation) for second point of Stripchart Display-----*/


                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 5))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 6))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 7))));

                                /*------Get All Data (wind, temp, insolation) for third point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 9))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 10))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 11))));

                                /*------Get All Data (wind, temp, insolation) for fourth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 13))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 14))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 15))));

                                /*------Get All Data (wind, temp, insolation) for fifth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 17))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 18))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 19))));

                                /*------Get All Data (wind, temp, insolation) for sixth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 21))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 22))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 23))));

                                /*------Get All Data (wind, temp, insolation) for seventh point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 25))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 26))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 27))));

                                /*------Get All Data (wind, temp, insolation) for eighth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 29))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 30))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 31))));


                                /*------Get All Data (wind, temp, insolation) for ninth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 33))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 34))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 35))));


                                /*------Get All Data (wind, temp, insolation) for tenth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 37))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 38))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 39))));


                                i += 1;
                                counter += 4;           /*------increase the counter value to get next line of data from CSV-----*/


                                barchart1.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart1.setAnimated(false);           /*------Set no animation-----*/
                                barchart2.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart2.setAnimated(false);           /*------Set no animation-----*/
                                barchart3.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart3.setAnimated(false);           /*------Set no animation-----*/
                                barchart1.getData().add(set1);          /*------Display Barchart for Wind Speed-----*/
                                barchart2.getData().add(set2);          /*------Display Barchart for Temperature-----*/
                                barchart3.getData().add(set3);          /*------Display Barchart for Insolation-----*/
                                barchart1.getBarGap();

                                barchart3.getBarGap();

                                stripchart.setId("strip");              /*------Set ID for Stripchart-----*/

                                t1.setId("dig1");                       /*------Set ID for wind digital display-----*/
                                t2.setId("dig2");                       /*------Set ID for temp digital display-----*/
                                t3.setId("dig3");                       /*------Set ID for insolation digital display-----*/


                                stripchart.getData().clear();           /*------clear the previous strip chart-----*/
                                stripchart.setAnimated(false);          /*------Set no animation-----*/
                                stripchart.getData().addAll(series1, series2, series3); /*------Display Stripchart for all Data-----*/


                            }


                        });
                    }
                }
            }
        }).start();

    }

    public void Speed5X(ActionEvent event) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 6000; x++) {
                    if (suspended == false) {                   /*------Check if pause is pressed-----*/
                        try {
                            Thread.sleep(200);                 /*------Wait for 0.2 second-----*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {


                                Calendar cal = Calendar.getInstance();

                                int day = cal.get(Calendar.DAY_OF_MONTH);       /*------Get the day of month-----*/
                                int month = cal.get(Calendar.MONTH);            /*------Get the month-----*/
                                int year = cal.get(Calendar.YEAR);              /*------Get the year-----*/

                                if (month == 12) {
                                    month = 1;
                                } else {
                                    month += 1;
                                }


                                int second = cal.get(Calendar.SECOND);          /*------Get the second-----*/
                                int minute = cal.get(Calendar.MINUTE);          /*------Get the minute-----*/
                                int hours = cal.get(Calendar.HOUR);             /*------Get the hours-----*/


                                /*------Storing Date and time in it's format in DateTime String-----*/

                                DateTime = (String.valueOf(day) + "-" + String.valueOf(String.format("%02d", month)) + "-" + String.valueOf(year) + "." + (String.format("%02d", hours)) + ":" + (String.format("%02d", minute)) + ":" + (String.format("%02d", second)));

                                t4.setText(DateTime);           /*------Set the DateTime for Digital Display-----*/
                                t4.getText();
                                t4.setId("dig4");               /*------Set the ID of Text t4-----*/


                                /*------XY Line Chart for Barchart Display-----*/


                                XYChart.Series set1 = new XYChart.Series<>();           /*------XY Bar Chart for Wind Speed-----*/
                                XYChart.Series set2 = new XYChart.Series<>();           /*------XY Bar Chart for Temperature-----*/
                                XYChart.Series set3 = new XYChart.Series<>();           /*------XY Bar Chart for Insolation-----*/


                                /*------XY Line Chart for Line Chart Display-----*/


                                XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();   /*------Series for Wind Speed-----*/
                                XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();          /*------Series for Temperature-----*/
                                XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();          /*------Series for Insolation-----*/


                                series1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Line chart-----*/
                                series2.setName("Temperature (Celcius)");       /*------Set Name of temperature Line chart-----*/
                                series3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Line chart-----*/


                                set1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Bar chart-----*/
                                set2.setName("Temperature (Celcius)");       /*------Set Name of temperature Bar chart-----*/
                                set3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Bar chart-----*/


                                 /*------Get Data for wind speed for Digital, Analog and Stripchart Display-----*/


                                set1.getData().add(new XYChart.Data("Wind Speed", Integer.parseInt(dataFromCsv.get(counter + 1))));
                                t1.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 1))))));
                                t1.getText();
                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 1))));


                                /*------Get Data for Temperature for Digital, Analog and Stripchart Display-----*/


                                set2.getData().add(new XYChart.Data("Temperature", Integer.parseInt(dataFromCsv.get(counter + 2))));
                                t2.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 2))))));
                                t2.getText();
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 2))));


                                /*------Get Data for Insolation for Digital, Analog and Stripchart Display-----*/


                                set3.getData().add(new XYChart.Data("Insolation", Integer.parseInt(dataFromCsv.get(counter + 3))));
                                t3.setText(String.valueOf(String.format("%03d", (Integer.parseInt(dataFromCsv.get(counter + 3))))));
                                t3.getText();
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 3))));


                                /*------Get All Data (wind, temp, insolation) for second point of Stripchart Display-----*/


                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 5))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 6))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 7))));

                                /*------Get All Data (wind, temp, insolation) for third point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 9))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 10))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 11))));

                                /*------Get All Data (wind, temp, insolation) for fourth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 13))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 14))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 15))));

                                /*------Get All Data (wind, temp, insolation) for fifth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 17))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 18))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 19))));

                                /*------Get All Data (wind, temp, insolation) for sixth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 21))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 22))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 23))));

                                /*------Get All Data (wind, temp, insolation) for seventh point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 25))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 26))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 27))));

                                /*------Get All Data (wind, temp, insolation) for eighth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 29))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 30))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 31))));


                                /*------Get All Data (wind, temp, insolation) for ninth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 33))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 34))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 35))));


                                /*------Get All Data (wind, temp, insolation) for tenth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 37))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 38))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 39))));


                                i += 1;
                                counter += 4;           /*------increase the counter value to get next line of data from CSV-----*/


                                barchart1.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart1.setAnimated(false);           /*------Set no animation-----*/
                                barchart2.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart2.setAnimated(false);           /*------Set no animation-----*/
                                barchart3.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart3.setAnimated(false);           /*------Set no animation-----*/
                                barchart1.getData().add(set1);          /*------Display Barchart for Wind Speed-----*/
                                barchart2.getData().add(set2);          /*------Display Barchart for Temperature-----*/
                                barchart3.getData().add(set3);          /*------Display Barchart for Insolation-----*/

                                barchart2.getBarGap();
                                barchart3.getBarGap();
                                barchart1.getCategoryGap();

                                stripchart.setId("strip");              /*------Set ID for Stripchart-----*/

                                t1.setId("dig1");                       /*------Set ID for wind digital display-----*/
                                t2.setId("dig2");                       /*------Set ID for temp digital display-----*/
                                t3.setId("dig3");                       /*------Set ID for insolation digital display-----*/


                                stripchart.getData().clear();           /*------clear the previous strip chart-----*/
                                stripchart.setAnimated(false);          /*------Set no animation-----*/
                                stripchart.getData().addAll(series1, series2, series3); /*------Display Stripchart for all Data-----*/


                            }


                        });
                    }
                }
            }
        }).start();


    }


    public void Speed10X(ActionEvent event) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 6000; x++) {
                    if (suspended == false) {                   /*------Check if pause is pressed-----*/
                        try {
                            Thread.sleep(100);                 /*------Wait for 0.1 second-----*/
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {


                                Calendar cal = Calendar.getInstance();

                                int day = cal.get(Calendar.DAY_OF_MONTH);       /*------Get the day of month-----*/
                                int month = cal.get(Calendar.MONTH);            /*------Get the month-----*/
                                int year = cal.get(Calendar.YEAR);              /*------Get the year-----*/

                                if (month == 12) {
                                    month = 1;
                                } else {
                                    month += 1;
                                }


                                int second = cal.get(Calendar.SECOND);          /*------Get the second-----*/
                                int minute = cal.get(Calendar.MINUTE);          /*------Get the minute-----*/
                                int hours = cal.get(Calendar.HOUR);             /*------Get the hours-----*/


                                /*------Storing Date and time in it's format in DateTime String-----*/

                                DateTime = (String.valueOf(day) + "-" + String.valueOf(String.format("%02d", month)) + "-" + String.valueOf(year) + "." + (String.format("%02d", hours)) + ":" + (String.format("%02d", minute)) + ":" + (String.format("%02d", second)));

                                t4.setText(DateTime);           /*------Set the DateTime for Digital Display-----*/
                                t4.getText();
                                t4.setId("dig4");               /*------Set the ID of Text t4-----*/


                                /*------XY Line Chart for Barchart Display-----*/


                                XYChart.Series set1 = new XYChart.Series<>();           /*------XY Bar Chart for Wind Speed-----*/
                                XYChart.Series set2 = new XYChart.Series<>();           /*------XY Bar Chart for Temperature-----*/
                                XYChart.Series set3 = new XYChart.Series<>();           /*------XY Bar Chart for Insolation-----*/


                                /*------XY Line Chart for Line Chart Display-----*/


                                XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();   /*------Series for Wind Speed-----*/
                                XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();          /*------Series for Temperature-----*/
                                XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();          /*------Series for Insolation-----*/


                                series1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Line chart-----*/
                                series2.setName("Temperature (Celcius)");       /*------Set Name of temperature Line chart-----*/
                                series3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Line chart-----*/


                                set1.setName("Wind Speed (Mph)");            /*------Set Name of wind speed Bar chart-----*/
                                set2.setName("Temperature (Celcius)");       /*------Set Name of temperature Bar chart-----*/
                                set3.setName("Insolation (W/m sq)");         /*------Set Name of insolation Bar chart-----*/


                                 /*------Get Data for wind speed for Digital, Analog and Stripchart Display-----*/


                                set1.getData().add(new XYChart.Data("Wind Speed", Integer.parseInt(dataFromCsv.get(counter + 1))));
                                t1.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 1))))));
                                t1.getText();
                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 1))));


                                /*------Get Data for Temperature for Digital, Analog and Stripchart Display-----*/


                                set2.getData().add(new XYChart.Data("Temperature", Integer.parseInt(dataFromCsv.get(counter + 2))));
                                t2.setText(String.valueOf(String.format("%02d", (Integer.parseInt(dataFromCsv.get(counter + 2))))));
                                t2.getText();
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 2))));


                                /*------Get Data for Insolation for Digital, Analog and Stripchart Display-----*/


                                set3.getData().add(new XYChart.Data("Insolation", Integer.parseInt(dataFromCsv.get(counter + 3))));
                                t3.setText(String.valueOf(String.format("%03d", (Integer.parseInt(dataFromCsv.get(counter + 3))))));
                                t3.getText();
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(DateTime), Integer.parseInt(dataFromCsv.get(counter + 3))));


                                /*------Get All Data (wind, temp, insolation) for second point of Stripchart Display-----*/


                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 5))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 6))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(9), Integer.parseInt(dataFromCsv.get(counter + 7))));

                                /*------Get All Data (wind, temp, insolation) for third point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 9))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 10))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(8), Integer.parseInt(dataFromCsv.get(counter + 11))));

                                /*------Get All Data (wind, temp, insolation) for fourth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 13))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 14))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(7), Integer.parseInt(dataFromCsv.get(counter + 15))));

                                /*------Get All Data (wind, temp, insolation) for fifth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 17))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 18))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(6), Integer.parseInt(dataFromCsv.get(counter + 19))));

                                /*------Get All Data (wind, temp, insolation) for sixth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 21))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 22))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(5), Integer.parseInt(dataFromCsv.get(counter + 23))));

                                /*------Get All Data (wind, temp, insolation) for seventh point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 25))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 26))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(4), Integer.parseInt(dataFromCsv.get(counter + 27))));

                                /*------Get All Data (wind, temp, insolation) for eighth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 29))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 30))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(3), Integer.parseInt(dataFromCsv.get(counter + 31))));


                                /*------Get All Data (wind, temp, insolation) for ninth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 33))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 34))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(2), Integer.parseInt(dataFromCsv.get(counter + 35))));


                                /*------Get All Data (wind, temp, insolation) for tenth point of Stripchart Display-----*/

                                series1.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 37))));
                                series2.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 38))));
                                series3.getData().add(new XYChart.Data<String, Number>(String.valueOf(1), Integer.parseInt(dataFromCsv.get(counter + 39))));


                                i += 1;
                                counter += 4;           /*------increase the counter value to get next line of data from CSV-----*/


                                barchart1.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart1.setAnimated(false);           /*------Set no animation-----*/
                                barchart2.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart2.setAnimated(false);           /*------Set no animation-----*/
                                barchart3.getData().clear();            /*------clear the previous bar chart-----*/
                                barchart3.setAnimated(false);           /*------Set no animation-----*/
                                barchart1.getData().add(set1);          /*------Display Barchart for Wind Speed-----*/
                                barchart2.getData().add(set2);          /*------Display Barchart for Temperature-----*/
                                barchart3.getData().add(set3);          /*------Display Barchart for Insolation-----*/
                                barchart1.getBarGap();
                                barchart2.getBarGap();
                                barchart3.getBorder();


                                stripchart.setId("strip");              /*------Set ID for Stripchart-----*/

                                t1.setId("dig1");                       /*------Set ID for wind digital display-----*/
                                t2.setId("dig2");                       /*------Set ID for temp digital display-----*/
                                t3.setId("dig3");                       /*------Set ID for insolation digital display-----*/


                                stripchart.getData().clear();           /*------clear the previous strip chart-----*/
                                stripchart.setAnimated(false);          /*------Set no animation-----*/
                                stripchart.getData().addAll(series1, series2, series3); /*------Display Stripchart for all Data-----*/


                            }


                        });
                    }
                }
            }
        }).start();

    }


}

