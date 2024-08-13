console.log('7. sumOfDigitsRecursion');

function sumOfDigitsRecursion(num: number): number {
  if(typeof num !== 'number') throw new Error("Provide a valid number!");
  if(num <= 1) return num;
  return (num % 10) + sumOfDigitsRecursion(Math.floor(num / 10));
}

console.log("sumOfDigitsRecursion => ", sumOfDigitsRecursion(9987));

console.log('8. given a rope of length n, we need to find maximum number of pieces you can make such that length of every piece is in set {a, b, c} for given three values of a, b, c');

// function maxPieces(n: number, a: number, b: number, c: number): number {
//   if(typeof n !== 'number' || typeof a !== 'number' || typeof b !== 'number' || typeof c !== 'number') {
//     throw new Error("All parameters must be number!");
//   }
  
//   if(n === 0) return 0;
//   if(n < 0) return -1;
  
//   let res: number = Math.max(maxPieces(n - a, a, b, c), maxPieces(n - b, a, b, c), maxPieces(n - c, a, b, c));
  
//   if(res === -1) return -1;
//   return res + 1;
// }

// using dynamic programming
function maxPieces(n: number, a: number, b: number, c: number): number {
    // Initialize the dp array with -1
    const dp: number[] = new Array(n + 1).fill(-1);
    dp[0] = 0; // Base case: 0 length rope can have 0 pieces

    // Iterate through the lengths from 1 to n
    for (let i = 1; i <= n; i++) {
        if (i >= a && dp[i - a] !== -1) {
            dp[i] = Math.max(dp[i], dp[i - a] + 1);
        }
        if (i >= b && dp[i - b] !== -1) {
            dp[i] = Math.max(dp[i], dp[i - b] + 1);
        }
        if (i >= c && dp[i - c] !== -1) {
            dp[i] = Math.max(dp[i], dp[i - c] + 1);
        }
    }

    // The result will be in dp[n]
    return dp[n] === -1 ? 0 : dp[n];
}

console.log(`maxPieces => ${maxPieces(23, 11, 9, 12)}`);

console.log('9. given a string, print all subsets of it(in any order)');

function printSub(str: string, current: string = "", index: number = 0): void {
  if(index === str.length) {
    console.log(current);
    return;
  }
  printSub(str, current, index + 1);
  printSub(str, current + str[index], index + 1);
}

printSub("ABC");

console.log('9. towerOfHanoi');

function towerOfHanoi(n: number, A: string, B: string, C: string): void {
  if(n === 1) {
    console.log(`Move 1 from ${A} to ${C}\n`);
    return;
  }
  
  towerOfHanoi(n - 1, A, C, B);
  console.log(`Move ${n} from ${A} to ${C}\n`);
  towerOfHanoi(n - 1, B, A, C);
  
}

towerOfHanoi(3, 'A', 'B', 'C');

