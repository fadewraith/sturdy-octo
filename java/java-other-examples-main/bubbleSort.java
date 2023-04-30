//bubble sort - in this we compare each element with adjacent & if its larger than immediate next. we replace it at each step
//until the largest one reaches at the end & in this way elements are sorted in ascending order

class bubbleSort{
	public static void main(String args[]){

		int arr[] = {9,8,7,6,5,4,3,2,1,0};

		for(int i=0;i<arr.length-1;i++){

			for(int j=i+1;j<arr.length;j++){

				if(arr[i] > arr[j]){

					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;

				}
			}
		}

		System.out.print("Bubble sorted: ");
		for(int i: arr){
			System.out.print(i+" ");
		}

		System.out.println();
	}
}