def sort_characters(s):
    a = list(s)
    freq ={}
    uni_ans =[]
    for i in a:
        freq[i] = freq.get(i,0) + 1
    for i in a:
        if i not in uni_ans:
            uni_ans.append(i)
    # uni_ans = list(freq.keys())  # unique characters


    for i in range(len(uni_ans)-1):
        for j in range(i+1,len(uni_ans)):
            if (freq[uni_ans[i]] < freq[uni_ans[j]] or
            (freq[uni_ans[i]] == freq[uni_ans[j]] and uni_ans[i] > uni_ans[j])):
                uni_ans[i],uni_ans[j] = uni_ans[j],uni_ans[i]
    return uni_ans
s = "raaajj"
print(sort_characters(s))