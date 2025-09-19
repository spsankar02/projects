def printLeadersBruteForce(arr, n):
    ans = []
    for i in range(n):
        leader = True
        for j in range(i+1,n):
            if arr[j] > arr[i]:
                leader = False
                break
        if leader:
            ans.append(arr[i])
    return ans

def printLeadersOptimal(arr, n):
    ans = []
    max = arr[n-1]
    ans.append(max)
    for i in range(n-2,-1,-1):
        if arr[i] > max:
            ans.append(arr[i])
            max = arr[i]
    return ans

if __name__ == '__main__':
    arr = [10, 22, 12, 3, 0, 6]
    n = len(arr)
    ans = printLeadersBruteForce(arr, n)
    for i in range(len(ans)):
        print(ans[i], end=" ")
    print()
    ans1 = printLeadersOptimal(arr, n)
    for i in range(len(ans1)-1,-1,-1):
        print(ans1[i], end=" ")
    print()