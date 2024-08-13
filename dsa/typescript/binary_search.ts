const a = new ArrayBuffer(6);

const a8 = new Uint8Array(a);
a8[0] = 45;
a8[2] = 45;
console.log(a8);

const a16 = new Uint16Array(a);

a16[2] = 0x4545;
console.log(a16);

a16[2] = 0x45;
console.log(a16);

function binary_search(haystack: number[], needle: number): boolean {
  
  if(haystack.length === 0 || haystack === null || haystack === undefined) {
    throw new Error("Array is empty!");
  }
  
  if(haystack.length === 1) {
    throw new Error("Array must contain atleast 2 numbers");
  }
  
  let lo: number = 0;
  let hi: number = haystack.length;
  do {
    const mid: number = Math.floor(lo + (hi - lo) / 2);
    const val: number = haystack[mid];
    
    if(val === needle) {
      return true;
    } else if(val > needle) {
      hi = mid;
    } else {
      lo = mid + 1;
    }
  } while(lo < hi);
  
  return false;
}

const arr: number[] = [1, 2, 3, 4, 5, 6];
console.log(binary_search(arr, 5));