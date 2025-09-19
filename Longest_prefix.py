class LongestCommonPrefix:
    def prefixFind(str):
        first = str[0]
        for i in range(len(first)):
            char = first[i]
            for s in str[1:]:
                if i >= len(s) or s[i] != char:
                    return first[:i]
        return first
string = ["flowers", "flow", "fly", "flight"]
obj = LongestCommonPrefix.prefixFind(string)
print('ans: ',obj)