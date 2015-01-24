
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

public class MainFrame extends JFrame{
	private static int WIDTH=400;
	private static int HEIGHT=500;
	//�仯
	private Vector<String> expression=new <String>Vector();
	private Vector<String> newexpression=new <String>Vector();
	
	private double result=0.0;
	private final String strInPanel1[]={"7","8","9","+","4","5","6","-","1","2","3","*",
			"(","0",")","/"};
	private final String strInPanel2[]={"Clear","Back"};
	private final String strInPanel3[]={"sin","cos","tan","开方","pow","lg"};
	JButton buttonInPanel1[]=new JButton[strInPanel1.length];
	JButton buttonInPanel2[]=new JButton[strInPanel2.length]; 
	JButton buttonInPanel3[]=new JButton[strInPanel3.length]; 
	private JTextField textField;
	private JButton equalButton;
	private JButton dotButton;
	private JButton piButton;
	
	public MainFrame(){
		int  i;
		JPanel Panel1=new JPanel(new GridLayout(4 , 4));//��4*4�����panel
		Panel1.setBounds(10, 220, 360, 200);
		//Panel1.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel1"));
		for(i=0;i<strInPanel1.length;i++){
			buttonInPanel1[i]=new JButton(strInPanel1[i]);	
			Panel1.add(buttonInPanel1[i]);
		}
		
		JPanel Panel2=new JPanel(new GridLayout(1, 1));
		Panel2.setBounds(10, 85, 160, 40);
		//Panel2.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel2"));
		for(i=0;i<strInPanel2.length;i++){
			buttonInPanel2[i]=new JButton(strInPanel2[i]);
			Panel2.add(buttonInPanel2[i]);
		}
		
		JPanel Panel3=new JPanel(new GridLayout(2, 3));
		Panel3.setBounds(180,85, 180, 125);
		//Panel3.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel3"));
		for(i=0;i<strInPanel3.length;i++){
			buttonInPanel3[i]=new JButton(strInPanel3[i]);
			Panel3.add(buttonInPanel3[i]);
		}
		
		JPanel Panel4=new JPanel(new GridLayout(1, 1));
		Panel4.setBounds(10, 125, 160, 84);
		//Panel4.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel4"));
		equalButton=new JButton("=");
		dotButton=new JButton(".");
		piButton=new JButton("��");
		Panel4.add(equalButton);
		Panel4.add(dotButton);
		Panel4.add(piButton);
		
		JPanel Panel5=new JPanel();
		Panel5.setBounds(10, 30, 300, 30);
		//Panel5.setBorder((Border) new TitledBorder(new EtchedBorder(), "panel5"));
		//textField=new JTextField(32);
		//textField.setSize(40, 50);
		textField=new JTextField();
		textField.setPreferredSize(new Dimension(320,60));//����textfield�߶ȣ�HEIGHT���ķ�����
		Panel5.add(textField);
		
		Container con=getContentPane();
		con.add(Panel1);
		con.add(Panel2);
		con.add(Panel3);
		con.add(Panel4);
		con.add(Panel5);
		setTitle("��ѧ������");
		setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ActionListener showText=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//�ı����ȡ��������ݣ�����ʾ�ڿ���
				expression.add(((JButton)e.getSource()).getText());
				textField.setText(textField.getText()+ ((JButton)e.getSource()).getText());
				//expression=textField.getText();
			}
		};
		//���ּ����������������Ҫ���     (ActionListener)showText �ļ���Ч��
		for(i=0;i<strInPanel1.length;i++){
			buttonInPanel1[i].addActionListener(showText);	
		}
		//С���ͦ�
		dotButton.addActionListener(showText);
		piButton.addActionListener(showText);
		
		for(i=0;i<strInPanel3.length;i++){
			buttonInPanel3[i].addActionListener(showText);
		}
		//clear
		buttonInPanel2[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				result=0;
				expression.clear();
				newexpression.clear();
				textField.setText("");
				//textField.setText(String.valueOf(result));
			}
		});
		//Back
		buttonInPanel2[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int tmp=expression.size();
				expression.remove(tmp-1);
				textField.setText(String.valueOf(expression));
			}
			
		});
		//�Ⱥ�
		equalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				newexpression=Trans(expression);
				result=calculate(newexpression);
				textField.setText("��׺"+expression+"  ��׺"+newexpression+"   ���"+result);	
			}
		}
		);
	}
	//��MainFrame�е�expression��׺���ʽתΪ��׺
	public Vector Trans(Vector<String> oldExp){
		Vector <String> newExp=new <String>Vector();
		int  i=0;
		Stack<String> s =new Stack<String>();//char ch = �ǻ�����char���ͣ���s.peek()���ص���object����
		//test
		//if(oldExp==null) 
			//return "�յ�";
		while(i<oldExp.size()){
			switch(oldExp.elementAt(i)){//String�и���±�ȡ�ַ�ķ���,��ע��Stack����String���͵�
			case "(":
				s.push(oldExp.elementAt(i));
				i++;
				break;
			case ")":
				while((!s.empty())&&s.peek()!="("){//����'('
					newExp.add(s.pop());
				}
				while(!s.empty()){
					if(s.peek()=="(")
						s.pop();//��'('��ջ��ɾ��
					else break;
				}
				i++;
				break;
			
			//�ȵ�ջ����ֱ��ջ���ȱ�elementAt(i)�����ȼ�С
			// >  /*  >  +-  >  
			 case"+":
	         case"-":
	        	 while(!s.empty()&&s.peek()!="("){
						newExp.add(s.pop());
	        	 }
	        	s.push(oldExp.elementAt(i));
				i++;
				break;
	         case"*":
	         case"/":
	        	 if((!s.empty())&&(s.peek()!="+"&&s.peek()!="-")&&s.peek()!="("){
	        		 	newExp.add(s.pop());
	        	 }
	             s.push(oldExp.elementAt(i));
	             i++;
	             break;
	         case "sin":
	         case "cos":
	         case "tan":
	         case "开方":
	         case "lg":
	        	 if(!s.empty()&&s.peek()!="+"&&s.peek()!="-"&&s.peek()!="*"&&s.peek()!="/"&&s.peek()!="("){
	        		 newExp.add(s.pop());
	        	 }
	        	 s.push(oldExp.elementAt(i));
	             i++;
	         
	         case " ":
	        	 break;
	         default:
                 while((i<oldExp.size())&&((oldExp.elementAt(i).toCharArray()[0]<='9'&&
                		 	oldExp.elementAt(i).toCharArray()[0]>='0')||
                		oldExp.elementAt(i).toCharArray()[0]=='.'||
                		oldExp.elementAt(i).toCharArray()[0]=='#')){
                	 newExp.add(oldExp.elementAt(i));
                	 i++;
                  }
                  newExp.add("#");//������һ�����ֵ��ַ�ָ��������Լ�����ж�λ���ֵı��ʽ
                  break;
			}
		}
		while(!s.empty()) {
	              newExp.add(s.pop());
	       }
		return newExp;
	}
	
	//��ݺ�׺���ʽ����
	public double calculate(Vector<String> newExp){
		Stack<Double> s=new <Double>Stack();
	    int i=0,k=0;
	    double a,b,c;
	    double d;
	    while(i<newExp.size()){
	        switch(newExp.elementAt(i)){
	        		case"+":
	                        a=s.pop();
	                        b=s.pop();
	                        c=b+a;
	                        s.push(c);//���浽ջ��
	                        break;

	                case"-":
		                	a=s.pop();
	                        b=s.pop();
	                        c=b-a;
	                        s.push(c);//���浽ջ��
	                        break;
	                case"*":
		                	a=s.pop();
	                        b=s.pop();
	                        c=b*a;
	                        s.push(c);//���浽ջ��
	                        break;
	                case"/":
		                	a=s.pop();
	                        b=s.pop();
	                        if(a==0){
	                        	
	                        }
	                        c=b/a;
	                        s.push(c);
	                        break;
	                case"sin":
	                		a=s.pop();
	                		c=Math.sin(a);
	                		s.push(c);
	                		break;
	                case"cos":
                		a=s.pop();
                		c=Math.cos(a);
                		s.push(c);
                		break;
	                case"tan":
                		a=s.pop();
                		c=Math.tan(a);
                		s.push(c);
                		break;
	                case"开方":
                		a=s.pop();
                		c=Math.sqrt(a);
                		s.push(c);
                		break;
	                case"lg":
                		a=s.pop();
                		c=Math.log10(a);
                		s.push(c);
                		break;	
	                default:
	                       /*----------------------------------------------------------------
	                       �ַ����ֵ��㷨ʵ�֣�
	                       1.d=10*d+cNew[j]-'0';��������ʵ�ְ��λ�����ֵļ��㣬�磺56+1
	                       2.����С���뷨�ǣ�������С���ʱ�����Թ�d��ֵ����10�����󣬲���
	                         Ҫ��¼��С��㵽��һ����#����λ��k������ֵҪ����pow(10,k)����d���С��
	                       -----------------------------------------------------------------*/
	                        d=0;
	                        if(newExp.elementAt(i)=="��"){
	                        	d=Math.PI;
	                        	s.push(d);
	                        	i++;
	                        	break;
	                        }
	                        while((i<newExp.size())&&((newExp.elementAt(i).toCharArray()[0]<='9')&&
	                       		 (newExp.elementAt(i).toCharArray()[0])>='0')||
	                       		 newExp.elementAt(i).toCharArray()[0]=='.'){
	                                if(newExp.elementAt(i)==".")
	                                {
	                                    i++;
	                                    k=i;//��j��ֵ����
	                                    while(newExp.elementAt(k)!="#")
	                                        k++;
	                                    k=k-i;//���ߵĲ�ΪС�����λ��
	                                }
	                                d=10*d+newExp.elementAt(i).toCharArray()[0]-'0';//�ַ����ֵ�ת��
	                           
	                                i++;
	                        }
	                        d=d/Math.pow(10, k);
	                        k=0;
	                        s.push(d);
	                        break;

	                }
	                i++;
	    }

	    return s.peek();
	}
	
	public static void main(String args[]){
		new MainFrame();
	}
	
}