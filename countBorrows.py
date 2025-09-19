def countBorrows(number1,number2):
    if number1 < number2:
        return "not possible"
    borrow = 0
    carry = 0
    n1 = str(number1)[::-1]
    n2 = str(number2)[::-1]
    length = max(len(n1),len(n2))
    n1 = n1.ljust(length,"0")
    n2 = n2.ljust(length,"0")
    for i in range(length):
        d1 = int(n1[i]) - carry
        d2 = int(n2[i])
        if d1 < d2:
            borrow += 1
            d1 += 10
            carry = 1
        else:
            carry = 0
    return borrow
print(countBorrows(754,666))