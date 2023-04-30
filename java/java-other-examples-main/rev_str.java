import java.util.*;

//class hello{
//	public static void main(String args[]){
//		Scanner sc = new Scanner(System.in);
//
//		String str = sc.nextLine();
//		char st[] = str.toCharArray();
//		String rev = "";
//		for(int i=st.length-1;i>=0;i--){
//			rev += st[i];
//		}
//		System.out.println(rev);
//	}
//}

class hello{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);

		String str = sc.nextLine();
		StringBuilder sb = new StringBuilder(str);

		for(int i=0;i<sb.length()/2;i++){
			
			int front = i;
			int back = sb.length() - 1 - i;

			char frontChar = sb.charAt(front);
			char backChar = sb.charAt(back);

			sb.setCharAt(front, backChar);
			sb.setCharAt(back, frontChar);


		}
		System.out.println(sb);
	}
}
