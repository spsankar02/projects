class ReverseWords:
    def reverse(s):
        lis = list(s)
        print('lis',lis)
        if lis[0] == " ":
            lis.pop(0)
        s = "".join(lis)
        print('s',s)
        out = s.split(" ")
        outt = out[::-1]
        return ' '.join(outt)
string = "this is an amazing program"
obj = ReverseWords.reverse(string)
print("out: ",obj)