/*
  Hash Table Implementation, methods - with hashing collisions
  set to store a key-value pair
  get to retrieve a value given its key
  remove to delete a key value pair
  hashing function to convert a string key to a numeric index
  --> increasing the size of the array is not the best soln to handle collisions, there maybe a possibility of data loss
  --> whenever the hash tbale reaches half the capacity or more the array capacity is doubled, and the key, vlaue pairs are rehashed,
  average case is constant 
*/

class HashTable {
  constructor(size) {
    this.table = new Array(size);
    this.size = size;
  }
  
  hash(key) {
    // will be used by set, get and remove methods
    let total = 0;
    for(let i = 0; i < key.length; i++) {
      total += key.charCodeAt(i);
    }
    // to makse sure index is in the bounds of the array
    return total % this.size;
  }
  
  set(key, value) {
    const index = this.hash(key);
    // this.table[index] = value; // commented for hasing collision tutorial
    const bucket = this.table[index];
    if (!bucket) {
      this.table[index] = [[key, value]];
    } else {
      const sameKeyItem = bucket.find(item => item[0] === key)
      if (sameKeyItem) {
        sameKeyItem[1] = value;
      } else {
        bucket.push([key, value]);
      }
    }
  }
  
  get(key) {
    const index = this.hash(key);
    // return this.table[index]; 
    // storing reference to that index position
    const bucket = this.table[index];
    if (bucket) {
      const sameKeyItem = bucket.find(item => item[0] === key)
      if (sameKeyItem) {
        return sameKeyItem[1];
      }
    }
    return undefined;
  }
  
  
  remove(key) {
    const index = this.hash(key);
    // this.table[index] = undefined;
    const bucket = this.table[index];
    if (bucket) {
      const sameKeyItem = bucket.find(item => item[0] === key)
      if (sameKeyItem) {
        bucket.splice(bucket.indexOf(sameKeyItem), 1)
      }
    }
  }
  
  display() {
    for(let i = 0; i < this.table.length; i++) {
      if(this.table[i]) {
        console.log(i , this.table[i]);
      }
    }
  }
}

const table = new HashTable(50);

table.set("name", "Bruce");
table.set("age", 25);
table.display();

console.log(table.get("name"));
table.set("mane", "Clark");
table.set("name", "Diana");
table.remove("name");
table.display();