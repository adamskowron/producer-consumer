import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/* 
 *  Problem producenta i konsumenta
 *
 *  Autor: Adam Skowroñski
 *   Data: 4 Grudnia 2017 r.
 */

public class ProducerConsumerWindow extends JFrame implements ActionListener, ChangeListener
{
	
	private static final long serialVersionUID = 1L;

	JPanel main = new JPanel();
	
	JComboBox<Integer> ComboBoxBufferSize;
	JComboBox ComboBoxProducers;
	JComboBox ComboBoxConsumers;
	JLabel BufferSizeLabel;
	JLabel ProducerAmmountLabel;
	JLabel ConsumerAmmountLabel;
	JTextArea text;
	JScrollPane ScrollPaneSimulation;
	JSlider MinProducerTimeSlider;
	JSlider MaxProducerTimeSlider;
	JSlider MinConsumerTimeSlider;
	JSlider MaxConsumerTimeSlider;
	
	JButton StartButton;
	JButton StopButton; 
	JButton ResetButton; 
	
	JLabel Sliderlabel1;
	JLabel Sliderlabel2;
	JLabel Sliderlabel3;
	JLabel Sliderlabel4;
	
	 private Buffer buffer;
	 private boolean isStarted = false;
	 
	 private java.util.ArrayList<Producer> producers;
	 private java.util.ArrayList<Consumer> consumers;
	 
	 
	 
	public ProducerConsumerWindow(){
		
		super("Producent Konsument - Symulacja");
		
		
		String[] numbers = {"1","2","3","4","5","6",};
		
		
		
		
		ComboBoxBufferSize = new JComboBox(numbers);
		ComboBoxProducers = new JComboBox(numbers);
		ComboBoxConsumers = new JComboBox(numbers);
		text = new JTextArea(20,35);
		ScrollPaneSimulation = new JScrollPane(text);
		BufferSizeLabel = new JLabel("Rozmiar bufora");
		ProducerAmmountLabel = new JLabel("Ilosc producentow");
		ConsumerAmmountLabel = new JLabel("Ilosc konsumentow");
		StartButton = new JButton("Start");
		StopButton = new JButton("Stop");
		ResetButton = new JButton("Restart");
		
		MinProducerTimeSlider = new JSlider(JSlider.HORIZONTAL,100,1000,100);
		MaxProducerTimeSlider = new JSlider(JSlider.HORIZONTAL,100,1000,1000);
		MinConsumerTimeSlider = new JSlider(JSlider.HORIZONTAL,100,1000,100);
		MaxConsumerTimeSlider = new JSlider(JSlider.HORIZONTAL,100,1000,1000);
		
		
		Font font = new Font("SliderFont", Font.ITALIC, 20);
		
		Sliderlabel1 = new JLabel("Min. czas produkcji");
		Sliderlabel1.setFont(font);
		Sliderlabel2 = new JLabel("Max. czas produkcji");
		Sliderlabel2.setFont(font);
		Sliderlabel3 = new JLabel("Min. czas konsumpcji");
		Sliderlabel3.setFont(font);
		Sliderlabel4 = new JLabel("Max. czas konsumpcji");
		Sliderlabel4.setFont(font);
		
		MinProducerTimeSlider.setPaintLabels(true);
		MinProducerTimeSlider.setFont(font);
		MinProducerTimeSlider.setMajorTickSpacing(200);
		MinProducerTimeSlider.setMinorTickSpacing(100);
		MinProducerTimeSlider.setPaintTicks(true);
		MinProducerTimeSlider.setPaintLabels(true);
		
		MaxProducerTimeSlider.setPaintLabels(true);
		MaxProducerTimeSlider.setFont(font);
		MaxProducerTimeSlider.setMajorTickSpacing(200);
		MaxProducerTimeSlider.setMinorTickSpacing(100);
		MaxProducerTimeSlider.setPaintTicks(true);
		MaxProducerTimeSlider.setPaintLabels(true);
		
		MinConsumerTimeSlider.setPaintLabels(true);
		MinConsumerTimeSlider.setFont(font);
		MinConsumerTimeSlider.setMajorTickSpacing(200);
		MinConsumerTimeSlider.setMinorTickSpacing(100);
		MinConsumerTimeSlider.setPaintTicks(true);
		MinConsumerTimeSlider.setPaintLabels(true);
		
		MaxConsumerTimeSlider.setPaintLabels(true);
		MaxConsumerTimeSlider.setFont(font);
		MaxConsumerTimeSlider.setMajorTickSpacing(200);
		MaxConsumerTimeSlider.setMinorTickSpacing(100);
		MaxConsumerTimeSlider.setPaintTicks(true);
		MaxConsumerTimeSlider.setPaintLabels(true);
		
		producers = new java.util.ArrayList<Producer>();
		consumers = new java.util.ArrayList<Consumer>();
	
		
		this.setDefaultCloseOperation(3);
		this.setSize(650,800);
		this.setResizable(false);
		
		ScrollPaneSimulation.createVerticalScrollBar();
		
		ComboBoxBufferSize.setEditable(false);
		ComboBoxProducers.setEditable(false);
		ComboBoxConsumers.setEditable(false);
		text.setEditable(false);
		
		ComboBoxBufferSize.addActionListener(this);
		ComboBoxProducers.addActionListener(this);
		ComboBoxConsumers.addActionListener(this);
		StartButton.addActionListener(this);
		StopButton.addActionListener(this);
		ResetButton.addActionListener(this);
		
		MinProducerTimeSlider.addChangeListener(this);
		MaxProducerTimeSlider.addChangeListener(this);
		MinConsumerTimeSlider.addChangeListener(this);
		MaxConsumerTimeSlider.addChangeListener(this);
		
		
		main.add(BufferSizeLabel);
		main.add(ComboBoxBufferSize);
		main.add(ProducerAmmountLabel);
		main.add(ComboBoxProducers);
		main.add(ConsumerAmmountLabel);
		main.add(ComboBoxConsumers);
		main.add(ScrollPaneSimulation);
		main.add(StartButton);
		main.add(StopButton);
		main.add(ResetButton);
		
		JPanel sliderpanel = new JPanel(new GridLayout(4, 2));
       main.add(sliderpanel);
        sliderpanel.add(Sliderlabel1);
		sliderpanel.add(MinProducerTimeSlider);
		sliderpanel.add(Sliderlabel2);
		sliderpanel.add(MaxProducerTimeSlider);
		sliderpanel.add(Sliderlabel3);
		sliderpanel.add(MinConsumerTimeSlider);
		sliderpanel.add(Sliderlabel4);
		sliderpanel.add(MaxConsumerTimeSlider);
		
		this.setContentPane(main);
		
		StopButton.setEnabled(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object src = e.getSource();
	
		if(src == StartButton)
		{
			if(!isStarted)
			{
				buffer = new Buffer((Integer.parseInt((String) ComboBoxBufferSize.getSelectedItem())));
				
				for(int i = 1; i <= (Integer.parseInt((String) ComboBoxProducers.getSelectedItem())); i++) {
					producers.add(new Producer (buffer,i,text));
				}
				
				for(int i = 1; i <= (Integer.parseInt((String) ComboBoxConsumers.getSelectedItem())); i++) {
					consumers.add(new Consumer (buffer,i,text));
				}
				isStarted = true;
				StartButton.setEnabled(false);
				StopButton.setEnabled(true);
				ComboBoxBufferSize.setEnabled(false);
				ComboBoxProducers.setEnabled(false);
				ComboBoxConsumers.setEnabled(false);
			}
			else 
			{
				for(Producer producer : producers)
				{
					producer.resumeSimulation();
				}
				for(Consumer consumer : consumers)
				{
					consumer.resumeSimulation();
				}
				StartButton.setEnabled(false);
				StopButton.setEnabled(true);
			}
		}
		
		if(src == StopButton)
		{
			for(Producer producer : producers)
			{
				producer.pauseSimulation();
			}
			for(Consumer consumer : consumers)
			{
				consumer.pauseSimulation();
			}
			StopButton.setEnabled(false);
			StartButton.setEnabled(true);
		}
		if(src == ResetButton)
		{
			this.dispose();
			ProducerConsumerWindow w = new ProducerConsumerWindow();
		}
		
		
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		Object src = e.getSource();
		
		
		if(src == MinProducerTimeSlider)
		{
			Producer.MIN_PRODUCER_TIME = MinProducerTimeSlider.getValue();
			MaxProducerTimeSlider.setMinimum(MinProducerTimeSlider.getValue());
			
		}
		if(src == MaxProducerTimeSlider)
		{
			Producer.MAX_PRODUCER_TIME = MaxProducerTimeSlider.getValue();
			MinProducerTimeSlider.setMaximum(MaxProducerTimeSlider.getValue());
		}
		if(src == MinConsumerTimeSlider)
		{
			Consumer.MIN_CONSUMER_TIME = MinConsumerTimeSlider.getValue();
			MaxConsumerTimeSlider.setMinimum(MinConsumerTimeSlider.getValue());
		}
		if(src == MaxConsumerTimeSlider)
		{
			Consumer.MAX_CONSUMER_TIME = MaxConsumerTimeSlider.getValue();
			MinConsumerTimeSlider.setMaximum(MaxConsumerTimeSlider.getValue());
		}
		
	}
	
	
	
	
	public static void main(String[] args){
		
		
		ProducerConsumerWindow w = new ProducerConsumerWindow();
		
		
	}

	
		
		
}
