

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookService {

	/*
	 * ( o1, o2) -> o2.getName().compareTo(o1.getName());
	 * for customs sorting, we are using comparator
	 */
	public List<Book> getBooksinSort() {
		List<Book> books = new BookDAO().getBooks();
		// below is the lambda approach
		Collections.sort(books, (o1, o2) -> o1.getName().compareTo(o2.getName()));
//		Collections.sort(books, new MyComparator()); // custom sorting
		// below is the anonymous implementation of Comparator which is our functional interface
//		Collections.sort(books, new Comparator<Book>() {
//			public int compare(Book o1, Book o2) {
//				return o2.getName().compareTo(o1.getName());
//			}
//			
//		});
		return books;
	}

	public static void main(String[] args) {
		System.out.println(new BookService().getBooksinSort());
	}
}

//
//class MyComparator implements Comparator<Book> {
// 
//	public int compare(Book o1, Book o2) {
//		return o2.getName().compareTo(o1.getName());
////		return o1.getName().compareTo(o2.getName());
//	}
//
//}