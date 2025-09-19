def rotate_string(a,g):
    # my try
    # l = list(a)
    # for i in range(len(a)):
    #     temp = l[0]
    #     for j in range(len(a)-1):
    #         l[j] = l[j+1]
    #     l[len(a)-1] = temp
    #     res = "".join(l)
    #     print('res',res)
    #     if res == g:
    #         return True
    # return False

    # optimized sol 1
    # if len(a) != len(g):
    #     return False
    # l = list(a)
    # for i in range(len(a)):
    #     temp = l.pop(0)
    #     l.append(temp)
    #     res = "".join(l)
    #     if res == g:
    #         return True
    # return False

    # optimized sol 2
    return len(a) == len(g) and g in (a+a)
a = "abcde"
g = "abcde"
print(rotate_string(a,g))