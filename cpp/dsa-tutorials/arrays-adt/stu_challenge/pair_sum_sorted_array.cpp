#include<iostream>
using namespace std;

int main () {

  int arr[] = { 1, 3, 4, 5, 6, 8, 9, 10, 12, 14 };
  int k = 10;

  int length = sizeof (arr) / sizeof (arr[0]);

  int i = 0, j = length - 1;
  // using while loop
  while (i < j) {
      if (arr[i] + arr[j] == k) {
          cout << arr[i] << " + " << arr[j] << " = " << k << endl;
          i++;
          j--;
	  }
      if (arr[i] + arr[j] < k) {
          i++;
      } else if (arr[i] + arr[j] > k) {
          j--;
      }

  }

  // using for loop
//   for (i = 0, j = length - 1; i < j;)
//     {
//       if (arr[i] + arr[j] == k)
// 	{
// 	  cout << arr[i] << " + " << arr[j] << " = " << k << endl;
// 	  i++;
// 	  j--;
// 	}
//       if (arr[i] + arr[j] < k)
// 	{
// 	  i++;
// 	}
//       else if (arr[i] + arr[j] > k)
// 	{
// 	  j--;
//
    // }

  return 0;
}

