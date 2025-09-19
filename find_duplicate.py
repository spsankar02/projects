def find_duplicate(lis):
    freq={}
    for i in lis:
        freq[i] = freq.get(i,0) + 1
    for key, val in freq.items():
        if val > 1:
            return key
    return -1
lis = [1,3,4,2,2]
print(find_duplicate(lis))