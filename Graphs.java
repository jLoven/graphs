//  3 February 2015 Jackie Loven

import javax.swing.*;
import org.math.plot.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Graphs {

	private JFrame frame = new JFrame("Papa's plot");
	private JPanel panel = new JPanel();

	ArrayList<String> deviceIDs = new ArrayList<String>();
	ArrayList<String> times = new ArrayList<String>();
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<String> weights = new ArrayList<String>();
	ArrayList<String> temperatures = new ArrayList<String>();
	ArrayList<String> humidities = new ArrayList<String>();
	ArrayList<String> pressures = new ArrayList<String>();
	ArrayList<String> lightIntensities = new ArrayList<String>();

	public Graphs() {

		try (BufferedReader br = new BufferedReader(new FileReader("src/DataFiles/papa_data_sample.txt")))
		{
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] items = sCurrentLine.split(",");
				//  Add the first item to ArrayList device_ID
				deviceIDs.add(items[0]);
				times.add(items[1]);
				dates.add(items[2]);
				weights.add(items[3]);
				temperatures.add(items[4]);
				humidities.add(items[5]);
				pressures.add(items[6]);
				lightIntensities.add(items[7]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//		System.out.println(deviceIDs);
		System.out.println(times);
		System.out.println(dates);
		//		System.out.println(weights);
		//		System.out.println(temperatures);
		//		System.out.println(humidities);
		//		System.out.println(pressures);
		//		System.out.println(lightIntensities);

		double[] weightsListForPlotting = new double[weights.size()];
		for(int i = 0; i < weights.size(); i ++){
			weightsListForPlotting[i] = Double.parseDouble(weights.get(i));
		}

		double[] timesListForPlotting = new double[times.size()];
		for(int i = 0; i < times.size(); i ++){
			String[] items = times.get(i).split(":");
			int seconds = Integer.parseInt(items[0]) * 3600 + Integer.parseInt(items[1]) 
					* 60 + Integer.parseInt(items[2]);
			timesListForPlotting[i] = seconds;
			
		}

		Plot2DPanel plot = new Plot2DPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(800, 400); //  Dimensions of panel
			}
		};

		plot.addLinePlot("my plot", timesListForPlotting, weightsListForPlotting);
		panel.setLayout(new BorderLayout());
		panel.add(plot);
		plot.setAxisLabels("Time (s)", "Weight");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setLocation(150, 150);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Graphs();
			}
		});
	}
}