/*
  merge sort - 
  time complexity - first one is logn
    second part contains while loop and its linear - Big(O) = O(nlogn)
    divide the array into sub arrays, each containing only one element(an array with one element is considered sorted)
    
    repeatedly merge  the sub arrays to produce new sorted sub arrays until there is only one sub array remaining, that will be the sorted array
*/

function mergeSort(arr) {
  if(arr.length < 2)
    return arr;
  const mid = Math.floor(arr.length / 2);
  const leftArr = arr.slice(0, mid);
  const rightArr = arr.slice(mid);
  // left and right arryas may contain more than 1 element, dividing till it contains one array
  return merge(mergeSort(leftArr), mergeSort(rightArr));
}

function merge(leftArr, rightArr) {
  const sortedArr = [];
  // checking  both the arrays are not empty and if one of the array is empty, we exit the loop
  while(leftArr.length && rightArr.length) {
    if(leftArr[0] <= rightArr[0]) {
      sortedArr.push(leftArr.shift())
    } else {
      sortedArr.push(rightArr.shift())
    }
  }
  return [...sortedArr, ...leftArr, ...rightArr]
}

console.log(mergeSort([8, 20, -2, 4, -6]));