def max_nesting_depth(s):
    current_depth = 0
    max_depth = 0
    for i in s:
        if i == '(':
            current_depth += 1
            max_depth = max(max_depth,current_depth)
        elif i == ')':
            current_depth -= 1
    return max_depth
s = "(1)+((2))+(((3)))"
print(max_nesting_depth(s))