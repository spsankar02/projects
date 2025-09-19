def longest_odd(s):
    for i in range(len(s)-1,-1,-1):
        if int(s[i]) % 2 == 1:
            result = s[:i+1].lstrip("0")
            return result if result else "0"
    return ""
s = "0214638"
print(longest_odd(s))