class UnionOfTwoArrays:
    def solution1(arr1,arr2):
        hash = {}
        list = []
        for num in arr1:
            hash[num] = hash.get(num,0) + 1
        for num in arr2:
            hash[num] = hash.get(num,0) + 1
        for key,value in hash.items():
            list.append(key)
        return list

    def solution2(arr1,arr2):
        sets = set()
        list = []
        for num in arr1:
            sets.add(num)
        for num in arr2:
            sets.add(num)
        for num in sets:
            list.append(num)
        return list

    def solution3(arr1,arr2):
        return -1
arr1 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
arr2 = [2, 3, 4, 4, 5, 11, 12]
obj1 = UnionOfTwoArrays.solution1(arr1,arr2)
obj2 = UnionOfTwoArrays.solution2(arr1,arr2)
obj3 = UnionOfTwoArrays.solution3(arr1,arr2)
print("solution 1 ans: ",obj1)
print("solution 2 ans: ",obj2)
print("solution 3 ans: ",obj3)