/*
  Cartesian Product - 
  here it is not quadratic, it depends on the length of 2 different arrays and if length are equal, then it is of O(n^2)
  time complexity - O(mn)
*/

function cartesianProduct(arr1, arr2) {
  const result = [];
  for(let i=0;i<arr1.length;i++) {
    for(let j=0;j<arr2.length;j++) {
      result.push([arr1[i], arr2[j]])
    }
  }
  
  return result;  
}

const arr1 = [1, 2];
const arr2 = [3, 4, 5];

console.log(cartesianProduct(arr1, arr2));