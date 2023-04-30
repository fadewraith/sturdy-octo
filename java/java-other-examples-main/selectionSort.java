//selection sort - in this sorting techinqiue what we do is, we consider first element index as min element index
//& then we iterate in an array for next minimum element & if we found, then we replace the current element index with that
//smallest elment index & then we interchage the values

class selectionSort{
	public static void main(String args[]){

		int arr[] = {9,8,7,6,5,4,3,2,1,0};

		for(int i=0;i<arr.length-1;i++){

			int min_indx = i;

			for(int j=i+1;j<arr.length;j++){

				if(arr[j] < arr[min_indx]){
					 min_indx = j;					

				}
			}
			int temp = arr[min_indx];
			arr[min_indx] = arr[i];
			arr[i] = temp;
		}


		System.out.print("selection sort: ");
		for(int i: arr){
			System.out.print(i+" ");
		}

		System.out.println();
	}
}