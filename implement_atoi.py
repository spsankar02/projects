def implement_atoi(s):
    ans = []
    for i in s:
        if i == ' ' or i == '-' or i == '+' or (i >= 'A' and i <= 'Z') or (i >= 'a' and i <= 'z'):
            break
        ans.append(i)
    if ans:
        return int("".join(ans))
    else:
        return 0
s = '1337c0d3'
print(implement_atoi(s))