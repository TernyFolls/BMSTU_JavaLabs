import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class BoggleBoard extends JFrame implements ActionListener {

	private TreeSet<String> words;
	private final int WIDTH = 275;
	private final int HEIGHT = 488;
	private static JTextArea currentWord;
	private boolean newWord;
	private JButton[] buttons;
	private boolean[][] selected;
	private boolean[][] lastSelect;
	private int points;
	private int curPoint;
	private int len;
	private ArrayList<String> wordsFound;
	private JTextArea pointCnt;
	private JPanel content;
	private Timer time;
	private double curTime;
	private JTextArea clock;
	private char[] charFrequency;
	
    public BoggleBoard() throws FileNotFoundException {
    	addFrequencies();
    	content = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	content.setOpaque(true);
        content.setBackground(Color.WHITE);
        content.setLayout(null);
        
        words = Dictionary.getWords("words.txt");
        
        currentWord = new JTextArea();
        currentWord.setEditable(false);
        clock = new JTextArea();
        clock.setSize(50,20);
        clock.setLocation(WIDTH/2-15, 50);
        clock.setBackground(Color.yellow);
        clock.setEditable(false);
       
        makeCharacterButtons();
        
        pointCnt = new JTextArea("Счёт: "+points);
        pointCnt.setSize(WIDTH/2, 30);
        pointCnt.setLocation(WIDTH/2, 295);
        pointCnt.setBackground(Color.lightGray);
        content.add(pointCnt);
         
         currentWord.setSize(WIDTH/2, 30);
         currentWord.setLocation(0,295);
         currentWord.setBackground(Color.cyan);
         String htmlButton = "<html><p style=\"text-align: center;\"><span style=\"color:#ffffff;\"><span style=\"font-family: verdana,geneva,sans-serif;\"><code><span style=\"background-color: rgb(255, 204, 0);\">Ввести слово</span></code></span></span></p>";
		JButton submit = new JButton(htmlButton);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(curTime>0)
				resetWord();
			}
		});
		submit.setLocation(0, 330);
		submit.setSize(WIDTH, 80);
		submit.setBackground(Color.orange);
		content.add(submit);
        JButton bottom = new JButton("***  Новая  игра  ***");
        bottom.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setUpNewGame();
        	}
        });
        
        bottom.setSize(WIDTH, 45);
        bottom.setLocation(0, 0);
        bottom.setBackground(Color.blue);

        JButton endBtn = new JButton("Закончить");
        endBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
			curTime = 0.0;
        	}
        });
        endBtn.setSize(WIDTH, 40);
        endBtn.setLocation(0, 410);
        endBtn.setBackground(Color.red);


        
        content.add(bottom, BorderLayout.PAGE_END);
        content.add(currentWord);
        content.add(clock);
	content.add(endBtn, BorderLayout.PAGE_END);
        setContentPane(content);
        setSize(WIDTH, HEIGHT);
        setLocationByPlatform(true);
        setVisible(true);
        time = new Timer(100, this);



        setUpNewGame();
    }
    

    public void addFrequencies() {
    	charFrequency = new char[]{'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e',
                                   'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 't',
                                   't', 't', 't', 't', 't', 't', 't', 't', 't', 't',
                                   't', 't', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a',
                                   'a', 'a', 'a', 'a', 'r', 'r', 'r', 'r', 'r', 'r',
                                   'r', 'r', 'r', 'r', 'r', 'r', 'i', 'i', 'i', 'i', 
                                   'i', 'i', 'i', 'i', 'i', 'i', 'i', 'n', 'n', 'n', 
                                   'n', 'n', 'n', 'n', 'n', 'n', 'n', 'n', 'o', 'o', 
                                   'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 's', 
                                   's', 's', 's', 's', 's', 's', 's', 's', 'd', 'd', 
                                   'd', 'd', 'd', 'd', 'c', 'c', 'c', 'c', 'c', 'h', 
                                   'h', 'h', 'h', 'h', 'l', 'l', 'l', 'l', 'l', 'f', 
                                   'f', 'f', 'f', 'm', 'm', 'm', 'm', 'p', 'p', 'p', 
                                   'p', 'u', 'u', 'u', 'u', 'g', 'g', 'g', 'y', 'y', 
                                   'y', 'w', 'w', 'b', 'j', 'k', 'q', 'v', 'x', 'z' }; 
    	}
    
    
    public void makeCharacterButtons() {
    	  buttons = new JButton[16];
          int index = 0;
          for(int x=0; x<4; x++) {
         	 for(int y=0; y<4; y++) {
         		 buttons[index] = new JButton("");
         		 buttons[index].setSize(49, 49);
         		 buttons[index].setLocation(30 + x*54, 76 + y*54);
         		 content.add(buttons[index]);
         		 JButton current = buttons[index];
         		 int xpos = x;
         		 int ypos = y;
         		 current.addActionListener(new ActionListener() {
         			 public void actionPerformed(ActionEvent e) {
         				 if(canSelect(xpos+1, ypos+1)) {
         					currentWord.append(current.getText());
         				 	current.setBackground(Color.cyan);
         				 }
         			 }
         		 });
         		 index++;
         	 }
          }
    }
    
    public void setUpNewGame() {
    	time.start();
    	curTime = 180.0;
    	wordsFound = new ArrayList<String>();
		newButtons();
		points = 0;
		newWord = true;
        selected = new boolean[6][6];
        lastSelect = new boolean[6][6];
		resetAllColors();
		currentWord.setText("");
		pointCnt.setText("Счёт: " + points);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		curTime-=.10;
		clock.setText(String.format("%.1f", curTime));
		if(curTime<0) {
			time.stop();
			endGame();
		}
		
	}
	
	public void endGame() {
		JPanel gameover = new JPanel();
		gameover.setOpaque(true);
        gameover.setBackground(Color.WHITE);
        gameover.setLayout(null);
		JTextArea block = new JTextArea();
		block.setSize(HEIGHT, WIDTH/4);
		block.setLocation(0,0);
		block.setText("\n                    Итоговый " + pointCnt.getText() + "\n\n                     Играть ещё раз?");
		block.setEditable(false);
		block.setBackground(Color.yellow);
		JButton yes = new JButton("yes");
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.setVisible(true);
				gameover.setVisible(false);
				setContentPane(content);
				setUpNewGame();
				return;
			}
		});
		JButton no = new JButton("no");
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		yes.setSize(WIDTH/4,40);
		no.setSize(WIDTH/4,40);
		yes.setLocation(50,HEIGHT/6);
		no.setLocation(150,HEIGHT/6);
		setContentPane(gameover);
		gameover.add(block);
		gameover.add(yes);
		gameover.add(no);
		gameover.setVisible(true);
	}
	
	public void resetWord() {
		String word = currentWord.getText();
		newWord = true;
		selected = new boolean[6][6];
		lastSelect = new boolean[6][6];
		if(words.contains(word.toLowerCase())&&(word.length()>2)) {
			if((!wordsFound.contains(word))) {
				wordsFound.add(word);
				adjustPoints(word.toLowerCase());
				pointCnt.setText("Счёт: "+points);
			}
		}
		currentWord.setText("");
		resetAllColors();
	}
	
	public void adjustPoints(String word) {
		len = word.length();
		ArrayList<Character> rareChar = new ArrayList<Character>(Arrays.asList('q'));
		for(int x=0; x<word.length(); x++) {
			char cur = word.charAt(x);
			if(rareChar.contains(cur)) {
				len++;
			}
		}
		switch(len) {
			case 3: points += len; break;
			case 4: points += len; break;
			case 5: points += len*2; break;
			case 6: points += len*3; break;
			case 7: points += len*5; break;
			default: points += len*11; break;
		
		}
	}

	public void resetAllColors() {
		for(int x=0; x<16; x++) {
			buttons[x].setBackground(Color.white);
		}
	}
	private final int[][] pos = { {1,1}, {0,1}, {1,0}, {-1,-1}, {-1,1}, {1,-1}, {0,-1}, {-1,0} };
	
	public boolean canSelect(int xpos, int ypos) {
		if(selected[xpos][ypos]) {
			return false;
		}
		if(newWord) {
			newWord = false;
			selected[xpos][ypos] = true;
			lastSelect[xpos][ypos] = true;

			return true;
		}
		boolean adjSel = false;
		for(int x=0; x<pos.length; x++) {
			int newxpos = pos[x][0] + xpos;
			int newypos = pos[x][1] + ypos;
			if(lastSelect[newxpos][newypos]) {
				lastSelect[newxpos][newypos] = false;
				adjSel = true;
				selected[xpos][ypos] = true;
				lastSelect[xpos][ypos] = true;
			}
		}
		return adjSel;
	}
	
	public void newButtons() {
		Random ran = new Random();
		for(int x=0; x<16; x++) {
			int newCharIndex = ran.nextInt(charFrequency.length);
			String newChar = ""+charFrequency[newCharIndex];
			buttons[x].setText(""+newChar.toUpperCase());
		}
	}
	

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
			new BoggleBoard();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
            }
        });
    }
}
