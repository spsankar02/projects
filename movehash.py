def movehash(s):
    x = s.count("#")
    s = s.replace("#","")
    return ("#"*x+s)

s = input()
print(movehash(s))