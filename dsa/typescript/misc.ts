console.log(`9. Given 2 crystal balls, determine the exact spot in which it will break in the most potimized way.`);

/**
* Mathematical Approach:
- Let’s say the total number of floors is (N).
- We’ll drop the first ball at height (H), where (H) is a multiple of some integer (e.g., every 10 floors).
- If the first ball breaks, we’ll use the second ball to search linearly from the previous height to the current height.
- The total number of drops will be minimized when the sum of the drops for both balls is minimized.
- The optimal interval for the first ball is approximately (\sqrt{2N}).
* Example:
- Suppose we have 100 floors.
- We’ll drop the first ball every 10 floors (10, 20, 30, …).
- If it breaks at 30, we’ll use the second ball to check floors 21 to 29.
- If it breaks at 25, we’ll check floors 21 to 24.
- And so on, until we find the exact floor.
*/

function two_crystal_balls(breaks: boolean[]): number {
  const jumpAmount: number = Math.floor(Math.sqrt(breaks.length));
  
  let i: number = jumpAmount;
  for(; i < breaks.length; i += jumpAmount) {
    if(breaks[i]) {
      break;
    }
  }
  i -= jumpAmount;
  
  for(let j = 0; j < jumpAmount && i < breaks.length; ++j, ++i)  {
    if(breaks[i]) {
      return i;
    }
  }
  
  return -1;
}

// Approach 1: Logarithmic Search (Adaptive Interval)
function logarithmicSearch(breaks: boolean[]): number {
  let interval = 1;
  let i = interval;

  while (i < breaks.length) {
    if (breaks[i]) {
      break;
    }
    interval *= 2;
    i += interval;
  }

  // Binary search within the narrowed range
  let left = i - interval / 2;
  let right = Math.min(i, breaks.length - 1);
  while (left <= right) {
    const mid = Math.floor((left + right) / 2);
    if (breaks[mid]) {
      return mid;
    } else {
      left = mid + 1;
    }
  }

  return -1;
}

// Approach 2: Ternary Search (Divide and Conquer)

function ternarySearch(breaks: boolean[]): number {
  let left = 0;
  let right = breaks.length - 1;

  while (left < right) {
    const oneThird = left + Math.floor((right - left) / 3);
    const twoThirds = left + Math.floor((2 * (right - left)) / 3);

    if (breaks[oneThird]) {
      right = oneThird;
    } else if (breaks[twoThirds]) {
      left = oneThird + 1;
      right = twoThirds;
    } else {
      left = twoThirds + 1;
    }
  }

  return breaks[left] ? left : -1;
}

// Approach 3: Dynamic Programming (Teamwork Makes the Magic Work)

function dynamicProgramming(breaks: boolean[]): number {
  const n = breaks.length;
  const dp: number[] = Array(n).fill(Infinity);
  dp[0] = 0;

  for (let i = 1; i < n; i++) {
    for (let j = 0; j < i; j++) {
      if (breaks[i]) {
        dp[i] = Math.min(dp[i], dp[j] + 1);
      }
    }
  }

  return dp[n - 1] === Infinity ? -1 : dp[n - 1];
}


const floors = [false, false, false, false, true, true, true, true];
const result = two_crystal_balls(floors);

console.log('two_crystal_balls => ', result);

const floors1 = [false, false, false, false, true, true, true, true];
const result1 = two_crystal_balls(floors1);
console.log(`Test Case 1: ${result1}`); // Expected output: 4

const floors2 = [false, false, false, true, true, true, true, true];
const result2 = two_crystal_balls(floors2);
console.log(`Test Case 2: ${result2}`); // Expected output: 3

const floors3 = [false, false, true, true, true, true, true, true];
const result3 = two_crystal_balls(floors3);
console.log(`Test Case 3: ${result3}`); // Expected output: 2

const floors4 = [false, true, true, true, true, true, true, true];
const result4 = two_crystal_balls(floors4);
console.log(`Test Case 4: ${result4}`); // Expected output: 1

const floors5 = [true, true, true, true, true, true, true, true];
const result5 = two_crystal_balls(floors5);
console.log(`Test Case 5: ${result5}`); // Expected output: 0

const floors6 = [false, false, false, false, false, false, false, false];
const result6 = two_crystal_balls(floors6);
console.log(`Test Case 6: ${result6}`); // Expected output: -1

const floors7 = [false, false, false, false, false, false, true, true];
const result7 = two_crystal_balls(floors7);
console.log(`Test Case 7: ${result7}`); // Expected output: 6

const floors8 = [false, false, false, false, false, false, false, true];
const result8 = two_crystal_balls(floors8);
console.log(`two_crystal_balls => Test Case 8: ${result8}`); // Expected output: 7