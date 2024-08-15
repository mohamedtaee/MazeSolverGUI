package mazeSolver;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;


/**
 * Application that displays a grid of cells, allows the user to select a cell
 * as a start position, another cell as a end position, and then finds the
 * shortest path between those two cells. The user can also select addition
 * cells to be considered untraversable, meaning that the path from start to
 * will need to go around those cells and not through them.
 * 
 * @author Mohamed Mohamed
 * @author Anthony Barnes
 *
 */
@SuppressWarnings("serial")
public class MazeSolverGUI extends JFrame {
	Boolean rdbtnStartSet = false;
	Boolean rdbtnEndSet = false;
	Boolean rdbtnObstacleSet = false;
	Boolean rdbtnResetSet = false;
	private int rows = 15;
	private int cols = 24;
	JLabel[] gridLabels = new JLabel[rows*cols];
	Color greenLabel = new Color(0, 153, 0);
	Color redLabel = new Color(204, 0, 0);
	String gridToGraphReturn = "";
	

	private JPanel contentPane;
	private JPanel commandPanel;
	private JScrollPane pathScrollPane;
	private JTextPane txtPath;
	private JTextField txtStatus;
	private JCheckBox effectsBox;
	private JCheckBox gridNums;


	/**
	 * Constructor of class MazeSolverGUI. Creates the frame.
	 */
	public MazeSolverGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 750, 600);
		contentPane = new JPanel(new CardLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Welcome Panel
		ImageIcon welcomeImage = new ImageIcon(getClass().getResource("resources/welcome.png") );
		Image image = welcomeImage.getImage();
		Image newimg = image.getScaledInstance(
				(int) (welcomeImage.getIconWidth() * 1.3), 
				(int) (welcomeImage.getIconHeight() * 1.3),  
				java.awt.Image.SCALE_SMOOTH);
		welcomeImage = new ImageIcon(newimg);
		
		JPanel welcomePanel = new JPanel();
		contentPane.add(welcomePanel, "welcomePanel");
		
		welcomePanel.setLayout(new BorderLayout(0, 0));
		welcomePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setIcon(welcomeImage);
		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
		
		JPanel welcomeCmdPanel = new JPanel();
		welcomeCmdPanel.setLayout(new BorderLayout(0, 0));
		welcomePanel.add(welcomeCmdPanel, BorderLayout.SOUTH);
		

		JButton startBtn = new JButton("Let's Get Started!");
		welcomeCmdPanel.add(startBtn, BorderLayout.CENTER);
		
		JTextPane welcomeInstrPane = new JTextPane();
		welcomeInstrPane.setBorder(new LineBorder(greenLabel));
				
        String instructionsContent = "";
        InputStream is = getClass().getResourceAsStream("/mazeSolver/resources/instructions.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
            	instructionsContent += line + "\n";
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
		welcomeInstrPane.setText(instructionsContent);
		welcomeCmdPanel.add(welcomeInstrPane, BorderLayout.SOUTH);		
		
		// Centered solution textPane.
		StyledDocument welcomeInstrDoc = welcomeInstrPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		welcomeInstrDoc.setParagraphAttributes(0, welcomeInstrDoc.getLength(), center, false);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "tabbedPane");
		JPanel mazeInput = new JPanel();
		tabbedPane.addTab("Maze Input", null, mazeInput, null);
		mazeInput.setLayout(new BorderLayout(0, 0));
		
		commandPanel = new JPanel();
		mazeInput.add(commandPanel, BorderLayout.SOUTH);
		commandPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JRadioButton rdbtnStart = new JRadioButton("Start Point");
		rdbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnStartSet = true;
				rdbtnEndSet = false;
				rdbtnObstacleSet = false;
				rdbtnResetSet = false;
			}
		});
		rdbtnStart.setHorizontalAlignment(SwingConstants.CENTER);
		commandPanel.add(rdbtnStart);
		
		JRadioButton rdbtnEnd = new JRadioButton("End Point");
		rdbtnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnStartSet = false;
				rdbtnEndSet = true;
				rdbtnObstacleSet = false;
				rdbtnResetSet = false;
			}
		});
		rdbtnEnd.setHorizontalAlignment(SwingConstants.CENTER);
		commandPanel.add(rdbtnEnd);
		
		JRadioButton rdbtnObstacle = new JRadioButton("Obstacle");
		rdbtnObstacle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnStartSet = false;
				rdbtnEndSet = false;
				rdbtnObstacleSet = true;
				rdbtnResetSet = false;
			}
		});
		rdbtnObstacle.setHorizontalAlignment(SwingConstants.CENTER);
		commandPanel.add(rdbtnObstacle);
		
		JRadioButton rdbtnReset = new JRadioButton("Reset Square");
		rdbtnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnStartSet = false;
				rdbtnEndSet = false;
				rdbtnObstacleSet = false;
				rdbtnResetSet = true;
			}
		});
		rdbtnReset.setHorizontalAlignment(SwingConstants.CENTER);
		commandPanel.add(rdbtnReset);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnStart);
		buttonGroup.add(rdbtnEnd);
		buttonGroup.add(rdbtnObstacle);
		buttonGroup.add(rdbtnReset);
		
		// Make welcome panel disappear
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
		        cardLayout.show(contentPane, "tabbedPane");
				
			}
		});
		
		JButton btnSolve = new JButton("Solve Maze");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean startExists = false;
				boolean endExists = false;
				for (JLabel label : gridLabels) {
					if (label.getBackground() == greenLabel) {
						startExists = true;
					}
					if (label.getBackground() == redLabel) {
						endExists = true;
					}
				}
				
				if (startExists && endExists) {
					GridToGraph graph = new GridToGraph(gridLabels);
					Graph G = graph.convert(rows, cols);
					MazeBFSToGrid BFS = new MazeBFSToGrid(gridLabels, G);
					
					for (JLabel label : gridLabels) {
						Color currentLblClr = label.getBackground();
						if (currentLblClr != greenLabel 
								&& currentLblClr != redLabel 
								&& currentLblClr != Color.black) {
							label.setBackground(Color.white);
						}
					}
					
					Stack<Integer> shortestPath = (Stack<Integer>) BFS.shortestPath();
					if (shortestPath == null) {
						txtStatus.setText(
								"Path from start to end does not exist.");
					}
					else {
						
						// Animation feature
						int n = shortestPath.size();
						int delay = (n > 17) ? (3500 / n) : 200;
						if (!effectsBox.isSelected()) {
							delay = 0;
						}
						Timer pathTimer = new Timer(delay, new ActionListener() {
							int pathCount = 0;
							@Override
							public void actionPerformed(ActionEvent e) {
								int i = shortestPath.pop();
								Color currentLblClr = gridLabels[i].getBackground();
								if (currentLblClr != greenLabel 
										&& currentLblClr != redLabel) {
									gridLabels[i].setBackground(Color.yellow);
								}
								pathCount++;
								
								if (pathCount == n) {
									((Timer)e.getSource()).stop();
								}
							}
						});
						pathTimer.start();
						
						txtPath.setText(BFS.toString());
						txtStatus.setText("Maze Solved!");
						txtPath.setCaretPosition(0);
					}
				}
				else if (!startExists && endExists) {
					txtStatus.setText("Start square missing.");
				}
				else if (startExists && !endExists) {
					txtStatus.setText("End square missing.");
				}
				else if (!startExists && !endExists) {
					txtStatus.setText("Start & End square missing.");
				}
			}
		});
		commandPanel.add(btnSolve);
		
		// Added reset button.
		JButton btnReset = new JButton("Reset Maze");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonGroup.clearSelection();
				for (JLabel label : gridLabels) {
					label.setBackground(Color.white);
				}
				txtStatus.setText("Welcome to MazeSolver");
				txtPath.setText("");
			}
		});
		commandPanel.add(btnReset);
		
		JPanel gridPanel = new JPanel();
		mazeInput.add(gridPanel);
		gridPanel.setLayout(new GridLayout(rows, cols, 0, 0));
		
		JPanel extrasTab = new JPanel();
		tabbedPane.addTab("Extras", null, extrasTab, null);
		extrasTab.setLayout(new GridLayout());
		
		txtPath = new JTextPane();
		txtPath.setText("Waiting for solution...");
		txtPath.setEditable(false);
		
		// Centered solution textPane.
		StyledDocument doc = txtPath.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		pathScrollPane = new JScrollPane(txtPath);


		extrasTab.add(pathScrollPane);
		
		JPanel extrasCmdPanel = new JPanel();
		extrasCmdPanel.setLayout(new GridLayout(10, 1));
		
		// Centered solution textPane.
		JLabel extrasCmdTitle = new JLabel("Settings");
		extrasCmdTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		extrasCmdTitle.setHorizontalAlignment(SwingConstants.CENTER);
		extrasCmdPanel.add(extrasCmdTitle);

		
		// Settings items
		effectsBox = new JCheckBox("Enable Visual Effects");
		effectsBox.setBorder(new EmptyBorder(0, 20, 0, 0));
		effectsBox.setSelected(true);
		
		gridNums = new JCheckBox("Display Grid Numbers");
		gridNums.setBorder(new EmptyBorder(0, 20, 0, 0));
		gridNums.setSelected(false);
		
		JButton applySettings = new JButton("Apply Settings");
		applySettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < gridLabels.length; i++) {
					if (gridNums.isSelected()) {
						gridLabels[i].setText("" + (i + 1));
					}
					else {
						gridLabels[i].setText("");
					}
				}
			}
		});

		extrasCmdPanel.add(effectsBox);
		extrasCmdPanel.add(gridNums);
		extrasCmdPanel.add(applySettings);
		extrasTab.add(extrasCmdPanel);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		txtStatus.setText("Welcome to MazeSolver");
		txtStatus.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(txtStatus, BorderLayout.NORTH);
		
		// Create grid
		for (int i = 0; i< gridLabels.length; i++) {
			gridLabels[i] = new JLabel("");
			gridLabels[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			gridPanel.add(gridLabels[i]);
			gridLabels[i].setOpaque(true);
			gridLabels[i].setBackground(Color.WHITE);
			gridLabels[i].addMouseListener(new LabelMouseListener());
		}
		
	}
	
	// Mouse listener for the labels
    private class LabelMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
        	JLabel gridLabel = (JLabel) e.getSource();
        	if (rdbtnStartSet) {
            	for (JLabel g : gridLabels) {
            		if (g.getBackground() == greenLabel) {
            			g.setBackground(Color.WHITE);
            		}
            	}
            	gridLabel.setBackground(greenLabel);
            }
            else if (rdbtnEndSet) {
            	for (JLabel g : gridLabels) {
            		if (g.getBackground() == redLabel) {
            			g.setBackground(Color.WHITE);
            		}
            	}
            	gridLabel.setBackground(redLabel);
            }
            else if (rdbtnObstacleSet) {
            	gridLabel.setBackground(Color.BLACK);
            }
            else if (rdbtnResetSet) {
            	gridLabel.setBackground(Color.WHITE);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
            	JLabel gridLabel = (JLabel) e.getSource();
            	if (rdbtnStartSet) {
                	for (JLabel g : gridLabels) {
                		if (g.getBackground() == greenLabel) {
                			g.setBackground(Color.WHITE);
                		}
                	}
                	gridLabel.setBackground(greenLabel);
                }
                else if (rdbtnEndSet) {
                	for (JLabel g : gridLabels) {
                		if (g.getBackground() == redLabel) {
                			g.setBackground(Color.WHITE);
                		}
                	}
                	gridLabel.setBackground(redLabel);
                }
                else if (rdbtnObstacleSet) {
                	gridLabel.setBackground(Color.BLACK);
                }
                else if (rdbtnResetSet) {
                	gridLabel.setBackground(Color.WHITE);
                }
            }
        }
    }
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeSolverGUI frame = new MazeSolverGUI();
					frame.setMinimumSize(new Dimension(750, 600));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
