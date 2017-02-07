package gastaldo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String[] args) throws IOException {
		int cartaRic1;
		int cartaRic2;
		ServerSocket ss = new ServerSocket(9999);
		Socket s = ss.accept();
		InputStreamReader isr = new InputStreamReader(s.getInputStream());
		BufferedReader in = new BufferedReader(isr);
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		if (in.readLine().equals("carte")) {
			int carta1 = ((int) (Math.random() * 10)+1);
			int carta2 = ((int) (Math.random() * 10)+1);
			s.getOutputStream().write(carta1);
			s.getOutputStream().write(carta2);
		}
		if (in.readLine().equals("Carta")) {
			cartaRic1 = s.getInputStream().read();
			cartaRic2 = s.getInputStream().read();
			int altracarta = ((int) (Math.random() * 10)+1);
			System.out.println(cartaRic1 + cartaRic2 + altracarta);
			if (cartaRic1 + cartaRic2 + altracarta <= 21) {
				s.getOutputStream().write(cartaRic1 + cartaRic2 + altracarta);
				out.println("Continua a giocare");
				altracarta=0;
			} else {
				out.println("Hai perso");
			}
		}
		if(in.readLine().equals("Resta")){
			int carta1svr=((int) (Math.random() * 10)+1);
			int carta2svr=((int) (Math.random() * 10)+1);
			int carta3svr=((int) (Math.random() * 10)+1);
			int sommasvr=0;
			if(carta1svr+carta2svr<=16){
				sommasvr=carta1svr+carta2svr+carta3svr;
				System.out.println(sommasvr);
				s.getOutputStream().write(sommasvr);
			}
		}

	}

}
