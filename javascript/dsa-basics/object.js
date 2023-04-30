/*
  JS objects - time complexity - 
  insert - O(1)
  remove - O(1)
  access - O(1)
  search - O(n)
  both returns the array of keys and values
  Object.keys() = Object.values() = Object.entries = O(n)
*/

const obj = {
  name: 'Hello',
  age: 25,
  "key-three": true,
  sayName() {
    return this.name;
  }
}

// obj.hobby = 'football';
// delete obj.hobby

// console.log(obj.name);
// console.log(obj['age']);
// console.log(obj['key-three']);
console.log(obj?.sayName());
// obj?.sayName();