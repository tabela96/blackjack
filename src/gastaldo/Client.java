package gastaldo;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;

public class Client {

	protected Shell shlBlackjack;
	private Socket s;
	private List list;
	private int carta1;
	private int carta2;
	private int somma;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client window = new Client();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws IOException 
	 */
	public void open() throws IOException {
		Display display = Display.getDefault();
		createContents();
		shlBlackjack.open();
		shlBlackjack.layout();
		vai();
		while (!shlBlackjack.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBlackjack = new Shell();
		shlBlackjack.setSize(242, 107);
		shlBlackjack.setText("BlackJack");
		
		list = new List(shlBlackjack, SWT.BORDER);
		list.setBounds(10, 10, 37, 42);
		
		Label lblEsito = new Label(shlBlackjack, SWT.NONE);
		lblEsito.setBounds(74, 44, 55, 15);

		
		Button btnCarta = new Button(shlBlackjack, SWT.NONE);
		btnCarta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PrintWriter out=new PrintWriter(s.getOutputStream(), true);
					out.println("Carta");
					s.getOutputStream().write(carta1);
					s.getOutputStream().write(carta2);
					somma=s.getInputStream().read();
					InputStreamReader isr=new InputStreamReader(s.getInputStream());
					BufferedReader in=new BufferedReader(isr);
					if(somma>21){
						lblEsito.setText("Hai perso");
					}else{
						lblEsito.setText("Continua");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCarta.setBounds(64, 9, 75, 25);
		btnCarta.setText("Carta");
		
		Button btnResta = new Button(shlBlackjack, SWT.NONE);
		btnResta.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					PrintWriter out=new PrintWriter(s.getOutputStream(), true);
					out.println("Resta");
					System.out.println(somma);
					s.getOutputStream().write(carta1);
					s.getOutputStream().write(carta2);
					s.getOutputStream().write(somma);
					if(s.getInputStream().read()>21 || s.getInputStream().read()<somma){
						lblEsito.setText("Hai vinto");
					}else{
						lblEsito.setText("Hai perso");
					}
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnResta.setBounds(145, 9, 75, 25);
		btnResta.setText("Resta");
		
	}
	
	public void vai() throws IOException{
		s=new Socket("localhost", 9999);
		PrintWriter out=new PrintWriter(s.getOutputStream(), true);
		out.println("carte");
		carta1=s.getInputStream().read();
		carta2=s.getInputStream().read();
		list.add(String.valueOf(carta1));
		list.add(String.valueOf(carta2));
	}
}
