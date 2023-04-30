/*
  Climbing Staricase - given a staircase of 'n' steeps, count the number of distinct ways to climb to the top. we can either climb 1 step or 2 steps at a time.
  if, we have to climb to step 'n', we can only climb from step 'n-1' or 'n-2. calculate the ways we can climb to 'n-1' and 'n-2' steps and add the 2.
  climbingStaircase(n) = climbingStaircase(n-1) + climbingStaircase(n-2);
  
  time complexity - O(n)
*/

function climbingStaircase(num) {
  const numOfWays = [1, 2];
  for(let i=2;i<=num;i++) {
    numOfWays[i] = numOfWays[i-1] + numOfWays[i-2]
  }
  
  return numOfWays[num - 1];
}



console.log(climbingStaircase(1));
console.log(climbingStaircase(2));
console.log(climbingStaircase(3));
console.log(climbingStaircase(4));
console.log(climbingStaircase(5));