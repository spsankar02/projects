# brute force
# def rearrange_array(lis):
#     pos = []
#     neg = []
#     for i in range(len(lis)):
#         if lis[i] > 0:
#             pos.append(lis[i])
#         else:
#             neg.append(lis[i])
#     for i in range(len(pos)):
#         lis[2*i] = pos[i]
#     for i in range(len(neg)):
#         lis[2*i+1] = neg[i]
#     return lis

# optimal

def rearrange_array(lis):
    n = len(lis)
    ans = [0] * n
    posIndex, negIndex = 0,1
    for i in range(n):
        if lis[i] < 0:
            ans[negIndex] = lis[i]
            negIndex += 2
        else:
            ans[posIndex] = lis[i]
            posIndex += 2
    return ans
lis = [1,2,-3,-1,-2,3]
print(rearrange_array(lis))