# in built 
# from collections import Counter
# def are_anagrams(s1: str, s2: str) -> bool:
#     # Quick length check to avoid unnecessary work
#     if len(s1) != len(s2):
#         return False
#     return Counter(s1) == Counter(s2)

def anagram(a,b):
    if len(a) != len(b):
        return False
    freq ={}
    for ch in a:
        freq[ch] = freq.get(ch,0) + 1
    for ch in b:
        if ch not in freq:
            return False
        freq[ch] -= 1
        print('fer',freq)
        if freq[ch] < 0:
            return False
    return True
a = "CAT"
b = "ACT"
print(anagram(a,b))