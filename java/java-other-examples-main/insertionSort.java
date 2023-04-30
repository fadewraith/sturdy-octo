// Insertion sort is a simple sorting algorithm that works similar to the way you sort playing cards in your hands. 
// The array is virtually split into a sorted and an unsorted part. Values from the unsorted part are picked and placed 
// at the correct position in the sorted part.
// 	Algorithm 
// To sort an array of size n in ascending order: 
// 1: Iterate from arr[1] to arr[n] over the array. 
// 2: Compare the current element (key) to its predecessor. 
// 3: If the key element is smaller than its predecessor, compare it to the elements before. Move the greater
//  elements one position up to make space for the swapped element.

class insertionSort{
	public static void main(String args[]){

		int arr[] = {9,8,7,6,5,4,3,2,1,0};

		for(int i=1;i<arr.length;i++){

			int current = arr[i];
			int j = i-1;

			while(j>=0 && current < arr[j]){
				arr[j+1] = arr[j];
				j--;
			}

			arr[j+1] = current;
		}


		System.out.print("insertion sort: ");
		for(int i: arr){
			System.out.print(i+" ");
		}

		System.out.println();
	}
}