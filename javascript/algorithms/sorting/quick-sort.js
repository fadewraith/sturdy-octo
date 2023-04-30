/*
  Quck sort - 
    worst case - O(n^2) - happens when the array is already sorted, instead of partitioning the arrays into smaller arrays, we end up partitioning into an empty array and a full array, and end up comparing with every other element and that is the quadratic time complexity
    avg case - O(nlogn) - recursively divide the array, i.e. logn and  have a for loop which is O(n), so its O(nlogn)
  Identify the pivot element in the array
     - pick first or last or a random or median as pivot
  put everything that's smaller than  the pivot into a 'left' array and everything that's greater than the pivot into a 'right' array.
  repeat the process for the individual 'left' and 'right' arrays  till we have an  array of length 1, which is sorted by definition
  repeatedly  concatenate the left array, pivot and right array till one sorted array remains
*/

function quickSort(arr) {
  // base case
  if(arr.length < 2)
    return arr;
  // taking pivot as last element
  let pivot = arr[arr.length - 1];
  let left = [];
  let right = [];
  for(let i=0;i<arr.length-1;i++) {
    if(arr[i] < pivot)
      left.push(arr[i]);
    else
      right.push(arr[i]);
  }
  return [...quickSort(left), pivot, ...quickSort(right)];
}

console.log(quickSort([-6, 20, 8, -2, 4]));