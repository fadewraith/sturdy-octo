import java.util.*;

class ite_rator{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		ArrayList<String> al = new ArrayList<String>();
		String name;
		for(int i=0;i<5;i++){
			name = sc.nextLine();
			al.add(name);
		}
		// for(String i:al)
		// System.out.println(i);

		Iterator itr = al.iterator();
		while(itr.hasNext()){
			System.out.print(itr.next()+" ");
		}
		System.out.println();
	}
}