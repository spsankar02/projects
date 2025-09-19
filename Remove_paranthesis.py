class RemoveOuterParentheses:
    @staticmethod
    def remove(s):
        res = []
        depth = 0
        for char in s:
            if char == '(':
                if depth > 0:
                    res.append(char)
                depth += 1
            elif char == ')':
                depth -= 1
                if depth > 0:
                    res.append(char)
        return ''.join(res)

s = "()(()())(())"
ans = RemoveOuterParentheses.remove(s)
print('ans:', ans)