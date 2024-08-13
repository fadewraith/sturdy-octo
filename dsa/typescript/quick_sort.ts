function quick_sort(arr: number[], low: number, high: number): number[] {
  if(low >= high) {
    return;
  }
  
  const pivotIndex: number = partition(arr, low, high);
  
  quick_sort(arr, low, pivotIndex - 1);
  quick_sort(arr, pivotIndex + 1, high);
}

function partition(arr: number[], low: number, high: number): number {
  
  const pivot: number = arr[high];
  let index: number = low - 1;
  
  for(let i = low; i < high; ++i) {
    if(arr[i] <= pivot) {
      index++;
      [arr[i], arr[index]] = [arr[index], arr[i]];
    }
  }
  
  index++;
  arr[high] = arr[index];
  arr[index] = pivot;
  
  return index;
}

// Test cases
function testQuickSort() {
  let arr1 = [3, 6, 8, 10, 1, 2, 1];
  quick_sort(arr1, 0, arr1.length - 1);
  console.log("Sorted array 1:", arr1); // [1, 1, 2, 3, 6, 8, 10]

  let arr2 = [1, 4, 3, 9, 7];
  quick_sort(arr2, 0, arr2.length - 1);
  console.log("Sorted array 2:", arr2); // [1, 3, 4, 7, 9]

  let arr3 = [];
  quick_sort(arr3, 0, arr3.length - 1);
  console.log("Sorted array 3:", arr3); // []

  let arr4 = [5, 5, 5, 5];
  quick_sort(arr4, 0, arr4.length - 1);
  console.log("Sorted array 4:", arr4); // [5, 5, 5, 5]

  let arr5 = [10, -1, 2, 5, 0, 6, 4, -5];
  quick_sort(arr5, 0, arr5.length - 1);
  console.log("Sorted array 5:", arr5); // [-5, -1, 0, 2, 4, 5, 6, 10]
}

testQuickSort();