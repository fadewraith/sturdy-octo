window.onload = function() {
    setInterval(function() {
        var button = document.querySelector('.checkInBtn.white-space');
        if(button) {
            button.click();
        }
    }, 500);
}
window.onload();

console.log(
  "#################################################################"
);

function deepClone(obj) {
  // If the object is null, return null
  if (obj === null) return null;
  // Create an empty object to store the clone
  let clone = {};
  // Loop through the keys of the original object
  for (let key of Object.keys(obj)) {
    // Check if the value is an object
    if (typeof obj[key] === "object") {
      // If yes, recursively clone the value and assign it to the clone
      clone[key] = deepClone(obj[key]);
    } else {
      // If not, simply assign the value to the clone
      clone[key] = obj[key];
    }
  }
  // If the original object is an array, convert the clone to an array
  if (Array.isArray(obj)) {
    clone.length = obj.length;
    clone = Array.from(clone);
  }
  // Return the clone object
  return clone;
}

const a_1 = { foo: "bar", obj: { a: 1, b: 2 } };
const b_1 = deepClone(a_1);

console.log(a_1 === b_1);
console.log(a_1.obj !== b_1.obj);

console.log(
  "#################################################################"
);

const myObj = {
  a: 1,
  b: "hello",
  c: [0, 1, 2],
  d: { e: 1, f: { g: "h", h: "1233", i: [1, 2, 3] } },
};

const deepFreeze = (obj) => {
  Object.keys(obj).forEach((prop) => {
    if (typeof obj[prop] === "object" && !Object.isFrozen(obj[prop]))
      deepFreeze(obj[prop]);
  });
  return Object.freeze(obj);
};
deepFreeze(myObj);

myObj.a = 10;
myObj.b = "hi";
myObj.c[1] = 4;
myObj.d.e = 0;
myObj.d.f.i[3] = "hello";

console.log(
  "#################################################################"
);

const hasUndefinedProperty = (objj, prop) =>
  objj.hasOwnProperty(prop) && objj[prop] === undefined;

const obj_3 = { prop: undefined };
hasUndefinedProperty(obj_3, "prop"); // true
hasUndefinedProperty(obj_3, "porp"); // false

console.log(
  "#################################################################"
);

const pick = (obj, arr) =>
  Object.fromEntries(Object.entries(obj).filter(([k]) => arr.includes(k)));

const omit = (obj, arr) =>
  Object.fromEntries(Object.entries(obj).filter(([k]) => !arr.includes(k)));

const obj_4 = { a: 1, b: "2", c: 3 };

pick(obj_4, ["a", "c"]); // { 'a': 1, 'c': 3 }
omit(obj_4, ["b"]); // { 'a': 1, 'c': 3 }

console.log(
  "#################################################################"
);

const pickBy = (obj, fn) =>
  Object.fromEntries(Object.entries(obj).filter(([k, v]) => fn(v, k)));

const omitBy = (obj, fn) =>
  Object.fromEntries(Object.entries(obj).filter(([k, v]) => !fn(v, k)));

const obj_5 = { a: 1, b: "2", c: 3 };

pickBy(obj_5, (x) => typeof x === "number"); // { a: 1, c: 3 }
omitBy(obj_5, (x) => typeof x !== "number"); // { a: 1, c: 3 }
omitBy(obj_5, (x) => typeof x === "number"); // { b: 2 }

console.log(
  "#################################################################"
);

const arr = [
  { id: 1, name: "Tom" },
  { id: 1, name: "Tom" },
  { id: 1, name: "Alice" },
  { id: 2, name: "Nick" },
  { id: 2, name: "James" },
];

const unique_arr = new Set(arr);
console.log([...unique_arr]);

console.log(
  "#################################################################"
);

function show() {
  console.log("show function");
}

show``;
new show(); // logs show function

const test_obj = {
  show_num: 1,
  get showFn() {
    console.log("show function");
  },
};

test_obj.showFn;

console.log(
  "#################################################################"
);

function isNumber(value) {
  const num = Number(value);
  const isTypeNumber = typeof num === "number";
  const isFiniteNumber = Number.isFinite(num);
  return isTypeNumber && isFiniteNumber;
}

const isNum = isNumber("NaN");
console.log("isNum -> ", isNum);

console.log(
  "#################################################################"
);

/*
  // to show timer
  timeLeft: number = 10;
  time_interval: any;
  displayTimer: any;

  startTimer() {
    this.time_interval = setInterval(() => {
      if(this.timeLeft !== 0) {
        this.timeLeft--;
      }

      if(this.timeLeft < 10) {
        this.displayTimer = '0' + this.timeLeft;
      } else {
        this.displayTimer = this.timeLeft;
      }

      if(this.timeLeft === 0) {
        this.displayTimer = '';
        this.clearTimer();
      }
    }, 1000);
  }

  clearTimer() {
    clearInterval(this.time_interval);
  }
  // displayTimer - use this to display timer
*/

console.log(
  "#################################################################"
);

// prime factor of a number
const primeFactors = (n) => {
  let a = [],
    f = 2;
  while (n > 1) {
    if (n % f === 0) {
      a.push(f);
      n /= f;
    } else {
      f++;
    }
  }
  return a;
};

primeFactors(1397);

console.log(
  "#################################################################"
);

function isNil(t) {
  return (t == null) || (t == undefined);
}

console.log(isNil(0));

console.log(
  "#################################################################"
);

function defangIPaddr(address) {
    return address.replace(/\./g, '[.]');
}

console.log(defangIPaddr('192.168.1.1'));

const ip = '192.168.0.1';
console.log(ip.split(".").join("[.]"));

const input = '192.168.0.1';
console.log(input.replaceAll(`.`, `[.]`));

console.log(
  "#################################################################"
);

function isObjectLike(value) {
	return Object.prototype.toString.call(value) === "[object Object]";
}

console.log(isObjectLike({})); // true
console.log(isObjectLike([])); // false
console.log(isObjectLike(null)); // false
console.log(isObjectLike("string")); // false

console.log(
  "#################################################################"
);

let find_max_num = [10, 9, 8, 7, 6, 99];

let a = [1, 2, 3, 4, 5, 6, 7, 8, 9];

let max = Number.NEGATIVE_INFINITY;

function maxNum(arr) {
  if(find_max_num.length === 0) return max;
  if(find_max_num.length === 1 && find_max_num[0] > max) return find_max_num[0];
  for(let x of arr) {
    if(x > max) max = x;
  }
  return max;
}

function isArraySorted(arr) {
  for(let i = 0; i < arr.length - 1; i++) {
    if(arr[i] > arr[i+1]) return false;
  }
  return true;
}

console.log(maxNum(find_max_num));
console.log(isArraySorted(a));

console.log(
  "#################################################################"
);


let thousands = ['', 'thousand', 'lakh', 'crore'];
let digits = ['zero', 'one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine'];
let tens = ['ten', 'eleven', 'twelve', 'thirteen', 'fourteen', 'fifteen', 'sixteen', 'seventeen', 'eighteen', 'nineteen'];
let twenties = ['twenty', 'thirty', 'forty', 'fifty', 'sixty', 'seventy', 'eighty', 'ninety'];
 
function convertNumberToWords(number) {
    if (number === undefined || number === null) return 'invalid input';
 
    number = number.toString().replace(/[\, ]/g, '');
    if (isNaN(number)) return 'invalid input';
 
    let decimalIndex = number.indexOf('.');
    let fractionPart = decimalIndex !== -1 ? number.slice(decimalIndex + 1) : '';
    let wholePart = decimalIndex !== -1 ? number.slice(0, decimalIndex) : number;
    if (wholePart.length > 15) return 'too big';
 
    let word = convertWholePartToWords(wholePart) + ' rupees';
 
    if (fractionPart) {
        let fractionWords = convertFractionPartToWords(fractionPart);
        word += ' and ' + fractionWords + ' paisa';
    } else {
        word += ' only';
    }
 
    return word.replace(/\s+/g, ' ').trim();
}
 
function convertWholePartToWords(num) {
    let word = '';
    let unitIndex = 0;
 
    while (num.length > 0) {
        let chunkSize;
        if (unitIndex === 1 || unitIndex === 2) { // handle thousands and lakhs
            chunkSize = 2;
        } else if (unitIndex === 3) { // handle crores
            chunkSize = 2;
        } else { // handle the hundreds place
            chunkSize = 3;
        }
 
        let chunk = num.slice(-chunkSize);
        num = num.slice(0, -chunkSize);
        
        let chunkNumber = parseInt(chunk, 10);
        if (chunkNumber > 0) {
            if (unitIndex === 0) {
                word = getWordsForChunk(chunkNumber) + ' ' + word;
            } else {
                word = getWordsForChunk(chunkNumber) + ' ' + thousands[unitIndex] + ' ' + word;
            }
        }
        unitIndex++;
    }
 
    return word.trim();
}
 
function convertFractionPartToWords(num) {
    let fractionNumber = parseInt(num, 10);
    if (fractionNumber === 0) return '';
    return getWordsForChunk(fractionNumber);
}
 
function getWordsForChunk(chunkNumber) {
    let chunkWord = '';
    if (chunkNumber >= 100) {
        chunkWord += digits[Math.floor(chunkNumber / 100)] + ' hundred ';
        chunkNumber = chunkNumber % 100;
    }
    if (chunkNumber >= 20) {
        chunkWord += twenties[Math.floor(chunkNumber / 10) - 2] + ' ';
        chunkNumber = chunkNumber % 10;
    }
    if (chunkNumber > 0 && chunkNumber < 10) {
        chunkWord += digits[chunkNumber] + ' ';
    } else if (chunkNumber >= 10) {
        chunkWord += tens[chunkNumber - 10] + ' ';
    }
    return chunkWord.trim();
}
 
console.log(convertNumberToWords(4050000)); // "forty lakh fifty thousand rupees only"
console.log(convertNumberToWords(4050000.12)); // "forty lakh fifty thousand rupees and twelve paisa"

console.log(
  "#################################################################"
);

let yearOrMonthData = [];
let date = new Date();

for (let i = 7; i >= 0; i--) { // to print n months back from the current month
// for (let i = 0; i <= 20; i++) { // to print n months forward from the current month
  
  // to print n months back from the current month
    let lastSixMonth = new Date(
        date.getFullYear(),
        date.getMonth() - i,
        1
    );
  
  // to print n months forward from the current month
  // let lastSixMonth = new Date(
  //       date.getFullYear(),
  //       date.getMonth() + i,
  //       1
  //   );

    // Convert the month number to its full name
    let monthName = lastSixMonth.toLocaleString("default", { month: "long" });

    // Store the month name and year, and the numeric month and year in the array
  // to print n months back from the current month
    yearOrMonthData.unshift({
        name: `${monthName}, ${lastSixMonth.getFullYear()}`,
        monthOrYear: `${lastSixMonth.getMonth() + 1}::${lastSixMonth.getFullYear()}`
    });
  
  // to print n months forward from the current month
  // yearOrMonthData.push({
  //       name: `${monthName}, ${lastSixMonth.getFullYear()}`,
  //       monthOrYear: `${lastSixMonth.getMonth() + 1}::${lastSixMonth.getFullYear()}`
  //   });
}

console.log(yearOrMonthData);

console.log(
  "#################################################################"
);

// to format the data for displaying in graph, both for month on month and year on year
function formatData(data, key){
    const fields = [
        "Pre Sub Rule Premium",
        "Post Sub Rule Premium",
        "Premium Amount",
        "War Strike Premium",
        "Overage Premium",
        "Additional Premium"
    ];

    const result = fields.map(field => ({
        name: field,
        series: [] as { value: number, name: string }[]
    }));

    const suffixes = {
        year: ["startYear", "middleYear", "endYear"],
        month: ["startMonth", "middleMonth", "endMonth"]
    };

    const dataKeys: { [key: string]: string } = {
        "Pre Sub Rule Premium": "PreSubRulePremiumPercentage",
        "Post Sub Rule Premium": "PostSubRulePremiumPercentage",
        "Premium Amount": "PremiumAmountPercentage",
        "War Strike Premium": "WarStrikePremiumPercentage",
        "Overage Premium": "OveragePremiumPercentage",
        "Additional Premium": "AdditionalPremiumPercentage"
    };

    // const monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

    suffixes[key].forEach(suffix => {
        if (key === "year") {
            const yearValue = data[0][suffix];
            if (yearValue) {
                result.forEach(entry => {
                    const fieldKey = suffix + dataKeys[entry.name];
                    entry.series.push({
                        value: data[0][fieldKey] !== null && data[0][fieldKey] !== undefined ? data[0][fieldKey] : 0,
                        name: yearValue.toString()
                    });
                });
            }
        } else if (key === "month") {
            const yearSuffix = suffix.replace("Month", "Year");
            const yearValue = data[0][yearSuffix];
            const monthValue = data[0][suffix];
            if (yearValue && monthValue !== null) {
                // const monthName = monthNames[monthValue - 1];
                const monthName = this.getMonthName(monthValue - 1);
                const fullDate = `${monthName}, ${yearValue}`;
                result.forEach(entry => {
                    const fieldKey = suffix + dataKeys[entry.name];
                    entry.series.push({
                        value: data[0][fieldKey] !== null && data[0][fieldKey] !== undefined ? data[0][fieldKey] : 0,
                        name: fullDate
                    });
                });
            }
        }
    });

    // Clean up any empty series
    result.forEach(entry => {
        entry.series = entry.series.filter(seriesItem => seriesItem.name);
    });

    return result;
}


// Example usage:
// console.log("yearData => ", formatData(data, "year"));
// console.log("monthData => ", formatData(data, "month"));

console.log(
  "#################################################################"
);


function isAlphaNumeric(char) {
  let code = char.charCodeAt(0);
  if(!(code > 47 && code < 58) && // numeric [0-9]
     !(code > 64 && code < 91) && // upper [A-Z]
     !(code > 96 && code < 123)) { // lower [a-z]
     return false;
  }
  return true;
}

console.log(
  "#################################################################"
);

// compare 2 arrays, if the second array contains the square of the first array
function isArraySame(a1, a2) {
  if(a1.length !== a2.length) {
    return false;
  }
  
  // bruteforce approach
  // for(let i = 0; i < a1.length; i++) {
  //   const isSquareIndex = a2.indexOf(a1[i] ** 2);
  //   if(isSquareIndex === -1) {
  //     return false;
  //   }
  //   a2.splice(isSquareIndex, 1);
  // }
  
  let f1 = {};
  let f2 = {};
  
  for(let v of a1) {
    f1[v] = (f1[v] || 0) + 1;
  }
  
  for(let v of a2) {
    f2[v] = (f2[v] || 0) + 1;
  }

  for(let k in f1) {
    // squaring and checking whether the key exists in f2 or not
    if(!(k ** 2 in f2)) {
      return false;
    }
    
    // occurrences should be same in both the objects, otherwise arrays are not equal
    if(f2[k ** 2] !== f1[k]) {
      return false;
    }
  }
  
  return true;
}

a1 = [1, 3, 5];
a2 = [25, 9, 1];
console.log(isArraySame(a1, a2));

console.log(
  "#################################################################"
);

function validAnagram(s1, s2) {
  if(s1.length !== s2.length) {
    return false;
  }
  
  const lookup = {};
  
  for(let i = 0; i < s1.length; i++) {
    let letter = s1[i];
    lookup[letter] ? lookup[letter] += 1 : lookup[letter] = 1;
  }
  
  console.log(lookup);
  
  for(let i = 0; i < s2.length; i++) {
    let letter = s2[i];
    if(!lookup[letter]) {
      return false;
    } else {
      lookup[letter] -= 1;
    }
  }
  console.log(lookup);
  return true;
}
const s1 = "madam1";
const s2 = "aammda";

console.log(validAnagram(s1, s2));

console.log(
  "#################################################################"
);

function sumZero(arr) {
  let left = 0;
  let right = arr.length - 1;
  while(left < right) {
    let sum = arr[left] + arr[right];
    if(sum === 0) {
      return [arr[left], arr[right]];
    } else if (sum > 0) {
      right--;
    } else {
      left++;
    }
  }
}

console.log(sumZero([-4, -3, -2, -1, 0, 1, 2, 3, 10, 13, 15, 20]));

console.log(
  "#################################################################"
);

function countUniqueValues(arr) {
  if(arr.length === 0 || arr.length === 1) return arr;
  const obj = {};
  
  for(let v of arr) {
    if(!obj[v]) {
      obj[v] = 1;
    } else {
      obj[v]++;
    }
  }
  
  const uniqueArray = [];
  let j = 0;
  for(let k in obj) {
    if(obj[k] === 1) {
      // uniqueArray.push(Number(k));
      uniqueArray[j++] = Number(k);
    }
  }
  return uniqueArray;
}

console.log(countUniqueValues([15, 3, 2, 1, 0, 1, 2, 3, 10, 13, 15, 20]));

console.log(
  "#################################################################"
);

function maxSubArraySum(arr, num) {
  if(num > arr.length) {
    return null;
  }
  
  let max = -Infinity;
  for(let i = 0; i < arr.length - num + 1; i++) {
    let temp = 0;
    for(let j = 0; j < num; j++) {
      temp += arr[i + j];
    }
    if(temp > max) {
      max = temp;
    }
  }
  return max;  
}

function maxSubArraySumUsingSlidingWindow(arr, num) {
  let maxSum = 0;
  let tempSum = 0;
  
  if(arr.length < num) {
    return null;
  }
  
  for(let i = 0; i < num; i++) {
    maxSum += arr[i];
  }
  tempSum = maxSum;
  
  for(let i = num; i < arr.length; i++) {
    tempSum = tempSum - arr[i - num] + arr[i];
    maxSum = Math.max(maxSum, tempSum);
  }
  return maxSum;
}

// console.log(maxSubArraySumUsingSlidingWindow([2, 6, 9, 2, 1, 8, 5, 6, 3], 3));
console.log(maxSubArraySumUsingSlidingWindow([2, 6, 9, 2, 1, 8, 5, 6, 3], 3));

console.log(
  "#################################################################"
);

function naiveSearch(long, short) {
  let count = 0;
  for(let i = 0; i < long.length; i++)   {
    for(let j = 0; j < short.length; j++) {
      if(short[j] !== long[i + j]) break;
      // means, if s2 = 'lol', j = 2, and j.length - 1 = 2, then we found the string and increment count
      if(j === short.length - 1) {
        count++;
      }
    }
  }
  return count;
}

console.log(naiveSearch("lorie lolled lol", "lol"));

console.log(
  "#################################################################"
);

// radix sort
function getDigit(num, i) {
  return Math.floor(Math.abs(num) / Math.pow(10, i)) % 10;
}

function digitCount(num) {
  if (num === 0) return 1;
  return Math.floor(Math.log10(Math.abs(num))) + 1;
}

function mostDigits(nums) {
  let maxDigits = 0;
  for (let i = 0; i < nums.length; i++) {
    maxDigits = Math.max(maxDigits, digitCount(nums[i]));
  }
  return maxDigits;
}

mostDigits([23,567,89,12234324,90])

function radixSort(nums){
    let maxDigitCount = mostDigits(nums);
    for(let k = 0; k < maxDigitCount; k++){
        let digitBuckets = Array.from({length: 10}, () => []);
        for(let i = 0; i < nums.length; i++){
            let digit = getDigit(nums[i],k);
            digitBuckets[digit].push(nums[i]);
        }
        nums = [].concat(...digitBuckets);
    }
    return nums;
}

radixSort([23,345,5467,12,2345,9852])

console.log(
  "#################################################################"
);